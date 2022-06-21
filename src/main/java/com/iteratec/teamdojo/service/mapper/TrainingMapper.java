package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.domain.Skill;
import com.iteratec.teamdojo.domain.Training;
import com.iteratec.teamdojo.service.dto.SkillDTO;
import com.iteratec.teamdojo.service.dto.TrainingDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Training} and its DTO {@link TrainingDTO}.
 */
@Mapper(componentModel = "spring")
@GeneratedByJHipster
public interface TrainingMapper extends EntityMapper<TrainingDTO, Training> {
    @Mapping(target = "skills", source = "skills", qualifiedByName = "skillTitleENSet")
    TrainingDTO toDto(Training s);

    @Mapping(target = "removeSkill", ignore = true)
    Training toEntity(TrainingDTO trainingDTO);

    @Named("skillTitleEN")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titleEN", source = "titleEN")
    SkillDTO toDtoSkillTitleEN(Skill skill);

    @Named("skillTitleENSet")
    default Set<SkillDTO> toDtoSkillTitleENSet(Set<Skill> skill) {
        return skill.stream().map(this::toDtoSkillTitleEN).collect(Collectors.toSet());
    }
}
