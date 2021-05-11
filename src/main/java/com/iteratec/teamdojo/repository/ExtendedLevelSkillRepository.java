package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.domain.LevelSkill;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExtendedLevelSkillRepository extends LevelSkillRepository {
    Page<LevelSkill> findBySkillIdIn(List<Long> skillIds, Pageable pageable);
}
