package com.practice.springng.blog.service.impl;

import com.practice.springng.blog.model.User;
import com.practice.springng.blog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {
    @Mock
    private UserRepository userRepository;

    private UserDetailsService userDetailsService;

    @BeforeEach
    void init() {
        userDetailsService = new UserDetailsServiceImpl(userRepository);
    }

    @Test
    void loadUserByUsernameSuccess() {
        User  user = new User(1L, "username", "password", "email", Collections.emptyList());
        Mockito.when(userRepository.findUserByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        UserDetails expected = new org.springframework.security.core.userdetails.User(user.getEmail(),
                        user.getPassword(), true, true, true,
                        true, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        UserDetails  actual = userDetailsService.loadUserByUsername(user.getEmail());
        assertEquals(expected, actual);
    }

    @Test
    void loadUserByEmailFail() {
        Mockito.when(userRepository.findUserByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("email"))
                .isExactlyInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User with email=[email] not found");
    }
}