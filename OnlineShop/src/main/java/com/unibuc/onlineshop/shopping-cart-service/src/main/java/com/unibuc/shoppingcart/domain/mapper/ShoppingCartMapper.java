package com.unibuc.shoppingcart.domain.mapper;

import com.unibuc.shoppingcart.domain.dto.ShoppingCartDto;
import com.unibuc.shoppingcart.domain.entity.ShoppingCart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ShoppingCartItemMapper.class)
public interface ShoppingCartMapper {
    ShoppingCartDto toDto(ShoppingCart shoppingCart);
}
