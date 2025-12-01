package com.auth.Auth.App.exception;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
    String message,
    HttpStatus statusCode,

    String error
) {
}
