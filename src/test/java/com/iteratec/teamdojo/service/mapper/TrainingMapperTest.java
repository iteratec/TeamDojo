package com.iteratec.teamdojo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainingMapperTest {

    private TrainingMapper trainingMapper;

    @BeforeEach
    public void setUp() {
        trainingMapper = new TrainingMapperImpl();
    }
}
