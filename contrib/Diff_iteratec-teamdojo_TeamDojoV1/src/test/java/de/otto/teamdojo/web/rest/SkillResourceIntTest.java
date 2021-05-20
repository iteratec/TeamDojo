6d5
< import de.otto.teamdojo.domain.TeamSkill;
8a8
> import de.otto.teamdojo.domain.TeamSkill;
13d12
< import de.otto.teamdojo.service.dto.SkillRateDTO;
36a36
> 
64,66c64,65
<     private static final Integer DEFAULT_EXPIRY_PERIOD = 24;
< 
<     private static final Integer UPDATED_EXPIRY_PERIOD = 25;
---
>     private static final Integer DEFAULT_EXPIRY_PERIOD = 1;
>     private static final Integer UPDATED_EXPIRY_PERIOD = 2;
125c124
<      * <p>
---
>      *
252,253c251
<             .andExpect(jsonPath("$.[*].rateCount").value(hasItem(DEFAULT_RATE_COUNT)))
<             .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)));
---
>             .andExpect(jsonPath("$.[*].rateCount").value(hasItem(DEFAULT_RATE_COUNT)));
255c253
< 
---
>     
709,726d706
<     @Test
<     @Transactional
<     public void getAllSkillsByTeamsIsEqualToSomething() throws Exception {
<         // Initialize the database
<         TeamSkill teams = TeamSkillResourceIntTest.createEntity(em);
<         em.persist(teams);
<         em.flush();
<         skill.addTeams(teams);
<         skillRepository.saveAndFlush(skill);
<         Long teamsId = teams.getId();
< 
<         // Get all the skillList where teams equals to teamsId
<         defaultSkillShouldBeFound("teamsId.equals=" + teamsId);
< 
<         // Get all the skillList where teams equals to teamsId + 1
<         defaultSkillShouldNotBeFound("teamsId.equals=" + (teamsId + 1));
<     }
< 
767a748,766
>     public void getAllSkillsByTeamsIsEqualToSomething() throws Exception {
>         // Initialize the database
>         TeamSkill teams = TeamSkillResourceIntTest.createEntity(em);
>         em.persist(teams);
>         em.flush();
>         skill.addTeams(teams);
>         skillRepository.saveAndFlush(skill);
>         Long teamsId = teams.getId();
> 
>         // Get all the skillList where teams equals to teamsId
>         defaultSkillShouldBeFound("teamsId.equals=" + teamsId);
> 
>         // Get all the skillList where teams equals to teamsId + 1
>         defaultSkillShouldNotBeFound("teamsId.equals=" + (teamsId + 1));
>     }
> 
> 
>     @Test
>     @Transactional
877,913d875
<     }
< 
<     @Test
<     @Transactional
<     public void createVote() throws Exception {
<         // Initialize the database
<         skillRepository.saveAndFlush(skill);
< 
<         assertThat(skill.getRateCount()).isEqualTo(0);
<         assertThat(skill.getRateScore()).isEqualTo(0);
< 
< 
<         SkillRateDTO skillRateDto = new SkillRateDTO(skill.getId(), 4, 5);
< 
<         restSkillMockMvc.perform(post("/api/skills/{id}/vote", skill.getId())
<             .contentType(TestUtil.APPLICATION_JSON_UTF8)
<             .content(TestUtil.convertObjectToJsonBytes(skillRateDto)))
<             .andExpect(status().isOk());
< 
<         // Validate the Skill in the database
<         List<Skill> skillList = skillRepository.findAll();
<         Skill testSkill = skillList.get(skillList.size() - 1);
<         assertThat(testSkill.getRateScore()).isEqualTo(4);
<         assertThat(testSkill.getRateCount()).isEqualTo(1);
< 
<         skillRateDto = new SkillRateDTO(skill.getId(), 2, 5);
< 
<         restSkillMockMvc.perform(post("/api/skills/{id}/vote", skill.getId())
<             .contentType(TestUtil.APPLICATION_JSON_UTF8)
<             .content(TestUtil.convertObjectToJsonBytes(skillRateDto)))
<             .andExpect(status().isOk());
< 
<         skillList = skillRepository.findAll();
<         testSkill = skillList.get(skillList.size() - 1);
<         assertThat(testSkill.getRateScore()).isEqualTo(3);
<         assertThat(testSkill.getRateCount()).isEqualTo(2);
< 
