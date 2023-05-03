package com.unibuc.gatewayservice.service;

import com.unibuc.gatewayservice.security.userdetails.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    public String getCurrentAuthUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return userPrincipal.getUser().getId().toString();
    }
}
