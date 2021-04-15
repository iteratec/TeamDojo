package com.iteratec.teamdojo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BadgeSkillMapperTest {

    private BadgeSkillMapper badgeSkillMapper;

    @BeforeEach
    public void setUp() {
        badgeSkillMapper = new BadgeSkillMapperImpl();
    }
}
