package com.unibuc.gatewayservice.controller;



import com.unibuc.gatewayservice.domain.order.CreateOrderRequest;
import com.unibuc.gatewayservice.domain.order.OrderDto;
import com.unibuc.gatewayservice.domain.order.OrderedItemDto;
import com.unibuc.gatewayservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderDto createOrder(@RequestParam(required = false) String userId){
        log.info("Creating order for userID: {}", userId);
        return orderService.createOrder(userId);
    }

    @GetMapping("/ordered-items")
    public List<OrderedItemDto> getOrderedItemsByUser(@RequestParam(required = false) String userId){
        log.info("Attempting to get all ordered items for userID: {}", userId);
        return orderService.findAllOrderedItems(userId);
    }


    @GetMapping
    public List<OrderDto> getOrdersForUser(@RequestParam(required = false) String userId){
        log.info("Attempting to get all ordered items for userID: {}", userId);
        return orderService.findAllOrders(userId);
    }


    @GetMapping("/ordered-items/all")
    public List<OrderedItemDto> getOrderedItems(){
        log.info("Attempting to get all ordered items");
        return orderService.getAllOrderedItems();
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
