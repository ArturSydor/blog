package com.practice.springng.blog.helpers;

import com.practice.springng.blog.dto.user.LoginRequest;
import com.practice.springng.blog.dto.user.RegistrationRequest;
import com.practice.springng.blog.model.Post;
import com.practice.springng.blog.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collections;

public final class EntityFactory {
    public static final User user = new User(1L, "username", "password", "email", Collections.emptyList());
    public static final Post post = new Post(1L, "Title", "Content", Instant.now(), Instant.now(), user);
    public static final RegistrationRequest registrationRequest = new RegistrationRequest("email", "username", "password");
    public static final UserDetails userdeails = new org.springframework.security.core.userdetails.User(user.getEmail(),
            user.getPassword(), Collections.emptyList());
    public static final LoginRequest loginRequest = new LoginRequest("email", "password");
    public static final Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

}
