package com.iteratec.teamdojo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LevelSkillMapperTest {

    private LevelSkillMapper levelSkillMapper;

    @BeforeEach
    public void setUp() {
        levelSkillMapper = new LevelSkillMapperImpl();
    }
}
