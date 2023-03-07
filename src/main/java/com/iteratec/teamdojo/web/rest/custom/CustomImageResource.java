/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.web.rest.custom;

import com.iteratec.teamdojo.service.custom.ExtendedImageService;
import com.iteratec.teamdojo.service.dto.ImageDTO;
import com.iteratec.teamdojo.web.rest.SkillResource;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Extends the generated ImageResource
 *
 * <p>
 * This component encapsulates code to extend {@link com.iteratec.teamdojo.web.rest.ImageResource} w/o subclassing
 * (see ADR-0001).
 * </p>
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class CustomImageResource {

    private final ExtendedImageService imageService;

    public CustomImageResource(@NonNull final ExtendedImageService imageService) {
        super();
        this.imageService = imageService;
    }

    /**
     * GET  /images/:id : get the "id" image.
     *
     * @param id the id of the imageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the imageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/images/{id}/content")
    public ResponseEntity<byte[]> getImageContent(@PathVariable Long id, @RequestParam(value = "size", required = false) String size) {
        log.debug("REST request to get Image : {}", id);
        Optional<ImageDTO> imageDTO = imageService.findOne(id);
        if (imageDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ImageDTO image = imageDTO.get();

        size = size == null ? "LARGE" : size.toUpperCase();
        byte[] imageBlob;
        String contentType;
        if (size.equals("SMALL")) {
            imageBlob = image.getSmall();
            contentType = image.getSmallContentType();
        } else if (size.equals("MEDIUM")) {
            imageBlob = image.getMedium();
            contentType = image.getMediumContentType();
        } else {
            imageBlob = image.getLarge();
            contentType = image.getLargeContentType();
        }
        return ResponseEntity.ok().header("Content-Type", contentType).cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS)).body(imageBlob);
    }

    /**
     * GET  /images/:id/hash : get the hash of the "id" image. Used for caching purposes.
     *
     * @param id the id of the image whose hash we want to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hash, or with status 404 (Not Found)
     */
    @GetMapping("/images/{id}/hash")
    public ResponseEntity<String> getImageHash(@PathVariable Long id) {
        log.debug("REST request to get Image hash: {}", id);
        Optional<ImageDTO> imageDTO = imageService.findOne(id);
        if (imageDTO.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        String hash = imageDTO.get().getHash();
        return ResponseEntity.ok().header("Content-Type", "text/plain").body(hash);
    }

    /**
     * GET  /images/name/:name : get the "name" image.
     *
     * @param title the name of the imageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the imageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/images/name/{title}")
    public ResponseEntity<byte[]> getImageContent(@PathVariable String title) {
        log.debug("REST request to get Image : {}", title);
        Optional<ImageDTO> imageDTO = imageService.findByTitle(title);
        if (imageDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ImageDTO image = imageDTO.get();
        byte[] imageBlob;
        String contentType;
        imageBlob = image.getLarge();
        contentType = image.getLargeContentType();
        return ResponseEntity.ok().header("Content-Type", contentType).cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS)).body(imageBlob);
    }
}
