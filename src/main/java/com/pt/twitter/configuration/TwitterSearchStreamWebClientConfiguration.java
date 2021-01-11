package com.pt.twitter.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class TwitterSearchStreamWebClientConfiguration {

    @Bean
    public WebClient twitterSearchStreamWebClient(final WebClient.Builder webclientBuilder,
                                                  final @Value("${twitter.twitter-search-stream.tweets-search-uri}") String twitterBaseUri,
                                                  final @Value("${twitter.bearer-token}") String bearerToken) {
        return webclientBuilder
                .baseUrl(twitterBaseUri)
                .filter(oauthFilter(bearerToken))
                .build();
    }

    private ExchangeFilterFunction oauthFilter(String token) {
        return (request, next) -> next
                .exchange(ClientRequest.from(request).headers(headers -> headers.setBearerAuth(token)).build());
    }
}
