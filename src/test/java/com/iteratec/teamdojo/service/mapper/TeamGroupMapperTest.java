package com.iteratec.teamdojo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeamGroupMapperTest {

    private TeamGroupMapper teamGroupMapper;

    @BeforeEach
    public void setUp() {
        teamGroupMapper = new TeamGroupMapperImpl();
    }
}
