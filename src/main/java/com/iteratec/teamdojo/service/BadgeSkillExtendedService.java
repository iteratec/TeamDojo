package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.service.dto.BadgeSkillDTO;
import java.util.List;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.iteratec.teamdojo.domain.BadgeSkill}.
 */
public interface BadgeSkillExtendedService extends BadgeSkillService {
    List<BadgeSkillDTO> findBySkillIdIn(List<Long> skillIds, Pageable pageable);
}
