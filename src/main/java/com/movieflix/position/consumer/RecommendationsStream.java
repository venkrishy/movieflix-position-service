package com.movieflix.position.consumer;

//@EnableBinding(KafkaStreamsProcessor.class)
//@Slf4j
public class RecommendationsStream {

//    @StreamListener("positionConsumer")
//    @SendTo("recommendationConsumer")
//    public KStream<Object, Recommendation> process(KStream<String, Position> input) {
//        KStream<String, String> titles = input.flatMapValues(value -> Arrays.asList(value.getTitle()));
//        KStream<String, String> titlesMap = titles.map((key, value) -> new KeyValue<>(value, value));
//        KGroupedStream<String, String> groupedByStream = titlesMap.groupByKey();
//        TimeWindowedKStream<String, String> windowedKStream = groupedByStream.windowedBy(TimeWindows.of(5000));
//        KTable<Windowed<String>, Long> ktable = windowedKStream.count(Materialized.as("title_counts"));
//        KStream<Object, Recommendation> kStream = ktable.toStream().map((key, value) ->
//                new KeyValue<>(null, new Recommendation(key.key(), value)));
//        return kStream;
//    }
}