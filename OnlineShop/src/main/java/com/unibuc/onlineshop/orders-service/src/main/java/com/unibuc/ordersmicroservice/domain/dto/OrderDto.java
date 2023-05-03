package com.unibuc.ordersmicroservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

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
