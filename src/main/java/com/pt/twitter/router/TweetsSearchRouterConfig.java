package com.pt.twitter.router;

import com.pt.twitter.facade.TweetsSearchStreamFacade;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
@Log4j2
public class TweetsSearchRouterConfig {

    private static final String API_TWEETS_SEARCH_STREAM_PATH = "/api/tweets/search/stream";
    private static final String API_TWEETS_SEARCH_STREAM_RULES_PATH = API_TWEETS_SEARCH_STREAM_PATH + "/rules";

    @Bean
    public RouterFunction<ServerResponse> route(final TweetsSearchStreamFacade tweetsSearchStreamFacade) {
        return RouterFunctions.route(GET(API_TWEETS_SEARCH_STREAM_RULES_PATH)
                .and(accept(APPLICATION_JSON)), tweetsSearchStreamFacade::getRules)
                .andRoute(POST(API_TWEETS_SEARCH_STREAM_RULES_PATH)
                        .and(accept(APPLICATION_JSON)), tweetsSearchStreamFacade::createRules)
                .andRoute(POST(API_TWEETS_SEARCH_STREAM_RULES_PATH + "/delete")
                        .and(accept(APPLICATION_JSON)), tweetsSearchStreamFacade::deleteRules)
                .andRoute(GET(API_TWEETS_SEARCH_STREAM_PATH), tweetsSearchStreamFacade::getTweetsSearchStream);
    }
}
