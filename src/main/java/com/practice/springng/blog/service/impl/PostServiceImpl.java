package com.practice.springng.blog.service.impl;

import com.practice.springng.blog.dto.post.PostDto;
import com.practice.springng.blog.exception.PostNotFoundException;
import com.practice.springng.blog.exception.UserNotFoundException;
import com.practice.springng.blog.model.Post;
import com.practice.springng.blog.model.User;
import com.practice.springng.blog.repository.PostRepository;
import com.practice.springng.blog.repository.UserRepository;
import com.practice.springng.blog.service.AuthService;
import com.practice.springng.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final AuthService authService;
    private final PostRepository postRepository;

    @Override
    public List<PostDto> getAll() {
        return postRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private PostDto mapToDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setCreatedOn(post.getCreatedOn());
        dto.setUpdatedOn(post.getUpdatedOn());
        dto.setUsername(post.getUser().getUsername());
        return dto;
    }

    @Override
    public PostDto getById(Long id) {
        Objects.requireNonNull(id);
        Post post = postRepository.findById(id).orElseThrow(
                () -> new PostNotFoundException(String.format("Post with id %s was not found", id)));
        return mapToDto(post);
    }

    @Override
    public void create(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCreatedOn(Instant.now());
        post.setUpdatedOn(Instant.now());
        User user = authService.getCurrentUser()
                .orElseThrow(() -> new UserNotFoundException("No user found to associate with post"));
        post.setUser(user);
        postRepository.save(post);
    }
}
