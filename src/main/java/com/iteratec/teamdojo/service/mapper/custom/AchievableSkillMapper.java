package com.iteratec.teamdojo.service.mapper.custom;

import com.iteratec.teamdojo.domain.custom.AchievableSkill;
import com.iteratec.teamdojo.service.dto.custom.AchievableSkillDTO;
import com.iteratec.teamdojo.service.mapper.EntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link AchievableSkill} and its DTO {@link AchievableSkillDTO}
 *
 * <p>This mapper is not generated by the JDL because the entity has no underlying database table and is pure synthetic
 * resulting from some join queries.</p>
 */
@Mapper(componentModel = "spring", uses = {})
public interface AchievableSkillMapper extends EntityMapper<AchievableSkillDTO, AchievableSkill> {}