package com.iteratec.teamdojo.service.impl.custom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;

import com.iteratec.teamdojo.repository.SkillRepository;
import com.iteratec.teamdojo.service.mapper.SkillMapper;
import com.iteratec.teamdojo.service.mapper.SkillMapperImpl;
import org.junit.jupiter.api.Test;

class ExtendedSkillServiceImplTest {

    private final SkillRepository repository = mock(SkillRepository.class);
    private final SkillMapper mapper = new SkillMapperImpl();
    private final ExtendedSkillServiceImpl sut = new ExtendedSkillServiceImpl(repository, mapper);

    @Test
    @SuppressWarnings("UnnecessaryBoxing") // We explicitly test for that.
    void nullSaveGet() {
        assertAll(
            () -> assertThat(sut.nullSaveGet((Integer) null)).isEqualTo(0),
            () -> assertThat(sut.nullSaveGet(Integer.valueOf(42))).isEqualTo(42),
            () -> assertThat(sut.nullSaveGet((Double) null)).isEqualTo(0),
            () -> assertThat(sut.nullSaveGet(Double.valueOf(42.23))).isEqualTo(42.23)
        );
    }
}
