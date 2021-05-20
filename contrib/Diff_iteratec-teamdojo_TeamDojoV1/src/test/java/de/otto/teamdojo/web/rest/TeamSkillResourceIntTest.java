544,553d543
<     @Test
<     @Transactional
<     public void getTeamSkillSkillStatusShouldNotBeNull()  {
<         TeamSkill persistedTeamSkill =  teamSkillRepository.saveAndFlush(teamSkill);
<         assertThat(persistedTeamSkill).isNotNull();
<         TeamSkillDTO teamSkillDTO =  teamSkillMapper.toDto(persistedTeamSkill);
<         assertThat(teamSkillDTO).isNotNull();
<         assertThat(teamSkillDTO.getSkillStatus()).isNotNull();
<     }
< 
