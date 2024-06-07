package com.devteria_spring_boot.identity_service.exception;

public enum ErrorCode {
    USER_EXISTED(1002, "User existed!"),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error!"),
    USERNAME_INVALID(1003, "Username must be at least 3 characters!"),
    INVALID_KEY(1001, "Invalid message key!"),
    PASSWORD_INVALID(1004, "Password must be at least 8 characters"),
    USER_NOT_EXISTED(1005, "User not existed!"),
    UNAUTHENTICATED(1006, "Unauthenticated!")
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
