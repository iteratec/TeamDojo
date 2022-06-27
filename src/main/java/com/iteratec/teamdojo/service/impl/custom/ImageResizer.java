/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.service.impl.custom;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOError;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Helper class to resize images.
 */
final class ImageResizer {

    static final String IMAGE_FORMAT = "png";

    /**
     * Resizes a given image to the maximum
     * <p>
     * The image is expected as raw byte array like your read all bytes from a file.
     * </p>
     * <p>The size is derived from the given enum and is scaled down so tht neither the resulting width
     * nor height exceeds it.</p>
     *
     * @param input must not be {@code null}
     * @param max must not be {@code null}
     * @return never {@code null}
     */
    byte[] resize(final byte[] input, @NonNull final MaxSize max) {
        final var original = fromArray(input);
        final var resized = resize(original, max);
        return toArray(resized);
    }

    private BufferedImage fromArray(final byte[] input) {
        try (final var buffer = new ByteArrayInputStream(input)) {
            return ImageIO.read(buffer);
        } catch (final IOException e) {
            // We do not act on files so IOException is nearly impossible.
            // If it will be thrown we can't handle it anyway, so we convert it into an runtime error.
            throw new IOError(e);
        }
    }

    private RenderedImage resize(@NonNull final BufferedImage input, @NonNull final MaxSize max) {
        // no scaling if img width and height are smaller than max
        if (input.getWidth() <= max.getPixels() && input.getHeight() <= max.getPixels()) {
            return input;
        }

        int width = max.getPixels();
        int height = max.getPixels();

        if (input.getWidth() < input.getHeight()) {
            width = (int) (input.getWidth() * (1.0 * height / input.getHeight()));
        } else {
            height = (int) (input.getHeight() * (1.0 * width / input.getWidth()));
        }

        final BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D drawing = resized.createGraphics();
        final Image tmp = input.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        drawing.drawImage(tmp, 0, 0, null);
        drawing.dispose();

        return resized;
    }

    private byte[] toArray(@NonNull final RenderedImage input) {
        try (final var buffer = new ByteArrayOutputStream()) {
            ImageIO.write(input, IMAGE_FORMAT, buffer);
            buffer.flush();
            return buffer.toByteArray();
        } catch (final IOException e) {
            // We do not act on files so IOException is nearly impossible.
            // If it will be thrown we can't handle it anyway, so we convert it into an runtime error.
            throw new IOError(e);
        }
    }

    /**
     * Defines the allowed maximum sizes for images
     */
    @ToString
    enum MaxSize {
        LARGE(512),
        MEDIUM(224),
        SMALL(72);

        /**
         * The maximum width or height for an image.
         */
        @Getter
        private final int pixels;

        MaxSize(int pixels) {
            this.pixels = pixels;
        }
    }
}
