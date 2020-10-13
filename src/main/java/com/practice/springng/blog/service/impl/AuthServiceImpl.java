package com.practice.springng.blog.service.impl;

import com.practice.springng.blog.dto.user.RegistrationRequest;
import com.practice.springng.blog.exception.DuplicatedEmailException;
import com.practice.springng.blog.model.User;
import com.practice.springng.blog.repository.UserRepository;
import com.practice.springng.blog.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void signUp(RegistrationRequest registrationRequest) {
        checkIfUserNotExist(registrationRequest.getEmail());
        User user = new User();
        user.setEmail(registrationRequest.getEmail());
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(encryptPassword(registrationRequest.getPassword()));
        userRepository.save(user);
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void checkIfUserNotExist(String email) {
        if (userRepository.findUserByEmail(email).isPresent()) {
            throw new DuplicatedEmailException(String.format("Email [%s] already exists", email));
        }
    }
}
