4,7c4
< import org.springframework.data.domain.Page;
< import org.springframework.data.domain.Pageable;
< import org.springframework.data.jpa.repository.JpaRepository;
< import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
---
> import org.springframework.data.jpa.repository.*;
10d6
< import java.util.List;
18d13
<     Page<Level> findByIdIn(List<Long> levelIds, Pageable pageable);
