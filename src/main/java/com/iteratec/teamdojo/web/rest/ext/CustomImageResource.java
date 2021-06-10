package com.iteratec.teamdojo.web.rest.ext;

import com.iteratec.teamdojo.service.dto.ImageDTO;
import com.iteratec.teamdojo.service.ext.ExtendedImageService;
import com.iteratec.teamdojo.web.rest.SkillResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Custom REST controller for managing {@link com.iteratec.teamdojo.domain.Image}.
 *
 * <p>This class is prefixed with "Custom" because we do not extend the generated {@link SkillResource}.
 * The reason for not extending the resource is that it would lead into ambiguous URI mappings due to inherited
 * methods with URI mapping annotations.</p>
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class CustomImageResource {

    private final ExtendedImageService imageService;

    public CustomImageResource(ExtendedImageService imageService) {
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
        if (!imageDTO.isPresent()) {
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
     * GET  /images/name/:name : get the "name" image.
     *
     * @param name the name of the imageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the imageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/images/name/{title}")
    public ResponseEntity<byte[]> getImageContent(@PathVariable String title) {
        log.debug("REST request to get Image : {}", title);
        Optional<ImageDTO> imageDTO = imageService.findByTitle(title);
        if (!imageDTO.isPresent()) {
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
