package com.iteratec.teamdojo.service.impl.ext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.iteratec.teamdojo.test.fixtures.ImageResourceFixtures;
import org.junit.jupiter.api.Test;

class ImageResizerTest {

    private final ImageResourceFixtures fixtures = new ImageResourceFixtures();
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
    void resize_pngToLarge() throws Exception {
        final var expected = fixtures.readFile(fixtures.expectedLargePng);

        final var result = sut.resize(fixtures.readFile(fixtures.quadraticInputPng), ImageResizer.MaxSize.LARGE);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void resize_pngToMedium() throws Exception {
        final var expected = fixtures.readFile(fixtures.expectedMediumPng);

        final var result = sut.resize(fixtures.readFile(fixtures.quadraticInputPng), ImageResizer.MaxSize.MEDIUM);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void resize_pngToSmall() throws Exception {
        final var expected = fixtures.readFile(fixtures.expectedSmallPng);

        final var result = sut.resize(fixtures.readFile(fixtures.quadraticInputPng), ImageResizer.MaxSize.SMALL);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void resize_jpgToLarge() throws Exception {
        final var expected = fixtures.readFile(fixtures.expectedLargeJpg);

        final var result = sut.resize(fixtures.readFile(fixtures.quadraticInputJpg), ImageResizer.MaxSize.LARGE);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void resize_jpgToMedium() throws Exception {
        final var expected = fixtures.readFile(fixtures.expectedMediumJpg);

        final var result = sut.resize(fixtures.readFile(fixtures.quadraticInputJpg), ImageResizer.MaxSize.MEDIUM);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void resize_jpgToSmall() throws Exception {
        final var expected = fixtures.readFile(fixtures.expectedSmallJpg);

        final var result = sut.resize(fixtures.readFile(fixtures.quadraticInputJpg), ImageResizer.MaxSize.SMALL);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void resize_jpgWithLessWidthThanHeight() throws Exception {
        final var expected = fixtures.readFile(fixtures.expectedRectangularJpg);

        final var result = sut.resize(fixtures.readFile(fixtures.rectangularInputJpg), ImageResizer.MaxSize.LARGE);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void resize_pngWithLessWidthThanHeight() throws Exception {
        final var expected = fixtures.readFile(fixtures.expectedRectangularPng);

        final var result = sut.resize(fixtures.readFile(fixtures.rectangularInputPng), ImageResizer.MaxSize.LARGE);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void resize_smallerThanExpectedDoesNotResize() throws Exception {
        final var expected = fixtures.readFile(fixtures.expectedSmallJpg);

        final var result = sut.resize(fixtures.readFile(fixtures.expectedSmallJpg), ImageResizer.MaxSize.LARGE);

        assertThat(result).isEqualTo(expected);
    }
}
