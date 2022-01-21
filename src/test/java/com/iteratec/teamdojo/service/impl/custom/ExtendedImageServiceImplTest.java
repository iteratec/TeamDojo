package com.iteratec.teamdojo.service.impl.custom;

import static com.iteratec.teamdojo.test.fixtures.ImageResourceFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.*;

import com.iteratec.teamdojo.domain.Image;
import com.iteratec.teamdojo.repository.custom.ExtendedImageRepository;
import com.iteratec.teamdojo.service.dto.ImageDTO;
import com.iteratec.teamdojo.service.mapper.ImageMapper;
import com.iteratec.teamdojo.service.mapper.ImageMapperImpl;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.AdditionalAnswers;

class ExtendedImageServiceImplTest {

    private final ExtendedImageRepository repo = mock(ExtendedImageRepository.class);
    private final ImageMapper mapper = new ImageMapperImpl();
    private final ExtendedImageServiceImpl sut = new ExtendedImageServiceImpl(repo, mapper);

    ExtendedImageServiceImplTest() throws NoSuchAlgorithmException {
        super();
    }

    @Test
    void save_ifLargeImageIsNullResetAll() {
        final var image = new ImageDTO();
        image.setLarge(null);
        image.setLargeContentType("");
        image.setMedium(new byte[] { 0, 1, 2 });
        image.setMediumContentType("");
        image.setSmall(new byte[] { 0, 1, 2 });
        image.setSmallContentType("");
        image.setHash("");

        sut.save(image);

        assertAll(
            () -> assertThat(image.getLarge()).isNull(),
            () -> assertThat(image.getLargeContentType()).isNull(),
            () -> assertThat(image.getMedium()).isNull(),
            () -> assertThat(image.getMediumContentType()).isNull(),
            () -> assertThat(image.getSmall()).isNull(),
            () -> assertThat(image.getSmallContentType()).isNull(),
            () -> assertThat(image.getHash()).isNull()
        );
    }

    @Test
    void save_resizesImage() {
        final var image = new ImageDTO();
        image.setLarge(quadraticInputPng());

        sut.save(image);

        assertAll(
            () -> assertThat(image.getLarge()).isEqualTo(expectedLargePng()),
            () -> assertThat(image.getLargeContentType()).isEqualTo("image/png"),
            () -> assertThat(image.getMedium()).isEqualTo(expectedMediumPng()),
            () -> assertThat(image.getMediumContentType()).isEqualTo("image/png"),
            () -> assertThat(image.getSmall()).isEqualTo(expectedSmallPng()),
            () -> assertThat(image.getSmallContentType()).isEqualTo("image/png"),
            () -> assertThat(image.getHash()).isEqualTo(expectedHashOfLargePng())
        );
    }

    @Test
    void save_ifAllImagesAreSetSaveAsIs() {
        final var image = new ImageDTO();
        image.setLarge(expectedLargePng());
        image.setLargeContentType("image/png");
        image.setMedium(expectedMediumPng());
        image.setMediumContentType("image/png");
        image.setSmall(expectedSmallPng());
        image.setSmallContentType("image/png");
        image.setHash(expectedHashOfLargePng());

        sut.save(image);

        assertAll(
            () -> assertThat(image.getLarge()).isEqualTo(expectedLargePng()),
            () -> assertThat(image.getLargeContentType()).isEqualTo("image/png"),
            () -> assertThat(image.getMedium()).isEqualTo(expectedMediumPng()),
            () -> assertThat(image.getMediumContentType()).isEqualTo("image/png"),
            () -> assertThat(image.getSmall()).isEqualTo(expectedSmallPng()),
            () -> assertThat(image.getSmallContentType()).isEqualTo("image/png"),
            () -> assertThat(image.getHash()).isEqualTo(expectedHashOfLargePng())
        );
    }

    @Test
    void save_callsSuperMethod() {
        // There is no easy way to verify if the overridden method is called.
        // The workaround here is to verify the call on the repo because save must
        // at least call it to convert the DTO to save the entity.
        sut.save(new ImageDTO());

        verify(repo, times(1)).save(any());
    }

    @ParameterizedTest
    @MethodSource("imagesToConsiderResizing")
    void shouldResizeImage_doNotResizeIfAllImagesAreSet(final ImageDTO image, final boolean expected) {
        assertThat(sut.shouldResizeImage(image)).isEqualTo(expected);
    }

    @SuppressWarnings("unused") // Used for parameterized test.
    static Stream<Arguments> imagesToConsiderResizing() {
        return Stream.of(
            arguments(image(new byte[0], new byte[0], new byte[0]), false),
            arguments(image(new byte[0], new byte[0], null), true),
            arguments(image(new byte[0], null, new byte[0]), true),
            arguments(image(new byte[0], null, null), true),
            arguments(image(null, new byte[0], new byte[0]), false),
            arguments(image(null, new byte[0], null), false),
            arguments(image(null, null, new byte[0]), false),
            arguments(image(null, null, null), false)
        );
    }

    private static ImageDTO image(final byte[] large, final byte[] medium, final byte[] small) {
        final var image = new ImageDTO();
        image.setLarge(large);
        image.setMedium(medium);
        image.setSmall(small);
        return image;
    }

    @Test
    void shouldResetImages_trueIfLargeImageIsNull() {
        final var image = new ImageDTO();
        image.setLarge(null);

        assertThat(sut.shouldResetImages(image)).isTrue();
    }

    @Test
    void shouldResetImages_falseIfLargeImageIsNotNull() {
        final var image = new ImageDTO();
        image.setLarge(new byte[0]);

        assertThat(sut.shouldResetImages(image)).isFalse();
    }

    @Test
    void digest() {
        assertThat(sut.digest("foobar".getBytes())).isEqualTo("3858F62230AC3C915F300C664312C63F");
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
        final var input = new ImageDTO();
        input.setId(23L);
        when(repo.findById(input.getId())).thenReturn(Optional.empty());
        when(repo.save(any())).then(AdditionalAnswers.returnsFirstArg());

        var output = sut.save(input);

        assertAll(
            () -> assertThat(output).as("Returned value must not be null").isNotNull(),
            () -> assertThat(output.getCreatedAt()).as("Created at of result must be now").isEqualTo(now),
            () -> assertThat(output.getUpdatedAt()).as("Updated at of result must be now").isEqualTo(now)
        );
    }

    @Test
    void save_modifyUpdatedAtToCurrentTimeIfEntityExists() {
        final var time = mock(InstantProvider.class);
        final var now = Instant.now();
        final var yesterday = now.minus(1, ChronoUnit.DAYS);
        when(time.now()).thenReturn(now);
        sut.setTime(time);
        final var input = new ImageDTO();
        input.setId(23L);
        final var persisted = new Image();
        persisted.setId(input.getId());
        persisted.setCreatedAt(yesterday);
        persisted.setUpdatedAt(yesterday);
        when(repo.findById(input.getId())).thenReturn(Optional.of(persisted));
        when(repo.save(any())).then(AdditionalAnswers.returnsFirstArg());

        var output = sut.save(input);

        assertAll(
            () -> assertThat(output).isNotNull(),
            () -> assertThat(output.getCreatedAt()).as("Created at of result must be yesterday").isEqualTo(yesterday),
            () -> assertThat(output.getUpdatedAt()).as("Updated at of result must be now").isEqualTo(now)
        );
    }
}
