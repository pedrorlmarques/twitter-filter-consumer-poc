package com.pt.twitter.facade;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface TweetsSearchStreamFacade {

    Mono<ServerResponse> getRules(ServerRequest serverRequest);

    Mono<ServerResponse> createRules(ServerRequest serverRequest);

    Mono<ServerResponse> getTweetsSearchStream(ServerRequest serverRequest);

    Mono<ServerResponse> deleteRules(ServerRequest serverRequest);
}
