package com.unibuc.gatewayservice.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderedItemDto {
    private String itemId;
    private String orderId;
    private String quantity;
    private String name;
    private String description;
    private String price;
}
