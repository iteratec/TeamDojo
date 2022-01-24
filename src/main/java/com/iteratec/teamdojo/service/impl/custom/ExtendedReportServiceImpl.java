package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.domain.Report;
import com.iteratec.teamdojo.repository.ReportRepository;
import com.iteratec.teamdojo.service.custom.ExtendedReportService;
import com.iteratec.teamdojo.service.dto.ReportDTO;
import com.iteratec.teamdojo.service.impl.ReportServiceImpl;
import com.iteratec.teamdojo.service.mapper.ReportMapper;
import lombok.NonNull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ExtendedReportServiceImpl extends ReportServiceImpl implements ExtendedReportService {

    private final AuditableDataTracker<ReportDTO, Report> tracker;

    public ExtendedReportServiceImpl(ReportRepository repo, ReportMapper mapper) {
        super(repo, mapper);
        this.tracker = new AuditableDataTracker<>(mapper, repo::findById);
    }

    @Override
    public void setTime(@NonNull InstantProvider time) {
        tracker.setTime(time);
    }

    @Override
    public ReportDTO save(final ReportDTO dto) {
        tracker.modifyCreatedAndUpdatedAt(dto);
        return super.save(dto);
    }
}
