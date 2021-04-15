package com.iteratec.teamdojo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportMapperTest {

    private ReportMapper reportMapper;

    @BeforeEach
    public void setUp() {
        reportMapper = new ReportMapperImpl();
    }
}
