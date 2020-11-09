package com.practice.springng.blog.service.impl;

import com.practice.springng.blog.dto.user.AuthenticationResponse;
import com.practice.springng.blog.dto.user.LoginRequest;
import com.practice.springng.blog.dto.user.RegistrationRequest;
import com.practice.springng.blog.exception.DuplicatedEmailException;
import com.practice.springng.blog.exception.NoLoggedInUserException;
import com.practice.springng.blog.helpers.EntityFactory;
import com.practice.springng.blog.model.User;
import com.practice.springng.blog.repository.UserRepository;
import com.practice.springng.blog.security.JwtProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.practice.springng.blog.service.AuthService;


import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    private AuthService authService;


    @BeforeEach
    public void init() {
        authService = new AuthServiceImpl(userRepository, passwordEncoder, authenticationManager, jwtProvider);
    }

    @Test
    @DisplayName("Sign up finished successfully")
    void signUpSuccess() {
        RegistrationRequest registrationRequest = EntityFactory.registrationRequest;
        Mockito.when(userRepository.findUserByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
        Mockito.when(passwordEncoder.encode(Mockito.any(CharSequence.class))).thenReturn("encoded password");
        authService.signUp(registrationRequest);
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }

    @Test
    @DisplayName("Sign up with not existing user")
    void signUpUserNotExists() {
        User  user = EntityFactory.user;
        RegistrationRequest registrationRequest = EntityFactory.registrationRequest;
        Mockito.when(userRepository.findUserByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        assertThatThrownBy(() -> authService.signUp(registrationRequest))
                .isExactlyInstanceOf(DuplicatedEmailException.class)
                .hasMessage(String.format("Email [%s] already exists", registrationRequest.getEmail()));
    }

    @Test
    @DisplayName("Login finished successfully")
    void loginSuccess() {
        LoginRequest loginRequest = EntityFactory.loginRequest;
        Authentication authentication = EntityFactory.authentication;
        Mockito.when(authenticationManager.authenticate(Mockito.any(Authentication.class)))
                .thenReturn(authentication);
        Mockito.when(jwtProvider.generateToken(Mockito.any(Authentication.class))).thenReturn("token");
        SecurityContextHolder.setContext(securityContext);
        AuthenticationResponse expected = new AuthenticationResponse("token", loginRequest.getEmail());
        AuthenticationResponse actual = authService.login(loginRequest);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Successfully get currently logged in user")
    void getCurrentUserSuccess() {
        User  expectedUser = EntityFactory.user;
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(userRepository.findUserByEmail(Mockito.anyString())).thenReturn(Optional.of(expectedUser));
        Mockito.when(authentication.getPrincipal())
                .thenReturn(EntityFactory.userdeails);
        SecurityContextHolder.setContext(securityContext);
        Optional<User> actual = authService.getCurrentUser();
        assertEquals(expectedUser, actual.orElseGet(User::new));
    }

    @Test
    @DisplayName("No authenticated user was found")
    void getCurrentUserFail() {
        Mockito.when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);
        assertThatThrownBy(() -> authService.getCurrentUser())
                .isExactlyInstanceOf(NoLoggedInUserException.class)
                .hasMessage("No logged in user");
    }

    @Test
    @DisplayName("User not found by email from security context")
    void getCurrentUserNotFound() {
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal())
                .thenReturn(EntityFactory.userdeails);
        Mockito.when(userRepository.findUserByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        SecurityContextHolder.setContext(securityContext);
        Optional<User> actual = authService.getCurrentUser();
        assertFalse(actual.isPresent());
    }
}