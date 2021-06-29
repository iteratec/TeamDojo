package com.iteratec.teamdojo.service.impl.ext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.NonNull;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ImageResizerTest {

    private static final String QUADRATIC_INPUT_JPG = "quadratic_input.jpg";
    private static final String QUADRATIC_INPUT_PNG = "quadratic_input.png";
    private static final String RECTANGULAR_INPUT_JPG = "rectangular_input.jpg";
    private static final String RECTANGULAR_INPUT_PNG = "rectangular_input.png";
    private static final String EXPECTED_SMALL_PNG = "expected_small.png";
    private static final String EXPECTED_MEDIUM_PNG = "expected_medium.png";
    private static final String EXPECTED_LARGE_PNG = "expected_large.png";
    private static final String EXPECTED_SMALL_JPG = "expected_small.jpg";
    private static final String EXPECTED_MEDIUM_JPG = "expected_medium.jpg";
    private static final String EXPECTED_LARGE_JPG = "expected_large.jpg";
    private static final String EXPECTED_RECTANGULAR_JPG = "expected_rectangular.jpg";
    private static final String EXPECTED_RECTANGULAR_PNG = "expected_rectangular.png";
    private final ImageResizer sut = new ImageResizer();

    private byte[] readFixtureFile(@NonNull final String filename) throws URISyntaxException, IOException {
        final var fixture = this.getClass().getResource(filename).toURI();
        return Files.readAllBytes(Paths.get(fixture));
    }

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
        final var expected = readFixtureFile(EXPECTED_LARGE_PNG);

        final var result = sut.resize(readFixtureFile(QUADRATIC_INPUT_PNG), ImageResizer.MaxSize.LARGE);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void resize_pngToMedium() throws Exception {
        final var expected = readFixtureFile(EXPECTED_MEDIUM_PNG);

        final var result = sut.resize(readFixtureFile(QUADRATIC_INPUT_PNG), ImageResizer.MaxSize.MEDIUM);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void resize_pngToSmall() throws Exception {
        final var expected = readFixtureFile(EXPECTED_SMALL_PNG);

        final var result = sut.resize(readFixtureFile(QUADRATIC_INPUT_PNG), ImageResizer.MaxSize.SMALL);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void resize_jpgToLarge() throws Exception {
        final var expected = readFixtureFile(EXPECTED_LARGE_JPG);

        final var result = sut.resize(readFixtureFile(QUADRATIC_INPUT_JPG), ImageResizer.MaxSize.LARGE);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void resize_jpgToMedium() throws Exception {
        final var expected = readFixtureFile(EXPECTED_MEDIUM_JPG);

        final var result = sut.resize(readFixtureFile(QUADRATIC_INPUT_JPG), ImageResizer.MaxSize.MEDIUM);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void resize_jpgToSmall() throws Exception {
        final var expected = readFixtureFile(EXPECTED_SMALL_JPG);

        final var result = sut.resize(readFixtureFile(QUADRATIC_INPUT_JPG), ImageResizer.MaxSize.SMALL);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void resize_jpgWithLessWidthThanHeight() throws Exception {
        final var expected = readFixtureFile(EXPECTED_RECTANGULAR_JPG);

        final var result = sut.resize(readFixtureFile(RECTANGULAR_INPUT_JPG), ImageResizer.MaxSize.LARGE);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void resize_pngWithLessWidthThanHeight() throws Exception {
        final var expected = readFixtureFile(EXPECTED_RECTANGULAR_PNG);

        final var result = sut.resize(readFixtureFile(RECTANGULAR_INPUT_PNG), ImageResizer.MaxSize.LARGE);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void resize_smallerThanExpectedDoesNotResize() throws Exception {
        final var expected = readFixtureFile(EXPECTED_SMALL_JPG);

        final var result = sut.resize(readFixtureFile(EXPECTED_SMALL_JPG), ImageResizer.MaxSize.LARGE);

        assertThat(result).isEqualTo(expected);
    }
}
