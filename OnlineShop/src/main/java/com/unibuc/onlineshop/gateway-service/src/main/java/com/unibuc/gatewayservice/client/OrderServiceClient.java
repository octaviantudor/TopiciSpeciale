package com.unibuc.gatewayservice.client;

import com.unibuc.gatewayservice.config.properties.OrderServiceProperties;
import com.unibuc.gatewayservice.domain.order.CreateOrderRequest;
import com.unibuc.gatewayservice.domain.order.OrderDto;
import com.unibuc.gatewayservice.domain.order.OrderedItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class OrderServiceClient {

    private final WebClient orderServiceWebClient;
    private final OrderServiceProperties orderServiceProperties;

    public OrderDto createOrderRequest(CreateOrderRequest createOrderRequest) {
        return orderServiceWebClient.post()
                .uri(uriBuilder -> uriBuilder.path(orderServiceProperties.getOrdersEndpoint())
                        .build())
                .body(Mono.just(createOrderRequest), CreateOrderRequest.class)
                .retrieve()
                .bodyToMono(OrderDto.class)
                .block();
    }

    public List<OrderedItemDto> getOrderedItemsByUserRequest(String userId){
        return orderServiceWebClient.get()
                .uri(uriBuilder -> uriBuilder.path(orderServiceProperties.getOrderedItemsEndpoint())
                        .queryParam("userId", userId).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<OrderedItemDto>>() {
                })
                .block();
    }


    public List<OrderDto> getOrdersForUser(String userId){
        return orderServiceWebClient.get()
                .uri(uriBuilder -> uriBuilder.path(orderServiceProperties.getOrdersEndpoint())
                        .queryParam("userId", userId).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<OrderDto>>() {
                })
                .block();
    }

    public List<OrderedItemDto> findAllOrderedItems() {
        return orderServiceWebClient.get()
                .uri(uriBuilder -> uriBuilder.path(orderServiceProperties.getOrderedItemsEndpoint()+"/all")
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<OrderedItemDto>>() {
                })
                .block();
    }

    public List<OrderDto> getOrders() {
        return orderServiceWebClient.get()
                .uri(uriBuilder -> uriBuilder.path(orderServiceProperties.getOrdersEndpoint() +"/all")
                       .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<OrderDto>>() {
                })
                .block();
    }

    public void markAsDone(String orderId) {
        orderServiceWebClient.get()
                .uri(uriBuilder -> uriBuilder.path(orderServiceProperties.getMarkOrderDoneEndpoint()).build(orderId))
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
