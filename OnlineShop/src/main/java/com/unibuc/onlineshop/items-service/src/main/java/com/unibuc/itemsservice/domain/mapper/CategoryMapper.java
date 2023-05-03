package com.unibuc.itemsservice.domain.mapper;

import com.unibuc.itemsservice.domain.dto.CategoryDto;
import com.unibuc.itemsservice.domain.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto toDto(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
