package com.unibuc.shoppingcart.service;

import com.unibuc.shoppingcart.domain.dto.AddCartItemRequest;
import com.unibuc.shoppingcart.domain.dto.ShoppingCartDto;
import com.unibuc.shoppingcart.domain.dto.ShoppingCartItemDto;
import com.unibuc.shoppingcart.domain.entity.ShoppingCart;
import com.unibuc.shoppingcart.domain.entity.ShoppingCartItem;
import com.unibuc.shoppingcart.domain.mapper.ShoppingCartItemMapper;
import com.unibuc.shoppingcart.domain.mapper.ShoppingCartMapper;
import com.unibuc.shoppingcart.repository.ShoppingCartItemsRepository;
import com.unibuc.shoppingcart.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemsRepository shoppingCartItemsRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final ShoppingCartItemMapper shoppingCartItemMapper;

    public ShoppingCartDto addItem(AddCartItemRequest addCartItemRequest) {

        var shoppingCart = getShoppingCartByUserId(addCartItemRequest.getUserId());

        shoppingCart.getItems()
                .stream().filter(shoppingCartItem2 -> shoppingCartItem2.getItemId().toString().equals(addCartItemRequest.getItemId()))
                .findFirst()
                .ifPresentOrElse(shoppingCartItem -> updateShoppingCartEntryQuantity(shoppingCartItem, addCartItemRequest),
                        () -> addItemInCart(addCartItemRequest, shoppingCart));

        return shoppingCartMapper.toDto(shoppingCart);

    }

    private void addItemInCart(AddCartItemRequest addCartItemRequest, ShoppingCart shoppingCart) {
        var shoppingCartItem = createShoppingCartItem(addCartItemRequest, shoppingCart);
        shoppingCartItemsRepository.save(shoppingCartItem);

        var shoppingCarItemsSet = shoppingCart.getItems();
        shoppingCarItemsSet.add(shoppingCartItem);
        shoppingCart.setItems(shoppingCarItemsSet);
    }


    private void updateShoppingCartEntryQuantity(ShoppingCartItem shoppingCartItem,
                                                 AddCartItemRequest addCartItemRequest) {
        shoppingCartItem.setQuantity(Integer.valueOf(addCartItemRequest.getQuantity()));
        shoppingCartItemsRepository.save(shoppingCartItem);

    }

    private ShoppingCart getShoppingCartByUserId(String userId){
        return shoppingCartRepository
                .findShoppingCartByUserId(userId)
                .orElseGet(() -> createAndSaveShoppingCart(userId));
    }

    private ShoppingCart createAndSaveShoppingCart(String userId){
        var shoppingCart = createShoppingCartForUser(userId);
        return shoppingCartRepository.save(shoppingCart);
    }
    private ShoppingCart createShoppingCartForUser(String userId){
        return ShoppingCart.builder()
                .userId(userId)
                .items(Collections.emptySet())
                .build();

    }

    private ShoppingCartItem createShoppingCartItem(AddCartItemRequest addCartItemRequest, ShoppingCart shoppingCart){
        return ShoppingCartItem.builder()
                .itemId(Integer.valueOf(addCartItemRequest.getItemId()))
                .shoppingCart(shoppingCart)
                .quantity(Integer.valueOf(addCartItemRequest.getQuantity()))
                .build();
    }


    public void clearShoppingCart(String userId) {
        var shoppingCart = getShoppingCartByUserId(userId);

        shoppingCartRepository.delete(shoppingCart);

    }

    public List<ShoppingCartItemDto> getShoppingCartItemsByUserId(String userId){
        return getShoppingCartByUserId(userId).getItems().stream()
                .map(shoppingCartItemMapper::toDto)
                .collect(Collectors.toList());
    }
}
