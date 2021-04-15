package com.iteratec.teamdojo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrganisationMapperTest {

    private OrganisationMapper organisationMapper;

    @BeforeEach
    public void setUp() {
        organisationMapper = new OrganisationMapperImpl();
    }
}
