package com.practice.springng.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(Collections.singletonList("Post Test"));
    }

    @PostMapping
    public ResponseEntity<?> create() {
        return ResponseEntity.ok().body("Test JWT");
    }
}
