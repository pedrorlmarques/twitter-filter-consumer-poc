package com.pt.twitter.service.impl;

import com.pt.twitter.domain.twitter.Rules;
import com.pt.twitter.producer.TweetsSearchStreamProducer;
import com.pt.twitter.service.TweetsSearchStreamService;
import com.pt.twitter.utils.RulesUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class TweetsSearchStreamServiceImpl implements TweetsSearchStreamService {

    private final WebClient twitterSearchStreamWebClient;
    private final TweetsSearchStreamProducer tweetsSearchStreamProducer;

    @Override
    public Mono<Rules> getAllRules() {
        return this.fetchAllRules();
    }

    @Override
    public Mono<Void> createRules(Map<String, String> rules) {
        return this.createRulesInternal(rules).then();
    }

    @Override
    public Flux<SenderResult<Object>> connectTweetsSearchStream() {
        return this.connectStream();
    }

    @Override
    public Mono<Void> deleteRules(List<String> rulesId) {
        return this.deleteRulesInternal(rulesId).then();
    }

    private Mono<String> deleteRulesInternal(List<String> existingRules) {

        var rulesToDeleteBody = RulesUtils.getFormattedString("{ \"delete\": { \"ids\": [%s]}}", existingRules);

        return this.twitterSearchStreamWebClient
                .post()
                .uri("/rules")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(rulesToDeleteBody))
                .retrieve()
                .bodyToMono(String.class)
                .log();
    }

    private Flux<SenderResult<Object>> connectStream() {
        return this.twitterSearchStreamWebClient
                .get()
                .retrieve()
                .bodyToFlux(String.class)
                .flatMap(tweetsSearchStreamProducer::sendKafkaMessage)
                .log();
    }

    private Mono<Rules> fetchAllRules() {
        return twitterSearchStreamWebClient
                .get()
                .uri("/rules")
                .retrieve()
                .bodyToMono(Rules.class);
    }

    private Mono<String> createRulesInternal(Map<String, String> rules) {

        var rulesToAddBody = RulesUtils.getFormattedString("{\"add\": [%s]}", rules);

        return twitterSearchStreamWebClient
                .post()
                .uri("/rules")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(rulesToAddBody))
                .retrieve()
                .bodyToMono(String.class)
                .log();
    }
}
