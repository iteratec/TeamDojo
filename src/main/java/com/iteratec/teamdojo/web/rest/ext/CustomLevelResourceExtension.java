package com.iteratec.teamdojo.web.rest.ext;

import com.iteratec.teamdojo.service.criteria.LevelCriteria;
import com.iteratec.teamdojo.service.dto.LevelDTO;
import com.iteratec.teamdojo.service.dto.LevelSkillDTO;
import com.iteratec.teamdojo.service.ext.ExtendedLevelService;
import com.iteratec.teamdojo.service.ext.ExtendedLevelSkillService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

/**
 * This component encapsulate code to extend {@link com.iteratec.teamdojo.web.rest.LevelResource} w/o subclassing
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
public class CustomLevelResourceExtension {

    private final ExtendedLevelSkillService levelSkills;
    private final ExtendedLevelService levels;

    public CustomLevelResourceExtension(ExtendedLevelService levels, ExtendedLevelSkillService levelSkills) {
        super();
        this.levelSkills = levelSkills;
        this.levels = levels;
    }

    /**
     * Guard method to determine if @{link {@link #getAllLevelsBySkills(LevelCriteria, Pageable)}}  should be invoked or not
     *
     * @param criteria may be {@code null}
     * @return {@code true} if it should be invoked, else {@code false}
     */
    public boolean shouldFindLevelsBySkillId(LevelCriteria criteria) {
        return criteria != null && criteria.getSkillsId() != null && criteria.getSkillsId().getIn() != null;
    }

    /**
     * GET  /levels : get all the levels.
     *
     * @param criteria not {@code null}
     * @param pageable not {@code null}
     * @return the ResponseEntity with status 200 (OK) and the list of levels in body
     */
    public ResponseEntity<List<LevelDTO>> getAllLevelsBySkills(final LevelCriteria criteria, final Pageable pageable) {
        final List<Long> skillsId = criteria.getSkillsId().getIn();

        log.debug("REST request to get Levels for Skills; {}", skillsId);

        final List<Long> levelIds = levelSkills
            .findBySkillIdIn(skillsId, pageable)
            .stream()
            .map(LevelSkillDTO::getLevel)
            .map(LevelDTO::getId)
            .collect(Collectors.toList());

        final Page<LevelDTO> page = levels.findByIdIn(levelIds, pageable);
        final HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
