/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.service.custom.CustomTeamScoreService;
import com.iteratec.teamdojo.service.dto.TeamDTO;
import com.iteratec.teamdojo.service.dto.custom.TeamScoreDTO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomTeamScoreServiceImpl implements CustomTeamScoreService {

    @Override
    public TeamScoreDTO calculateTeamScore(@NonNull final TeamDTO t) {
        final var achieved = calculateAchieved();
        final var required = calculateRequired();
        final var totalScore = calculateTotalScore();
        final var progressInPercent = calculateProgressInPercent();
        final var completed = calculateCompleted(achieved, required);
        return new TeamScoreDTO(achieved, required, totalScore, progressInPercent, completed);
    }

    int calculateTotalScore() {
        return 0;
    }

    int calculateRequired() {
        return 0;
    }

    int calculateAchieved() {
        return 0;
    }

    float calculateProgressInPercent() {
        return 0.0f;
    }

    boolean calculateCompleted(final int achieved, final int required) {
        return achieved >= required;
    }
}
