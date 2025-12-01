package com.auth.Auth.App.exception;


public class IllegalArgumentException  extends RuntimeException{
    public IllegalArgumentException() {
        super("Illegal argument provided");
    }

    public IllegalArgumentException(String message) {
        super(message);
    }
}
