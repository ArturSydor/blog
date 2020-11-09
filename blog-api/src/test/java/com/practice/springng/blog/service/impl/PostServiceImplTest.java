package com.practice.springng.blog.service.impl;

import com.practice.springng.blog.dto.post.PostDto;
import com.practice.springng.blog.exception.PostNotFoundException;
import com.practice.springng.blog.exception.UserNotFoundException;
import com.practice.springng.blog.helpers.EntityFactory;
import com.practice.springng.blog.model.Post;
import com.practice.springng.blog.model.User;
import com.practice.springng.blog.repository.PostRepository;
import com.practice.springng.blog.service.AuthService;
import com.practice.springng.blog.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.parser.Entity;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private AuthService authService;

    private PostService postService;

    private Post post;

    private PostDto expectedPostDto;

    @BeforeEach
    public void init() {
        postService = new PostServiceImpl(authService, postRepository);
        post = EntityFactory.post;
        expectedPostDto = mapDtoToEntity(post);
    }

    private PostDto mapDtoToEntity(Post post) {
        return new PostDto(post.getId(), post.getTitle(), post.getContent(), post.getCreatedOn(),
                post.getUpdatedOn(), post.getUser().getUsername());
    }

    @Test
    @DisplayName("Find all posts")
    void getAll() {
        Mockito.when(postRepository.findAll()).thenReturn(Collections.singletonList(post));
        List<PostDto> expected = Collections.singletonList(expectedPostDto);
        List<PostDto> actual = postService.getAll();
        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Find certain post by ID")
    void getByIdSuccess() {
        Long postId = 1L;
        Mockito.when(postRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(post));
        PostDto actual = postService.getById(postId);
        assertEquals(expectedPostDto, actual);
    }

    @Test
    @DisplayName("Find post by passed null id")
    void getByNullId() {
        assertThatThrownBy(() -> postService.getById(null)).isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Try to find not existing user by id")
    void getByIdNotExistingUser() {
        Long postId = 1L;
        Mockito.when(postRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> postService.getById(postId))
                .isExactlyInstanceOf(PostNotFoundException.class)
                .hasMessage(String.format("Post with id %s was not found", postId));
    }

    @Test
    @DisplayName("Save new post for current loged in user")
    void createSuccess() {
        Mockito.when(authService.getCurrentUser()).thenReturn(Optional.of(EntityFactory.user));
        Mockito.when(postRepository.save(Mockito.any(Post.class))).thenReturn(post);
        postService.create(expectedPostDto);
        Mockito.verify(postRepository, Mockito.times(1)).save(Mockito.any(Post.class));
    }

    @Test
    @DisplayName("Failed to create post without loged in user")
    void createFail() {
        Mockito.when(authService.getCurrentUser()).thenReturn(Optional.empty());
        assertThatThrownBy(() -> postService.create(expectedPostDto))
                .isExactlyInstanceOf(UserNotFoundException.class)
                .hasMessage("No user found to associate with post");
    }
}