2a3
> import de.otto.teamdojo.service.LevelSkillService;
5d5
< import de.otto.teamdojo.service.LevelSkillService;
9a10
> 
15,16d15
< import java.util.LinkedList;
< import java.util.List;
18d16
< import java.util.stream.Collectors;
80,96d77
< 
<     /**
<      * Get levelSkills by skill id.
<      *
<      * @param skillIds the ids of the skills
<      * @return the entity
<      */
<     @Override
<     @Transactional(readOnly = true)
<     public List<LevelSkillDTO> findBySkillIdIn(List<Long> skillIds, Pageable pageable) {
<         log.debug("Request to get LevelSkill by skill Ids: {}", skillIds);
<         return levelSkillRepository.findBySkillIdIn(skillIds, pageable)
<             .stream()
<             .map(levelSkillMapper::toDto)
<             .collect(Collectors.toCollection(LinkedList::new));
<     }
< 
