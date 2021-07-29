package com.iteratec.teamdojo.web.rest.custom;

import com.iteratec.teamdojo.service.criteria.BadgeCriteria;
import com.iteratec.teamdojo.service.custom.ExtendedBadgeService;
import com.iteratec.teamdojo.service.custom.ExtendedBadgeSkillService;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.dto.BadgeSkillDTO;
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

    /**
     * Make this static method with side effects injectable for better testing.
     */
    @Setter
    private Supplier<UriComponentsBuilder> currentRequestUri = ServletUriComponentsBuilder::fromCurrentRequest;

    public CustomBadgeResourceExtension(final ExtendedBadgeService badges, final ExtendedBadgeSkillService badgeSkills) {
        super();
        this.badges = badges;
        this.badgeSkills = badgeSkills;
    }

    /**
     * Guard method to determine if @{link {@link #findBadgesBySkills(BadgeCriteria, Pageable)}} should be invoked or not
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

        final List<Long> badgeIds = findSkillsAndMapToBadgeIds(skillIds, pageable);
        final Page<BadgeDTO> page = badges.findByIdIn(badgeIds, pageable);
        final HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(currentRequestUri.get(), page);

        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    List<Long> findSkillsAndMapToBadgeIds(final List<Long> skillIds, final Pageable pageable) {
        return badgeSkills
            .findBySkillIdIn(skillIds, pageable)
            .stream()
            .map(BadgeSkillDTO::getBadge)
            .map(BadgeDTO::getId)
            .collect(Collectors.toList());
    }
}