package com.iteratec.teamdojo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LevelMapperTest {

    private LevelMapper levelMapper;

    @BeforeEach
    public void setUp() {
        levelMapper = new LevelMapperImpl();
    }
}
