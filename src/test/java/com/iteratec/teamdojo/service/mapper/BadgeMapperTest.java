package com.iteratec.teamdojo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BadgeMapperTest {

    private BadgeMapper badgeMapper;

    @BeforeEach
    public void setUp() {
        badgeMapper = new BadgeMapperImpl();
    }
}
