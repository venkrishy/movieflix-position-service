package com.movieflix.position.consumer;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
//TODO: Research further  Spring Cloud DataFlow which is on top of Spring Cloud Stream
//TODO: Use streamsBuilderFactoryBean https://stackoverflow.com/questions/51733039/kafka-streams-with-spring-boot/51958629
// You can start and stop its stream using kStreamBuilderFactory.start();
// kStreamBuilderFactory.setUncaughtExceptionHandler((thread, throwable) -> log.error("UncaughtExceptionHandler " + throwable.getMessage()));
// @PostConstruct start stream
// @PreDestroy stop stream
// use state stores kStreamBuilder.addStateStore(supplier);
// KeyValue.pair(key, objectMapper.readValue(value, clazz)); if exception is thrown, return KeyValue.pair(key, null);
//kStream.process(processSupplier, supplierNames.toArray(new String[supplierNames.size()]));
//@EventListener(ContextRefreshedEvent.class)
//public void initializeStream() {
//        if (streamProperties.isSeekToEndOnStartup()) {
//        resetOffset(new ResetOffset(0, null, -1, null));
//        }
//        startStream();
//        }
public class TrendKafkaStream {

    @Value("${kafka.bootstrap-servers:localhost}")
    private String bootstrapServers;

    @Value("${kafka.trend.applicationId}")
    private String trendApplicationId;

    @Value("${kafka.autoreset}")
    private String autoreset;

    @Value("${kafka.username}")
    private String username;

    @Value("${kafka.password}")
    private String password;

    @Value("${kafka.topic.position}")
    private String positionTopic;

    @Value("${kafka.topic.recommendation}")
    private String recommendationTopic;

    @Value("${currentProfile}")
    private String currentProfile;


    public void triggerProcessingStream() {

        Properties props = new Properties();

        final Serde<String> stringSerde = Serdes.String();
        final Serde<Long> longSerde = Serdes.Long();

//        Map<String, Object> serdeProps = new HashMap<>();
//        final Serializer<Position> positionSerializer = new JsonPOJOSerializer<>();
//        serdeProps.put("JsonPOJOClass", Position.class);
//        positionSerializer.configure(serdeProps, false);
//
//        final Deserializer<Position> pageViewDeserializer = new JsonPOJODeserializer<>();
//        serdeProps.put("JsonPOJOClass", Position.class);
//        pageViewDeserializer.configure(serdeProps, false);

//        final Serde<Position> positionSerde = Serdes.serdeFrom(positionSerializer, pageViewDeserializer);

        props.put(StreamsConfig.APPLICATION_ID_CONFIG, trendApplicationId);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(
                StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
                Serdes.String().getClass().getName());
        props.put(
                StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
                Serdes.String().getClass().getName());
        props.put(
                StreamsConfig.STATE_DIR_CONFIG,
                System.getProperty("user.home") + "/kafkatemp");

        if (!currentProfile.equals("local")) {
            String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
            String jaasCfg = String.format(jaasTemplate, username, password);
            props.put("security.protocol", "SASL_SSL");
            props.put("sasl.mechanism", "SCRAM-SHA-256");
            props.put("sasl.jaas.config", jaasCfg);
        }



        StreamsBuilder builder = new StreamsBuilder();
//        KStream<String, Position> titleCountsStream = builder.stream(positionTopic, Consumed.with(stringSerde, positionSerde));
        KStream<String, String> titleCountsStream = builder.stream(positionTopic, Consumed.with(stringSerde, stringSerde));
        KTable<String, Long> titleCounts = titleCountsStream
                .filter((k, v) -> {
                    log.info("Inside Stream: filter. key={}, value={}", k, v);
                    return true;
                })
                .groupByKey().count();


        log.info("FINISHED CREATING A TITLE COUNTS STREAM");


        titleCounts.toStream().foreach((w, c) -> log.info("title: " + w + " -> " + c));

        titleCounts.toStream().to(recommendationTopic, Produced.with(stringSerde, longSerde));

        titleCounts.toStream().print(Printed.toSysOut());

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        // usually the stream application would be running forever,
        // in this example we just let it run for some time and stop since the input data is finite.
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {

        }

        streams.close();
    }
}