package com.unibuc.shoppingcart.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ShoppingCartDto {

    public String id;
    public String userId;
    public List<ShoppingCartItemDto> items;
}
