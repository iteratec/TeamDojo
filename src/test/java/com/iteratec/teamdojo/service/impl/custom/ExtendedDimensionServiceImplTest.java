package com.iteratec.teamdojo.service.impl.custom;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.iteratec.teamdojo.domain.Dimension;
import com.iteratec.teamdojo.repository.DimensionRepository;
import com.iteratec.teamdojo.service.dto.DimensionDTO;
import com.iteratec.teamdojo.service.mapper.DimensionMapperImpl;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;

class ExtendedDimensionServiceImplTest {

    private final DimensionRepository repo = mock(DimensionRepository.class);
    private final ExtendedDimensionServiceImpl sut = new ExtendedDimensionServiceImpl(repo, new DimensionMapperImpl());

    @Test
    void setTime_doesNotAllowNull() {
        assertThrows(NullPointerException.class, () -> sut.setTime(null));
    }

    @Test
    @Disabled("TODO #42 Fix tested types")
    void save_modifyCreatedAtAndUpdatedAtToSameCurrentTimeIfEntityNotExists() {
        final var time = mock(InstantProvider.class);
        final var now = Instant.now();
        when(time.now()).thenReturn(now);
        sut.setTime(time);
        final var input = new DimensionDTO();
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
    @Disabled("TODO #42 Fix tested types")
    void save_modifyUpdatedAtToCurrentTimeIfEntityExists() {
        final var time = mock(InstantProvider.class);
        final var now = Instant.now();
        final var yesterday = now.minus(1, ChronoUnit.DAYS);
        when(time.now()).thenReturn(now);
        sut.setTime(time);
        final var input = new DimensionDTO();
        input.setId(23L);
        final var persisted = new Dimension();
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
