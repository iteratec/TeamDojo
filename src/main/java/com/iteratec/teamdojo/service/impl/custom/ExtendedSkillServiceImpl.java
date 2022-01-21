package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.domain.Skill;
import com.iteratec.teamdojo.repository.SkillRepository;
import com.iteratec.teamdojo.service.custom.ExtendedSkillService;
import com.iteratec.teamdojo.service.dto.SkillDTO;
import com.iteratec.teamdojo.service.impl.SkillServiceImpl;
import com.iteratec.teamdojo.service.mapper.SkillMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class ExtendedSkillServiceImpl extends SkillServiceImpl implements ExtendedSkillService {

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    public ExtendedSkillServiceImpl(SkillRepository skillRepository, SkillMapper skillMapper) {
        super(skillRepository, skillMapper);
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    @Override
    public SkillDTO createVote(Long id, Integer rateScore) {
        Skill skill = skillRepository.findById(id).orElseGet(Skill::new);

        Integer rateCount = skill.getRateCount() == null ? 0 : skill.getRateCount();
        Double sumRate = (skill.getRateScore() == null ? 0 : skill.getRateScore()) * rateCount;
        Double newrate = sumRate + rateScore;
        Double avgRate = newrate / (rateCount + 1);

        skill.setRateScore(avgRate);
        skill.setRateCount(rateCount + 1);
        skill = skillRepository.saveAndFlush(skill);

        return skillMapper.toDto(skill);
    }
}
