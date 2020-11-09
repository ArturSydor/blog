package com.practice.springng.blog.repository.embeded;

import com.practice.springng.blog.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@RequiredArgsConstructor
@DataJpaTest
@ActiveProfiles("test")
public class PostRepositoryTestEmbedded {
    PostRepository postRepository;


}
