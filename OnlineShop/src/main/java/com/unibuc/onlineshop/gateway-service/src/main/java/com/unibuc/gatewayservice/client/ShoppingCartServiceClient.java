package com.unibuc.gatewayservice.client;

import com.unibuc.gatewayservice.config.properties.ShoppingCartServiceProperties;
import com.unibuc.gatewayservice.domain.items.ItemDto;
import com.unibuc.gatewayservice.domain.shoppingcart.AddCartItemRequest;
import com.unibuc.gatewayservice.domain.shoppingcart.ShoppingCartDto;
import com.unibuc.gatewayservice.domain.shoppingcart.ShoppingCartItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class ShoppingCartServiceClient {
    private final WebClient shoppingCartServiceWebClient;
    private final ShoppingCartServiceProperties shoppingCartServiceProperties;

    public List<ShoppingCartItemDto> getShoppingCartByUserId(String userId) {
        return shoppingCartServiceWebClient.get()
                .uri(uriBuilder -> uriBuilder.path(shoppingCartServiceProperties.getShoppingCartItemsEndpoint()).build(userId))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ShoppingCartItemDto>>() {
                })
                .block();
    }

    public ShoppingCartDto addItemToShoppingCart(AddCartItemRequest addCartItemRequest){
        return shoppingCartServiceWebClient.post()
                .uri(uriBuilder -> uriBuilder.path(shoppingCartServiceProperties.getShoppingCartEndpoint()).build())
                .body(Mono.just(addCartItemRequest), AddCartItemRequest.class)
                .retrieve()
                .bodyToMono(ShoppingCartDto.class)
                .block();

    }

    public ResponseEntity<Void> clearShoppingCart(String userId){
        return shoppingCartServiceWebClient.put()
                .uri(uriBuilder -> uriBuilder.path(shoppingCartServiceProperties.getClearShoppingCartEndpoint()).build(userId))
                .retrieve()
                .toBodilessEntity()
                .block();
    }

}
