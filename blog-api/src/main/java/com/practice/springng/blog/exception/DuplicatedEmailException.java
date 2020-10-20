package com.practice.springng.blog.exception;

public class DuplicatedEmailException extends RuntimeException {
    public DuplicatedEmailException() {
    }

    public DuplicatedEmailException(String message) {
        super(message);
    }

    public DuplicatedEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
