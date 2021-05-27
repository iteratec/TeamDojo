package com.iteratec.teamdojo.service.impl.ext;

import com.iteratec.teamdojo.repository.ext.ExtendedLevelSkillRepository;
import com.iteratec.teamdojo.service.dto.LevelSkillDTO;
import com.iteratec.teamdojo.service.ext.ExtendedLevelSkillService;
import com.iteratec.teamdojo.service.impl.LevelSkillServiceImpl;
import com.iteratec.teamdojo.service.mapper.LevelSkillMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Primary
@Service
public class ExtendedLevelSkillServiceImpl extends LevelSkillServiceImpl implements ExtendedLevelSkillService {

    private final Logger log = LoggerFactory.getLogger(ExtendedLevelSkillServiceImpl.class);

    private ExtendedLevelSkillRepository levelSkillRepository;
    private LevelSkillMapper levelSkillMapper;

    public ExtendedLevelSkillServiceImpl(ExtendedLevelSkillRepository levelSkillRepository, LevelSkillMapper levelSkillMapper) {
        super(levelSkillRepository, levelSkillMapper);
    }

    /**
     * Get levelSkills by skill id.
     *
     * @param skillIds the ids of the skills
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public List<LevelSkillDTO> findBySkillIdIn(List<Long> skillIds, Pageable pageable) {
        log.debug("Request to get LevelSkill by skill Ids: {}", skillIds);
        return levelSkillRepository
            .findBySkillIdIn(skillIds, pageable)
            .stream()
            .map(levelSkillMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
