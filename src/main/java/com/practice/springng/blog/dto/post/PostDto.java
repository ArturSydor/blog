package com.practice.springng.blog.dto.post;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
public class PostDto {
    private Long id;
    @NotNull
    @NotEmpty
    private String title;
    @NotNull
    @NotEmpty
    private String content;
    private Instant createdOn;
    private Instant updatedOn;
    private String username;
}
