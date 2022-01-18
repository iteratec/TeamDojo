package com.iteratec.teamdojo.repository.custom;

import com.iteratec.teamdojo.domain.Level;
import com.iteratec.teamdojo.repository.LevelRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Custom extension for {@link LevelRepository}
 *
 * <p>We use separate interfaces extending the generated one to avoid modification of generated code (see ADR-0001).</p>
 */
@Repository
public interface ExtendedLevelRepository extends LevelRepository {
    Page<Level> findByIdIn(List<Long> levelIds, Pageable pageable);
}
