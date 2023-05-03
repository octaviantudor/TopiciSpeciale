package com.unibuc.gatewayservice.config.properties;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ShoppingCartServiceProperties {
    private final String shoppingCartEndpoint;
    private final String shoppingCartItemsEndpoint;
    private final String clearShoppingCartEndpoint;

    public ShoppingCartServiceProperties(
            @Value("${config.service.shoppingCartService.endpoints.shoppingCart}") String shoppingCartEndpoint,
            @Value("${config.service.shoppingCartService.endpoints.shoppingCartItems}") String shoppingCartItemsEndpoint,
            @Value("${config.service.shoppingCartService.endpoints.clearShoppingCart}") String clearShoppingCartEndpoint) {
        this.shoppingCartEndpoint = shoppingCartEndpoint;
        this.shoppingCartItemsEndpoint = shoppingCartItemsEndpoint;
        this.clearShoppingCartEndpoint = clearShoppingCartEndpoint;
    }
}
