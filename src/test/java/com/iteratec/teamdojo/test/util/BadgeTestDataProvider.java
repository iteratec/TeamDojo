package com.iteratec.teamdojo.test.util;

import com.iteratec.teamdojo.domain.Badge;
import com.iteratec.teamdojo.domain.BadgeSkill;
import com.iteratec.teamdojo.domain.Dimension;
import com.iteratec.teamdojo.domain.Skill;
import java.util.Set;
import javax.persistence.EntityManager;
import org.assertj.core.util.Sets;

public class BadgeTestDataProvider {

    private static String DESCRIPTION = " Description";
    public static String AWS_READY_NAME = "AWS Ready";
    public static String AWS_READY_DESC = AWS_READY_NAME + DESCRIPTION;

    public static String ALWAYS_UP_TO_DATE_NAME = "Always up to date";
    public static String ALWAYS_UP_TO_DATE_DESC = ALWAYS_UP_TO_DATE_NAME + DESCRIPTION;

    public static String BEST_TEAM_NAME = "Always up to date";
    public static String BEST_TEAM_DESC = BEST_TEAM_NAME + DESCRIPTION;

    public static BadgeBuilder badge(String name) {
        return new BadgeBuilder(name);
    }

    public static BadgeBuilder awsReady() {
        return new BadgeBuilder(AWS_READY_NAME).description(AWS_READY_DESC);
    }

    public static BadgeBuilder alwaysUpToDate() {
        return new BadgeBuilder(ALWAYS_UP_TO_DATE_NAME).description(ALWAYS_UP_TO_DATE_DESC);
    }

    public static BadgeBuilder bestTeam() {
        return new BadgeBuilder(BEST_TEAM_NAME).description(BEST_TEAM_DESC);
    }

    public static class BadgeBuilder {

        private final String title;
        private String description;
        private Double requiredScore = 1.0;
        private Double instantMultiplier = 0.0;
        private Integer completionBonus = 0;
        private Set<Dimension> dimensions = Sets.newHashSet();
        private Set<BadgeSkill> skills = Sets.newHashSet();

        public BadgeBuilder(String title) {
            this.title = title;
        }

        public BadgeBuilder description(String description) {
            this.description = description;
            return this;
        }

        public BadgeBuilder requiredScore(Double requiredScore) {
            this.requiredScore = requiredScore;
            return this;
        }

        public BadgeBuilder instantMultiplier(Double instantMultiplier) {
            this.instantMultiplier = instantMultiplier;
            return this;
        }

        public BadgeBuilder completionBonus(Integer completionBonus) {
            this.completionBonus = completionBonus;
            return this;
        }

        public BadgeBuilder addDimension(Dimension dimension) {
            this.dimensions.add(dimension);
            return this;
        }

        public BadgeBuilder addSkill(Skill skill) {
            BadgeSkill badgeSkill = new BadgeSkill();
            badgeSkill.setSkill(skill);
            this.skills.add(badgeSkill);
            return this;
        }

        public Badge build(EntityManager em) {
            Badge badge = build();
            em.persist(badge);
            badge.getSkills().forEach(em::persist);
            return badge;
        }

        public Badge build() {
            Badge badge = new Badge()
                .titleEN(title)
                .descriptionEN(this.description)
                .requiredScore(requiredScore)
                .instantMultiplier(instantMultiplier)
                .completionBonus(completionBonus);
            this.dimensions.forEach(badge::addDimensions);
            this.skills.forEach(badge::addSkills);
            return badge;
        }
    }
}
