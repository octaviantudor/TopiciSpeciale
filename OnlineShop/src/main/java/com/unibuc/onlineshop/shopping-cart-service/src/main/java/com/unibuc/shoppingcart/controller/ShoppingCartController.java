package com.unibuc.shoppingcart.controller;


import com.unibuc.shoppingcart.domain.dto.AddCartItemRequest;
import com.unibuc.shoppingcart.domain.dto.ShoppingCartDto;
import com.unibuc.shoppingcart.domain.dto.ShoppingCartItemDto;
import com.unibuc.shoppingcart.domain.entity.ShoppingCart;
import com.unibuc.shoppingcart.domain.entity.ShoppingCartItem;
import com.unibuc.shoppingcart.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/shopping-cart")
@RequiredArgsConstructor
@Slf4j
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @PostMapping
    public ShoppingCartDto addItem(@RequestBody AddCartItemRequest addCartItemRequest) {
        log.info("Attempting to add {} item with Id: {} in the shoppingCart, for user with Id: {} ", addCartItemRequest.getQuantity(),
                addCartItemRequest.getItemId(), addCartItemRequest.getUserId());
        return shoppingCartService.addItem(addCartItemRequest);

    }

    @GetMapping("/items/{userId}")
    public List<ShoppingCartItemDto> getShoppingCartItems(@PathVariable(value = "userId") String userId){
        log.info("Attempting to get shopping cart items for user with ID: {}", userId);
        return shoppingCartService.getShoppingCartItemsByUserId(userId);
    }

    @PutMapping("/clear/{userId}")
    public ResponseEntity<?> clearShoppingCart(@PathVariable(value = "userId") String userId){
        log.info("Attempting to clear shopping cart items for user with ID: {}", userId);
        shoppingCartService.clearShoppingCart(userId);
        return ResponseEntity.ok().build();
    }

}
