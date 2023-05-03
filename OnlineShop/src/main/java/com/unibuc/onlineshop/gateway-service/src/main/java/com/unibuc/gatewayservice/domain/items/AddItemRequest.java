package com.unibuc.gatewayservice.domain.items;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddItemRequest {

    private String name;
    private String description;
    private String price;
    private String categoryName;
    private String quantity;
}
