package com.unibuc.gatewayservice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ClientProperties {

    private final String itemServiceHost;
    private final String orderServiceHost;
    private final String shoppingCartServiceHost;

    public ClientProperties(
            @Value("${config.service.itemService.host}") String itemServiceHost,
            @Value("${config.service.orderService.host}") String orderServiceHost,
            @Value("${config.service.shoppingCartService.host}") String shoppingCartServiceHost) {
        this.itemServiceHost = itemServiceHost;
        this.orderServiceHost = orderServiceHost;
        this.shoppingCartServiceHost = shoppingCartServiceHost;
    }
}
