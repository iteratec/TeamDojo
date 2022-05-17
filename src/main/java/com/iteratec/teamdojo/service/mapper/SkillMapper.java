package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Skill;
import com.iteratec.teamdojo.service.dto.SkillDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Skill} and its DTO {@link SkillDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
@GeneratedByJHipster
public interface SkillMapper extends EntityMapper<SkillDTO, Skill> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SkillDTO toDtoId(Skill skill);

    @Named("titleEN")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titleEN", source = "titleEN")
    SkillDTO toDtoTitleEN(Skill skill);

    @Named("titleENSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titleEN", source = "titleEN")
    Set<SkillDTO> toDtoTitleENSet(Set<Skill> skill);
}
