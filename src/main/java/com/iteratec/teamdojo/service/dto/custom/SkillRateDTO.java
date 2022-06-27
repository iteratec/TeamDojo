/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.service.dto.custom;

public class SkillRateDTO {

    private Integer rateScore;
    private Integer rateCount;
    private Long skillId;

    public SkillRateDTO() {
        // Empty constructor needed for Jackson.
    }

    public SkillRateDTO(Long skillId, Integer rateScore, Integer rateCount) {
        this.rateScore = rateScore;
        this.rateCount = rateCount;
        this.skillId = skillId;
    }

    public Integer getRateCount() {
        return rateCount;
    }

    public void setRateCount(Integer rateCount) {
        this.rateCount = rateCount;
    }

    public Integer getRateScore() {
        return rateScore;
    }

    public void setRateScore(Integer rateScore) {
        this.rateScore = rateScore;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }
}
