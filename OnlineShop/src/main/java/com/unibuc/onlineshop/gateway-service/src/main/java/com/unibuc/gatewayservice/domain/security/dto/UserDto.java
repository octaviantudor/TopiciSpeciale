package com.unibuc.gatewayservice.domain.security.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class UserDto {

    private String username;

    private String email;

    private String name;

    private List<String> roles;
}
