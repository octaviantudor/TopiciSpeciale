package com.unibuc.gatewayservice.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderedItemsRequest {

    private String itemId;
    private String quantity;
}
