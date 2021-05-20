4c4,9
< import de.otto.teamdojo.domain.*;
---
> 
> import de.otto.teamdojo.domain.Level;
> import de.otto.teamdojo.domain.Level;
> import de.otto.teamdojo.domain.LevelSkill;
> import de.otto.teamdojo.domain.Image;
> import de.otto.teamdojo.domain.Dimension;
6d10
< import de.otto.teamdojo.service.LevelQueryService;
8d11
< import de.otto.teamdojo.service.LevelSkillService;
11a15,17
> import de.otto.teamdojo.service.dto.LevelCriteria;
> import de.otto.teamdojo.service.LevelQueryService;
> 
25d30
< import org.springframework.util.Base64Utils;
29d33
< import java.util.Date;
33,40d36
< import static de.otto.teamdojo.test.util.BadgeTestDataProvider.alwaysUpToDate;
< import static de.otto.teamdojo.test.util.BadgeTestDataProvider.awsReady;
< import static de.otto.teamdojo.test.util.DimensionTestDataProvider.operations;
< import static de.otto.teamdojo.test.util.DimensionTestDataProvider.security;
< import static de.otto.teamdojo.test.util.LevelTestDataProvider.*;
< import static de.otto.teamdojo.test.util.SkillTestDataProvider.*;
< import static de.otto.teamdojo.test.util.TeamTestDataProvider.ft1;
< import static de.otto.teamdojo.test.util.TeamTestDataProvider.ft2;
43d38
< import static org.hamcrest.Matchers.containsInAnyOrder;
85,87d79
<     private LevelSkillService levelSkillService;
< 
<     @Autowired
106,122d97
<     private Badge badge;
< 
<     private Team team1;
<     private Team team2;
<     private Skill inputValidation;
<     private Skill softwareUpdates;
<     private Skill strongPasswords;
<     private Skill dockerized;
<     private Level yellow;
<     private Level orange;
<     private Level os1;
<     private Dimension security;
<     private Dimension operations;
<     private TeamSkill teamSkill;
<     private Badge awsReady;
<     private Badge alwaysUpToDate;
< 
126c101
<         final LevelResource levelResource = new LevelResource(levelService, levelQueryService, levelSkillService);
---
>         final LevelResource levelResource = new LevelResource(levelService, levelQueryService);
278c253
< 
---
>     
521,539d495
<     public void getAllLevelsByDimensionIsEqualToSomething() throws Exception {
<         // Initialize the database
<         Dimension dimension = DimensionResourceIntTest.createEntity(em);
<         em.persist(dimension);
<         em.flush();
<         level.setDimension(dimension);
<         levelRepository.saveAndFlush(level);
<         Long dimensionId = dimension.getId();
< 
<         // Get all the levelList where dimension equals to dimensionId
<         defaultLevelShouldBeFound("dimensionId.equals=" + dimensionId);
< 
<         // Get all the levelList where dimension equals to dimensionId + 1
<         defaultLevelShouldNotBeFound("dimensionId.equals=" + (dimensionId + 1));
<     }
< 
< 
<     @Test
<     @Transactional
574a531
> 
592a550
> 
595,608c553
<     public void getAllLevelsBySkillId() throws Exception {
< 
<         setupTestData();
<         em.flush();
< 
<         restLevelMockMvc.perform(get("/api/levels?skillsId.in="+softwareUpdates.getId()))
<             .andExpect(status().isOk())
<             .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
<             .andExpect(jsonPath("$.length()").value(2))
<             .andExpect(jsonPath("$.[*].id").value(containsInAnyOrder(
<                 yellow.getId().intValue(),
<                 os1.getId().intValue())));
< 
< 
---
>     public void getAllLevelsByDimensionIsEqualToSomething() throws Exception {
610,611c555,556
<         LevelSkill skills = LevelSkillResourceIntTest.createEntity(em);
<         em.persist(skills);
---
>         Dimension dimension = DimensionResourceIntTest.createEntity(em);
>         em.persist(dimension);
613c558
<         level.addSkills(skills);
---
>         level.setDimension(dimension);
615c560
<         Long skillsId = skills.getId();
---
>         Long dimensionId = dimension.getId();
617,618c562,563
<         // Get all the levelList where skills equals to skillsId
<         defaultLevelShouldBeFound("skillsId.equals=" + skillsId);
---
>         // Get all the levelList where dimension equals to dimensionId
>         defaultLevelShouldBeFound("dimensionId.equals=" + dimensionId);
620,621c565,566
<         // Get all the levelList where skills equals to skillsId + 1
<         defaultLevelShouldNotBeFound("skillsId.equals=" + (skillsId + 1));
---
>         // Get all the levelList where dimension equals to dimensionId + 1
>         defaultLevelShouldNotBeFound("dimensionId.equals=" + (dimensionId + 1));
624d568
< 
781,833d724
<     }
< 
<     private void setupTestData() {
<         inputValidation = inputValidation().build(em);
<         softwareUpdates = softwareUpdates().build(em);
<         strongPasswords = strongPasswords().build(em);
<         dockerized = dockerized().build(em);
<         Skill evilUserStories = evilUserStories().build(em);
< 
<         security = security().build(em);
<         operations = operations().build(em);
< 
<         yellow = yellow(security).addSkill(inputValidation).addSkill(softwareUpdates).build(em);
<         orange = orange(security).addSkill(strongPasswords).dependsOn(yellow).build(em);
<         os1 = os1(operations).addSkill(softwareUpdates).build(em);
< 
<         awsReady = awsReady().addDimension(security).addDimension(operations).
<             addSkill(inputValidation).addSkill(dockerized).build(em);
< 
<         alwaysUpToDate = alwaysUpToDate().addSkill(softwareUpdates).build(em);
< 
<         team1 = ft1().build(em);
<         team1.addParticipations(security);
<         em.persist(team1);
<         teamSkill = new TeamSkill();
<         teamSkill.setTeam(team1);
<         teamSkill.setSkill(inputValidation);
<         teamSkill.setVote(1);
<         em.persist(teamSkill);
<         team1.addSkills(teamSkill);
<         em.persist(team1);
< 
<         teamSkill = new TeamSkill();
<         teamSkill.setTeam(team1);
<         teamSkill.setSkill(softwareUpdates);
<         teamSkill.setVote(1);
<         em.persist(teamSkill);
<         team1.addSkills(teamSkill);
<         em.persist(team1);
< 
<         team2 = ft2().build(em);
<         team2.addParticipations(security);
<         team2.addParticipations(operations);
<         em.persist(team2);
<         teamSkill = new TeamSkill();
<         teamSkill.setTeam(team2);
<         teamSkill.setSkill(softwareUpdates);
<         teamSkill.setCompletedAt(new Date().toInstant());
<         teamSkill.setVote(1);
<         em.persist(teamSkill);
<         team2.addSkills(teamSkill);
<         em.persist(team2);
< 
