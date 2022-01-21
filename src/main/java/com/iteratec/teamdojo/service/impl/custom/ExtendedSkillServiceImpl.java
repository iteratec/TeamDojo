package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.domain.Skill;
import com.iteratec.teamdojo.repository.SkillRepository;
import com.iteratec.teamdojo.service.custom.ExtendedSkillService;
import com.iteratec.teamdojo.service.dto.SkillDTO;
import com.iteratec.teamdojo.service.impl.SkillServiceImpl;
import com.iteratec.teamdojo.service.mapper.SkillMapper;
import com.iteratec.teamdojo.util.NullSafeAccess;
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
    public SkillDTO createVote(final long id, final int rateScore) {
        final Skill skill = skillRepository.findById(id).orElseGet(Skill::new);

        skill.setRateScore(calculateAverageRate(skill, rateScore));
        skill.setRateCount(NullSafeAccess.get(skill.getRateCount()) + 1);

        return skillMapper.toDto(skillRepository.saveAndFlush(skill));
    }

    double calculateAverageRate(final Skill skill, final int rateScore) {
        final int rateCount = NullSafeAccess.get(skill.getRateCount());
        final double sumRate = NullSafeAccess.get(skill.getRateScore()) * rateCount;
        final double newRate = sumRate + rateScore;

        return newRate / (rateCount + 1);
    }
}
