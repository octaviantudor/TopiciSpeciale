package com.unibuc.gatewayservice.domain.items;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
}
