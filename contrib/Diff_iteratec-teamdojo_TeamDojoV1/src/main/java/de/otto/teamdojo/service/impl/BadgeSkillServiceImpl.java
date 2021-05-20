2a3
> import de.otto.teamdojo.service.BadgeSkillService;
5d5
< import de.otto.teamdojo.service.BadgeSkillService;
9a10
> 
15,16d15
< import java.util.LinkedList;
< import java.util.List;
18d16
< import java.util.stream.Collectors;
79,95d76
<     }
< 
<     /**
<      * Get one badgeSkill by skill id.
<      *
<      * @param skillIds the id of the skills
<      * @return the entity
<      */
<     @Override
<     @Transactional(readOnly = true)
<     public List<BadgeSkillDTO> findBySkillIdIn(List<Long> skillIds, Pageable pageable){
<         log.debug("Request to get BadgeSkill by skill Ids: {}", skillIds);
< 
<         return badgeSkillRepository.findBySkillIdIn(skillIds, pageable)
<             .stream()
<             .map(badgeSkillMapper::toDto)
<             .collect(Collectors.toCollection(LinkedList::new));
