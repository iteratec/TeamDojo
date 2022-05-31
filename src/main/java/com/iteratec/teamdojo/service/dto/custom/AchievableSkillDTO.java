package com.iteratec.teamdojo.service.dto.custom;

import com.iteratec.teamdojo.domain.enumeration.custom.SkillStatus;
import java.time.Instant;
import lombok.*;

/**
 * A DTO representing skills that can be achieved by a team
 */
@Getter
@Setter
@ToString
@NoArgsConstructor //Default constructor needed for Jackson.
@AllArgsConstructor
public class AchievableSkillDTO {

    private Long teamSkillId;
    private Long skillId;
    private String titleEN;
    private String titleDE;
    private String descriptionEN;
    private String descriptionDE;
    private Instant achievedAt;
    private Instant verifiedAt;
    private Integer vote;
    private String voters;
    private boolean irrelevant;
    private Integer score;
    private Integer skillExpiryPeriod;
    private Double rateScore;
    private Integer rateCount;

    public SkillStatus getSkillStatus() {
        return SkillStatus.determineSkillStatus(irrelevant, achievedAt, skillExpiryPeriod);
    }
}
