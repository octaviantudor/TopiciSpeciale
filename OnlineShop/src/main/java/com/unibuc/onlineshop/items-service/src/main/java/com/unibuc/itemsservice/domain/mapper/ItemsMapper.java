package com.unibuc.itemsservice.domain.mapper;

import com.unibuc.itemsservice.domain.dto.ItemDto;
import com.unibuc.itemsservice.domain.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemsMapper {
    private final CategoryMapper categoryMapper;

    public ItemDto toDto(Item item){
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice().toString())
                .description(item.getDescription())
                .quantity(item.getQuantity().toString())
                .category(categoryMapper.toDto(item.getCategory()))
                .build();
    }
}
