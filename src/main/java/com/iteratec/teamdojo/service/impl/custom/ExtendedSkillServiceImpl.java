package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.domain.Skill;
import com.iteratec.teamdojo.repository.SkillRepository;
import com.iteratec.teamdojo.service.custom.ExtendedSkillService;
import com.iteratec.teamdojo.service.dto.SkillDTO;
import com.iteratec.teamdojo.service.impl.SkillServiceImpl;
import com.iteratec.teamdojo.service.mapper.SkillMapper;
import com.iteratec.teamdojo.util.NullSafeAccess;
import lombok.NonNull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class ExtendedSkillServiceImpl extends SkillServiceImpl implements ExtendedSkillService {

    private final SkillRepository repo;
    private final SkillMapper mapper;
    private final AuditableDataTracker<SkillDTO, Skill> tracker;

    public ExtendedSkillServiceImpl(final SkillRepository repository, final SkillMapper mapper) {
        super(repository, mapper);
        this.repo = repository;
        this.mapper = mapper;
        this.tracker = new AuditableDataTracker<>(mapper, repository::findById);
    }

    public void setTime(@NonNull InstantProvider time) {
        tracker.setTime(time);
    }

    @Override
    public SkillDTO createVote(final long id, final int rateScore) {
        final Skill skill = repo.findById(id).orElseGet(Skill::new);

        skill.setRateScore(calculateAverageRate(skill, rateScore));
        skill.setRateCount(NullSafeAccess.get(skill.getRateCount()) + 1);

        return mapper.toDto(repo.saveAndFlush(skill));
    }

    double calculateAverageRate(final Skill skill, final int rateScore) {
        final int rateCount = NullSafeAccess.get(skill.getRateCount());
        final double sumRate = NullSafeAccess.get(skill.getRateScore()) * rateCount;
        final double newRate = sumRate + rateScore;

        return newRate / (rateCount + 1);
    }

    @Override
    public SkillDTO save(final SkillDTO skill) {
        tracker.modifyCreatedAndUpdatedAt(skill);
        return super.save(skill);
    }
}
