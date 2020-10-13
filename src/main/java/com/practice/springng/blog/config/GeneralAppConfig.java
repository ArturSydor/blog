package com.practice.springng.blog.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralAppConfig {
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
