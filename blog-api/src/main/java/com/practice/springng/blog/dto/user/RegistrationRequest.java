package com.practice.springng.blog.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RegistrationRequest {
    @NotEmpty
    @NotNull
    private String username;
    @NotEmpty
    @NotNull
    private String email;
    @NotEmpty
    @NotNull
    private String password;
}
