package com.iteratec.teamdojo.repository.custom;

import com.iteratec.teamdojo.domain.LevelSkill;
import com.iteratec.teamdojo.repository.LevelSkillRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtendedLevelSkillRepository extends LevelSkillRepository {
    Page<LevelSkill> findBySkillIdIn(List<Long> skillIds, Pageable pageable);
}
