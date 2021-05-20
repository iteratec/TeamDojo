4c4,8
< import de.otto.teamdojo.domain.*;
---
> 
> import de.otto.teamdojo.domain.Badge;
> import de.otto.teamdojo.domain.BadgeSkill;
> import de.otto.teamdojo.domain.Image;
> import de.otto.teamdojo.domain.Dimension;
6,7d9
< import de.otto.teamdojo.service.ActivityService;
< import de.otto.teamdojo.service.BadgeQueryService;
9d10
< import de.otto.teamdojo.service.BadgeSkillService;
11d11
< import de.otto.teamdojo.service.impl.BadgeServiceImpl;
13a14,15
> import de.otto.teamdojo.service.dto.BadgeCriteria;
> import de.otto.teamdojo.service.BadgeQueryService;
22a25
> import org.springframework.data.domain.PageRequest;
37d39
< import java.util.Date;
39,49c41
< import static de.otto.teamdojo.test.util.BadgeTestDataProvider.alwaysUpToDate;
< import static de.otto.teamdojo.test.util.BadgeTestDataProvider.awsReady;
< import static de.otto.teamdojo.test.util.DimensionTestDataProvider.security;
< import static de.otto.teamdojo.test.util.DimensionTestDataProvider.operations;
< import static de.otto.teamdojo.test.util.LevelTestDataProvider.orange;
< import static de.otto.teamdojo.test.util.LevelTestDataProvider.yellow;
< import static de.otto.teamdojo.test.util.LevelTestDataProvider.os1;
< import static de.otto.teamdojo.test.util.SkillTestDataProvider.*;
< import static de.otto.teamdojo.test.util.SkillTestDataProvider.evilUserStories;
< import static de.otto.teamdojo.test.util.TeamTestDataProvider.ft1;
< import static de.otto.teamdojo.test.util.TeamTestDataProvider.ft2;
---
> 
52d43
< import static org.hamcrest.Matchers.containsInAnyOrder;
92c83
<     private ActivityService activityServiceMock;
---
>     private BadgeRepository badgeRepositoryMock;
101c92
<     private BadgeQueryService badgeQueryService;
---
>     private BadgeService badgeService;
104c95
<     private BadgeSkillService badgeSkillService;
---
>     private BadgeQueryService badgeQueryService;
125,139d115
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
143,144c119
<         BadgeService badgeService = new BadgeServiceImpl(badgeRepository, badgeMapper, activityServiceMock);
<         final BadgeResource badgeResource = new BadgeResource(badgeService, badgeQueryService, badgeSkillService);
---
>         final BadgeResource badgeResource = new BadgeResource(badgeService, badgeQueryService);
297c272
< 
---
>     
300c275
<         BadgeResource badgeResource = new BadgeResource(badgeServiceMock, badgeQueryService, badgeSkillService);
---
>         BadgeResource badgeResource = new BadgeResource(badgeServiceMock, badgeQueryService);
310c285
<             .andExpect(status().isOk());
---
>         .andExpect(status().isOk());
317,319c292,294
<         BadgeResource badgeResource = new BadgeResource(badgeServiceMock, badgeQueryService, badgeSkillService);
<         when(badgeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
<         MockMvc restBadgeMockMvc = MockMvcBuilders.standaloneSetup(badgeResource)
---
>         BadgeResource badgeResource = new BadgeResource(badgeServiceMock, badgeQueryService);
>             when(badgeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
>             MockMvc restBadgeMockMvc = MockMvcBuilders.standaloneSetup(badgeResource)
326c301
<             .andExpect(status().isOk());
---
>         .andExpect(status().isOk());
328c303
<         verify(badgeServiceMock, times(1)).findAllWithEagerRelationships(any());
---
>             verify(badgeServiceMock, times(1)).findAllWithEagerRelationships(any());
695a671
> 
698,700c674,677
<     public void getAllBadgesBySkillIds() throws Exception {
< 
<         setupTestData();
---
>     public void getAllBadgesByImageIsEqualToSomething() throws Exception {
>         // Initialize the database
>         Image image = ImageResourceIntTest.createEntity(em);
>         em.persist(image);
701a679,681
>         badge.setImage(image);
>         badgeRepository.saveAndFlush(badge);
>         Long imageId = image.getId();
703,707c683,684
<         restBadgeMockMvc.perform(get("/api/badges?skillsId.in=" + softwareUpdates.getId()))
<             .andExpect(status().isOk())
<             .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
<             .andExpect(jsonPath("$.length()").value(1))
<             .andExpect(jsonPath("$.[*].id").value(containsInAnyOrder(alwaysUpToDate.getId().intValue())));
---
>         // Get all the badgeList where image equals to imageId
>         defaultBadgeShouldBeFound("imageId.equals=" + imageId);
708a686,687
>         // Get all the badgeList where image equals to imageId + 1
>         defaultBadgeShouldNotBeFound("imageId.equals=" + (imageId + 1));
730,748d708
< 
<     @Test
<     @Transactional
<     public void getAllBadgesByImageIsEqualToSomething() throws Exception {
<         // Initialize the database
<         Image image = ImageResourceIntTest.createEntity(em);
<         em.persist(image);
<         em.flush();
<         badge.setImage(image);
<         badgeRepository.saveAndFlush(badge);
<         Long imageId = image.getId();
< 
<         // Get all the badgeList where image equals to imageId
<         defaultBadgeShouldBeFound("imageId.equals=" + imageId);
< 
<         // Get all the badgeList where image equals to imageId + 1
<         defaultBadgeShouldNotBeFound("imageId.equals=" + (imageId + 1));
<     }
< 
911,963d870
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
