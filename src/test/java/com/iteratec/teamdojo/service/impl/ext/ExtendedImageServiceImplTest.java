package com.iteratec.teamdojo.service.impl.ext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

import com.iteratec.teamdojo.repository.ext.ExtendedImageRepository;
import com.iteratec.teamdojo.service.dto.ImageDTO;
import com.iteratec.teamdojo.service.mapper.ImageMapper;
import com.iteratec.teamdojo.test.fixtures.ImageResourceFixtures;
import java.awt.image.BufferedImage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ExtendedImageServiceImplTest {

    private final ImageResourceFixtures fixtures = new ImageResourceFixtures();
    private final ExtendedImageRepository repo = mock(ExtendedImageRepository.class);
    private final ImageMapper mapper = mock(ImageMapper.class);
    private final ExtendedImageServiceImpl sut = new ExtendedImageServiceImpl(repo, mapper);

    @Test
    void save_ifLargeImageIsNullAllWillBeNull() {
        final var dto = new ImageDTO();
        dto.setLarge(null);
        dto.setMedium(new byte[] { 0, 1, 2 });
        dto.setSmall(new byte[] { 0, 1, 2 });

        sut.save(dto);

        assertAll(
            () -> assertThat(dto.getLarge()).isNull(),
            () -> assertThat(dto.getMedium()).isNull(),
            () -> assertThat(dto.getSmall()).isNull()
        );
    }

    @Test
    void save_resizesImage() throws Exception {
        final var dto = new ImageDTO();

        dto.setLarge(fixtures.readFile(fixtures.quadraticInputPng));

        sut.save(dto);

        assertAll(
            () -> assertThat(dto.getLarge()).isEqualTo(fixtures.readFile(fixtures.expectedLargePng)),
            () -> assertThat(dto.getMedium()).isEqualTo(fixtures.readFile(fixtures.expectedMediumPng)),
            () -> assertThat(dto.getSmall()).isEqualTo(fixtures.readFile(fixtures.expectedSmallPng))
        );
    }

    @Test
    void save_callsSuperMethod() {
        // There is no easy way to verify if the overridden method is called.
        // The workaround here is to verify the call on the mapper because save must
        // at least call it to convert the DTO to save the entity.
        final var dto = new ImageDTO();

        sut.save(dto);

        verify(mapper, times(1)).toEntity(dto);
    }
}
