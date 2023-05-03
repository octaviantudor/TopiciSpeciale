package com.unibuc.gatewayservice.domain.items;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private String price;
    private String quantity;
    private CategoryDto category;

}
