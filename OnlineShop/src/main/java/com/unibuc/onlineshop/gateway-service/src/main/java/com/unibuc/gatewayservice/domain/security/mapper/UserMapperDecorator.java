package com.unibuc.gatewayservice.domain.security.mapper;


import com.unibuc.gatewayservice.domain.security.User;
import com.unibuc.gatewayservice.domain.security.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

public abstract class UserMapperDecorator implements UserMapper {

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDto userTouserDto(User user) {
        UserDto userDto = userMapper.userTouserDto(user);
        userDto.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList()));
        return userDto;

    }

}
