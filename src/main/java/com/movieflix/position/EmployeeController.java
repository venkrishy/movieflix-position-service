package com.movieflix.position;


import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movieflix.position.producer.Sender;

@RestController
@Slf4j
public class EmployeeController {

    @Autowired
    Sender sender;

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    InMemoryDatabase inMemoryDatabase;

    @GetMapping("/employee/produce")
    public Map<String, Object> produce() {
        Iterable<Employees> employees = employeeRepo.findAll();
        Map<String, Object> ret = new HashMap<String, Object>();
        for(Employees employee : employees) {
            //Get employee from db
            sender.sendToEmployeeTopic("" + employee.getEmpNo(), employee);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
        ret.put("comment", "Employees added loading from mysql");
        ret.put("employees", employees);
        return ret;
    }

    @GetMapping("/employee/list")
    public Map<String, Object> list() {
        List<Employees> employees = inMemoryDatabase.getEmployees();
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("employees", employees);
        return ret;
    }
}
