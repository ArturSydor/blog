package com.practice.springng.blog.service;

import com.practice.springng.blog.dto.post.PostDto;

import java.util.List;

public interface PostService {
    List<PostDto> getAll();

    PostDto getById(Long id);

    void create(PostDto postDto);
}
