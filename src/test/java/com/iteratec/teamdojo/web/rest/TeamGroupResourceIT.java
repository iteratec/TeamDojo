package com.iteratec.teamdojo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.IntegrationTest;
import com.iteratec.teamdojo.domain.Team;
import com.iteratec.teamdojo.domain.TeamGroup;
import com.iteratec.teamdojo.domain.TeamGroup;
import com.iteratec.teamdojo.repository.TeamGroupRepository;
import com.iteratec.teamdojo.service.criteria.TeamGroupCriteria;
// ### MODIFICATION-START ###
import com.iteratec.teamdojo.service.custom.ExtendedTeamGroupService;
// ### MODIFICATION-END ###
import com.iteratec.teamdojo.service.dto.TeamGroupDTO;
import com.iteratec.teamdojo.service.mapper.TeamGroupMapper;
// ### MODIFICATION-START ###
import com.iteratec.teamdojo.test.util.StaticInstantProvider;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
// ### MODIFICATION-END ###
import org.junit.jupiter.api.BeforeEach;
// ### MODIFICATION-START ###
import org.junit.jupiter.api.Disabled;
// ### MODIFICATION-END ###
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TeamGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
@GeneratedByJHipster
class TeamGroupResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    // ### MODIFICATION-START ###
    private static final Instant CUSTOM_CREATED_AND_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    // ### MODIFICATION-END ###
    private static final String ENTITY_API_URL = "/api/team-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TeamGroupRepository teamGroupRepository;

    @Autowired
    private TeamGroupMapper teamGroupMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTeamGroupMockMvc;

    // ### MODIFICATION-START ###
    @Autowired
    private ExtendedTeamGroupService extendedTeamGroupService;

    // ### MODIFICATION-END ###

    private TeamGroup teamGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamGroup createEntity(EntityManager em) {
        TeamGroup teamGroup = new TeamGroup()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return teamGroup;
    }

    // ### MODIFICATION-START ###
    /**
     * Create a parent entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamGroup createParentEntity(EntityManager em) {
        TeamGroup teamGroup = new TeamGroup()
            .title("Parent")
            .description("This is used als parent for the actual subject under test to prevent endless loops!")
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return teamGroup;
    }

    // ### MODIFICATION-END ###

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamGroup createUpdatedEntity(EntityManager em) {
        TeamGroup teamGroup = new TeamGroup()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return teamGroup;
    }

    @BeforeEach
    public void initTest() {
        teamGroup = createEntity(em);
    }

    // ### MODIFICATION-START ###
    @BeforeEach
    public void initInstantProvider() {
        extendedTeamGroupService.setTime(StaticInstantProvider.forFixedTime(CUSTOM_CREATED_AND_UPDATED_AT));
    }

    // ### MODIFICATION-END ###

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void createTeamGroup() throws Exception {
        int databaseSizeBeforeCreate = teamGroupRepository.findAll().size();
        // Create the TeamGroup
        TeamGroupDTO teamGroupDTO = teamGroupMapper.toDto(teamGroup);
        restTeamGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamGroupDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TeamGroup in the database
        List<TeamGroup> teamGroupList = teamGroupRepository.findAll();
        assertThat(teamGroupList).hasSize(databaseSizeBeforeCreate + 1);
        TeamGroup testTeamGroup = teamGroupList.get(teamGroupList.size() - 1);
        assertThat(testTeamGroup.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTeamGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        // ### MODIFICATION-START ###
        assertThat(testTeamGroup.getCreatedAt()).isEqualTo(CUSTOM_CREATED_AND_UPDATED_AT);
        assertThat(testTeamGroup.getUpdatedAt()).isEqualTo(CUSTOM_CREATED_AND_UPDATED_AT);
        // ### MODIFICATION-END ###
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void createTeamGroupWithExistingId() throws Exception {
        // Create the TeamGroup with an existing ID
        teamGroup.setId(1L);
        TeamGroupDTO teamGroupDTO = teamGroupMapper.toDto(teamGroup);

        int databaseSizeBeforeCreate = teamGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamGroup in the database
        List<TeamGroup> teamGroupList = teamGroupRepository.findAll();
        assertThat(teamGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamGroupRepository.findAll().size();
        // set the field null
        teamGroup.setTitle(null);

        // Create the TeamGroup, which fails.
        TeamGroupDTO teamGroupDTO = teamGroupMapper.toDto(teamGroup);

        restTeamGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamGroupDTO))
            )
            .andExpect(status().isBadRequest());

        List<TeamGroup> teamGroupList = teamGroupRepository.findAll();
        assertThat(teamGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @Disabled("Ignored because we removed the validation for this field in the DTO.")
    // ### MODIFICATION-END ###
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamGroupRepository.findAll().size();
        // set the field null
        teamGroup.setCreatedAt(null);

        // Create the TeamGroup, which fails.
        TeamGroupDTO teamGroupDTO = teamGroupMapper.toDto(teamGroup);

        restTeamGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamGroupDTO))
            )
            .andExpect(status().isBadRequest());

        List<TeamGroup> teamGroupList = teamGroupRepository.findAll();
        assertThat(teamGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @Disabled("Ignored because we removed the validation for this field in the DTO.")
    // ### MODIFICATION-END ###
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamGroupRepository.findAll().size();
        // set the field null
        teamGroup.setUpdatedAt(null);

        // Create the TeamGroup, which fails.
        TeamGroupDTO teamGroupDTO = teamGroupMapper.toDto(teamGroup);

        restTeamGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamGroupDTO))
            )
            .andExpect(status().isBadRequest());

        List<TeamGroup> teamGroupList = teamGroupRepository.findAll();
        assertThat(teamGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTeamGroups() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        // Get all the teamGroupList
        restTeamGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getTeamGroup() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        // Get the teamGroup
        restTeamGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, teamGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(teamGroup.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getTeamGroupsByIdFiltering() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        Long id = teamGroup.getId();

        defaultTeamGroupShouldBeFound("id.equals=" + id);
        defaultTeamGroupShouldNotBeFound("id.notEquals=" + id);

        defaultTeamGroupShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTeamGroupShouldNotBeFound("id.greaterThan=" + id);

        defaultTeamGroupShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTeamGroupShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTeamGroupsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        // Get all the teamGroupList where title equals to DEFAULT_TITLE
        defaultTeamGroupShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the teamGroupList where title equals to UPDATED_TITLE
        defaultTeamGroupShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTeamGroupsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        // Get all the teamGroupList where title not equals to DEFAULT_TITLE
        defaultTeamGroupShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the teamGroupList where title not equals to UPDATED_TITLE
        defaultTeamGroupShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTeamGroupsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        // Get all the teamGroupList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultTeamGroupShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the teamGroupList where title equals to UPDATED_TITLE
        defaultTeamGroupShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTeamGroupsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        // Get all the teamGroupList where title is not null
        defaultTeamGroupShouldBeFound("title.specified=true");

        // Get all the teamGroupList where title is null
        defaultTeamGroupShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamGroupsByTitleContainsSomething() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        // Get all the teamGroupList where title contains DEFAULT_TITLE
        defaultTeamGroupShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the teamGroupList where title contains UPDATED_TITLE
        defaultTeamGroupShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTeamGroupsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        // Get all the teamGroupList where title does not contain DEFAULT_TITLE
        defaultTeamGroupShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the teamGroupList where title does not contain UPDATED_TITLE
        defaultTeamGroupShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTeamGroupsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        // Get all the teamGroupList where description equals to DEFAULT_DESCRIPTION
        defaultTeamGroupShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the teamGroupList where description equals to UPDATED_DESCRIPTION
        defaultTeamGroupShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTeamGroupsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        // Get all the teamGroupList where description not equals to DEFAULT_DESCRIPTION
        defaultTeamGroupShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the teamGroupList where description not equals to UPDATED_DESCRIPTION
        defaultTeamGroupShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTeamGroupsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        // Get all the teamGroupList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultTeamGroupShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the teamGroupList where description equals to UPDATED_DESCRIPTION
        defaultTeamGroupShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTeamGroupsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        // Get all the teamGroupList where description is not null
        defaultTeamGroupShouldBeFound("description.specified=true");

        // Get all the teamGroupList where description is null
        defaultTeamGroupShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamGroupsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        // Get all the teamGroupList where description contains DEFAULT_DESCRIPTION
        defaultTeamGroupShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the teamGroupList where description contains UPDATED_DESCRIPTION
        defaultTeamGroupShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTeamGroupsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        // Get all the teamGroupList where description does not contain DEFAULT_DESCRIPTION
        defaultTeamGroupShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the teamGroupList where description does not contain UPDATED_DESCRIPTION
        defaultTeamGroupShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTeamGroupsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        // Get all the teamGroupList where createdAt equals to DEFAULT_CREATED_AT
        defaultTeamGroupShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the teamGroupList where createdAt equals to UPDATED_CREATED_AT
        defaultTeamGroupShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllTeamGroupsByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        // Get all the teamGroupList where createdAt not equals to DEFAULT_CREATED_AT
        defaultTeamGroupShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the teamGroupList where createdAt not equals to UPDATED_CREATED_AT
        defaultTeamGroupShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllTeamGroupsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        // Get all the teamGroupList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultTeamGroupShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the teamGroupList where createdAt equals to UPDATED_CREATED_AT
        defaultTeamGroupShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllTeamGroupsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        // Get all the teamGroupList where createdAt is not null
        defaultTeamGroupShouldBeFound("createdAt.specified=true");

        // Get all the teamGroupList where createdAt is null
        defaultTeamGroupShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamGroupsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        // Get all the teamGroupList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultTeamGroupShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the teamGroupList where updatedAt equals to UPDATED_UPDATED_AT
        defaultTeamGroupShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllTeamGroupsByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        // Get all the teamGroupList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultTeamGroupShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the teamGroupList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultTeamGroupShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllTeamGroupsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        // Get all the teamGroupList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultTeamGroupShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the teamGroupList where updatedAt equals to UPDATED_UPDATED_AT
        defaultTeamGroupShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllTeamGroupsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        // Get all the teamGroupList where updatedAt is not null
        defaultTeamGroupShouldBeFound("updatedAt.specified=true");

        // Get all the teamGroupList where updatedAt is null
        defaultTeamGroupShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamGroupsByTeamsIsEqualToSomething() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);
        Team teams;
        if (TestUtil.findAll(em, Team.class).isEmpty()) {
            teams = TeamResourceIT.createEntity(em);
            em.persist(teams);
            em.flush();
        } else {
            teams = TestUtil.findAll(em, Team.class).get(0);
        }
        em.persist(teams);
        em.flush();
        teamGroup.addTeams(teams);
        teamGroupRepository.saveAndFlush(teamGroup);
        Long teamsId = teams.getId();

        // Get all the teamGroupList where teams equals to teamsId
        defaultTeamGroupShouldBeFound("teamsId.equals=" + teamsId);

        // Get all the teamGroupList where teams equals to (teamsId + 1)
        defaultTeamGroupShouldNotBeFound("teamsId.equals=" + (teamsId + 1));
    }

    @Test
    @Transactional
    void getAllTeamGroupsByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);
        // ### MODIFICATION-START ###
        /* Here we use a different entity as parent, unless the tested group (subject under test) will reference itself
         * because the looked up parent group is the one and only in the db created in the line above. This leads to
         * a circular self-reference where parent is never null. This leads to a endless loop and stack overflow in the
         * team group mapper because the assumption is that there will be one root team group with parent == null!
         */
        TeamGroup parent = createParentEntity(em);
        // ### MODIFICATION-END ###
        em.persist(parent);
        em.flush();
        // FIXME: #101 Add custom parent to prevent stackoverflow due to endles chain of parents by using self as parent The testutil finds the SUT itself.
        teamGroup.setParent(parent);
        teamGroupRepository.saveAndFlush(teamGroup);
        Long parentId = parent.getId();

        // Get all the teamGroupList where parent equals to parentId
        defaultTeamGroupShouldBeFound("parentId.equals=" + parentId);

        // Get all the teamGroupList where parent equals to (parentId + 1)
        defaultTeamGroupShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTeamGroupShouldBeFound(String filter) throws Exception {
        restTeamGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));

        // Check, that the count call also returns 1
        restTeamGroupMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTeamGroupShouldNotBeFound(String filter) throws Exception {
        restTeamGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTeamGroupMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTeamGroup() throws Exception {
        // Get the teamGroup
        restTeamGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void putNewTeamGroup() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        int databaseSizeBeforeUpdate = teamGroupRepository.findAll().size();

        // Update the teamGroup
        TeamGroup updatedTeamGroup = teamGroupRepository.findById(teamGroup.getId()).get();
        // Disconnect from session so that the updates on updatedTeamGroup are not directly saved in db
        em.detach(updatedTeamGroup);
        updatedTeamGroup.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);
        TeamGroupDTO teamGroupDTO = teamGroupMapper.toDto(updatedTeamGroup);

        restTeamGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teamGroupDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamGroupDTO))
            )
            .andExpect(status().isOk());

        // Validate the TeamGroup in the database
        List<TeamGroup> teamGroupList = teamGroupRepository.findAll();
        assertThat(teamGroupList).hasSize(databaseSizeBeforeUpdate);
        TeamGroup testTeamGroup = teamGroupList.get(teamGroupList.size() - 1);
        assertThat(testTeamGroup.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTeamGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        // ### MODIFICATION-START ###
        assertThat(testTeamGroup.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        // ### MODIFICATION-END ###
        assertThat(testTeamGroup.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void putNonExistingTeamGroup() throws Exception {
        int databaseSizeBeforeUpdate = teamGroupRepository.findAll().size();
        teamGroup.setId(count.incrementAndGet());

        // Create the TeamGroup
        TeamGroupDTO teamGroupDTO = teamGroupMapper.toDto(teamGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teamGroupDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamGroup in the database
        List<TeamGroup> teamGroupList = teamGroupRepository.findAll();
        assertThat(teamGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void putWithIdMismatchTeamGroup() throws Exception {
        int databaseSizeBeforeUpdate = teamGroupRepository.findAll().size();
        teamGroup.setId(count.incrementAndGet());

        // Create the TeamGroup
        TeamGroupDTO teamGroupDTO = teamGroupMapper.toDto(teamGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamGroup in the database
        List<TeamGroup> teamGroupList = teamGroupRepository.findAll();
        assertThat(teamGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void putWithMissingIdPathParamTeamGroup() throws Exception {
        int databaseSizeBeforeUpdate = teamGroupRepository.findAll().size();
        teamGroup.setId(count.incrementAndGet());

        // Create the TeamGroup
        TeamGroupDTO teamGroupDTO = teamGroupMapper.toDto(teamGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamGroupMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TeamGroup in the database
        List<TeamGroup> teamGroupList = teamGroupRepository.findAll();
        assertThat(teamGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void partialUpdateTeamGroupWithPatch() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        int databaseSizeBeforeUpdate = teamGroupRepository.findAll().size();

        // Update the teamGroup using partial update
        TeamGroup partialUpdatedTeamGroup = new TeamGroup();
        partialUpdatedTeamGroup.setId(teamGroup.getId());

        partialUpdatedTeamGroup.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);

        restTeamGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeamGroup.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeamGroup))
            )
            .andExpect(status().isOk());

        // Validate the TeamGroup in the database
        List<TeamGroup> teamGroupList = teamGroupRepository.findAll();
        assertThat(teamGroupList).hasSize(databaseSizeBeforeUpdate);
        TeamGroup testTeamGroup = teamGroupList.get(teamGroupList.size() - 1);
        assertThat(testTeamGroup.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTeamGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTeamGroup.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testTeamGroup.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void fullUpdateTeamGroupWithPatch() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        int databaseSizeBeforeUpdate = teamGroupRepository.findAll().size();

        // Update the teamGroup using partial update
        TeamGroup partialUpdatedTeamGroup = new TeamGroup();
        partialUpdatedTeamGroup.setId(teamGroup.getId());

        partialUpdatedTeamGroup
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restTeamGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeamGroup.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeamGroup))
            )
            .andExpect(status().isOk());

        // Validate the TeamGroup in the database
        List<TeamGroup> teamGroupList = teamGroupRepository.findAll();
        assertThat(teamGroupList).hasSize(databaseSizeBeforeUpdate);
        TeamGroup testTeamGroup = teamGroupList.get(teamGroupList.size() - 1);
        assertThat(testTeamGroup.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTeamGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTeamGroup.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTeamGroup.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void patchNonExistingTeamGroup() throws Exception {
        int databaseSizeBeforeUpdate = teamGroupRepository.findAll().size();
        teamGroup.setId(count.incrementAndGet());

        // Create the TeamGroup
        TeamGroupDTO teamGroupDTO = teamGroupMapper.toDto(teamGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, teamGroupDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamGroup in the database
        List<TeamGroup> teamGroupList = teamGroupRepository.findAll();
        assertThat(teamGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void patchWithIdMismatchTeamGroup() throws Exception {
        int databaseSizeBeforeUpdate = teamGroupRepository.findAll().size();
        teamGroup.setId(count.incrementAndGet());

        // Create the TeamGroup
        TeamGroupDTO teamGroupDTO = teamGroupMapper.toDto(teamGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamGroup in the database
        List<TeamGroup> teamGroupList = teamGroupRepository.findAll();
        assertThat(teamGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void patchWithMissingIdPathParamTeamGroup() throws Exception {
        int databaseSizeBeforeUpdate = teamGroupRepository.findAll().size();
        teamGroup.setId(count.incrementAndGet());

        // Create the TeamGroup
        TeamGroupDTO teamGroupDTO = teamGroupMapper.toDto(teamGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamGroupMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TeamGroup in the database
        List<TeamGroup> teamGroupList = teamGroupRepository.findAll();
        assertThat(teamGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void deleteTeamGroup() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        int databaseSizeBeforeDelete = teamGroupRepository.findAll().size();

        // Delete the teamGroup
        restTeamGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, teamGroup.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TeamGroup> teamGroupList = teamGroupRepository.findAll();
        assertThat(teamGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    // ### MODIFICATION-START ###
    @Test
    @Transactional
    @WithMockUser(username = "user", authorities = { "ROLE_USER" })
    void deleteTeamGroupAsUserIsForbidden() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        int databaseSizeBeforeDelete = teamGroupRepository.findAll().size();

        // Delete the teamGroup
        restTeamGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, teamGroup.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());

        // Validate the database contains one less item
        List<TeamGroup> teamGroupList = teamGroupRepository.findAll();
        assertThat(teamGroupList).hasSize(databaseSizeBeforeDelete);
    }

    @Test
    @Transactional
    @WithUnauthenticatedMockUser
    void deleteTeamGroupAsAnonymousUserIsForbidden() throws Exception {
        // Initialize the database
        teamGroupRepository.saveAndFlush(teamGroup);

        int databaseSizeBeforeDelete = teamGroupRepository.findAll().size();

        // Delete the teamGroup
        restTeamGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, teamGroup.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());

        // Validate the database contains one less item
        List<TeamGroup> teamGroupList = teamGroupRepository.findAll();
        assertThat(teamGroupList).hasSize(databaseSizeBeforeDelete);
    }
    // ### MODIFICATION-END ###

}
