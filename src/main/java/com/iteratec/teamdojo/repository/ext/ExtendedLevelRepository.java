package com.iteratec.teamdojo.repository.ext;

import com.iteratec.teamdojo.domain.Level;
import com.iteratec.teamdojo.repository.LevelRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExtendedLevelRepository extends LevelRepository {
    Page<Level> findByIdIn(List<Long> levelIds, Pageable pageable);
}
