package com.iteratec.teamdojo.service.impl.ext;

import static com.iteratec.teamdojo.test.fixtures.ImageResourceFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

import com.iteratec.teamdojo.repository.ext.ExtendedImageRepository;
import com.iteratec.teamdojo.service.dto.ImageDTO;
import com.iteratec.teamdojo.service.mapper.ImageMapper;
import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.Test;

class ExtendedImageServiceImplTest {

    private final ExtendedImageRepository repo = mock(ExtendedImageRepository.class);
    private final ImageMapper mapper = mock(ImageMapper.class);
    private final ExtendedImageServiceImpl sut = new ExtendedImageServiceImpl(repo, mapper);

    ExtendedImageServiceImplTest() throws NoSuchAlgorithmException {
        super();
    }

    @Test
    void save_ifLargeImageIsNullAllWillBeNull() {
        final var dto = new ImageDTO();
        dto.setLarge(null);
        dto.setLargeContentType("");
        dto.setMedium(new byte[] { 0, 1, 2 });
        dto.setMediumContentType("");
        dto.setSmall(new byte[] { 0, 1, 2 });
        dto.setSmallContentType("");
        dto.setHash("");

        sut.save(dto);

        assertAll(
            () -> assertThat(dto.getLarge()).isNull(),
            () -> assertThat(dto.getLargeContentType()).isNull(),
            () -> assertThat(dto.getMedium()).isNull(),
            () -> assertThat(dto.getMediumContentType()).isNull(),
            () -> assertThat(dto.getSmall()).isNull(),
            () -> assertThat(dto.getSmallContentType()).isNull(),
            () -> assertThat(dto.getHash()).isNull()
        );
    }

    @Test
    void save_resizesImage() {
        final var dto = new ImageDTO();
        dto.setLarge(quadraticInputPng());

        sut.save(dto);

        assertAll(
            () -> assertThat(dto.getLarge()).isEqualTo(expectedLargePng()),
            () -> assertThat(dto.getLargeContentType()).isEqualTo("image/png"),
            () -> assertThat(dto.getMedium()).isEqualTo(expectedMediumPng()),
            () -> assertThat(dto.getMediumContentType()).isEqualTo("image/png"),
            () -> assertThat(dto.getSmall()).isEqualTo(expectedSmallPng()),
            () -> assertThat(dto.getSmallContentType()).isEqualTo("image/png"),
            () -> assertThat(dto.getHash()).isEqualTo(expectedHashOfLargePng())
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

    @Test
    void digest() {
        assertThat(sut.digest("foobar".getBytes())).isEqualTo("3858F62230AC3C915F300C664312C63F");
    }
}
