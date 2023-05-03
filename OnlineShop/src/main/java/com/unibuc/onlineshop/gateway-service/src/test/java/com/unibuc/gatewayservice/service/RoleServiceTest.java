package com.unibuc.gatewayservice.service;

import com.unibuc.gatewayservice.domain.security.Role;
import com.unibuc.gatewayservice.domain.security.enums.RoleName;
import com.unibuc.gatewayservice.exception.AppException;
import com.unibuc.gatewayservice.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;


    @Test
    void shouldReturnUserRole() {
        // Given
        Role role = Role.builder().name(RoleName.ROLE_USER).build();
        Mockito.when(roleRepository.findByName(RoleName.ROLE_USER)).thenReturn(role);

        // When
        Role userRole = roleService.getUserRole();

        // Then
        Assertions.assertEquals(role, userRole);
        Mockito.verify(roleRepository, Mockito.times(1)).findByName(RoleName.ROLE_USER);
    }

    @Test
    void shouldReturnRoleByRoleName() {
        // Given
        RoleName roleName = RoleName.ROLE_ADMIN;
        Role role = Role.builder().name(roleName).build();
        Mockito.when(roleRepository.findByNameOptional(roleName)).thenReturn(Optional.of(role));

        // When
        Role foundRole = roleService.getRoleByRoleName(roleName);

        // Then
        Assertions.assertEquals(role, foundRole);
        Mockito.verify(roleRepository, Mockito.times(1)).findByNameOptional(roleName);
    }


    @Test
    void shouldThrowAppExceptionWhenRoleNotFound() {
        // Given
        RoleName roleName = RoleName.ROLE_ADMIN;
        Mockito.when(roleRepository.findByNameOptional(roleName)).thenReturn(Optional.empty());

        // When
        Assertions.assertThrows(AppException.class, () -> roleService.getRoleByRoleName(roleName));

        // Then
        Mockito.verify(roleRepository, Mockito.times(1)).findByNameOptional(roleName);
    }


    @Test
    void shouldReturnAllRoles() {
        // Given
        List<Role> roles = Arrays.asList(Role.builder().name(RoleName.ROLE_USER).build(), Role.builder().name(RoleName.ROLE_ADMIN).build());
        Mockito.when(roleRepository.findAll()).thenReturn(roles);

        // When
        List<Role> allRoles = roleService.findAllRoles();

        // Then
        Assertions.assertEquals(roles, allRoles);
        Mockito.verify(roleRepository, Mockito.times(1)).findAll();
    }

}