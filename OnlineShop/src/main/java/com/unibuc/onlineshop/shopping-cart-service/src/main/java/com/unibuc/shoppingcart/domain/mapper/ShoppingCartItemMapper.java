package com.unibuc.shoppingcart.domain.mapper;

import com.unibuc.shoppingcart.domain.dto.ShoppingCartItemDto;
import com.unibuc.shoppingcart.domain.entity.ShoppingCartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShoppingCartItemMapper {

    ShoppingCartItemDto toDto(ShoppingCartItem shoppingCartItem);
}
