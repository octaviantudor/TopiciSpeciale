package com.unibuc.gatewayservice.domain.shoppingcart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItemDto {

    private String id;
    private String itemId;
    private String quantity;
    private String name;
    private String description;
    private String price;

}
