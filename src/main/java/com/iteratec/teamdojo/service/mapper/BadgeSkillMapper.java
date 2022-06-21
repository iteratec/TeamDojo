package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Badge;
import com.iteratec.teamdojo.domain.BadgeSkill;
import com.iteratec.teamdojo.domain.Skill;
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.dto.BadgeSkillDTO;
import com.iteratec.teamdojo.service.dto.SkillDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BadgeSkill} and its DTO {@link BadgeSkillDTO}.
 */
@Mapper(componentModel = "spring")
@GeneratedByJHipster
public interface BadgeSkillMapper extends EntityMapper<BadgeSkillDTO, BadgeSkill> {
    @Mapping(target = "badge", source = "badge", qualifiedByName = "badgeTitleEN")
    @Mapping(target = "skill", source = "skill", qualifiedByName = "skillTitleEN")
    BadgeSkillDTO toDto(BadgeSkill s);

    @Named("badgeTitleEN")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titleEN", source = "titleEN")
    BadgeDTO toDtoBadgeTitleEN(Badge badge);

    @Named("skillTitleEN")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titleEN", source = "titleEN")
    SkillDTO toDtoSkillTitleEN(Skill skill);
}
