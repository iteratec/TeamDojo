/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.domain.enumeration.custom;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;

class SkillStatusTest {

    private static final Instant NOW = Instant.now();

    @Test
    void determineSkillStatus_open() {
        final var sut = SkillStatus.determineSkillStatus(false, null, 14);

        assertThat(sut).isEqualTo(SkillStatus.OPEN);
    }

    @Test
    void determineSkillStatus_achieved() {
        final var completedAt = NOW.minus(2, ChronoUnit.DAYS);

        final var sut = SkillStatus.determineSkillStatus(false, completedAt, 14);

        assertThat(sut).isEqualTo(SkillStatus.ACHIEVED);
    }

    @Test
    void determineSkillStatus_achieved_skill_does_not_expire() {
        final var completedAt = NOW.minus(2, ChronoUnit.DAYS);

        final var sut = SkillStatus.determineSkillStatus(false, completedAt, null);

        assertThat(sut).isEqualTo(SkillStatus.ACHIEVED);
    }

    @Test
    void determineSkillStatus_expired() {
        final var completedAt = NOW.minus(22, ChronoUnit.DAYS);

        final var sut = SkillStatus.determineSkillStatus(false, completedAt, 14);

        assertThat(sut).isEqualTo(SkillStatus.EXPIRED);
    }

    @Test
    void determineSkillStatus_expiring() {
        final var completedAt = NOW.minus(18, ChronoUnit.DAYS);
        final var sut = SkillStatus.determineSkillStatus(false, completedAt, 14);

        assertThat(sut).isEqualTo(SkillStatus.EXPIRING);
    }

    @Test
    void determineSkillStatus_irrelevant() {
        final var sut = SkillStatus.determineSkillStatus(true, null, null);

        assertThat(sut).isEqualTo(SkillStatus.IRRELEVANT);
    }
}
