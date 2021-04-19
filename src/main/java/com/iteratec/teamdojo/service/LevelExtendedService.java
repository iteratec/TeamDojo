package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.service.dto.LevelDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.iteratec.teamdojo.domain.Level}.
 */
public interface LevelExtendedService extends LevelService {
    Page<LevelDTO> findByIdIn(List<Long> levelIds, Pageable pageable);
}
