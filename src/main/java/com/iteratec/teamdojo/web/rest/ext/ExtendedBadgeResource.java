package com.iteratec.teamdojo.web.rest.ext;

import com.iteratec.teamdojo.repository.BadgeRepository;
import com.iteratec.teamdojo.service.BadgeQueryService;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.dto.BadgeSkillDTO;
import com.iteratec.teamdojo.service.ext.ExtendedBadgeService;
import com.iteratec.teamdojo.service.ext.ExtendedBadgeSkillService;
import com.iteratec.teamdojo.web.rest.BadgeResource;
import com.iteratec.teamdojo.web.rest.ext.util.CustomPaginationUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v2")
public class ExtendedBadgeResource extends BadgeResource {

    private final ExtendedBadgeSkillService badgeSkillService;
    private final ExtendedBadgeService badgeService;

    public ExtendedBadgeResource(
        ExtendedBadgeService badgeService,
        BadgeRepository badgeRepository,
        BadgeQueryService badgeQueryService,
        ExtendedBadgeSkillService badgeSkillService
    ) {
        super(badgeService, badgeRepository, badgeQueryService);
        this.badgeSkillService = badgeSkillService;
        this.badgeService = badgeService;
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
