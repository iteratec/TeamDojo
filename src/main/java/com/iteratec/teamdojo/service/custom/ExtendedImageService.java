/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.service.custom;

import com.iteratec.teamdojo.service.ImageService;
import com.iteratec.teamdojo.service.dto.ImageDTO;
import com.iteratec.teamdojo.service.impl.custom.InstantProviderInjectable;
import java.util.Optional;

/**
 * API extension for custom service behaviour
 * <p>
 * See ADR-0001 for more details about this pattern.
 * </p>
 */
public interface ExtendedImageService extends ImageService, InstantProviderInjectable {
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
