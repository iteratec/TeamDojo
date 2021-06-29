package com.iteratec.teamdojo.test.fixtures;

import java.io.IOError;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.NonNull;

/**
 * Externalized test fixtures for all components doing image resizing
 */
public final class ImageResourceFixtures {

    private static final String DEFAULT_HASH = "3C87DDFCADF9B2AD0A6DE4B491B71D7F";
    private static final String UPDATED_HASH = "08332C69A77B3D73376DF2FC43D9B0C0"; // XXX: Necessary?
    public final String quadraticInputJpg = "quadratic_input.jpg";
    public final String quadraticInputPng = "quadratic_input.png";
    public final String rectangularInputJpg = "rectangular_input.jpg";
    public final String rectangularInputPng = "rectangular_input.png";
    public final String expectedSmallPng = "expected_small.png";
    public final String expectedMediumPng = "expected_medium.png";
    public final String expectedLargePng = "expected_large.png";
    public final String expectedSmallJpg = "expected_small.jpg";
    public final String expectedMediumJpg = "expected_medium.jpg";
    public final String expectedLargeJpg = "expected_large.jpg";
    public final String expectedRectangularJpg = "expected_rectangular.jpg";
    public final String expectedRectangularPng = "expected_rectangular.png";

    private static ImageResourceFixtures create() {
        return new ImageResourceFixtures();
    }

    public static byte[] expectedSmallPng() {
        return readFile(create().expectedSmallPng);
    }

    public static byte[] expectedMediumPng() {
        return readFile(create().expectedMediumPng);
    }

    public static byte[] expectedLargePng() {
        return readFile(create().expectedLargePng);
    }

    public static byte[] quadraticInput() {
        return readFile(create().quadraticInputPng);
    }

    public static String defaultHash() {
        return DEFAULT_HASH;
    }

    public static String updatedHash() {
        return UPDATED_HASH;
    }

    public static byte[] readFile(@NonNull final String filename) {
        try {
            final var fixture = ImageResourceFixtures.class.getResource(filename).toURI();
            return Files.readAllBytes(Paths.get(fixture));
        } catch (final IOException | URISyntaxException e) {
            throw new IOError(e);
        }
    }
}
