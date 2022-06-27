/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.web.rest.custom;

import com.iteratec.teamdojo.service.criteria.LevelCriteria;
import com.iteratec.teamdojo.service.custom.ExtendedLevelService;
import com.iteratec.teamdojo.service.custom.ExtendedLevelSkillService;
import com.iteratec.teamdojo.service.dto.LevelDTO;
import com.iteratec.teamdojo.service.dto.LevelSkillDTO;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

/**
 * Extends the generated LevelResource
 *
 * <p>
 * This component encapsulates code to extend {@link com.iteratec.teamdojo.web.rest.LevelResource} w/o subclassing
 * (see ADR-0001).
 * </p>
 */
@Slf4j
@Component
public class CustomLevelResourceExtension {

    private final ExtendedLevelService levels;
    private final ExtendedLevelSkillService levelSkills;

    /**
     * Make this static method with side effects injectable for better testing.
     */
    @Setter
    private Supplier<UriComponentsBuilder> currentRequestUri = ServletUriComponentsBuilder::fromCurrentRequest;

    public CustomLevelResourceExtension(final ExtendedLevelService levels, final ExtendedLevelSkillService levelSkills) {
        super();
        this.levels = levels;
        this.levelSkills = levelSkills;
    }

    /**
     * Guard method to determine if @{link {@link #findLevelsBySkills(LevelCriteria, Pageable)}}  should be invoked or not
     *
     * @param criteria may be {@code null}
     * @return {@code true} if it should be invoked, else {@code false}
     */
    public boolean shouldFindLevelsBySkillId(final LevelCriteria criteria) {
        if (criteria == null) {
            return false;
        }

        if (criteria.getSkillsId() == null) {
            return false;
        }

        if (criteria.getSkillsId().getIn() == null) {
            return false;
        }

        return true;
    }

    /**
     * Finds all levels by given skills criteria
     *
     * @param criteria not {@code null}
     * @param pageable not {@code null}
     * @return the response entity with status 200 (OK) and the list of level IDs in the body
     */
    public ResponseEntity<List<LevelDTO>> findLevelsBySkills(final LevelCriteria criteria, final Pageable pageable) {
        final List<Long> skillIds = criteria.getSkillsId().getIn();

        log.debug("Finding levels for skills with ids: {}", skillIds);

        final List<Long> levelIds = findSkillsAndMapToLevelIds(skillIds, pageable);

        final Page<LevelDTO> page = levels.findByIdIn(levelIds, pageable);
        final HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(currentRequestUri.get(), page);

        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    List<Long> findSkillsAndMapToLevelIds(final List<Long> skillIds, final Pageable pageable) {
        return levelSkills
            .findBySkillIdIn(skillIds, pageable)
            .stream()
            .map(LevelSkillDTO::getLevel)
            .map(LevelDTO::getId)
            .collect(Collectors.toList());
    }
}
