package com.iteratec.teamdojo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeamSkillMapperTest {

    private TeamSkillMapper teamSkillMapper;

    @BeforeEach
    public void setUp() {
        teamSkillMapper = new TeamSkillMapperImpl();
    }
}
