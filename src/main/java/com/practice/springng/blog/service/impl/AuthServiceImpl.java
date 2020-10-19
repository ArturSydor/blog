package com.practice.springng.blog.service.impl;

import com.practice.springng.blog.dto.user.LoginRequest;
import com.practice.springng.blog.dto.user.RegistrationRequest;
import com.practice.springng.blog.exception.DuplicatedEmailException;
import com.practice.springng.blog.exception.NoLoggedInUserException;
import com.practice.springng.blog.model.User;
import com.practice.springng.blog.repository.UserRepository;
import com.practice.springng.blog.security.JwtProvider;
import com.practice.springng.blog.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

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

    @Override
    public String login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateToken(authentication);
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void checkIfUserNotExist(String email) {
        if (userRepository.findUserByEmail(email).isPresent()) {
            throw new DuplicatedEmailException(String.format("Email [%s] already exists", email));
        }
    }

    @Override
    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(Objects.isNull(authentication)) {
            throw new NoLoggedInUserException("No logged in user");
        }
        String email = ((org.springframework.security.core.userdetails.User)
                authentication.getPrincipal()).getUsername();
        return userRepository.findUserByEmail(email);
    }
}
