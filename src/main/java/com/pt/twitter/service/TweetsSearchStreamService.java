package com.pt.twitter.service;

import com.pt.twitter.domain.twitter.Rules;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

import java.util.List;
import java.util.Map;

public interface TweetsSearchStreamService {

    Mono<Rules> getAllRules();

    Mono<Void> createRules(Map<String, String> rules);

    Flux<SenderResult<Object>> connectTweetsSearchStream();

    Mono<Void> deleteRules(List<String> rulesId);
}
