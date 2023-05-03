package com.unibuc.ordersmicroservice.controller;

import com.unibuc.ordersmicroservice.domain.dto.CreateOrderRequest;
import com.unibuc.ordersmicroservice.domain.dto.OrderDto;
import com.unibuc.ordersmicroservice.domain.dto.OrderedItemDto;
import com.unibuc.ordersmicroservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderDto createOrder(@RequestBody CreateOrderRequest createOrderRequest){
        log.info("Creating order for userID: {}", createOrderRequest.getUserId());
        return orderService.createOrder(createOrderRequest);
    }
    @GetMapping("/ordered-items")
    public List<OrderedItemDto> getOrderedItemsByUser(@RequestParam(name = "userId") String userId){
        log.info("Attempting to get all ordered items for userID: {}", userId);
        return orderService.findAllOrderedItemsByUser(userId);
    }

    @GetMapping
    public List<OrderDto> getUserOrder(@RequestParam(name = "userId", required = false) String userId){
        log.info("Getting all the orders for userID: {}", userId);
        return orderService.getOrdersForUser(userId);
    }

    @GetMapping("/ordered-items/all")
    public List<OrderedItemDto> getOrderedItems(){
        log.info("Attempting to get all ordered items");
        return orderService.findAllOrderedItems();
    }

    @GetMapping("/all")
    public List<OrderDto> getOrders(){
        log.info("Getting all the orders");
        return orderService.getOrders();
    }

    @GetMapping("/{orderId}/done")
    public void markOrderAsDone(@PathVariable(name = "orderId") String orderId){
        log.info("Attempting to mark as DONE the order with id: {}", orderId);
        orderService.markAsDone(orderId);
    }
}
