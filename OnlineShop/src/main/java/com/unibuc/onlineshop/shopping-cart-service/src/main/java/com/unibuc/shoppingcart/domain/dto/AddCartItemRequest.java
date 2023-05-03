package com.unibuc.shoppingcart.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddCartItemRequest {

    private String itemId;
    private String userId;
    private String quantity;
}
