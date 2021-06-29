package com.iteratec.teamdojo.service.impl.ext;

import static com.iteratec.teamdojo.test.fixtures.ImageResourceFixtures.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ImageResizerTest {

    private final ImageResizer sut = new ImageResizer();

    @Test
    void resize_inputMustNotBeNull() {
        assertThrows(NullPointerException.class, () -> sut.resize(null, ImageResizer.MaxSize.LARGE));
    }

    @Test
    void resize_maxMustNotBeNull() {
        assertThrows(NullPointerException.class, () -> sut.resize(new byte[0], null));
    }

    @Test
    void resize_pngToLarge() {
        final var result = sut.resize(quadraticInputPng(), ImageResizer.MaxSize.LARGE);

        assertThat(result).isEqualTo(expectedLargePng());
    }

    @Test
    void resize_pngToMedium() {
        final var result = sut.resize(quadraticInputPng(), ImageResizer.MaxSize.MEDIUM);

        assertThat(result).isEqualTo(expectedMediumPng());
    }

    @Test
    void resize_pngToSmall() {
        final var result = sut.resize(quadraticInputPng(), ImageResizer.MaxSize.SMALL);

        assertThat(result).isEqualTo(expectedSmallPng());
    }

    @Test
    void resize_jpgToLarge() {
        final var result = sut.resize(quadraticInputJpg(), ImageResizer.MaxSize.LARGE);

        assertThat(result).isEqualTo(expectedLargeJpg());
    }

    @Test
    void resize_jpgToMedium() {
        final var result = sut.resize(quadraticInputJpg(), ImageResizer.MaxSize.MEDIUM);

        assertThat(result).isEqualTo(expectedMediumJpg());
    }

    @Test
    void resize_jpgToSmall() {
        final var result = sut.resize(quadraticInputJpg(), ImageResizer.MaxSize.SMALL);

        assertThat(result).isEqualTo(expectedSmallJpg());
    }

    @Test
    void resize_jpgWithLessWidthThanHeight() {
        final var result = sut.resize(rectangularInputJpg(), ImageResizer.MaxSize.LARGE);

        assertThat(result).isEqualTo(expectedRectangularJpg());
    }

    @Test
    void resize_pngWithLessWidthThanHeight() {
        final var result = sut.resize(rectangularInputPng(), ImageResizer.MaxSize.LARGE);

        assertThat(result).isEqualTo(expectedRectangularPng());
    }

    @Test
    void resize_smallerThanExpectedDoesNotResize() {
        final var result = sut.resize(expectedSmallJpg(), ImageResizer.MaxSize.LARGE);

        assertThat(result).isEqualTo(expectedSmallJpg());
    }
}
