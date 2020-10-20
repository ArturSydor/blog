package com.practice.springng.blog.exception;

public class NoLoggedInUserException extends RuntimeException {
    public NoLoggedInUserException() {
    }

    public NoLoggedInUserException(String message) {
        super(message);
    }

    public NoLoggedInUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
