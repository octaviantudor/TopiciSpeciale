package com.unibuc.itemsservice.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private String price;
    private String quantity;
    private CategoryDto category;

}
