package com.unibuc.ordersmicroservice.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderedItemDto {
    private String itemId;
    private String quantity;
    private String orderId;
}
