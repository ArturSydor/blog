package com.practice.springng.blog.service;

import com.practice.springng.blog.dto.user.RegistrationRequest;

public interface AuthService {
    void signUp(RegistrationRequest registrationRequest);
}
