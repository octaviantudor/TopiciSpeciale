package com.unibuc.ordersmicroservice.domain.mapper;

import com.unibuc.ordersmicroservice.domain.dto.CreateOrderRequest;
import com.unibuc.ordersmicroservice.domain.dto.OrderDto;
import com.unibuc.ordersmicroservice.domain.entity.Order;
import org.mapstruct.*;

import java.util.HashMap;

@Mapper(componentModel = "spring", uses = {OrderedItemsMapper.class})
public interface OrdersMapper {

    @Mapping(source = "id", target = "id")
    OrderDto toDto(Order order);

    Order toEntity(CreateOrderRequest orderDto);

}
