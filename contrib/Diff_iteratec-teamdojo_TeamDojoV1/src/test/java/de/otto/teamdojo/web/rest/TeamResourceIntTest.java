6d5
< import de.otto.teamdojo.domain.Dimension;
8a8
> import de.otto.teamdojo.domain.Dimension;
61,62c61,62
<     private static final String DEFAULT_SHORT_NAME = "AAAAAA";
<     private static final String UPDATED_SHORT_NAME = "BBBBBB";
---
>     private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
>     private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";
620,638d619
<     public void getAllTeamsByParticipationsIsEqualToSomething() throws Exception {
<         // Initialize the database
<         Dimension participations = DimensionResourceIntTest.createEntity(em);
<         em.persist(participations);
<         em.flush();
<         team.addParticipations(participations);
<         teamRepository.saveAndFlush(team);
<         Long participationsId = participations.getId();
< 
<         // Get all the teamList where participations equals to participationsId
<         defaultTeamShouldBeFound("participationsId.equals=" + participationsId);
< 
<         // Get all the teamList where participations equals to participationsId + 1
<         defaultTeamShouldNotBeFound("participationsId.equals=" + (participationsId + 1));
<     }
< 
< 
<     @Test
<     @Transactional
671a653,671
>     }
> 
> 
>     @Test
>     @Transactional
>     public void getAllTeamsByParticipationsIsEqualToSomething() throws Exception {
>         // Initialize the database
>         Dimension participations = DimensionResourceIntTest.createEntity(em);
>         em.persist(participations);
>         em.flush();
>         team.addParticipations(participations);
>         teamRepository.saveAndFlush(team);
>         Long participationsId = participations.getId();
> 
>         // Get all the teamList where participations equals to participationsId
>         defaultTeamShouldBeFound("participationsId.equals=" + participationsId);
> 
>         // Get all the teamList where participations equals to participationsId + 1
>         defaultTeamShouldNotBeFound("participationsId.equals=" + (participationsId + 1));
