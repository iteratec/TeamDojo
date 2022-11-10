/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.web.rest.custom;

import com.iteratec.teamdojo.service.TeamService;
import com.iteratec.teamdojo.service.custom.CustomTeamScoreService;
import com.iteratec.teamdojo.service.dto.custom.TeamScoreDTO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API endpoint to retrieve the team-score for given teams
 *
 * <p>
 * See ADR-0005 for more information.
 * </p>
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class CustomTeamScoreResource {

    private final CustomTeamScoreService teamScoreService;
    private final TeamService teamService;

    public CustomTeamScoreResource(@NonNull final CustomTeamScoreService teamScoreService, @NonNull final TeamService teamService) {
        super();
        this.teamScoreService = teamScoreService;
        this.teamService = teamService;
    }

    @GetMapping("/team-score/{teamId}")
    public ResponseEntity<TeamScoreDTO> getTeamSkill(@PathVariable long teamId) {
        final var team = teamService.findOne(teamId);

        if (team.isPresent()) {
            final var teamScore = teamScoreService.calculateTeamScore(team.get());
            return ResponseEntity.ok().body(teamScore);
        }

        return (ResponseEntity<TeamScoreDTO>) ResponseEntity.notFound();
    }
}
