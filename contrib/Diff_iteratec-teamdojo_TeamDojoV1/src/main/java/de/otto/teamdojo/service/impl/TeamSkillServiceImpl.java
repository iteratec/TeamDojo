2a3
> import de.otto.teamdojo.service.TeamSkillService;
5,6d5
< import de.otto.teamdojo.service.OrganizationService;
< import de.otto.teamdojo.service.TeamSkillService;
10a10
> 
16d15
< import java.time.Instant;
32,35c31
<     private final OrganizationService organizationService;
< 
<     public TeamSkillServiceImpl(TeamSkillRepository teamSkillRepository, TeamSkillMapper teamSkillMapper,
<                                 OrganizationService organizationService) {
---
>     public TeamSkillServiceImpl(TeamSkillRepository teamSkillRepository, TeamSkillMapper teamSkillMapper) {
38d33
<         this.organizationService = organizationService;
50,63d44
<         if (teamSkillDTO.getVote() == null) {
<             teamSkillDTO.setVote(0);
<         }
< 
<         Integer requiredVotes = organizationService.getCurrentOrganization().getCountOfConfirmations();
< 
<         if (teamSkillDTO.getVote() >= requiredVotes && teamSkillDTO.getVerifiedAt() == null) {
<             teamSkillDTO.setVerifiedAt(Instant.now());
<         }
< 
<         if (teamSkillDTO.getVote() < requiredVotes && teamSkillDTO.getVerifiedAt() != null) {
<             teamSkillDTO.setVerifiedAt(null);
<         }
< 
105,106c86
<         log.debug("Request to delete TeamSkill : {}", id);
<         teamSkillRepository.deleteById(id);
---
>         log.debug("Request to delete TeamSkill : {}", id);        teamSkillRepository.deleteById(id);
