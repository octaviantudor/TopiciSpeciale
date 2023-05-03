package com.unibuc.gatewayservice.service;


import com.unibuc.gatewayservice.domain.security.Role;
import com.unibuc.gatewayservice.domain.security.enums.RoleName;
import com.unibuc.gatewayservice.exception.AppException;
import com.unibuc.gatewayservice.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {

        return roleRepository.findByName(RoleName.ROLE_USER);
    }

    public Role getRoleByRoleName(RoleName roleName){ return roleRepository.findByNameOptional(roleName).orElseThrow(
            () -> new AppException("Invalid role name!", HttpStatus.BAD_REQUEST.toString())
    );}

    public List<Role> findAllRoles(){
        return roleRepository.findAll();
    }
}
