package com.iteratec.teamdojo.service.custom;

import com.iteratec.teamdojo.service.LevelService;
import com.iteratec.teamdojo.service.dto.LevelDTO;
import com.iteratec.teamdojo.service.impl.custom.InstantProviderInjectable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExtendedLevelService extends LevelService, InstantProviderInjectable {
    Page<LevelDTO> findByIdIn(List<Long> levelIds, Pageable pageable);
}
