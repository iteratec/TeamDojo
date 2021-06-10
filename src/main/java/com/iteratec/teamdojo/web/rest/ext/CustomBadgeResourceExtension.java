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

/**
 * This component encapsulate code to extend {@link com.iteratec.teamdojo.web.rest.BadgeResource} w/o subclassing
 * <p>
 * Why not simply subclassing? If we subclass the generated resource we will have duplicate resource uri mappings because
 * this class would also define the same URIs as the parent class. Also the functionality of this class is used deep
 * inside a method of the BadgeResource, so we would need to add something like a Template Method Pattern which would
 * lead to lot of changes in the generated class. This approach is at the moment the solution with as less as possible
 * changes to the generated class.
 * </p>
 * <p>
 * This class is annotated as component so it will be autowired.
 * </p>
 */
@Slf4j
@Component
public class CustomBadgeResourceExtension {

    private final ExtendedBadgeService badges;
    private final ExtendedBadgeSkillService badgeSkills;

    public CustomBadgeResourceExtension(final ExtendedBadgeService badges, final ExtendedBadgeSkillService badgeSkills) {
        super();
        this.badges = badges;
        this.badgeSkills = badgeSkills;
    }

    /**
     * Guard method to determine if @{link {@link #getAllBadgesBySkills(List, Pageable)}} should be invoked or not
     *
     * @param criteria may be {@code null}
     * @return {@code true} if it should be invoked, else {@code false}
     */
    public boolean shouldFindBadgesBySkills(final BadgeCriteria criteria) {
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

        List<BadgeSkillDTO> badgeSkills = this.badgeSkills.findBySkillIdIn(skillsId, pageable);
        List<Long> badgeIds = new ArrayList<>();
        for (BadgeSkillDTO badgeSkill : badgeSkills) {
            badgeIds.add(badgeSkill.getBadge().getId());
        }

        Page<BadgeDTO> page = badges.findByIdIn(badgeIds, pageable);
        HttpHeaders headers = CustomPaginationUtil.generatePaginationHttpHeaders(page, "/api/badges");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
