package com.unibuc.gatewayservice.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private String id;
    private String userId;
    private String price;
    private String status;
    private String date;
    private List<OrderedItemDto> orderedItems;
}
