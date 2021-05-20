6,7d5
< import de.otto.teamdojo.service.ActivityService;
< import de.otto.teamdojo.service.BadgeService;
18d15
< import java.util.List;
34,36c31
<     private final ActivityService activityService;
< 
<     public BadgeServiceImpl(BadgeRepository badgeRepository, BadgeMapper badgeMapper, ActivityService activityService) {
---
>     public BadgeServiceImpl(BadgeRepository badgeRepository, BadgeMapper badgeMapper) {
39d33
<         this.activityService = activityService;
51d44
<         boolean newBadge = badgeDTO.getId() == null;
54,58c47
<         badgeDTO = badgeMapper.toDto(badge);
<         if (newBadge) {
<             this.activityService.createForNewBadge(badgeDTO);
<         }
<         return badgeDTO;
---
>         return badgeMapper.toDto(badge);
75,81d63
<     public Page<BadgeDTO> findByIdIn(List<Long> badgeIds, Pageable pageable){
<         log.debug("Request to get Badges by Badge Ids: {}", badgeIds);
<         return badgeRepository.findByIdIn(badgeIds, pageable)
<             .map(badgeMapper::toDto);
< 
<     }
< 
89a72
>     
112,113c95
<         log.debug("Request to delete Badge : {}", id);
<         badgeRepository.deleteById(id);
---
>         log.debug("Request to delete Badge : {}", id);        badgeRepository.deleteById(id);
