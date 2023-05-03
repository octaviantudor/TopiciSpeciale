package com.unibuc.gatewayservice.controller;

import com.unibuc.gatewayservice.domain.security.dto.LoginRequestDto;
import com.unibuc.gatewayservice.domain.security.dto.LoginResponseDto;
import com.unibuc.gatewayservice.domain.security.dto.RegisterRequest;
import com.unibuc.gatewayservice.domain.security.mapper.UserMapper;
import com.unibuc.gatewayservice.security.jwt.JwtTokenProvider;
import com.unibuc.gatewayservice.security.userdetails.CustomUserDetailsService;
import com.unibuc.gatewayservice.service.UserService;
import com.unibuc.gatewayservice.util.HttpResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Auth Controller, An entry class for all incoming requests
 */
@RequiredArgsConstructor
@Slf4j
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    private final UserService userService;
    private final UserMapper userMapper;

    private final HttpResponseUtil httpResponseUtil;

    /**
     * Validate the credentials and generate the jwt tokens
     *
     * @return access token and refresh token
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {

        var userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());

        authenticate(loginRequest.getUsername(), loginRequest.getPassword(), userDetails.getAuthorities());


        var accessJwt = tokenProvider.generateAccessToken(userDetails);

        var userDTO = userMapper.userTouserDto(userService.findByUsernameOrEmail(userDetails.getUsername()));

        return ResponseEntity.ok(LoginResponseDto.builder().accessToken(accessJwt).userDetails(userDTO).build());
    }

    private void authenticate(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password, authorities));
    }



    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        log.info("Attempting to create an account with the username: {}, and email: {}", registerRequest.getUsername(), registerRequest.getEmail());

        userService.verifyIfUsernameOrEmailExists(registerRequest.getUsername(), registerRequest.getEmail());
        userService.createUser(registerRequest);


        return ResponseEntity
                .ok(httpResponseUtil.createHttpResponse(HttpStatus.CREATED, "User registered successfully"));
    }


}
