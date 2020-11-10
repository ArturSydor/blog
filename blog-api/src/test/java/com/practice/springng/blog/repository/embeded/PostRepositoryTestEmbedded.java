package com.practice.springng.blog.repository.embeded;

import com.practice.springng.blog.helpers.EntityFactory;
import com.practice.springng.blog.model.Post;
import com.practice.springng.blog.model.User;
import com.practice.springng.blog.repository.PostRepository;
import com.practice.springng.blog.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolationException;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class PostRepositoryTestEmbedded {
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    void savePostSuccess() {
        User user = userRepository.save(EntityFactory.user);
        Post post = EntityFactory.setUpPost("test", "test", user);
        Post savedPost = postRepository.save(post);
        assertThat(savedPost).usingRecursiveComparison().ignoringFields("id").isEqualTo(post);
    }

    @Test
    void savePostWithInvalidDataFail() {
        Post post = EntityFactory.setUpPost(null, null, null);
        assertThatThrownBy(() -> postRepository.save(post))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void findPostByIdSuccess() {
        User user = userRepository.save(EntityFactory.user);
        Post postToSave = EntityFactory.setUpPost("test", "test", user);
        Post savedPost = postRepository.save(postToSave);
        Post post = postRepository.findById(savedPost.getId()).orElseGet(() -> null);
        assertNotNull(post);
    }

    @Test
    void findByNotExistingIdFail() {
        Post post = postRepository.findById(0L).orElseGet(() -> null);
        assertNull(post);
    }

    @Test
    void findAll() {
        User user = userRepository.save(EntityFactory.user);
        Post postToSave = EntityFactory.setUpPost("test", "test", user);
        Post savedPost = postRepository.save(postToSave);
        List<Post> expected = Collections.singletonList(savedPost);
        List<Post> actual = postRepository.findAll();
        assertIterableEquals(expected, actual);
    }

    @Test
    void updatePost() {
        User user = userRepository.save(EntityFactory.user);
        Post postToSave = EntityFactory.setUpPost("test", "test", user);
        Post savedPost = postRepository.save(postToSave);
        savedPost.setTitle("updated");
        postRepository.save(savedPost);
        Post updated = postRepository.findById(savedPost.getId()).orElseGet(Post::new);
        assertEquals("updated", updated.getTitle());
    }

    @Test
    void deletePost() {
        User user = userRepository.save(EntityFactory.user);
        Post postToSave = EntityFactory.setUpPost("test", "test", user);
        Post savedPost = postRepository.save(postToSave);
        postRepository.delete(savedPost);
        Post post = postRepository.findById(savedPost.getId()).orElseGet(() -> null);
        assertNull(post);
    }
}
