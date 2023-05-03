package com.unibuc.gatewayservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Data
@Slf4j
@RequiredArgsConstructor
@Configuration
public class WebClientConfig {
    private final ClientProperties clientProperties;
    private final ObjectMapper objectMapper;

    @Bean("itemServiceWebClient")
    public WebClient itemServiceWebClient() {
        return WebClient.builder()
                .baseUrl(clientProperties.getItemServiceHost())
                .exchangeStrategies(exchangeStrategies())
                .filter(logRequest())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean("orderServiceWebClient")
    public WebClient orderServiceWebClient() {
        return WebClient.builder()
                .baseUrl(clientProperties.getOrderServiceHost())
                .exchangeStrategies(exchangeStrategies())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter(logRequest())
                .build();
    }

    @Bean("shoppingCartServiceWebClient")
    public WebClient shoppingCartServiceWebClient() {
        return WebClient.builder()
                .baseUrl(clientProperties.getShoppingCartServiceHost())
                .exchangeStrategies(exchangeStrategies())
                .filter(logRequest())
                .build();
    }

    private ExchangeStrategies exchangeStrategies() {
        final int size = 16 * 1024 * 1024;
        return ExchangeStrategies.builder().codecs(
                configurer -> {
                    configurer.defaultCodecs().maxInMemorySize(size);
                }).build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            logRequest(clientRequest);
            return Mono.just(clientRequest);
        });
    }

    private void logRequest(ClientRequest clientRequest) {
        log.info("{} {}{} {} Headers {}",
                clientRequest.method().name(),
                clientRequest.url().getHost(),
                clientRequest.url().getPath(),
                clientRequest.url().getQuery(),
                clientRequest.headers());
    }
}
