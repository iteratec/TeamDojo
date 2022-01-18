package com.iteratec.teamdojo.repository.custom;

import com.iteratec.teamdojo.domain.Image;
import com.iteratec.teamdojo.repository.BadgeRepository;
import com.iteratec.teamdojo.repository.ImageRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * Custom extension for {@link ImageRepository}
 *
 * <p>We use separate interfaces extending the generated one to avoid modification of generated code (see ADR-0001).</p>
 */
@Repository
public interface ExtendedImageRepository extends ImageRepository {
    Optional<Image> findByTitle(String title);
}
