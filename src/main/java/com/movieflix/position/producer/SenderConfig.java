package com.movieflix.position.producer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.movieflix.position.Position;

@Configuration
public class SenderConfig {

    @Value("${kafka.bootstrap-servers:localhost}")
    private String bootstrapServers;

    @Value("${kafka.username}")
    private String username;

    @Value("${kafka.password}")
    private String password;

    @Value("${currentProfile}")
    private String currentProfile;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        // list of host:port pairs used for establishing the initial connections to the Kakfa cluster
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
        String jaasCfg = String.format(jaasTemplate, username, password);

        if (!currentProfile.equals("local")) {
            props.put("security.protocol", "SASL_SSL");
            props.put("sasl.mechanism", "SCRAM-SHA-256");
            props.put("sasl.jaas.config", jaasCfg);
        }
        return props;
    }

//    @Bean
//    @ConfigurationProperties("orderslotting.kafka")
//    public KafkaProperties producerFactory() {
//        return new KafkaProperties();
//    }


    @Bean
    public ProducerFactory<String, Position> producerFactory() {
        //producerFactory().buildProducerProperties if using Spring Framework's Kafka Properties d
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Position> kafkaTemplate() {
        KafkaTemplate<String, Position> kafkaTemplate = new KafkaTemplate<>(producerFactory());
        //logs exceptions thrown when sending messages
        kafkaTemplate.setProducerListener(new LoggingProducerListener<String, Position>());
        return kafkaTemplate;
    }

    @Bean
    public Sender sender() {
        return new Sender();
    }
}