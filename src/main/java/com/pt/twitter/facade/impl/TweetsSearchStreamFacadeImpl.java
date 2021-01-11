package com.pt.twitter.facade.impl;

import com.pt.twitter.domain.CreateRuleRequest;
import com.pt.twitter.domain.DeleteRuleRequest;
import com.pt.twitter.domain.twitter.Rules;
import com.pt.twitter.facade.TweetsSearchStreamFacade;
import com.pt.twitter.service.TweetsSearchStreamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

import java.time.Instant;

import static com.pt.twitter.utils.RulesUtils.convertCreateRuleRequestToMap;
import static com.pt.twitter.utils.RulesUtils.convertDeleteRuleRequestToMap;

@Component
@RequiredArgsConstructor
@Log4j2
public class TweetsSearchStreamFacadeImpl implements TweetsSearchStreamFacade {

    private final TweetsSearchStreamService tweetsSearchStreamService;

    @Override
    public Mono<ServerResponse> getRules(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters
                        .fromProducer(tweetsSearchStreamService.getAllRules(), Rules.class));
    }

    @Override
    public Mono<ServerResponse> createRules(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(CreateRuleRequest.class)
                .map(convertCreateRuleRequestToMap())
                .flatMap(rulesToCreate -> ServerResponse.ok()
                        .body(BodyInserters
                                .fromProducer(this.tweetsSearchStreamService.createRules(rulesToCreate), Void.class)));
    }


    @Override
    public Mono<ServerResponse> getTweetsSearchStream(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(BodyInserters
                        .fromProducer(this.tweetsSearchStreamService
                                        .connectTweetsSearchStream()
                                        .map(SenderResult::recordMetadata)
                                        .map(recordMetadata ->
                                                new PublishResult(recordMetadata.topic(),
                                                        recordMetadata.partition(),
                                                        recordMetadata.offset(),
                                                        Instant.ofEpochMilli(recordMetadata.timestamp())))
                                , PublishResult.class));
    }

    @Override
    public Mono<ServerResponse> deleteRules(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(DeleteRuleRequest.class)
                .map(convertDeleteRuleRequestToMap())
                .flatMap(rulesToDelete -> ServerResponse.ok()
                        .body(BodyInserters
                                .fromProducer(this.tweetsSearchStreamService.deleteRules(rulesToDelete), Void.class)));
    }


    private static class PublishResult {
        public final String topic;
        public final int partition;
        public final long offset;
        public final Instant timestamp;

        private PublishResult(String topic, int partition, long offset, Instant timestamp) {
            this.topic = topic;
            this.partition = partition;
            this.offset = offset;
            this.timestamp = timestamp;
        }
    }
}
