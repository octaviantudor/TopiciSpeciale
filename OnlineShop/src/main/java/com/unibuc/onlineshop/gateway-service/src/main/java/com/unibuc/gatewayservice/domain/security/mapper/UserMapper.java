package com.unibuc.gatewayservice.domain.security.mapper;

import com.unibuc.gatewayservice.domain.security.User;
import com.unibuc.gatewayservice.domain.security.dto.RegisterRequest;
import com.unibuc.gatewayservice.domain.security.dto.UserDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User toEntity(RegisterRequest registerRequest);

    @Mapping(target = "roles", ignore = true)
    UserDto userTouserDto(User user);
}
