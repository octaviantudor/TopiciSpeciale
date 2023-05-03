package com.unibuc.shoppingcart.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShoppingCartItemDto {

    private String id;
    private String itemId;
    private String quantity;
}
