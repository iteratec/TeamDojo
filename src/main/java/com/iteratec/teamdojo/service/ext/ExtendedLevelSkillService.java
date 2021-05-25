package com.iteratec.teamdojo.service.ext;

import com.iteratec.teamdojo.service.LevelSkillService;
import com.iteratec.teamdojo.service.dto.LevelSkillDTO;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ExtendedLevelSkillService extends LevelSkillService {
    List<LevelSkillDTO> findBySkillIdIn(List<Long> skillIds, Pageable pageable);
}
