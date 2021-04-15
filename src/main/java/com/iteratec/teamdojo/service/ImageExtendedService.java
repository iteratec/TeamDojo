package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.service.dto.ImageDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.iteratec.teamdojo.domain.Image}.
 */
public interface ImageExtendedService extends ImageService {
    ImageDTO save(ImageDTO imageDTO);

    /**
     * Get the "name" image.
     *
     * @param name the name of the entity
     * @return the entity
     */
    Optional<ImageDTO> findByName(String name);
}
