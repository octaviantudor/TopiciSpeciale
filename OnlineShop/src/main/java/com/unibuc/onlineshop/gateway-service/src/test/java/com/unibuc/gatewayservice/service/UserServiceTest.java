package com.unibuc.gatewayservice.service;

import com.unibuc.gatewayservice.domain.security.Role;
import com.unibuc.gatewayservice.domain.security.User;
import com.unibuc.gatewayservice.domain.security.dto.RegisterRequest;
import com.unibuc.gatewayservice.domain.security.enums.AccountStatus;
import com.unibuc.gatewayservice.domain.security.enums.RoleName;
import com.unibuc.gatewayservice.domain.security.mapper.UserMapper;
import com.unibuc.gatewayservice.exception.AlreadyExistsException;
import com.unibuc.gatewayservice.exception.CustomException;
import com.unibuc.gatewayservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserService userService;


    @Test
    void createUser__ShouldReturnOK(){
        when(userMapper.toEntity(any())).thenReturn(User.builder().build());
        when(passwordEncoder.encode(any())).thenReturn("pass");
        when(roleService.getRoleByRoleName(any())).thenReturn(Role.builder().name(RoleName.ROLE_USER).build());
        when(userRepository.save(any())).thenReturn(User.builder().build());

        userService.createUser(RegisterRequest.builder().build());

        verify(passwordEncoder, times(1)).encode(any());
        verify(userRepository, times(1)).save(any());


    }

    @Test
    void testVerifyIfUsernameOrEmailExists_WhenUsernameExists_ThenThrowAlreadyExistsException() {
        // Arrange
        String username = "testuser";
        String email = "testuser@test.com";
        Mockito.when(userRepository.existsByUsername(username)).thenReturn(true);

        // Act + Assert
        assertThrows(AlreadyExistsException.class, () -> userService.verifyIfUsernameOrEmailExists(username, email));
    }

    @Test
    void testVerifyIfUsernameOrEmailExists_WhenUsernameAndEmailDoNotExist_ThenDoNotThrowException() {
        // Arrange
        String username = "testuser";
        String email = "testuser@test.com";
        Mockito.when(userRepository.existsByUsername(username)).thenReturn(false);
        Mockito.when(userRepository.existsByEmail(email)).thenReturn(false);

        // Act + Assert
        assertDoesNotThrow(() -> userService.verifyIfUsernameOrEmailExists(username, email));
    }

    @Test
    void testFindByUsernameOrEmail_WhenUserExistsWithUsername_ThenReturnUser() {
        // Arrange
        String userNameOrEmail = "testuser";
        User user = new User();
        Mockito.when(userRepository.findByUsernameOrEmail(userNameOrEmail, userNameOrEmail)).thenReturn(Optional.of(user));

        // Act
        User result = userService.findByUsernameOrEmail(userNameOrEmail);

        // Assert
        assertEquals(user, result);
    }


    @Test
    void testFindByUsernameOrEmail_WhenUserExistsWithEmail_ThenReturnUser() {
        // Arrange
        String userNameOrEmail = "testuser@test.com";
        User user = new User();
        Mockito.when(userRepository.findByUsernameOrEmail(userNameOrEmail, userNameOrEmail)).thenReturn(Optional.of(user));

        // Act
        User result = userService.findByUsernameOrEmail(userNameOrEmail);

        // Assert
        assertEquals(user, result);
    }

    @Test
    void testFindByUsernameOrEmail_WhenUserDoesNotExistWithUsernameOrEmail_ThenThrowAlreadyExistsException() {
        // Arrange
        String userNameOrEmail = "testuser";
        Mockito.when(userRepository.findByUsernameOrEmail(userNameOrEmail, userNameOrEmail)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(AlreadyExistsException.class, () -> userService.findByUsernameOrEmail(userNameOrEmail));
    }

}