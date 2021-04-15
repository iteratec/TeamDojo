package com.iteratec.teamdojo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ImageMapperTest {

    private ImageMapper imageMapper;

    @BeforeEach
    public void setUp() {
        imageMapper = new ImageMapperImpl();
    }
}
