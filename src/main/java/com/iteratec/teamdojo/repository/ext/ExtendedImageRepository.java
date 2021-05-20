package com.iteratec.teamdojo.repository.ext;

import com.iteratec.teamdojo.domain.Image;
import com.iteratec.teamdojo.repository.ImageRepository;
import java.util.Optional;

public interface ExtendedImageRepository extends ImageRepository {
    Optional<Image> findByTitle(String title);
}
