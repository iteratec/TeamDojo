4,8c4
< import de.otto.teamdojo.service.SkillService;
< import org.springframework.data.domain.Page;
< import org.springframework.data.domain.Pageable;
< import org.springframework.data.jpa.repository.JpaRepository;
< import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
---
> import org.springframework.data.jpa.repository.*;
11d6
< import java.util.List;
19,20d13
< 
<     Page<LevelSkill> findBySkillIdIn(List<Long> skillIds,  Pageable pageable);
