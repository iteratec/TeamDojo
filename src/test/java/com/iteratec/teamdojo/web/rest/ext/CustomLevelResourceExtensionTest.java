package com.iteratec.teamdojo.web.rest.ext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.iteratec.teamdojo.service.criteria.LevelCriteria;
import com.iteratec.teamdojo.service.dto.LevelDTO;
import com.iteratec.teamdojo.service.dto.LevelSkillDTO;
import com.iteratec.teamdojo.service.ext.ExtendedLevelService;
import com.iteratec.teamdojo.service.ext.ExtendedLevelSkillService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

class CustomLevelResourceExtensionTest {

    private final ExtendedLevelSkillService levelSkills = mock(ExtendedLevelSkillService.class);
    private final ExtendedLevelService levels = mock(ExtendedLevelService.class);
    private final CustomLevelResourceExtension sut = new CustomLevelResourceExtension(levels, levelSkills);

    private List<LevelSkillDTO> createLevelSkillFixtures(final List<Long> badgeIds) {
        return createLevelFixtures(badgeIds)
            .stream()
            .map(
                level -> {
                    var levelSkill = new LevelSkillDTO();
                    levelSkill.setLevel(level);
                    return levelSkill;
                }
            )
            .collect(Collectors.toList());
    }

    private List<LevelDTO> createLevelFixtures(final List<Long> levelIds) {
        return levelIds
            .stream()
            .map(
                id -> {
                    var level = new LevelDTO();
                    level.setId(id);
                    return level;
                }
            )
            .collect(Collectors.toList());
    }

    @Test
    void shouldFindBadgesBySkills_falseIfCriteriaIsNull() {
        Assertions.assertThat(sut.shouldFindLevelsBySkillId(null)).isFalse();
    }

    @Test
    void shouldFindBadgesBySkills_falseIfSkillsIdIsNull() {
        Assertions.assertThat(sut.shouldFindLevelsBySkillId(new LevelCriteria())).isFalse();
    }

    @Test
    void shouldFindBadgesBySkills_falseIfSkillsIdsGetInIsNull() {
        final var criteria = new LevelCriteria();
        criteria.skillsId(); // This initializes the value.

        Assertions.assertThat(sut.shouldFindLevelsBySkillId(criteria)).isFalse();
    }

    @Test
    void shouldFindBadgesBySkills_trueIfSkillsIdAndGetInAreSet() {
        final var criteria = new LevelCriteria();
        criteria.skillsId().setIn(Arrays.asList(1L, 2L, 3L)); // This initializes the value.

        Assertions.assertThat(sut.shouldFindLevelsBySkillId(criteria)).isTrue();
    }

    @Test
    void findLevelsBySkills() {
        sut.setCurrentRequestUri(() -> UriComponentsBuilder.fromPath("/foo/bar"));
        final var skillIds = Arrays.asList(1L, 2L, 3L);
        final var badgeIds = Arrays.asList(11L, 22L, 33L);
        final var criteria = new LevelCriteria();
        criteria.skillsId().setIn(skillIds);
        when(levelSkills.findBySkillIdIn(eq(skillIds), any(Pageable.class))).thenReturn(createLevelSkillFixtures(badgeIds));
        final var page = mock(Page.class);
        when(levels.findByIdIn(eq(badgeIds), any(Pageable.class))).thenReturn(page);
        when(page.getTotalElements()).thenReturn(3L);
        final var badges = createLevelFixtures(badgeIds);
        when(page.getContent()).thenReturn(badges);

        final var result = sut.findLevelsBySkills(criteria, mock(Pageable.class));

        assertAll(
            () -> assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK),
            () ->
                assertThat(result.getHeaders())
                    .contains(
                        entry(
                            HttpHeaders.LINK,
                            Collections.singletonList("</foo/bar?page=-1&size=0>; rel=\"last\",</foo/bar?page=0&size=0>; rel=\"first\"")
                        )
                    ),
            () -> assertThat(result.getHeaders()).contains(entry("X-Total-Count", Collections.singletonList("3"))),
            () -> assertThat(result.getBody()).isEqualTo(badges)
        );
    }

    @Test
    void findSkillsAndMapToLevelIds() {
        when(levelSkills.findBySkillIdIn(anyList(), any(Pageable.class))).thenReturn(createLevelSkillFixtures(Arrays.asList(1L, 2L, 3L)));

        final var result = sut.findSkillsAndMapToLevelIds(Collections.emptyList(), mock(Pageable.class));

        assertThat(result).contains(1L, 2L, 3L);
    }
}
