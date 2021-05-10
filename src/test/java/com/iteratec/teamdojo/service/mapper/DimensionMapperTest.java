package com.iteratec.teamdojo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DimensionMapperTest {

    private DimensionMapper dimensionMapper;

    @BeforeEach
    public void setUp() {
        dimensionMapper = new DimensionMapperImpl();
    }
}
