package com.iteratec.teamdojo.service.custom;

import com.iteratec.teamdojo.service.ImageService;
import com.iteratec.teamdojo.service.dto.ImageDTO;
import java.util.Optional;

/**
 * Custom extension to the generated service
 */
public interface ExtendedImageService extends ImageService {
    /**
     * Get the image by name
     *
     * @param name the name of the entity
     * @return never {@code null}
     */
    Optional<ImageDTO> findByTitle(String name);

    /**
     * Save a image
     *
     * <p>This implement overrides the method with business logic additionally to the {@link ImageService#save(ImageDTO)
     * generated one}.</p>
     *
     * @param imageDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    ImageDTO save(ImageDTO imageDTO);
}
