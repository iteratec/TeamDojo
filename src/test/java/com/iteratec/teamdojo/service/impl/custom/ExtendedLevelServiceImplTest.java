package com.iteratec.teamdojo.service.impl.custom;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.iteratec.teamdojo.domain.Level;
import com.iteratec.teamdojo.repository.custom.ExtendedLevelRepository;
import com.iteratec.teamdojo.service.dto.LevelDTO;
import com.iteratec.teamdojo.service.mapper.DimensionMapperImpl;
import com.iteratec.teamdojo.service.mapper.ImageMapperImpl;
import com.iteratec.teamdojo.service.mapper.LevelMapper;
import com.iteratec.teamdojo.service.mapper.LevelMapperImpl;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.springframework.test.util.ReflectionTestUtils;

class ExtendedLevelServiceImplTest {

    private final ExtendedLevelRepository repo = mock(ExtendedLevelRepository.class);
    private final LevelMapper mapper = new LevelMapperImpl();
    private final ExtendedLevelServiceImpl sut = new ExtendedLevelServiceImpl(repo, mapper);

    @BeforeEach
    void prepareMapper() {
        // This is necessary due to the shitty architecture of JHipster: They autowire the generated
        // sub mappers via private fields – which is highly discouraged by the way – and so we must
        // use reflection magic which will break on field renames, or we must use an extremely heavy
        // @SpringBoot test to have fully working DI at the place.
        ReflectionTestUtils.setField(mapper, "imageMapper", new ImageMapperImpl());
        ReflectionTestUtils.setField(mapper, "dimensionMapper", new DimensionMapperImpl());
    }

    @Test
    void setTime_doesNotAllowNull() {
        assertThrows(NullPointerException.class, () -> sut.setTime(null));
    }

    @Test
    void save_modifyCreatedAtAndUpdatedAtToSameCurrentTimeIfEntityNotExists() {
        final var time = mock(InstantProvider.class);
        final var now = Instant.now();
        when(time.now()).thenReturn(now);
        sut.setTime(time);
        final var input = new LevelDTO();
        input.setId(23L);
        when(repo.findById(input.getId())).thenReturn(Optional.empty());
        when(repo.save(any())).then(AdditionalAnswers.returnsFirstArg());

        var output = sut.save(input);

        assertAll(
            () -> Assertions.assertThat(output).as("Returned value must not be null").isNotNull(),
            () -> Assertions.assertThat(output.getCreatedAt()).as("Created at of result must be now").isEqualTo(now),
            () -> Assertions.assertThat(output.getUpdatedAt()).as("Updated at of result must be now").isEqualTo(now)
        );
    }

    @Test
    void save_modifyUpdatedAtToCurrentTimeIfEntityExists() {
        final var time = mock(InstantProvider.class);
        final var now = Instant.now();
        final var yesterday = now.minus(1, ChronoUnit.DAYS);
        when(time.now()).thenReturn(now);
        sut.setTime(time);
        final var input = new LevelDTO();
        input.setId(23L);
        final var persisted = new Level();
        persisted.setId(input.getId());
        persisted.setCreatedAt(yesterday);
        persisted.setUpdatedAt(yesterday);
        when(repo.findById(input.getId())).thenReturn(Optional.of(persisted));
        when(repo.save(any())).then(AdditionalAnswers.returnsFirstArg());

        var output = sut.save(input);

        assertAll(
            () -> Assertions.assertThat(output).isNotNull(),
            () -> Assertions.assertThat(output.getCreatedAt()).as("Created at of result must be yesterday").isEqualTo(yesterday),
            () -> Assertions.assertThat(output.getUpdatedAt()).as("Updated at of result must be now").isEqualTo(now)
        );
    }
}
