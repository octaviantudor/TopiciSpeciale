package com.unibuc.gatewayservice.security.constants;

public enum ErrorCodes {
    UNNAUTHORIZED(401);

    private int errorCode;

    private ErrorCodes(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
