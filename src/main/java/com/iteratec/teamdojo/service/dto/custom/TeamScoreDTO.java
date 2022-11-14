/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.service.dto.custom;

import com.iteratec.teamdojo.service.dto.TeamDTO;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor // Needed for Jackson.
@AllArgsConstructor
public class TeamScoreDTO {

    public static final TeamScoreDTO NULL = new TeamScoreDTO();

    private TeamDTO team;
    private Long score;
}
