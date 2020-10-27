package com.practice.springng.blog.service;

import com.practice.springng.blog.dto.user.AuthenticationResponse;
import com.practice.springng.blog.dto.user.LoginRequest;
import com.practice.springng.blog.dto.user.RegistrationRequest;
import com.practice.springng.blog.model.User;

import java.util.Optional;

public interface AuthService {
    void signUp(RegistrationRequest registrationRequest);

    AuthenticationResponse login(LoginRequest loginRequest);

    Optional<User> getCurrentUser();
}
