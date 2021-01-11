package com.pt.twitter.producer;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;

@Component
@RequiredArgsConstructor
public class TweetsSearchStreamProducer {

    private final KafkaSender<String, String> kafkaSender;

    @Value("${twitter.twitter-search-stream.topic}")
    private String topic;

    public Flux<SenderResult<Object>> sendKafkaMessage(String message) {
        return this.kafkaSender
                .send(Mono.justOrEmpty(message)
                        .map(body -> SenderRecord
                                .create(topic, null, null, null, body, null)));
    }
}
