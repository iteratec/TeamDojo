/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.service.custom;

import com.iteratec.teamdojo.service.SkillService;
import com.iteratec.teamdojo.service.dto.SkillDTO;
import com.iteratec.teamdojo.service.impl.custom.InstantProviderInjectable;

/**
 * API extension for custom service behaviour
 * <p>
 * See ADR-0001 for more details about this pattern.
 * </p>
 */
public interface ExtendedSkillService extends SkillService, InstantProviderInjectable {
    /**
     * Creates a new vote
     * @param id the entity to update
     * @param rateScore stars to update
     * @return the persisted entity
     */
    SkillDTO createVote(long id, int rateScore);
}
