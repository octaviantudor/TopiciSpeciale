package com.unibuc.gatewayservice.service;

import com.unibuc.gatewayservice.client.ItemServiceClient;
import com.unibuc.gatewayservice.client.ShoppingCartServiceClient;
import com.unibuc.gatewayservice.domain.items.ItemDto;
import com.unibuc.gatewayservice.domain.shoppingcart.AddCartItemRequest;
import com.unibuc.gatewayservice.domain.shoppingcart.ShoppingCartDto;
import com.unibuc.gatewayservice.domain.shoppingcart.ShoppingCartItemDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceTest {

    @Mock
    private ShoppingCartServiceClient shoppingCartServiceClient;
    @Mock
    private ItemServiceClient itemServiceClient;
    @Mock
    private AuthService authService;
    @InjectMocks
    private ShoppingCartService shoppingCartService;

    @Test
    void addItem__ShouldReturnOK(){
        List<ShoppingCartItemDto> shoppingCartItems = Arrays.asList(
                ShoppingCartItemDto.builder().itemId("1").quantity("2").build(),
                ShoppingCartItemDto.builder().itemId("1").quantity("3").build()
        );

        given(itemServiceClient.getSingleItemByIdRequest(any())).willReturn(Mono.just(ItemDto.builder().id(1L).quantity("200").build()));
        given(authService.getCurrentAuthUserId()).willReturn("1");
        given(shoppingCartServiceClient.getShoppingCartByUserId(any())).willReturn(shoppingCartItems);
        given(shoppingCartServiceClient.addItemToShoppingCart(any())).willReturn(ShoppingCartDto.builder().build());

        var result = shoppingCartService.addItem(AddCartItemRequest.builder().itemId("1").quantity("5").build());

        assertNotNull(result);
        verify(itemServiceClient, times(1)).getSingleItemByIdRequest(any());
        verify(shoppingCartServiceClient, times(1)).addItemToShoppingCart(any());
    }



    @Test
    void getShoppingCartItemsByUserId__ShouldReturnOk(){
        List<ShoppingCartItemDto> shoppingCartItems = Arrays.asList(
                ShoppingCartItemDto.builder().itemId("1").quantity("2").build(),
                ShoppingCartItemDto.builder().itemId("2").quantity("3").build()
        );


        given(authService.getCurrentAuthUserId()).willReturn("1");

        given(itemServiceClient.getAllItemsRequest()).willReturn(Arrays.asList(ItemDto.builder().id(1L).name("Ceva2").price("20").build(),
                ItemDto.builder().id(2L).name("Ceva").price("20").build()));

        given(shoppingCartServiceClient.getShoppingCartByUserId(any())).willReturn(shoppingCartItems);

        var result = shoppingCartService.getShoppingCartItemsByUserId("ceva");


        assertNotNull(result);
        verify(itemServiceClient, times(1)).getAllItemsRequest();
        verify(shoppingCartServiceClient, times(1)).getShoppingCartByUserId(any());
    }


    @Test
    void testClearShoppingCart() {
        // Arrange
        String userId = "123";
        when(authService.getCurrentAuthUserId()).thenReturn(userId);

        // Act
        shoppingCartService.clearShoppingCart("ceva");

        // Assert
        verify(shoppingCartServiceClient, times(1)).clearShoppingCart(userId);
    }



}