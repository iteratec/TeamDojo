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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
     * Guard method to determine if @{link {@link #findBadgesBySkills(List, Pageable)}} should be invoked or not
     *
     * @param criteria may be {@code null}
     * @return {@code true} if it should be invoked, else {@code false}
     */
    public boolean shouldFindBadgesBySkills(final BadgeCriteria criteria) {
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
     * Finds all badges by given skills criteria
     *
     * @param criteria not {@code null}
     * @param pageable not {@code null}
     * @return a response entity with status 200 (OK) and the list of badge IDs in the body
     */
    public ResponseEntity<List<BadgeDTO>> findBadgesBySkills(final BadgeCriteria criteria, final Pageable pageable) {
        final List<Long> skillIds = criteria.getSkillsId().getIn();
        log.debug("Finding badges for skills with ids: {}", skillIds);

        final List<Long> badgeIds = badgeSkills.findBySkillIdIn(skillIds, pageable)
            .stream()
            .map(BadgeSkillDTO::getBadge)
            .map(BadgeDTO::getId)
            .collect(Collectors.toList());

        final Page<BadgeDTO> page = badges.findByIdIn(badgeIds, pageable);
        final HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return ResponseEntity.ok()
            .headers(headers)
            .body(page.getContent());
    }

}
