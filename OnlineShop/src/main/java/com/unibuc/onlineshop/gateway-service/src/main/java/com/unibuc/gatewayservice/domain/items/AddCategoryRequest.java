package com.unibuc.gatewayservice.domain.items;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddCategoryRequest {

    private String name;
    private String description;
}
