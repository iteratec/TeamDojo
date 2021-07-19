package com.iteratec.teamdojo.service.custom;

import com.iteratec.teamdojo.service.BadgeSkillService;
import com.iteratec.teamdojo.service.dto.BadgeSkillDTO;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ExtendedBadgeSkillService extends BadgeSkillService {
    List<BadgeSkillDTO> findBySkillIdIn(List<Long> skillIds, Pageable pageable);
}
