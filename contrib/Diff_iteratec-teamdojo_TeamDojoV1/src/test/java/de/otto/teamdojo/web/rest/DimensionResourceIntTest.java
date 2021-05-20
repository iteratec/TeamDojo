1c1
<     package de.otto.teamdojo.web.rest;
---
> package de.otto.teamdojo.web.rest;
3a4
> 
5d5
< import de.otto.teamdojo.domain.Team;
7a8
> import de.otto.teamdojo.domain.Team;
10d10
< import de.otto.teamdojo.service.TeamService;
13a14
> import de.otto.teamdojo.service.dto.DimensionCriteria;
33a35
> 
86,89d87
<     @Autowired
<     private TeamService teamService;
< 
< 
104c102
<      * <p>
---
>      *
193c191
< 
---
>     
289,307d286
<     public void getAllDimensionsByParticipantsIsEqualToSomething() throws Exception {
<         // Initialize the database
<         Team participants = TeamResourceIntTest.createEntity(em);
<         em.persist(participants);
<         em.flush();
<         dimension.addParticipants(participants);
<         dimensionRepository.saveAndFlush(dimension);
<         Long participantsId = participants.getId();
< 
<         // Get all the dimensionList where participants equals to participantsId
<         defaultDimensionShouldBeFound("participantsId.equals=" + participantsId);
< 
<         // Get all the dimensionList where participants equals to participantsId + 1
<         defaultDimensionShouldNotBeFound("participantsId.equals=" + (participantsId + 1));
<     }
< 
< 
<     @Test
<     @Transactional
340a320,338
>     }
> 
> 
>     @Test
>     @Transactional
>     public void getAllDimensionsByParticipantsIsEqualToSomething() throws Exception {
>         // Initialize the database
>         Team participants = TeamResourceIntTest.createEntity(em);
>         em.persist(participants);
>         em.flush();
>         dimension.addParticipants(participants);
>         dimensionRepository.saveAndFlush(dimension);
>         Long participantsId = participants.getId();
> 
>         // Get all the dimensionList where participants equals to participantsId
>         defaultDimensionShouldBeFound("participantsId.equals=" + participantsId);
> 
>         // Get all the dimensionList where participants equals to participantsId + 1
>         defaultDimensionShouldNotBeFound("participantsId.equals=" + (participantsId + 1));
