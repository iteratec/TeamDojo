package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.domain.Dimension;
import com.iteratec.teamdojo.repository.DimensionRepository;
import com.iteratec.teamdojo.service.custom.ExtendedDimensionService;
import com.iteratec.teamdojo.service.dto.DimensionDTO;
import com.iteratec.teamdojo.service.impl.DimensionServiceImpl;
import com.iteratec.teamdojo.service.mapper.DimensionMapper;
import lombok.NonNull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ExtendedDimensionServiceImpl extends DimensionServiceImpl implements ExtendedDimensionService {

    private final AuditableDataTracker<DimensionDTO, Dimension> tracker;

    public ExtendedDimensionServiceImpl(DimensionRepository repo, DimensionMapper mapper) {
        super(repo, mapper);
        this.tracker = new AuditableDataTracker<>(mapper, repo::findById);
    }

    @Override
    public void setTime(@NonNull InstantProvider time) {
        tracker.setTime(time);
    }

    @Override
    public DimensionDTO save(final DimensionDTO dto) {
        tracker.modifyCreatedAndUpdatedAt(dto);
        return super.save(dto);
    }
}
