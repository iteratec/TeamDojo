package com.iteratec.teamdojo.repository;

import com.iteratec.teamdojo.domain.BadgeSkill;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BadgeSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BadgeSkillExtendedRepository extends JpaRepository<BadgeSkill, Long>, JpaSpecificationExecutor<BadgeSkill> {
    Page<BadgeSkill> findBySkillIdIn(List<Long> skillsIds, Pageable pageable);
}
