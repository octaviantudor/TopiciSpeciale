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
import com.unibuc.gatewayservice.security.constants.AuthConstants;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;


    public void verifyIfUsernameOrEmailExists(String username, String email) {
        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
            throw new AlreadyExistsException(AuthConstants.USERNAME_OR_EMAIL_EXIST);
        }
    }

    public User findByUsernameOrEmail(String userNameOrEmail) {
        return userRepository.findByUsernameOrEmail(userNameOrEmail, userNameOrEmail)
                .orElseThrow(() -> new AlreadyExistsException(AuthConstants.USERNAME_OR_EMAIL_EXIST));
    }

    public void createUser(RegisterRequest registerRequest) {
        var user = userMapper.toEntity(registerRequest);

        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        user.setAccountStatus(AccountStatus.ACTIVE);

        if (Objects.isNull(registerRequest.getRoleName())){
            registerRequest.setRoleName(RoleName.ROLE_USER);
        }

        if (registerRequest.getRoleName().isRoleAdmin()) {

            throw new CustomException("INVALID USER ROLE: " + registerRequest.getRoleName());
        }

        user.setRoles(Collections.singleton(roleService.getRoleByRoleName(registerRequest.getRoleName())));

        userRepository.save(user);
    }
}
