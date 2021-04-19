package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.service.dto.LevelSkillDTO;
import java.util.List;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.iteratec.teamdojo.domain.LevelSkill}.
 */
public interface LevelSkillExtendedService extends LevelSkillService {
    List<LevelSkillDTO> findBySkillIdIn(List<Long> skillIds, Pageable pageable);
}
