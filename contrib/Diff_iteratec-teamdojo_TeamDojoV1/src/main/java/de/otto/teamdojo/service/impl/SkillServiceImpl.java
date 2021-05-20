2a3
> import de.otto.teamdojo.service.SkillService;
5d5
< import de.otto.teamdojo.service.SkillService;
9a10
> 
86,106d86
<     }
< 
<     /**
<      * Creates a new vote
<      * @param id the entity to udpate
<      * @param rateScore stars to update
<      * @return the persisted entity
<      */
<     public SkillDTO createVote(Long id, Integer rateScore){
<         Skill skill = skillRepository.findById(id).get();
< 
<         Integer rateCount = skill.getRateCount() == null ? 0 : skill.getRateCount();
<         Double sumRate = (skill.getRateScore() == null ? 0 : skill.getRateScore()) * rateCount;
<         Double newrate = sumRate + rateScore;
<         Double avgRate = newrate / (rateCount + 1);
< 
<         skill.setRateScore(avgRate);
<         skill.setRateCount(rateCount+1);
<         skill = skillRepository.saveAndFlush(skill);
< 
<         return skillMapper.toDto(skill);
