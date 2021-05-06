package com.iteratec.teamdojo.web.rest;

import com.iteratec.teamdojo.repository.BadgeRepository;
import com.iteratec.teamdojo.service.BadgeExtendedService;
import com.iteratec.teamdojo.service.BadgeQueryService;
import com.iteratec.teamdojo.service.BadgeSkillExtendedService;
import com.iteratec.teamdojo.service.criteria.BadgeCriteria;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.dto.BadgeSkillDTO;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

/**
 * REST controller for managing {@link com.iteratec.teamdojo.domain.Badge}.
 */
public class BadgeExtendedResource extends BadgeResource {

    private final Logger log = LoggerFactory.getLogger(BadgeExtendedResource.class);

    private final BadgeSkillExtendedService badgeSkillService;

    private final BadgeExtendedService badgeService;

    private final BadgeQueryService badgeQueryService;

    public BadgeExtendedResource(
        BadgeExtendedService badgeService,
        BadgeRepository badgeRepository,
        BadgeQueryService badgeQueryService,
        BadgeSkillExtendedService badgeSkillService
    ) {
        super(badgeService, badgeRepository, badgeQueryService);
        this.badgeSkillService = badgeSkillService;
        this.badgeService = badgeService;
        this.badgeQueryService = badgeQueryService;
    }

    /**
     * {@code GET  /badges} : get all the badges.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of badges in body.
     */
    @GetMapping("/badges")
    @Override
    public ResponseEntity<List<BadgeDTO>> getAllBadges(BadgeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Badges by criteria: {}", criteria);
        if (criteria != null && criteria.getSkillsId() != null && criteria.getSkillsId().getIn() != null) return getAllBadgesBySkills(
            criteria.getSkillsId().getIn(),
            pageable
        );

        Page<BadgeDTO> page = badgeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /badges : get all the badges.
     *
     * @param pageable the pagination information
     * @param skillsId the skillIds to search for
     * @return the ResponseEntity with status 200 (OK) and the list of badges in body
     */
    public ResponseEntity<List<BadgeDTO>> getAllBadgesBySkills(List<Long> skillsId, Pageable pageable) {
        log.debug("REST request to get Badges for Skills: {}", skillsId);

        List<BadgeSkillDTO> badgeSkills = badgeSkillService.findBySkillIdIn(skillsId, pageable);
        List<Long> badgeIds = new ArrayList<>();
        for (BadgeSkillDTO badgeSkill : badgeSkills) {
            badgeIds.add(badgeSkill.getId());
        }

        Page<BadgeDTO> page = badgeService.findByIdIn(badgeIds, pageable);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("/api/badges");
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder, page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
