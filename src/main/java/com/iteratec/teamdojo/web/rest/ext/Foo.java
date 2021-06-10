package com.iteratec.teamdojo.web.rest.ext;

import com.iteratec.teamdojo.service.criteria.BadgeCriteria;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.dto.BadgeSkillDTO;
import com.iteratec.teamdojo.service.ext.ExtendedBadgeService;
import com.iteratec.teamdojo.service.ext.ExtendedBadgeSkillService;
import com.iteratec.teamdojo.web.rest.ext.util.CustomPaginationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class Foo {

    private final ExtendedBadgeSkillService badgeSkillService;
    private final ExtendedBadgeService badgeService;

    public Foo(ExtendedBadgeService badgeService, ExtendedBadgeSkillService badgeSkillService) {
        this.badgeSkillService = badgeSkillService;
        this.badgeService = badgeService;
    }

    public boolean isFoo(BadgeCriteria criteria) {
        return criteria != null && criteria.getSkillsId() != null && criteria.getSkillsId().getIn() != null;
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
            badgeIds.add(badgeSkill.getBadge().getId());
        }

        Page<BadgeDTO> page = badgeService.findByIdIn(badgeIds, pageable);
        HttpHeaders headers = CustomPaginationUtil.generatePaginationHttpHeaders(page, "/api/badges");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
