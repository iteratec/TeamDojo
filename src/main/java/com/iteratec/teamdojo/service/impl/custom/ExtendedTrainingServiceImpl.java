package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.domain.Training;
import com.iteratec.teamdojo.repository.TrainingRepository;
import com.iteratec.teamdojo.service.custom.ExtendedTrainingService;
import com.iteratec.teamdojo.service.dto.TrainingDTO;
import com.iteratec.teamdojo.service.impl.TrainingServiceImpl;
import com.iteratec.teamdojo.service.mapper.TrainingMapper;
import lombok.NonNull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ExtendedTrainingServiceImpl extends TrainingServiceImpl implements ExtendedTrainingService {

    private final AuditableDataTracker<TrainingDTO, Training> tracker;

    public ExtendedTrainingServiceImpl(TrainingRepository repo, TrainingMapper mapper) {
        super(repo, mapper);
        this.tracker = new AuditableDataTracker<>(mapper, repo::findById);
    }

    @Override
    public void setTime(@NonNull InstantProvider time) {
        tracker.setTime(time);
    }

    @Override
    public TrainingDTO save(final TrainingDTO dto) {
        tracker.modifyCreatedAndUpdatedAt(dto);
        return super.save(dto);
    }
}
