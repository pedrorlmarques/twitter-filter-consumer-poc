package com.pt.twitter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

@Configuration
public class KafkaConfiguration {

    @Bean
    KafkaSender<String, String> kafkaSender(final KafkaProperties kafkaProperties) {
        return KafkaSender.create(SenderOptions.create(kafkaProperties.getProducerProps()));
    }
}
