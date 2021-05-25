package com.iteratec.teamdojo.service.ext;

import com.iteratec.teamdojo.service.LevelService;
import com.iteratec.teamdojo.service.dto.LevelDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExtendedLevelService extends LevelService {
    Page<LevelDTO> findByIdIn(List<Long> levelIds, Pageable pageable);
}
