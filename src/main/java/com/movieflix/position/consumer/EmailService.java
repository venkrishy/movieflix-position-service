package com.movieflix.position.consumer;

import lombok.extern.slf4j.Slf4j;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Email;

@Service
@Slf4j
public class EmailService {
    public static void main(String[] args) throws MailjetException {
        sendEmail();
    }
    public static void sendEmail() throws MailjetException {
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        client = new MailjetClient("d68f2898ddfa6599ea7cf76755d9cb14", "8c0ded1499645b91f8cd304f4d851d05");
        request = new MailjetRequest(Email.resource)
                .property(Email.FROMEMAIL, "venkrishy@mailjet.com")
                .property(Email.FROMNAME, "Mailjet Pilot")
                .property(Email.SUBJECT, "Your email flight plan!")
                .property(Email.TEXTPART, "Dear passenger, welcome to Mailjet! May the delivery force be with you!")
                .property(Email.HTMLPART, "<h3>Dear passenger, welcome to Mailjet!</h3><br />May the delivery force be with you!")
                .property(Email.RECIPIENTS, new JSONArray()
                        .put(new JSONObject()
                                .put("Email", "venkrishy@gmail.com")));
        response = client.post(request);
        System.out.println(response.getStatus());
        System.out.println(response.getData());
    }
}
