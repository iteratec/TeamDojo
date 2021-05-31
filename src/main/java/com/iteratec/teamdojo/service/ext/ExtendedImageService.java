package com.iteratec.teamdojo.service.ext;

import com.iteratec.teamdojo.service.ImageService;
import com.iteratec.teamdojo.service.dto.ImageDTO;
import java.util.Optional;

public interface ExtendedImageService extends ImageService {
    /**
     * Get the "name" image.
     *
     * @param name the name of the entity
     * @return the entity
     */
    Optional<ImageDTO> findByTitle(String name);
}
