package com.unibuc.gatewayservice.service;


import com.unibuc.gatewayservice.client.ItemServiceClient;
import com.unibuc.gatewayservice.client.ShoppingCartServiceClient;
import com.unibuc.gatewayservice.domain.items.ItemDto;
import com.unibuc.gatewayservice.domain.shoppingcart.AddCartItemRequest;
import com.unibuc.gatewayservice.domain.shoppingcart.ShoppingCartDto;
import com.unibuc.gatewayservice.domain.shoppingcart.ShoppingCartItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartServiceClient shoppingCartServiceClient;
    private final ItemServiceClient itemServiceClient;
    private final AuthService authService;

    public ShoppingCartDto addItem(AddCartItemRequest addCartItemRequest) {
        var item = itemServiceClient.getSingleItemByIdRequest(addCartItemRequest.getItemId()).block();

        var userId = authService.getCurrentAuthUserId();

        addCartItemRequest.setUserId(userId);
        var currentShoppingCartItems = shoppingCartServiceClient.getShoppingCartByUserId(userId);

        var totalQuantity = calculateTotalQuantityInShoppingCart(currentShoppingCartItems, addCartItemRequest.getItemId());

        if (Integer.parseInt(item.getQuantity()) < Integer.parseInt(addCartItemRequest.getQuantity()) + totalQuantity)
            throw new RuntimeException("Quantity not available for this product!!");

        return shoppingCartServiceClient.addItemToShoppingCart(addCartItemRequest);
    }


    public List<ShoppingCartItemDto> getShoppingCartItemsByUserId(String ceva) {

        var userId = authService.getCurrentAuthUserId();
        var items = itemServiceClient.getAllItemsRequest().stream()
                .collect(Collectors.toMap(ItemDto::getId, Function.identity(), (item1, item2) -> item1));


        var shoppingCartList = shoppingCartServiceClient.getShoppingCartByUserId(userId);

        shoppingCartList.forEach(
                shoppingCartItemDto -> updateShoppingCartItemsDetails(items, shoppingCartItemDto)
        );

        return shoppingCartList;
    }

    private void updateShoppingCartItemsDetails(Map<Long, ItemDto> items, ShoppingCartItemDto shoppingCartItemDto) {

        var item = items.get(Long.valueOf(shoppingCartItemDto.getItemId()));

        shoppingCartItemDto.setDescription(item.getDescription());
        shoppingCartItemDto.setName(item.getName());
        shoppingCartItemDto.setPrice(item.getPrice());
    }


    private Integer calculateTotalQuantityInShoppingCart(List<ShoppingCartItemDto> shoppingCartItemDtos, String itemId) {
        return shoppingCartItemDtos.stream()
                .filter(shoppingCartItemDto -> shoppingCartItemDto.getItemId().equals(itemId))
                .map(ShoppingCartItemDto::getQuantity)
                .map(Integer::parseInt)
                .reduce(0, Integer::sum);
    }

    public void clearShoppingCart(String ceva) {
        var userId = authService.getCurrentAuthUserId();
        shoppingCartServiceClient.clearShoppingCart(userId);
    }
}
