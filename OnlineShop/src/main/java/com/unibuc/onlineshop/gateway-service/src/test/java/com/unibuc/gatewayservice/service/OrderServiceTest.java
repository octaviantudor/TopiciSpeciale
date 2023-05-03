package com.unibuc.gatewayservice.service;

import com.unibuc.gatewayservice.client.ItemServiceClient;
import com.unibuc.gatewayservice.client.OrderServiceClient;
import com.unibuc.gatewayservice.client.ShoppingCartServiceClient;
import com.unibuc.gatewayservice.domain.items.ItemDto;
import com.unibuc.gatewayservice.domain.order.CreateOrderRequest;
import com.unibuc.gatewayservice.domain.order.OrderDto;
import com.unibuc.gatewayservice.domain.order.OrderedItemDto;
import com.unibuc.gatewayservice.domain.order.OrderedItemsRequest;
import com.unibuc.gatewayservice.domain.shoppingcart.ShoppingCartItemDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderServiceClient orderServiceClient;

    @Mock
    private ItemServiceClient itemServiceClient;

    @Mock
    private ShoppingCartServiceClient shoppingCartServiceClient;

    @Mock
    private AuthService authService;

    @InjectMocks
    private OrderService orderService;


    @Test
    void testCreateOrder() {
        List<ShoppingCartItemDto> shoppingCartItems = Arrays.asList(
                ShoppingCartItemDto.builder().itemId("1").quantity("2").build(),
                ShoppingCartItemDto.builder().itemId("1").quantity("3").build()
        );

        given(authService.getCurrentAuthUserId()).willReturn("1");
        given(shoppingCartServiceClient.getShoppingCartByUserId(any())).willReturn(shoppingCartItems);
        given(itemServiceClient.getSingleItemByIdRequest(any())).willReturn(Mono.just(ItemDto.builder().id(1L).price("200").build()));
        given(itemServiceClient.updateItemQuantity(any(), any())).willReturn(Mono.just(ItemDto.builder().build()));
        given(shoppingCartServiceClient.clearShoppingCart(any())).willReturn(ResponseEntity.ok().build());
        given(orderServiceClient.createOrderRequest(any())).willReturn(OrderDto.builder().build());

        var result = orderService.createOrder("ceva");

        assertNotNull(result);
        verify(orderServiceClient, times(1)).createOrderRequest(any());
        verify(shoppingCartServiceClient, times(1)).clearShoppingCart(any());


    }

    @Test
    public void findAllOrderedItems__ShouldReturnOk(){
        given(authService.getCurrentAuthUserId()).willReturn("1");
        given(itemServiceClient.getAllItemsRequest())
                .willReturn(Collections.singletonList(ItemDto.builder().id(1L).price("20").name("ceva").build()));
        given(orderServiceClient.getOrderedItemsByUserRequest(any()))
                .willReturn(Collections.singletonList(OrderedItemDto.builder().itemId("1").build()));

        var result = orderService.findAllOrderedItems("ceva");

        assertNotNull(result);
        verify(orderServiceClient, times(1)).getOrderedItemsByUserRequest(any());
        verify(itemServiceClient, times(1)).getAllItemsRequest();
    }


    @Test
    void testFindAllOrders() {
        // given
        String ceva = "test";
        String userId = "user1";
        List<OrderDto> orders = Arrays.asList(new OrderDto(), new OrderDto());
        when(authService.getCurrentAuthUserId()).thenReturn(userId);
        when(orderServiceClient.getOrdersForUser(userId)).thenReturn(orders);

        // when
        List<OrderDto> result = orderService.findAllOrders(ceva);

        // then
        assertEquals(orders, result);
        verify(authService).getCurrentAuthUserId();
        verify(orderServiceClient).getOrdersForUser(userId);
    }

    @Test
    void testGetAllOrderedItems() {
        // given
        List<OrderedItemDto> orderedItems = Arrays.asList(new OrderedItemDto(), new OrderedItemDto());
        when(orderServiceClient.findAllOrderedItems()).thenReturn(orderedItems);

        // when
        List<OrderedItemDto> result = orderService.getAllOrderedItems();

        // then
        assertEquals(orderedItems, result);
        verify(orderServiceClient).findAllOrderedItems();
    }

    @Test
    void testGetOrders() {
        // given
        List<OrderDto> orders = Arrays.asList(new OrderDto(), new OrderDto());
        when(orderServiceClient.getOrders()).thenReturn(orders);

        // when
        List<OrderDto> result = orderService.getOrders();

        // then
        assertEquals(orders, result);
        verify(orderServiceClient).getOrders();
    }

    @Test
    void testMarkAsDone() {
        // given
        String orderId = "order1";

        // when
        orderService.markAsDone(orderId);

        // then
        verify(orderServiceClient).markAsDone(orderId);
    }


}