package com.practice.springng.blog.repository.embeded;

import com.practice.springng.blog.helpers.EntityFactory;
import com.practice.springng.blog.model.User;
import com.practice.springng.blog.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTestEmbedded {
    @Autowired
    UserRepository userRepository;

    @Test
    void saveUserSuccess() {
        User user = EntityFactory.user;
        User savedUser = userRepository.save(user);
        assertThat(savedUser).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(user);
    }

    @Test
    void testUserWithInvalidDataFail() {
        User user = EntityFactory.setUpUser(null, null, null);
        assertThatThrownBy(() -> userRepository.save(user))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void findUserByIdSuccess() {
        User savedUser = userRepository.save(EntityFactory.user);
        Optional<User> user = userRepository.findById(savedUser.getId());
        assertTrue(user.isPresent());
    }

    @Test
    void findUserByNotExistingIdFail() {
        userRepository.save(EntityFactory.user);
        Optional<User> user = userRepository.findById(0L);
        assertFalse(user.isPresent());
    }

    @Test
    void findUserByEmailSuccess() {
        User savedUser = userRepository.save(EntityFactory.user);
        Optional<User> user = userRepository.findUserByEmail(savedUser.getEmail());
        assertTrue(user.isPresent());
    }

    @Test
    void findUserByNotExistingEmailFail() {
        userRepository.save(EntityFactory.user);
        Optional<User> user = userRepository.findUserByEmail("not existing");
        assertFalse(user.isPresent());
    }

    @Test
    void findAll() {
        User savedUser = userRepository.save(EntityFactory.user);
        List<User> expected = Collections.singletonList(savedUser);
        List<User> actual = userRepository.findAll();
        assertIterableEquals(expected, actual);
    }

    @Test
    void updateUser() {
        User savedUser = userRepository.save(EntityFactory.user);
        savedUser.setEmail("updated email");
        userRepository.save(savedUser);
        User updated = userRepository.findById(savedUser.getId()).orElseGet(User::new);
        assertEquals(savedUser.getEmail(), updated.getEmail());
    }

    @Test
    void deleteUser() {
        User savedUser = userRepository.save(EntityFactory.user);
        userRepository.delete(savedUser);
        Optional<User> user = userRepository.findById(savedUser.getId());
        assertTrue(user.isEmpty());
    }
}
