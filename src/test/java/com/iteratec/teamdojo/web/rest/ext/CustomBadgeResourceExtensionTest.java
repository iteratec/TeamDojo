package com.iteratec.teamdojo.web.rest.ext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.iteratec.teamdojo.service.criteria.BadgeCriteria;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.dto.BadgeSkillDTO;
import com.iteratec.teamdojo.service.ext.ExtendedBadgeService;
import com.iteratec.teamdojo.service.ext.ExtendedBadgeSkillService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

class CustomBadgeResourceExtensionTest {

    private final ExtendedBadgeService badges = mock(ExtendedBadgeService.class);
    private final ExtendedBadgeSkillService badgeSkills = mock(ExtendedBadgeSkillService.class);
    private final CustomBadgeResourceExtension sut = new CustomBadgeResourceExtension(badges, badgeSkills);

    private List<BadgeSkillDTO> createBadgeSkillFixtures(final List<Long> badgeIds) {
        return createBadgeFixtures(badgeIds)
            .stream()
            .map(
                badge -> {
                    var badgeSkill = new BadgeSkillDTO();
                    badgeSkill.setBadge(badge);
                    return badgeSkill;
                }
            )
            .collect(Collectors.toList());
    }

    private List<BadgeDTO> createBadgeFixtures(final List<Long> badgeIds) {
        return badgeIds
            .stream()
            .map(
                id -> {
                    var badge = new BadgeDTO();
                    badge.setId(id);
                    return badge;
                }
            )
            .collect(Collectors.toList());
    }

    @Test
    void shouldFindBadgesBySkills_falseIfCriteriaIsNull() {
        assertThat(sut.shouldFindBadgesBySkills(null)).isFalse();
    }

    @Test
    void shouldFindBadgesBySkills_falseIfSkillsIdIsNull() {
        assertThat(sut.shouldFindBadgesBySkills(new BadgeCriteria())).isFalse();
    }

    @Test
    void shouldFindBadgesBySkills_falseIfSkillsIdsGetInIsNull() {
        final var criteria = new BadgeCriteria();
        criteria.skillsId(); // This initializes the value.

        assertThat(sut.shouldFindBadgesBySkills(criteria)).isFalse();
    }

    @Test
    void shouldFindBadgesBySkills_trueIfSkillsIdAndGetInAreSet() {
        final var criteria = new BadgeCriteria();
        criteria.skillsId().setIn(Arrays.asList(1L, 2L, 3L));

        assertThat(sut.shouldFindBadgesBySkills(criteria)).isTrue();
    }

    @Test
    void findBadgesBySkills() {
        sut.setCurrentRequestUri(() -> UriComponentsBuilder.fromPath("/foo/bar"));
        final var skillIds = Arrays.asList(1L, 2L, 3L);
        final var badgeIds = Arrays.asList(11L, 22L, 33L);
        final var criteria = new BadgeCriteria();
        criteria.skillsId().setIn(skillIds);
        when(badgeSkills.findBySkillIdIn(eq(skillIds), any(Pageable.class))).thenReturn(createBadgeSkillFixtures(badgeIds));
        final var page = mock(Page.class);
        when(badges.findByIdIn(eq(badgeIds), any(Pageable.class))).thenReturn(page);
        when(page.getTotalElements()).thenReturn(3L);
        final var badges = createBadgeFixtures(badgeIds);
        when(page.getContent()).thenReturn(badges);

        final var result = sut.findBadgesBySkills(criteria, mock(Pageable.class));

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
    void findSkillsAndMapToBadgeIds() {
        when(badgeSkills.findBySkillIdIn(anyList(), any(Pageable.class))).thenReturn(createBadgeSkillFixtures(Arrays.asList(1L, 2L, 3L)));

        final var result = sut.findSkillsAndMapToBadgeIds(Collections.emptyList(), mock(Pageable.class));

        assertThat(result).contains(1L, 2L, 3L);
    }
}
