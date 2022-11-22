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

    // TODO Define API based on UI requirements. Seems that we need the properties from Progress and irrelevancy.

    /**
     * TODO: Please refine.
     * Number of achieved skills?
     * Not negative?
     */
    private int achieved;
    /**
     * TODO: Please refine.
     * How many skills are required?
     * Not negative?
     */
    private int required;
    /**
     * TODO: Please refine.
     * Not negative?
     */
    private int totalScore;
    private float progressInPercent;
    private boolean completed;
}
