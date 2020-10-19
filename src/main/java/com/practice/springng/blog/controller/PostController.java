package com.practice.springng.blog.controller;

import com.practice.springng.blog.dto.post.PostDto;
import com.practice.springng.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostDto getById(@PathVariable Long id) {
        return postService.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostDto> getAll() {
        return postService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@RequestBody @Valid PostDto postDto) {
        postService.create(postDto);
    }
}
