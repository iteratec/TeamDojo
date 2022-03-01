package com.iteratec.teamdojo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iteratec.teamdojo.IntegrationTest;
import com.iteratec.teamdojo.domain.Skill;
import com.iteratec.teamdojo.domain.Team;
import com.iteratec.teamdojo.domain.TeamSkill;
import com.iteratec.teamdojo.domain.enumeration.SkillStatus;
import com.iteratec.teamdojo.repository.TeamSkillRepository;
import com.iteratec.teamdojo.service.criteria.TeamSkillCriteria;
// ### MODIFICATION-START ###
import com.iteratec.teamdojo.service.custom.ExtendedTeamSkillService;
// ### MODIFICATION-END ###
import com.iteratec.teamdojo.service.dto.TeamSkillDTO;
import com.iteratec.teamdojo.service.mapper.TeamSkillMapper;
// ### MODIFICATION-START ###
import com.iteratec.teamdojo.test.util.StaticInstantProvider;
// ### MODIFICATION-END ###
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
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
 * Integration tests for the {@link TeamSkillResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TeamSkillResourceIT {

    private static final Instant DEFAULT_COMPLETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_COMPLETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VERIFIED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VERIFIED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IRRELEVANT = false;
    private static final Boolean UPDATED_IRRELEVANT = true;

    private static final SkillStatus DEFAULT_SKILL_STATUS = SkillStatus.OPEN;
    private static final SkillStatus UPDATED_SKILL_STATUS = SkillStatus.ACHIEVED;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final Integer DEFAULT_VOTE = 1;
    private static final Integer UPDATED_VOTE = 2;
    private static final Integer SMALLER_VOTE = 1 - 1;

    private static final String DEFAULT_VOTERS = "AAAAAAAAAA";
    private static final String UPDATED_VOTERS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    // ### MODIFICATION-START ###
    private static final Instant CUSTOM_CREATED_AND_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    // ### MODIFICATION-END ###

    private static final String ENTITY_API_URL = "/api/team-skills";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TeamSkillRepository teamSkillRepository;

    @Autowired
    private TeamSkillMapper teamSkillMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTeamSkillMockMvc;

    // ### MODIFICATION-START ###
    @Autowired
    private ExtendedTeamSkillService extendedTeamSkillService;

    // ### MODIFICATION-END ###

    private TeamSkill teamSkill;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamSkill createEntity(EntityManager em) {
        TeamSkill teamSkill = new TeamSkill()
            .completedAt(DEFAULT_COMPLETED_AT)
            .verifiedAt(DEFAULT_VERIFIED_AT)
            .irrelevant(DEFAULT_IRRELEVANT)
            .skillStatus(DEFAULT_SKILL_STATUS)
            .note(DEFAULT_NOTE)
            .vote(DEFAULT_VOTE)
            .voters(DEFAULT_VOTERS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        // Add required entity
        Skill skill;
        if (TestUtil.findAll(em, Skill.class).isEmpty()) {
            skill = SkillResourceIT.createEntity(em);
            em.persist(skill);
            em.flush();
        } else {
            skill = TestUtil.findAll(em, Skill.class).get(0);
        }
        teamSkill.setSkill(skill);
        // Add required entity
        Team team;
        if (TestUtil.findAll(em, Team.class).isEmpty()) {
            team = TeamResourceIT.createEntity(em);
            em.persist(team);
            em.flush();
        } else {
            team = TestUtil.findAll(em, Team.class).get(0);
        }
        teamSkill.setTeam(team);
        return teamSkill;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamSkill createUpdatedEntity(EntityManager em) {
        TeamSkill teamSkill = new TeamSkill()
            .completedAt(UPDATED_COMPLETED_AT)
            .verifiedAt(UPDATED_VERIFIED_AT)
            .irrelevant(UPDATED_IRRELEVANT)
            .skillStatus(UPDATED_SKILL_STATUS)
            .note(UPDATED_NOTE)
            .vote(UPDATED_VOTE)
            .voters(UPDATED_VOTERS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        // Add required entity
        Skill skill;
        if (TestUtil.findAll(em, Skill.class).isEmpty()) {
            skill = SkillResourceIT.createUpdatedEntity(em);
            em.persist(skill);
            em.flush();
        } else {
            skill = TestUtil.findAll(em, Skill.class).get(0);
        }
        teamSkill.setSkill(skill);
        // Add required entity
        Team team;
        if (TestUtil.findAll(em, Team.class).isEmpty()) {
            team = TeamResourceIT.createUpdatedEntity(em);
            em.persist(team);
            em.flush();
        } else {
            team = TestUtil.findAll(em, Team.class).get(0);
        }
        teamSkill.setTeam(team);
        return teamSkill;
    }

    @BeforeEach
    public void initTest() {
        teamSkill = createEntity(em);
    }

    // ### MODIFICATION-START ###
    @BeforeEach
    public void initInstantProvider() {
        extendedTeamSkillService.setTime(StaticInstantProvider.forFixedTime(CUSTOM_CREATED_AND_UPDATED_AT));
    }

    // ### MODIFICATION-END ###

    @Test
    @Transactional
    void createTeamSkill() throws Exception {
        int databaseSizeBeforeCreate = teamSkillRepository.findAll().size();
        // Create the TeamSkill
        TeamSkillDTO teamSkillDTO = teamSkillMapper.toDto(teamSkill);
        restTeamSkillMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamSkillDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TeamSkill in the database
        List<TeamSkill> teamSkillList = teamSkillRepository.findAll();
        assertThat(teamSkillList).hasSize(databaseSizeBeforeCreate + 1);
        TeamSkill testTeamSkill = teamSkillList.get(teamSkillList.size() - 1);
        assertThat(testTeamSkill.getCompletedAt()).isEqualTo(DEFAULT_COMPLETED_AT);
        assertThat(testTeamSkill.getVerifiedAt()).isEqualTo(DEFAULT_VERIFIED_AT);
        assertThat(testTeamSkill.getIrrelevant()).isEqualTo(DEFAULT_IRRELEVANT);
        assertThat(testTeamSkill.getSkillStatus()).isEqualTo(DEFAULT_SKILL_STATUS);
        assertThat(testTeamSkill.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testTeamSkill.getVote()).isEqualTo(DEFAULT_VOTE);
        assertThat(testTeamSkill.getVoters()).isEqualTo(DEFAULT_VOTERS);
        // ### MODIFICATION-START ###
        assertThat(testTeamSkill.getCreatedAt()).isEqualTo(CUSTOM_CREATED_AND_UPDATED_AT);
        assertThat(testTeamSkill.getUpdatedAt()).isEqualTo(CUSTOM_CREATED_AND_UPDATED_AT);
        // ### MODIFICATION-END ###
    }

    @Test
    @Transactional
    void createTeamSkillWithExistingId() throws Exception {
        // Create the TeamSkill with an existing ID
        teamSkill.setId(1L);
        TeamSkillDTO teamSkillDTO = teamSkillMapper.toDto(teamSkill);

        int databaseSizeBeforeCreate = teamSkillRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamSkillMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamSkillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamSkill in the database
        List<TeamSkill> teamSkillList = teamSkillRepository.findAll();
        assertThat(teamSkillList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSkillStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamSkillRepository.findAll().size();
        // set the field null
        teamSkill.setSkillStatus(null);

        // Create the TeamSkill, which fails.
        TeamSkillDTO teamSkillDTO = teamSkillMapper.toDto(teamSkill);

        restTeamSkillMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamSkillDTO))
            )
            .andExpect(status().isBadRequest());

        List<TeamSkill> teamSkillList = teamSkillRepository.findAll();
        assertThat(teamSkillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVoteIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamSkillRepository.findAll().size();
        // set the field null
        teamSkill.setVote(null);

        // Create the TeamSkill, which fails.
        TeamSkillDTO teamSkillDTO = teamSkillMapper.toDto(teamSkill);

        restTeamSkillMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamSkillDTO))
            )
            .andExpect(status().isBadRequest());

        List<TeamSkill> teamSkillList = teamSkillRepository.findAll();
        assertThat(teamSkillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @Disabled("Ignored because we removed the validation for this field in the DTO.")
    // ### MODIFICATION-END ###
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamSkillRepository.findAll().size();
        // set the field null
        teamSkill.setCreatedAt(null);

        // Create the TeamSkill, which fails.
        TeamSkillDTO teamSkillDTO = teamSkillMapper.toDto(teamSkill);

        restTeamSkillMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamSkillDTO))
            )
            .andExpect(status().isBadRequest());

        List<TeamSkill> teamSkillList = teamSkillRepository.findAll();
        assertThat(teamSkillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @Disabled("Ignored because we removed the validation for this field in the DTO.")
    // ### MODIFICATION-END ###
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamSkillRepository.findAll().size();
        // set the field null
        teamSkill.setUpdatedAt(null);

        // Create the TeamSkill, which fails.
        TeamSkillDTO teamSkillDTO = teamSkillMapper.toDto(teamSkill);

        restTeamSkillMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamSkillDTO))
            )
            .andExpect(status().isBadRequest());

        List<TeamSkill> teamSkillList = teamSkillRepository.findAll();
        assertThat(teamSkillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTeamSkills() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList
        restTeamSkillMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].completedAt").value(hasItem(DEFAULT_COMPLETED_AT.toString())))
            .andExpect(jsonPath("$.[*].verifiedAt").value(hasItem(DEFAULT_VERIFIED_AT.toString())))
            .andExpect(jsonPath("$.[*].irrelevant").value(hasItem(DEFAULT_IRRELEVANT.booleanValue())))
            .andExpect(jsonPath("$.[*].skillStatus").value(hasItem(DEFAULT_SKILL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].vote").value(hasItem(DEFAULT_VOTE)))
            .andExpect(jsonPath("$.[*].voters").value(hasItem(DEFAULT_VOTERS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getTeamSkill() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get the teamSkill
        restTeamSkillMockMvc
            .perform(get(ENTITY_API_URL_ID, teamSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(teamSkill.getId().intValue()))
            .andExpect(jsonPath("$.completedAt").value(DEFAULT_COMPLETED_AT.toString()))
            .andExpect(jsonPath("$.verifiedAt").value(DEFAULT_VERIFIED_AT.toString()))
            .andExpect(jsonPath("$.irrelevant").value(DEFAULT_IRRELEVANT.booleanValue()))
            .andExpect(jsonPath("$.skillStatus").value(DEFAULT_SKILL_STATUS.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.vote").value(DEFAULT_VOTE))
            .andExpect(jsonPath("$.voters").value(DEFAULT_VOTERS))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getTeamSkillsByIdFiltering() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        Long id = teamSkill.getId();

        defaultTeamSkillShouldBeFound("id.equals=" + id);
        defaultTeamSkillShouldNotBeFound("id.notEquals=" + id);

        defaultTeamSkillShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTeamSkillShouldNotBeFound("id.greaterThan=" + id);

        defaultTeamSkillShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTeamSkillShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByCompletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where completedAt equals to DEFAULT_COMPLETED_AT
        defaultTeamSkillShouldBeFound("completedAt.equals=" + DEFAULT_COMPLETED_AT);

        // Get all the teamSkillList where completedAt equals to UPDATED_COMPLETED_AT
        defaultTeamSkillShouldNotBeFound("completedAt.equals=" + UPDATED_COMPLETED_AT);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByCompletedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where completedAt not equals to DEFAULT_COMPLETED_AT
        defaultTeamSkillShouldNotBeFound("completedAt.notEquals=" + DEFAULT_COMPLETED_AT);

        // Get all the teamSkillList where completedAt not equals to UPDATED_COMPLETED_AT
        defaultTeamSkillShouldBeFound("completedAt.notEquals=" + UPDATED_COMPLETED_AT);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByCompletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where completedAt in DEFAULT_COMPLETED_AT or UPDATED_COMPLETED_AT
        defaultTeamSkillShouldBeFound("completedAt.in=" + DEFAULT_COMPLETED_AT + "," + UPDATED_COMPLETED_AT);

        // Get all the teamSkillList where completedAt equals to UPDATED_COMPLETED_AT
        defaultTeamSkillShouldNotBeFound("completedAt.in=" + UPDATED_COMPLETED_AT);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByCompletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where completedAt is not null
        defaultTeamSkillShouldBeFound("completedAt.specified=true");

        // Get all the teamSkillList where completedAt is null
        defaultTeamSkillShouldNotBeFound("completedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamSkillsByVerifiedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where verifiedAt equals to DEFAULT_VERIFIED_AT
        defaultTeamSkillShouldBeFound("verifiedAt.equals=" + DEFAULT_VERIFIED_AT);

        // Get all the teamSkillList where verifiedAt equals to UPDATED_VERIFIED_AT
        defaultTeamSkillShouldNotBeFound("verifiedAt.equals=" + UPDATED_VERIFIED_AT);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByVerifiedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where verifiedAt not equals to DEFAULT_VERIFIED_AT
        defaultTeamSkillShouldNotBeFound("verifiedAt.notEquals=" + DEFAULT_VERIFIED_AT);

        // Get all the teamSkillList where verifiedAt not equals to UPDATED_VERIFIED_AT
        defaultTeamSkillShouldBeFound("verifiedAt.notEquals=" + UPDATED_VERIFIED_AT);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByVerifiedAtIsInShouldWork() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where verifiedAt in DEFAULT_VERIFIED_AT or UPDATED_VERIFIED_AT
        defaultTeamSkillShouldBeFound("verifiedAt.in=" + DEFAULT_VERIFIED_AT + "," + UPDATED_VERIFIED_AT);

        // Get all the teamSkillList where verifiedAt equals to UPDATED_VERIFIED_AT
        defaultTeamSkillShouldNotBeFound("verifiedAt.in=" + UPDATED_VERIFIED_AT);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByVerifiedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where verifiedAt is not null
        defaultTeamSkillShouldBeFound("verifiedAt.specified=true");

        // Get all the teamSkillList where verifiedAt is null
        defaultTeamSkillShouldNotBeFound("verifiedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamSkillsByIrrelevantIsEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where irrelevant equals to DEFAULT_IRRELEVANT
        defaultTeamSkillShouldBeFound("irrelevant.equals=" + DEFAULT_IRRELEVANT);

        // Get all the teamSkillList where irrelevant equals to UPDATED_IRRELEVANT
        defaultTeamSkillShouldNotBeFound("irrelevant.equals=" + UPDATED_IRRELEVANT);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByIrrelevantIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where irrelevant not equals to DEFAULT_IRRELEVANT
        defaultTeamSkillShouldNotBeFound("irrelevant.notEquals=" + DEFAULT_IRRELEVANT);

        // Get all the teamSkillList where irrelevant not equals to UPDATED_IRRELEVANT
        defaultTeamSkillShouldBeFound("irrelevant.notEquals=" + UPDATED_IRRELEVANT);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByIrrelevantIsInShouldWork() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where irrelevant in DEFAULT_IRRELEVANT or UPDATED_IRRELEVANT
        defaultTeamSkillShouldBeFound("irrelevant.in=" + DEFAULT_IRRELEVANT + "," + UPDATED_IRRELEVANT);

        // Get all the teamSkillList where irrelevant equals to UPDATED_IRRELEVANT
        defaultTeamSkillShouldNotBeFound("irrelevant.in=" + UPDATED_IRRELEVANT);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByIrrelevantIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where irrelevant is not null
        defaultTeamSkillShouldBeFound("irrelevant.specified=true");

        // Get all the teamSkillList where irrelevant is null
        defaultTeamSkillShouldNotBeFound("irrelevant.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamSkillsBySkillStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where skillStatus equals to DEFAULT_SKILL_STATUS
        defaultTeamSkillShouldBeFound("skillStatus.equals=" + DEFAULT_SKILL_STATUS);

        // Get all the teamSkillList where skillStatus equals to UPDATED_SKILL_STATUS
        defaultTeamSkillShouldNotBeFound("skillStatus.equals=" + UPDATED_SKILL_STATUS);
    }

    @Test
    @Transactional
    void getAllTeamSkillsBySkillStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where skillStatus not equals to DEFAULT_SKILL_STATUS
        defaultTeamSkillShouldNotBeFound("skillStatus.notEquals=" + DEFAULT_SKILL_STATUS);

        // Get all the teamSkillList where skillStatus not equals to UPDATED_SKILL_STATUS
        defaultTeamSkillShouldBeFound("skillStatus.notEquals=" + UPDATED_SKILL_STATUS);
    }

    @Test
    @Transactional
    void getAllTeamSkillsBySkillStatusIsInShouldWork() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where skillStatus in DEFAULT_SKILL_STATUS or UPDATED_SKILL_STATUS
        defaultTeamSkillShouldBeFound("skillStatus.in=" + DEFAULT_SKILL_STATUS + "," + UPDATED_SKILL_STATUS);

        // Get all the teamSkillList where skillStatus equals to UPDATED_SKILL_STATUS
        defaultTeamSkillShouldNotBeFound("skillStatus.in=" + UPDATED_SKILL_STATUS);
    }

    @Test
    @Transactional
    void getAllTeamSkillsBySkillStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where skillStatus is not null
        defaultTeamSkillShouldBeFound("skillStatus.specified=true");

        // Get all the teamSkillList where skillStatus is null
        defaultTeamSkillShouldNotBeFound("skillStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamSkillsByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where note equals to DEFAULT_NOTE
        defaultTeamSkillShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the teamSkillList where note equals to UPDATED_NOTE
        defaultTeamSkillShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where note not equals to DEFAULT_NOTE
        defaultTeamSkillShouldNotBeFound("note.notEquals=" + DEFAULT_NOTE);

        // Get all the teamSkillList where note not equals to UPDATED_NOTE
        defaultTeamSkillShouldBeFound("note.notEquals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultTeamSkillShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the teamSkillList where note equals to UPDATED_NOTE
        defaultTeamSkillShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where note is not null
        defaultTeamSkillShouldBeFound("note.specified=true");

        // Get all the teamSkillList where note is null
        defaultTeamSkillShouldNotBeFound("note.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamSkillsByNoteContainsSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where note contains DEFAULT_NOTE
        defaultTeamSkillShouldBeFound("note.contains=" + DEFAULT_NOTE);

        // Get all the teamSkillList where note contains UPDATED_NOTE
        defaultTeamSkillShouldNotBeFound("note.contains=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByNoteNotContainsSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where note does not contain DEFAULT_NOTE
        defaultTeamSkillShouldNotBeFound("note.doesNotContain=" + DEFAULT_NOTE);

        // Get all the teamSkillList where note does not contain UPDATED_NOTE
        defaultTeamSkillShouldBeFound("note.doesNotContain=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByVoteIsEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where vote equals to DEFAULT_VOTE
        defaultTeamSkillShouldBeFound("vote.equals=" + DEFAULT_VOTE);

        // Get all the teamSkillList where vote equals to UPDATED_VOTE
        defaultTeamSkillShouldNotBeFound("vote.equals=" + UPDATED_VOTE);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByVoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where vote not equals to DEFAULT_VOTE
        defaultTeamSkillShouldNotBeFound("vote.notEquals=" + DEFAULT_VOTE);

        // Get all the teamSkillList where vote not equals to UPDATED_VOTE
        defaultTeamSkillShouldBeFound("vote.notEquals=" + UPDATED_VOTE);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByVoteIsInShouldWork() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where vote in DEFAULT_VOTE or UPDATED_VOTE
        defaultTeamSkillShouldBeFound("vote.in=" + DEFAULT_VOTE + "," + UPDATED_VOTE);

        // Get all the teamSkillList where vote equals to UPDATED_VOTE
        defaultTeamSkillShouldNotBeFound("vote.in=" + UPDATED_VOTE);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByVoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where vote is not null
        defaultTeamSkillShouldBeFound("vote.specified=true");

        // Get all the teamSkillList where vote is null
        defaultTeamSkillShouldNotBeFound("vote.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamSkillsByVoteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where vote is greater than or equal to DEFAULT_VOTE
        defaultTeamSkillShouldBeFound("vote.greaterThanOrEqual=" + DEFAULT_VOTE);

        // Get all the teamSkillList where vote is greater than or equal to UPDATED_VOTE
        defaultTeamSkillShouldNotBeFound("vote.greaterThanOrEqual=" + UPDATED_VOTE);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByVoteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where vote is less than or equal to DEFAULT_VOTE
        defaultTeamSkillShouldBeFound("vote.lessThanOrEqual=" + DEFAULT_VOTE);

        // Get all the teamSkillList where vote is less than or equal to SMALLER_VOTE
        defaultTeamSkillShouldNotBeFound("vote.lessThanOrEqual=" + SMALLER_VOTE);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByVoteIsLessThanSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where vote is less than DEFAULT_VOTE
        defaultTeamSkillShouldNotBeFound("vote.lessThan=" + DEFAULT_VOTE);

        // Get all the teamSkillList where vote is less than UPDATED_VOTE
        defaultTeamSkillShouldBeFound("vote.lessThan=" + UPDATED_VOTE);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByVoteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where vote is greater than DEFAULT_VOTE
        defaultTeamSkillShouldNotBeFound("vote.greaterThan=" + DEFAULT_VOTE);

        // Get all the teamSkillList where vote is greater than SMALLER_VOTE
        defaultTeamSkillShouldBeFound("vote.greaterThan=" + SMALLER_VOTE);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByVotersIsEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where voters equals to DEFAULT_VOTERS
        defaultTeamSkillShouldBeFound("voters.equals=" + DEFAULT_VOTERS);

        // Get all the teamSkillList where voters equals to UPDATED_VOTERS
        defaultTeamSkillShouldNotBeFound("voters.equals=" + UPDATED_VOTERS);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByVotersIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where voters not equals to DEFAULT_VOTERS
        defaultTeamSkillShouldNotBeFound("voters.notEquals=" + DEFAULT_VOTERS);

        // Get all the teamSkillList where voters not equals to UPDATED_VOTERS
        defaultTeamSkillShouldBeFound("voters.notEquals=" + UPDATED_VOTERS);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByVotersIsInShouldWork() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where voters in DEFAULT_VOTERS or UPDATED_VOTERS
        defaultTeamSkillShouldBeFound("voters.in=" + DEFAULT_VOTERS + "," + UPDATED_VOTERS);

        // Get all the teamSkillList where voters equals to UPDATED_VOTERS
        defaultTeamSkillShouldNotBeFound("voters.in=" + UPDATED_VOTERS);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByVotersIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where voters is not null
        defaultTeamSkillShouldBeFound("voters.specified=true");

        // Get all the teamSkillList where voters is null
        defaultTeamSkillShouldNotBeFound("voters.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamSkillsByVotersContainsSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where voters contains DEFAULT_VOTERS
        defaultTeamSkillShouldBeFound("voters.contains=" + DEFAULT_VOTERS);

        // Get all the teamSkillList where voters contains UPDATED_VOTERS
        defaultTeamSkillShouldNotBeFound("voters.contains=" + UPDATED_VOTERS);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByVotersNotContainsSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where voters does not contain DEFAULT_VOTERS
        defaultTeamSkillShouldNotBeFound("voters.doesNotContain=" + DEFAULT_VOTERS);

        // Get all the teamSkillList where voters does not contain UPDATED_VOTERS
        defaultTeamSkillShouldBeFound("voters.doesNotContain=" + UPDATED_VOTERS);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where createdAt equals to DEFAULT_CREATED_AT
        defaultTeamSkillShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the teamSkillList where createdAt equals to UPDATED_CREATED_AT
        defaultTeamSkillShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where createdAt not equals to DEFAULT_CREATED_AT
        defaultTeamSkillShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the teamSkillList where createdAt not equals to UPDATED_CREATED_AT
        defaultTeamSkillShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultTeamSkillShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the teamSkillList where createdAt equals to UPDATED_CREATED_AT
        defaultTeamSkillShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where createdAt is not null
        defaultTeamSkillShouldBeFound("createdAt.specified=true");

        // Get all the teamSkillList where createdAt is null
        defaultTeamSkillShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamSkillsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultTeamSkillShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the teamSkillList where updatedAt equals to UPDATED_UPDATED_AT
        defaultTeamSkillShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultTeamSkillShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the teamSkillList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultTeamSkillShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultTeamSkillShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the teamSkillList where updatedAt equals to UPDATED_UPDATED_AT
        defaultTeamSkillShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllTeamSkillsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where updatedAt is not null
        defaultTeamSkillShouldBeFound("updatedAt.specified=true");

        // Get all the teamSkillList where updatedAt is null
        defaultTeamSkillShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamSkillsBySkillIsEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);
        Skill skill;
        if (TestUtil.findAll(em, Skill.class).isEmpty()) {
            skill = SkillResourceIT.createEntity(em);
            em.persist(skill);
            em.flush();
        } else {
            skill = TestUtil.findAll(em, Skill.class).get(0);
        }
        em.persist(skill);
        em.flush();
        teamSkill.setSkill(skill);
        teamSkillRepository.saveAndFlush(teamSkill);
        Long skillId = skill.getId();

        // Get all the teamSkillList where skill equals to skillId
        defaultTeamSkillShouldBeFound("skillId.equals=" + skillId);

        // Get all the teamSkillList where skill equals to (skillId + 1)
        defaultTeamSkillShouldNotBeFound("skillId.equals=" + (skillId + 1));
    }

    @Test
    @Transactional
    void getAllTeamSkillsByTeamIsEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);
        Team team;
        if (TestUtil.findAll(em, Team.class).isEmpty()) {
            team = TeamResourceIT.createEntity(em);
            em.persist(team);
            em.flush();
        } else {
            team = TestUtil.findAll(em, Team.class).get(0);
        }
        em.persist(team);
        em.flush();
        teamSkill.setTeam(team);
        teamSkillRepository.saveAndFlush(teamSkill);
        Long teamId = team.getId();

        // Get all the teamSkillList where team equals to teamId
        defaultTeamSkillShouldBeFound("teamId.equals=" + teamId);

        // Get all the teamSkillList where team equals to (teamId + 1)
        defaultTeamSkillShouldNotBeFound("teamId.equals=" + (teamId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTeamSkillShouldBeFound(String filter) throws Exception {
        restTeamSkillMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].completedAt").value(hasItem(DEFAULT_COMPLETED_AT.toString())))
            .andExpect(jsonPath("$.[*].verifiedAt").value(hasItem(DEFAULT_VERIFIED_AT.toString())))
            .andExpect(jsonPath("$.[*].irrelevant").value(hasItem(DEFAULT_IRRELEVANT.booleanValue())))
            .andExpect(jsonPath("$.[*].skillStatus").value(hasItem(DEFAULT_SKILL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].vote").value(hasItem(DEFAULT_VOTE)))
            .andExpect(jsonPath("$.[*].voters").value(hasItem(DEFAULT_VOTERS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));

        // Check, that the count call also returns 1
        restTeamSkillMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTeamSkillShouldNotBeFound(String filter) throws Exception {
        restTeamSkillMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTeamSkillMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTeamSkill() throws Exception {
        // Get the teamSkill
        restTeamSkillMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTeamSkill() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        int databaseSizeBeforeUpdate = teamSkillRepository.findAll().size();

        // Update the teamSkill
        TeamSkill updatedTeamSkill = teamSkillRepository.findById(teamSkill.getId()).get();
        // Disconnect from session so that the updates on updatedTeamSkill are not directly saved in db
        em.detach(updatedTeamSkill);
        updatedTeamSkill
            .completedAt(UPDATED_COMPLETED_AT)
            .verifiedAt(UPDATED_VERIFIED_AT)
            .irrelevant(UPDATED_IRRELEVANT)
            .skillStatus(UPDATED_SKILL_STATUS)
            .note(UPDATED_NOTE)
            .vote(UPDATED_VOTE)
            .voters(UPDATED_VOTERS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        TeamSkillDTO teamSkillDTO = teamSkillMapper.toDto(updatedTeamSkill);

        restTeamSkillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teamSkillDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamSkillDTO))
            )
            .andExpect(status().isOk());

        // Validate the TeamSkill in the database
        List<TeamSkill> teamSkillList = teamSkillRepository.findAll();
        assertThat(teamSkillList).hasSize(databaseSizeBeforeUpdate);
        TeamSkill testTeamSkill = teamSkillList.get(teamSkillList.size() - 1);
        assertThat(testTeamSkill.getCompletedAt()).isEqualTo(UPDATED_COMPLETED_AT);
        assertThat(testTeamSkill.getVerifiedAt()).isEqualTo(UPDATED_VERIFIED_AT);
        assertThat(testTeamSkill.getIrrelevant()).isEqualTo(UPDATED_IRRELEVANT);
        assertThat(testTeamSkill.getSkillStatus()).isEqualTo(UPDATED_SKILL_STATUS);
        assertThat(testTeamSkill.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testTeamSkill.getVote()).isEqualTo(UPDATED_VOTE);
        assertThat(testTeamSkill.getVoters()).isEqualTo(UPDATED_VOTERS);
        // ### MODIFICATION-START ###
        assertThat(testTeamSkill.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        // ### MODIFICATION-END ###
        assertThat(testTeamSkill.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingTeamSkill() throws Exception {
        int databaseSizeBeforeUpdate = teamSkillRepository.findAll().size();
        teamSkill.setId(count.incrementAndGet());

        // Create the TeamSkill
        TeamSkillDTO teamSkillDTO = teamSkillMapper.toDto(teamSkill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamSkillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teamSkillDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamSkillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamSkill in the database
        List<TeamSkill> teamSkillList = teamSkillRepository.findAll();
        assertThat(teamSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTeamSkill() throws Exception {
        int databaseSizeBeforeUpdate = teamSkillRepository.findAll().size();
        teamSkill.setId(count.incrementAndGet());

        // Create the TeamSkill
        TeamSkillDTO teamSkillDTO = teamSkillMapper.toDto(teamSkill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamSkillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamSkillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamSkill in the database
        List<TeamSkill> teamSkillList = teamSkillRepository.findAll();
        assertThat(teamSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTeamSkill() throws Exception {
        int databaseSizeBeforeUpdate = teamSkillRepository.findAll().size();
        teamSkill.setId(count.incrementAndGet());

        // Create the TeamSkill
        TeamSkillDTO teamSkillDTO = teamSkillMapper.toDto(teamSkill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamSkillMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamSkillDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TeamSkill in the database
        List<TeamSkill> teamSkillList = teamSkillRepository.findAll();
        assertThat(teamSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTeamSkillWithPatch() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        int databaseSizeBeforeUpdate = teamSkillRepository.findAll().size();

        // Update the teamSkill using partial update
        TeamSkill partialUpdatedTeamSkill = new TeamSkill();
        partialUpdatedTeamSkill.setId(teamSkill.getId());

        partialUpdatedTeamSkill
            .completedAt(UPDATED_COMPLETED_AT)
            .verifiedAt(UPDATED_VERIFIED_AT)
            .skillStatus(UPDATED_SKILL_STATUS)
            .note(UPDATED_NOTE)
            .vote(UPDATED_VOTE)
            .voters(UPDATED_VOTERS)
            .updatedAt(UPDATED_UPDATED_AT);

        restTeamSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeamSkill.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeamSkill))
            )
            .andExpect(status().isOk());

        // Validate the TeamSkill in the database
        List<TeamSkill> teamSkillList = teamSkillRepository.findAll();
        assertThat(teamSkillList).hasSize(databaseSizeBeforeUpdate);
        TeamSkill testTeamSkill = teamSkillList.get(teamSkillList.size() - 1);
        assertThat(testTeamSkill.getCompletedAt()).isEqualTo(UPDATED_COMPLETED_AT);
        assertThat(testTeamSkill.getVerifiedAt()).isEqualTo(UPDATED_VERIFIED_AT);
        assertThat(testTeamSkill.getIrrelevant()).isEqualTo(DEFAULT_IRRELEVANT);
        assertThat(testTeamSkill.getSkillStatus()).isEqualTo(UPDATED_SKILL_STATUS);
        assertThat(testTeamSkill.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testTeamSkill.getVote()).isEqualTo(UPDATED_VOTE);
        assertThat(testTeamSkill.getVoters()).isEqualTo(UPDATED_VOTERS);
        assertThat(testTeamSkill.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testTeamSkill.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateTeamSkillWithPatch() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        int databaseSizeBeforeUpdate = teamSkillRepository.findAll().size();

        // Update the teamSkill using partial update
        TeamSkill partialUpdatedTeamSkill = new TeamSkill();
        partialUpdatedTeamSkill.setId(teamSkill.getId());

        partialUpdatedTeamSkill
            .completedAt(UPDATED_COMPLETED_AT)
            .verifiedAt(UPDATED_VERIFIED_AT)
            .irrelevant(UPDATED_IRRELEVANT)
            .skillStatus(UPDATED_SKILL_STATUS)
            .note(UPDATED_NOTE)
            .vote(UPDATED_VOTE)
            .voters(UPDATED_VOTERS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restTeamSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeamSkill.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeamSkill))
            )
            .andExpect(status().isOk());

        // Validate the TeamSkill in the database
        List<TeamSkill> teamSkillList = teamSkillRepository.findAll();
        assertThat(teamSkillList).hasSize(databaseSizeBeforeUpdate);
        TeamSkill testTeamSkill = teamSkillList.get(teamSkillList.size() - 1);
        assertThat(testTeamSkill.getCompletedAt()).isEqualTo(UPDATED_COMPLETED_AT);
        assertThat(testTeamSkill.getVerifiedAt()).isEqualTo(UPDATED_VERIFIED_AT);
        assertThat(testTeamSkill.getIrrelevant()).isEqualTo(UPDATED_IRRELEVANT);
        assertThat(testTeamSkill.getSkillStatus()).isEqualTo(UPDATED_SKILL_STATUS);
        assertThat(testTeamSkill.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testTeamSkill.getVote()).isEqualTo(UPDATED_VOTE);
        assertThat(testTeamSkill.getVoters()).isEqualTo(UPDATED_VOTERS);
        assertThat(testTeamSkill.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTeamSkill.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingTeamSkill() throws Exception {
        int databaseSizeBeforeUpdate = teamSkillRepository.findAll().size();
        teamSkill.setId(count.incrementAndGet());

        // Create the TeamSkill
        TeamSkillDTO teamSkillDTO = teamSkillMapper.toDto(teamSkill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, teamSkillDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamSkillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamSkill in the database
        List<TeamSkill> teamSkillList = teamSkillRepository.findAll();
        assertThat(teamSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTeamSkill() throws Exception {
        int databaseSizeBeforeUpdate = teamSkillRepository.findAll().size();
        teamSkill.setId(count.incrementAndGet());

        // Create the TeamSkill
        TeamSkillDTO teamSkillDTO = teamSkillMapper.toDto(teamSkill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamSkillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamSkill in the database
        List<TeamSkill> teamSkillList = teamSkillRepository.findAll();
        assertThat(teamSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTeamSkill() throws Exception {
        int databaseSizeBeforeUpdate = teamSkillRepository.findAll().size();
        teamSkill.setId(count.incrementAndGet());

        // Create the TeamSkill
        TeamSkillDTO teamSkillDTO = teamSkillMapper.toDto(teamSkill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamSkillMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamSkillDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TeamSkill in the database
        List<TeamSkill> teamSkillList = teamSkillRepository.findAll();
        assertThat(teamSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTeamSkill() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        int databaseSizeBeforeDelete = teamSkillRepository.findAll().size();

        // Delete the teamSkill
        restTeamSkillMockMvc
            .perform(delete(ENTITY_API_URL_ID, teamSkill.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TeamSkill> teamSkillList = teamSkillRepository.findAll();
        assertThat(teamSkillList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
