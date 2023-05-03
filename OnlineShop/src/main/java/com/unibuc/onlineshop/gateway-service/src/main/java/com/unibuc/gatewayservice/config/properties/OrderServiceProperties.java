package com.unibuc.gatewayservice.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class OrderServiceProperties {
    private final String ordersEndpoint;
    private final String orderedItemsEndpoint;

    private final String markOrderDoneEndpoint;

    public OrderServiceProperties(
            @Value("${config.service.orderService.endpoints.orders}") String ordersEndpoint,
            @Value("${config.service.orderService.endpoints.orderedItems}") String orderedItemsEndpoint,
            @Value("${config.service.orderService.endpoints.done}") String markOrderDoneEndpoint) {
        this.ordersEndpoint = ordersEndpoint;
        this.orderedItemsEndpoint = orderedItemsEndpoint;
        this.markOrderDoneEndpoint = markOrderDoneEndpoint;
    }
}
