package com.iteratec.teamdojo.service.impl.ext;

import com.iteratec.teamdojo.repository.ext.ExtendedBadgeSkillRepository;
import com.iteratec.teamdojo.service.dto.BadgeSkillDTO;
import com.iteratec.teamdojo.service.ext.ExtendedBadgeSkillService;
import com.iteratec.teamdojo.service.impl.BadgeSkillServiceImpl;
import com.iteratec.teamdojo.service.mapper.BadgeSkillMapper;
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
public class ExtendedBadgeSkillServiceImpl extends BadgeSkillServiceImpl implements ExtendedBadgeSkillService {

    private final Logger log = LoggerFactory.getLogger(ExtendedBadgeSkillServiceImpl.class);
    private final ExtendedBadgeSkillRepository badgeSkillRepository;
    private final BadgeSkillMapper badgeSkillMapper;

    public ExtendedBadgeSkillServiceImpl(ExtendedBadgeSkillRepository badgeSkillRepository, BadgeSkillMapper badgeSkillMapper) {
        super(badgeSkillRepository, badgeSkillMapper);
        this.badgeSkillRepository = badgeSkillRepository;
        this.badgeSkillMapper = badgeSkillMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BadgeSkillDTO> findBySkillIdIn(List<Long> skillIds, Pageable pageable) {
        log.debug("Request to get BadgeSkill by skill Ids: {}", skillIds);

        return badgeSkillRepository
            .findBySkillIdIn(skillIds, pageable)
            .stream()
            .map(badgeSkillMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}