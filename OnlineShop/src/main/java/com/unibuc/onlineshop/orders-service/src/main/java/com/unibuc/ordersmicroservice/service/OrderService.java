package com.unibuc.ordersmicroservice.service;

import com.unibuc.ordersmicroservice.domain.dto.CreateOrderRequest;
import com.unibuc.ordersmicroservice.domain.dto.OrderDto;
import com.unibuc.ordersmicroservice.domain.dto.OrderedItemDto;
import com.unibuc.ordersmicroservice.domain.dto.OrderedItemsRequest;
import com.unibuc.ordersmicroservice.domain.entity.Order;
import com.unibuc.ordersmicroservice.domain.entity.OrderedItem;
import com.unibuc.ordersmicroservice.domain.mapper.OrderedItemsMapper;
import com.unibuc.ordersmicroservice.domain.mapper.OrdersMapper;
import com.unibuc.ordersmicroservice.repository.OrderRepository;
import com.unibuc.ordersmicroservice.repository.OrderedItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderedItemsRepository orderedItemsRepository;
    private final OrdersMapper ordersMapper;
    private final OrderedItemsMapper orderedItemsMapper;

    public OrderDto createOrder(CreateOrderRequest createOrderRequest) {

        var order = ordersMapper.toEntity(createOrderRequest);
        order.setStatus("IN PROGRESS");
        order.setDate(LocalDate.now());
        setOrderForOrderedItems(order);

        return ordersMapper.toDto(orderRepository.save(order));
    }


    public List<OrderDto> getOrdersForUser(String userId) {

        return orderRepository.findByUserId(userId).stream()
                .map(ordersMapper::toDto)
                .collect(Collectors.toList());

    }

    public List<OrderedItemDto> findAllOrderedItemsByUser(String userId){
        return orderedItemsRepository.findAllByUserId(userId)
                .stream()
                .map(orderedItemsMapper::toDto)
                .collect(Collectors.toList());
    }

    private void setOrderForOrderedItems(Order order) {
        order.getOrderedItems().forEach(orderedItem -> orderedItem.setOrder(order));
    }


    public List<OrderedItemDto> findAllOrderedItems() {
        return orderedItemsRepository.findAll()
                .stream()
                .map(orderedItemsMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getOrders() {
        return orderRepository.findAll().stream()
                .map(ordersMapper::toDto)
                .collect(Collectors.toList());
    }

    public void markAsDone(String orderId) {

        var order = orderRepository.findById(Long.valueOf(orderId))
                .orElseThrow(() -> new RuntimeException("Order not found to me marked as DONE"));

        order.setStatus("DONE");

        orderRepository.save(order);
    }
}
