package com.unibuc.gatewayservice.security.handler;

import com.unibuc.gatewayservice.domain.security.dto.LoginRequestDto;
import com.unibuc.gatewayservice.exception.AppException;
import com.unibuc.gatewayservice.security.constants.AuthConstants;
import com.unibuc.gatewayservice.security.constants.ErrorCodes;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * A handler class responsible to manipulate the request
 */
@Component
public class RequestHandler {

    /**
     * Decoding user credentials
     *
     * @return LoginRequest with user name and password
     */
    public LoginRequestDto decodeCredentials(String authCredentials) {
        System.out.println(authCredentials);
        String[] authParts = authCredentials.split("\\s+");
        String authInfo = authParts[0];
        String[] credentials = authInfo.split(":");
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setUsername(credentials[0]);
        loginRequest.setPassword(credentials[1]);
        return loginRequest;
    }

    /**
     * Get jwt from request
     *
     * @return JWT
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AuthConstants.AUTH_KEY);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        throw new AppException("Jwt is empty or Bearer missing", ErrorCodes.UNNAUTHORIZED.toString());
    }

    /**
     * Get jwt from string request
     *
     * @return JWT
     */
    public String getJwtFromStringRequest(String request) {
        if (StringUtils.hasText(request) && request.startsWith("Bearer ")) {
            return request.substring(7);
        }
        throw new AppException("Jwt is empty or Bearer missing", ErrorCodes.UNNAUTHORIZED.toString());
    }
}
