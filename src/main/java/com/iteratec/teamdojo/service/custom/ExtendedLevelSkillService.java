/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.service.custom;

import com.iteratec.teamdojo.service.LevelSkillService;
import com.iteratec.teamdojo.service.dto.LevelSkillDTO;
import java.util.List;
import org.springframework.data.domain.Pageable;

/**
 * API extension for custom service behaviour
 * <p>
 * See ADR-0001 for more details about this pattern.
 * </p>
 */
public interface ExtendedLevelSkillService extends LevelSkillService {
    /**
     * Get levelSkills by skill id.
     *
     * @param skillIds the ids of the skills
     * @return the entity
     */
    List<LevelSkillDTO> findBySkillIdIn(List<Long> skillIds, Pageable pageable);
}
