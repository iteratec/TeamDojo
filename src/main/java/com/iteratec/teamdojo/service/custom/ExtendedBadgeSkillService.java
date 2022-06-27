/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.service.custom;

import com.iteratec.teamdojo.service.BadgeSkillService;
import com.iteratec.teamdojo.service.dto.BadgeSkillDTO;
import java.util.List;
import org.springframework.data.domain.Pageable;

/**
 * API extension for custom service behaviour
 * <p>
 * See ADR-0001 for more details about this pattern.
 * </p>
 */
public interface ExtendedBadgeSkillService extends BadgeSkillService {
    List<BadgeSkillDTO> findBySkillIdIn(List<Long> skillIds, Pageable pageable);
}
