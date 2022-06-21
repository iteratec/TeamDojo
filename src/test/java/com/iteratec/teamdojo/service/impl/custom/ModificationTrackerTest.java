package com.iteratec.teamdojo.service.impl.custom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.iteratec.teamdojo.domain.Image;
import com.iteratec.teamdojo.repository.ImageRepository;
import com.iteratec.teamdojo.service.dto.ImageDTO;
import com.iteratec.teamdojo.service.mapper.ImageMapperImpl;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModificationTrackerTest {

    private final ImageRepository repo = mock(ImageRepository.class);
    private final InstantProvider time = mock(InstantProvider.class);
    private final AuditableDataTracker<ImageDTO, Image> sut = new AuditableDataTracker<>(new ImageMapperImpl(), repo::findById);

    @BeforeEach
    void injectInstantProvider() {
        sut.setTime(time);
    }

    @Test
    void modifyCreatedAndUpdatedAt_newlyCreated() {
        final var now = Instant.now();
        when(time.now()).thenReturn(now);
        final var data = new ImageDTO();

        sut.modifyCreatedAndUpdatedAt(data);

        assertAll(
            () -> assertThat(data.getCreatedAt()).as("Created at of result must be now").isEqualTo(now),
            () -> assertThat(data.getUpdatedAt()).as("Updated at of result must be now").isEqualTo(now)
        );
    }

    @Test
    void modifyCreatedAndUpdatedAt_updated() {
        final var now = Instant.now();
        final var yesterday = now.minus(1, ChronoUnit.DAYS);
        when(time.now()).thenReturn(now);
        final var id = 42L;
        final var persisted = new Image();
        persisted.setId(id);
        persisted.createdAt(yesterday);
        persisted.updatedAt(yesterday);
        when(repo.findById(id)).thenReturn(Optional.of(persisted));

        final var data = new ImageDTO();
        data.setId(id);

        sut.modifyCreatedAndUpdatedAt(data);

        assertAll(
            () -> assertThat(data.getCreatedAt()).as("Created at of result must be now").isEqualTo(yesterday),
            () -> assertThat(data.getUpdatedAt()).as("Updated at of result must be now").isEqualTo(now)
        );
    }
}
