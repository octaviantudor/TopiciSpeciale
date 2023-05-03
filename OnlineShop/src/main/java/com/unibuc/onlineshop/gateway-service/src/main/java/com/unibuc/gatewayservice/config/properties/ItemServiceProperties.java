package com.unibuc.gatewayservice.config.properties;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ItemServiceProperties {
    private final String itemsEndpoint;
    private final String categoriesEndpoint;

    private final String singleItemEndpoint;

    private final String updateItemEndpoint;

    private final String reviewsEndpoint;

    public ItemServiceProperties(
            @Value("${config.service.itemService.endpoints.items}") String itemsEndpoint,
            @Value("${config.service.itemService.endpoints.categories}")String categoriesEndpoint,
            @Value("${config.service.itemService.endpoints.singleItem}") String singleItemEndpoint,
            @Value("${config.service.itemService.endpoints.updateItem}") String updateItemEndpoint,
            @Value("${config.service.itemService.endpoints.reviewsEndpoint}") String reviewsEndpoint) {
        this.itemsEndpoint = itemsEndpoint;
        this.categoriesEndpoint = categoriesEndpoint;
        this.singleItemEndpoint = singleItemEndpoint;
        this.updateItemEndpoint = updateItemEndpoint;
        this.reviewsEndpoint = reviewsEndpoint;
    }
}
