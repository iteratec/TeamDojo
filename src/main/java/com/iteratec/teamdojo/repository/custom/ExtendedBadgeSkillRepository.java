package com.iteratec.teamdojo.repository.custom;

import com.iteratec.teamdojo.domain.BadgeSkill;
import com.iteratec.teamdojo.repository.BadgeRepository;
import com.iteratec.teamdojo.repository.BadgeSkillRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Custom extension for {@link BadgeSkillRepository}
 *
 * <p>We use separate interfaces extending the generated one to avoid modification of generated code (see ADR-0001).</p>
 */
@Repository
public interface ExtendedBadgeSkillRepository extends BadgeSkillRepository {
    Page<BadgeSkill> findBySkillIdIn(List<Long> skillsIds, Pageable pageable);
}
