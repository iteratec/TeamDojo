/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.web.rest.custom;

import com.iteratec.teamdojo.service.custom.CustomAchievableSkillService;
import com.iteratec.teamdojo.service.dto.custom.AchievableSkillDTO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;

/**
 * Complete custom controller for {@link com.iteratec.teamdojo.service.dto.custom.AchievableSkillDTO}
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class CustomTeamAchievableSkillResource {

    private static final String ENTITY_NAME = "AchievableSkillDTO";

    private final CustomAchievableSkillService achievableSkillService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public CustomTeamAchievableSkillResource(CustomAchievableSkillService achievableSkillService) {
        this.achievableSkillService = achievableSkillService;
    }

    @GetMapping("/teams/{teamId}/achievable-skills")
    public ResponseEntity<List<AchievableSkillDTO>> getAchievableSkills(
        @PathVariable Long teamId,
        @RequestParam(name = "levelId", required = false, defaultValue = "") List<Long> levelIds,
        @RequestParam(name = "badgeId", required = false, defaultValue = "") List<Long> badgeIds,
        @RequestParam(name = "filter", required = false, defaultValue = "") List<String> filterNames,
        Pageable pageable
    ) {
        log.debug("REST request to get AchievableSkills for Team; {}", teamId);
        final Page<AchievableSkillDTO> page = achievableSkillService.findAllByTeamAndLevelAndBadge(
            teamId,
            levelIds,
            badgeIds,
            filterNames,
            pageable
        );
        final HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/teams/{teamId}/achievable-skills-by-dimension")
    public ResponseEntity<List<AchievableSkillDTO>> getAchievableSkillsByDimensions(
        @PathVariable Long teamId,
        @RequestParam(name = "dimensionId") Long dimensionId,
        @RequestParam(name = "filter", required = false, defaultValue = "") List<String> filterNames,
        Pageable pageable
    ) {
        log.debug("REST request to get AchievableSkills for Team and Dimension {}, {}", teamId, dimensionId);
        final Page<AchievableSkillDTO> page = achievableSkillService.findAllByTeamAndDimension(teamId, dimensionId, filterNames, pageable);
        final HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/teams/{teamId}/achievable-skills/{skillId}")
    public ResponseEntity<AchievableSkillDTO> getAchievableSkills(@PathVariable Long teamId, @PathVariable Long skillId) {
        log.debug("REST request to get AchievableSkills for Team {} - Skill {}", teamId, skillId);
        final AchievableSkillDTO skill = achievableSkillService.findAchievableSkill(teamId, skillId);
        if (skill == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(skill, HttpStatus.OK);
        }
    }

    @PutMapping("/teams/{teamId}/achievable-skills")
    public ResponseEntity<AchievableSkillDTO> updateAchievableSkill(
        @PathVariable Long teamId,
        @RequestBody AchievableSkillDTO achievableSkill
    ) {
        log.debug("REST request to put vote {}", achievableSkill.getVote());
        final AchievableSkillDTO result = achievableSkillService.updateAchievableSkill(teamId, achievableSkill);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getSkillId().toString()))
            .body(result);
    }
}
