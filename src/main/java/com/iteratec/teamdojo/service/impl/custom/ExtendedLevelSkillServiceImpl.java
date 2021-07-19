package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.repository.custom.ExtendedLevelSkillRepository;
import com.iteratec.teamdojo.service.custom.ExtendedLevelSkillService;
import com.iteratec.teamdojo.service.dto.LevelSkillDTO;
import com.iteratec.teamdojo.service.impl.LevelSkillServiceImpl;
import com.iteratec.teamdojo.service.mapper.LevelSkillMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Primary
@Service
public class ExtendedLevelSkillServiceImpl extends LevelSkillServiceImpl implements ExtendedLevelSkillService {

    private final ExtendedLevelSkillRepository levelSkillRepository;
    private final LevelSkillMapper levelSkillMapper;

    public ExtendedLevelSkillServiceImpl(ExtendedLevelSkillRepository levelSkillRepository, LevelSkillMapper levelSkillMapper) {
        super(levelSkillRepository, levelSkillMapper);
        this.levelSkillRepository = levelSkillRepository;
        this.levelSkillMapper = levelSkillMapper;
    }

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
