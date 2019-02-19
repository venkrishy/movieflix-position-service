package com.movieflix.position.consumer;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

@Slf4j
public class ColorsKafkaStream {

    public static void main(String[] args) {
        Properties config = new Properties();
        Serde<String> stringSerde = Serdes.String();
        Serde<Long> longSerde = Serdes.Long();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "favorite-color-java");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        //only in local, not in prod
        config.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, "0");

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, String> colorsInput = streamsBuilder.stream("favourite-colour-input");

        KStream<String, String> usersAndColorsStream = colorsInput
                .filter((key, value) -> key == null)
                .filter((key, value) -> value.contains(","))
                .selectKey((key, value) -> value.split(",")[0].toLowerCase())
                .mapValues(value -> value.split(",")[1].toLowerCase())
                .filter((user, color) -> Arrays.asList("green", "blue", "red").contains(color));
        usersAndColorsStream.to("user-keys-and-colors");

        KTable<String, String> usersAndColorsTable = streamsBuilder.table("user-keys-and-colors");

        KTable<String, Long> favoriteColors = usersAndColorsTable
                .groupBy( (user, color) -> new KeyValue<>(color, color))
                .count(Materialized.as("CountsByColors"));

        favoriteColors.toStream().to("favourite-colour-output", Produced.with(stringSerde, longSerde));

        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), config);

        //cleanUp step, do this in local, not in prod
        streams.cleanUp();

        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

    }
}
