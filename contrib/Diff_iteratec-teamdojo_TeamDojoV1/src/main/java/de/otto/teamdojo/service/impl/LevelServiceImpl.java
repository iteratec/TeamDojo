6d5
< import de.otto.teamdojo.service.LevelService;
17,18d15
< import java.util.LinkedList;
< import java.util.List;
20d16
< import java.util.stream.Collectors;
91,98d86
<     }
< 
<     public Page<LevelDTO> findByIdIn(List<Long> levelIds, Pageable pageable){
<         {
<             log.debug("Request to get Levels by level Ids: {}", levelIds);
<             return levelRepository.findByIdIn(levelIds, pageable)
<                 .map(levelMapper::toDto);
<         }
