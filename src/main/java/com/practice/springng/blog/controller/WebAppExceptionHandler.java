package com.practice.springng.blog.controller;

import com.practice.springng.blog.exception.DuplicatedEmailException;
import com.practice.springng.blog.exception.NoLoggedInUserException;
import com.practice.springng.blog.exception.PostNotFoundException;
import com.practice.springng.blog.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebAppExceptionHandler {
    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<?> handleDuplicatedEmail(DuplicatedEmailException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoLoggedInUserException.class)
    public ResponseEntity<?> handleNoLoggedInUser(NoLoggedInUserException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<?> handlePostNotFound(PostNotFoundException ex) {
        return  new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
