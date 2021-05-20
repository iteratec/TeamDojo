package com.iteratec.teamdojo.repository.ext;

import com.iteratec.teamdojo.domain.BadgeSkill;
import com.iteratec.teamdojo.repository.BadgeSkillRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BadgeSkill entity.
 */
@Repository
public interface ExtendedBadgeSkillRepository extends BadgeSkillRepository {
    Page<BadgeSkill> findBySkillIdIn(List<Long> skillsIds, Pageable pageable);
}
