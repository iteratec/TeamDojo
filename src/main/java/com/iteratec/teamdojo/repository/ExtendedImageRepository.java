package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.domain.Image;
import java.util.Optional;

public interface ExtendedImageRepository extends ImageRepository {
    Optional<Image> findByTitle(String title);
}
