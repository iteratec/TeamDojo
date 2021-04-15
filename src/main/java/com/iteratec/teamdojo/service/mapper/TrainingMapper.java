package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.domain.*;
import com.iteratec.teamdojo.service.dto.TrainingDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Training} and its DTO {@link TrainingDTO}.
 */
@Mapper(componentModel = "spring", uses = { SkillMapper.class })
public interface TrainingMapper extends EntityMapper<TrainingDTO, Training> {
    @Mapping(target = "skills", source = "skills", qualifiedByName = "titleSet")
    TrainingDTO toDto(Training s);

    @Mapping(target = "removeSkill", ignore = true)
    Training toEntity(TrainingDTO trainingDTO);
}
