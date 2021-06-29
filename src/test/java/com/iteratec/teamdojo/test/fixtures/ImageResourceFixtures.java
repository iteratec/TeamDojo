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

    private static final String EXPECTED_HASH_OF_LARGE_PNG = "3C87DDFCADF9B2AD0A6DE4B491B71D7F";
    private static final String UPDATED_HASH = "08332C69A77B3D73376DF2FC43D9B0C0"; // XXX: Necessary?
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

    /**
     * Pure static helper.
     */
    private ImageResourceFixtures() {
        super();
    }

    public static byte[] expectedSmallPng() {
        return readFile(EXPECTED_SMALL_PNG);
    }

    public static byte[] expectedMediumPng() {
        return readFile(EXPECTED_MEDIUM_PNG);
    }

    public static byte[] expectedLargePng() {
        return readFile(EXPECTED_LARGE_PNG);
    }

    public static byte[] expectedRectangularPng() {
        return readFile(EXPECTED_RECTANGULAR_PNG);
    }

    public static byte[] quadraticInputPng() {
        return readFile(QUADRATIC_INPUT_PNG);
    }

    public static byte[] rectangularInputPng() {
        return readFile(RECTANGULAR_INPUT_PNG);
    }

    public static byte[] expectedSmallJpg() {
        return readFile(EXPECTED_SMALL_JPG);
    }

    public static byte[] expectedMediumJpg() {
        return readFile(EXPECTED_MEDIUM_JPG);
    }

    public static byte[] expectedLargeJpg() {
        return readFile(EXPECTED_LARGE_JPG);
    }

    public static byte[] expectedRectangularJpg() {
        return readFile(EXPECTED_RECTANGULAR_JPG);
    }

    public static byte[] quadraticInputJpg() {
        return readFile(QUADRATIC_INPUT_JPG);
    }

    public static byte[] rectangularInputJpg() {
        return readFile(RECTANGULAR_INPUT_JPG);
    }

    public static String expectedHashOfLargePng() {
        return EXPECTED_HASH_OF_LARGE_PNG;
    }

    public static String updatedHash() {
        return UPDATED_HASH;
    }

    private static byte[] readFile(@NonNull final String filename) {
        try {
            final var fixture = ImageResourceFixtures.class.getResource(filename).toURI();
            return Files.readAllBytes(Paths.get(fixture));
        } catch (final IOException | URISyntaxException e) {
            throw new IOError(e);
        }
    }
}
