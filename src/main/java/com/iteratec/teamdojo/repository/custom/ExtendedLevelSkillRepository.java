package com.iteratec.teamdojo.repository.custom;

import com.iteratec.teamdojo.domain.LevelSkill;
import com.iteratec.teamdojo.repository.LevelSkillRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Custom extension for {@link LevelSkillRepository}
 *
 * <p>We use separate interfaces extending the generated one to avoid modification of generated code (see ADR-0001).</p>
 */
@Repository
public interface ExtendedLevelSkillRepository extends LevelSkillRepository {
    Page<LevelSkill> findBySkillIdIn(List<Long> skillIds, Pageable pageable);
}
