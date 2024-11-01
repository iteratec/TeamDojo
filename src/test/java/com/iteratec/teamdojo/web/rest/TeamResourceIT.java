package com.iteratec.teamdojo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.IntegrationTest;
import com.iteratec.teamdojo.domain.Dimension;
import com.iteratec.teamdojo.domain.Image;
import com.iteratec.teamdojo.domain.Team;
import com.iteratec.teamdojo.domain.TeamGroup;
import com.iteratec.teamdojo.domain.TeamSkill;
import com.iteratec.teamdojo.repository.TeamRepository;
import com.iteratec.teamdojo.service.TeamService;
import com.iteratec.teamdojo.service.criteria.TeamCriteria;
// ### MODIFICATION-START ###
import com.iteratec.teamdojo.service.custom.ExtendedTeamService;
// ### MODIFICATION-END ###
import com.iteratec.teamdojo.service.dto.TeamDTO;
import com.iteratec.teamdojo.service.mapper.TeamMapper;
// ### MODIFICATION-START ###
import com.iteratec.teamdojo.test.util.StaticInstantProvider;
// ### MODIFICATION-END ###
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
// ### MODIFICATION-START ###
import org.junit.jupiter.api.Disabled;
// ### MODIFICATION-END ###
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TeamResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
@GeneratedByJHipster
class TeamResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SLOGAN = "AAAAAAAAAA";
    private static final String UPDATED_SLOGAN = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final Instant DEFAULT_EXPIRATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_OFFICIAL = false;
    private static final Boolean UPDATED_OFFICIAL = true;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    // ### MODIFICATION-START ###
    private static final Instant CUSTOM_CREATED_AND_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    // ### MODIFICATION-END ###
    private static final String ENTITY_API_URL = "/api/teams";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TeamRepository teamRepository;

    @Mock
    private TeamRepository teamRepositoryMock;

    @Autowired
    private TeamMapper teamMapper;

    @Mock
    private TeamService teamServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTeamMockMvc;

    // ### MODIFICATION-START ###
    @Autowired
    private ExtendedTeamService extendedTeamService;

    // ### MODIFICATION-END ###

    private Team team;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Team createEntity(EntityManager em) {
        Team team = new Team()
            .title(DEFAULT_TITLE)
            .shortTitle(DEFAULT_SHORT_TITLE)
            .slogan(DEFAULT_SLOGAN)
            .contact(DEFAULT_CONTACT)
            .expirationDate(DEFAULT_EXPIRATION_DATE)
            .official(DEFAULT_OFFICIAL)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        // Add required entity
        TeamGroup teamGroup;
        if (TestUtil.findAll(em, TeamGroup.class).isEmpty()) {
            teamGroup = TeamGroupResourceIT.createEntity(em);
            em.persist(teamGroup);
            em.flush();
        } else {
            teamGroup = TestUtil.findAll(em, TeamGroup.class).get(0);
        }
        team.setGroup(teamGroup);
        return team;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Team createUpdatedEntity(EntityManager em) {
        Team team = new Team()
            .title(UPDATED_TITLE)
            .shortTitle(UPDATED_SHORT_TITLE)
            .slogan(UPDATED_SLOGAN)
            .contact(UPDATED_CONTACT)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .official(UPDATED_OFFICIAL)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        // Add required entity
        TeamGroup teamGroup;
        if (TestUtil.findAll(em, TeamGroup.class).isEmpty()) {
            teamGroup = TeamGroupResourceIT.createUpdatedEntity(em);
            em.persist(teamGroup);
            em.flush();
        } else {
            teamGroup = TestUtil.findAll(em, TeamGroup.class).get(0);
        }
        team.setGroup(teamGroup);
        return team;
    }

    @BeforeEach
    public void initTest() {
        team = createEntity(em);
    }

    // ### MODIFICATION-START ###
    @BeforeEach
    public void initInstantProvider() {
        extendedTeamService.setTime(StaticInstantProvider.forFixedTime(CUSTOM_CREATED_AND_UPDATED_AT));
    }

    // ### MODIFICATION-END ###

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void createTeam() throws Exception {
        int databaseSizeBeforeCreate = teamRepository.findAll().size();
        // Create the Team
        TeamDTO teamDTO = teamMapper.toDto(team);
        restTeamMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeCreate + 1);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTeam.getShortTitle()).isEqualTo(DEFAULT_SHORT_TITLE);
        assertThat(testTeam.getSlogan()).isEqualTo(DEFAULT_SLOGAN);
        assertThat(testTeam.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testTeam.getExpirationDate()).isEqualTo(DEFAULT_EXPIRATION_DATE);
        assertThat(testTeam.getOfficial()).isEqualTo(DEFAULT_OFFICIAL);
        // ### MODIFICATION-START ###
        assertThat(testTeam.getCreatedAt()).isEqualTo(CUSTOM_CREATED_AND_UPDATED_AT);
        assertThat(testTeam.getUpdatedAt()).isEqualTo(CUSTOM_CREATED_AND_UPDATED_AT);
        // ### MODIFICATION-END ###
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void createTeamWithExistingId() throws Exception {
        // Create the Team with an existing ID
        team.setId(1L);
        TeamDTO teamDTO = teamMapper.toDto(team);

        int databaseSizeBeforeCreate = teamRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamRepository.findAll().size();
        // set the field null
        team.setTitle(null);

        // Create the Team, which fails.
        TeamDTO teamDTO = teamMapper.toDto(team);

        restTeamMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamDTO))
            )
            .andExpect(status().isBadRequest());

        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkShortTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamRepository.findAll().size();
        // set the field null
        team.setShortTitle(null);

        // Create the Team, which fails.
        TeamDTO teamDTO = teamMapper.toDto(team);

        restTeamMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamDTO))
            )
            .andExpect(status().isBadRequest());

        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOfficialIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamRepository.findAll().size();
        // set the field null
        team.setOfficial(null);

        // Create the Team, which fails.
        TeamDTO teamDTO = teamMapper.toDto(team);

        restTeamMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamDTO))
            )
            .andExpect(status().isBadRequest());

        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @Disabled("Ignored because we removed the validation for this field in the DTO.")
    // ### MODIFICATION-END ###
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamRepository.findAll().size();
        // set the field null
        team.setCreatedAt(null);

        // Create the Team, which fails.
        TeamDTO teamDTO = teamMapper.toDto(team);

        restTeamMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamDTO))
            )
            .andExpect(status().isBadRequest());

        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @Disabled("Ignored because we removed the validation for this field in the DTO.")
    // ### MODIFICATION-END ###
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamRepository.findAll().size();
        // set the field null
        team.setUpdatedAt(null);

        // Create the Team, which fails.
        TeamDTO teamDTO = teamMapper.toDto(team);

        restTeamMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamDTO))
            )
            .andExpect(status().isBadRequest());

        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTeams() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList
        restTeamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(team.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].shortTitle").value(hasItem(DEFAULT_SHORT_TITLE)))
            .andExpect(jsonPath("$.[*].slogan").value(hasItem(DEFAULT_SLOGAN)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].expirationDate").value(hasItem(DEFAULT_EXPIRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].official").value(hasItem(DEFAULT_OFFICIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTeamsWithEagerRelationshipsIsEnabled() throws Exception {
        when(teamServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTeamMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(teamServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTeamsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(teamServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTeamMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(teamServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getTeam() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get the team
        restTeamMockMvc
            .perform(get(ENTITY_API_URL_ID, team.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(team.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.shortTitle").value(DEFAULT_SHORT_TITLE))
            .andExpect(jsonPath("$.slogan").value(DEFAULT_SLOGAN))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT))
            .andExpect(jsonPath("$.expirationDate").value(DEFAULT_EXPIRATION_DATE.toString()))
            .andExpect(jsonPath("$.official").value(DEFAULT_OFFICIAL.booleanValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getTeamsByIdFiltering() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        Long id = team.getId();

        defaultTeamShouldBeFound("id.equals=" + id);
        defaultTeamShouldNotBeFound("id.notEquals=" + id);

        defaultTeamShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTeamShouldNotBeFound("id.greaterThan=" + id);

        defaultTeamShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTeamShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTeamsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where title equals to DEFAULT_TITLE
        defaultTeamShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the teamList where title equals to UPDATED_TITLE
        defaultTeamShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTeamsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where title not equals to DEFAULT_TITLE
        defaultTeamShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the teamList where title not equals to UPDATED_TITLE
        defaultTeamShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTeamsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultTeamShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the teamList where title equals to UPDATED_TITLE
        defaultTeamShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTeamsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where title is not null
        defaultTeamShouldBeFound("title.specified=true");

        // Get all the teamList where title is null
        defaultTeamShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamsByTitleContainsSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where title contains DEFAULT_TITLE
        defaultTeamShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the teamList where title contains UPDATED_TITLE
        defaultTeamShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTeamsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where title does not contain DEFAULT_TITLE
        defaultTeamShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the teamList where title does not contain UPDATED_TITLE
        defaultTeamShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTeamsByShortTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where shortTitle equals to DEFAULT_SHORT_TITLE
        defaultTeamShouldBeFound("shortTitle.equals=" + DEFAULT_SHORT_TITLE);

        // Get all the teamList where shortTitle equals to UPDATED_SHORT_TITLE
        defaultTeamShouldNotBeFound("shortTitle.equals=" + UPDATED_SHORT_TITLE);
    }

    @Test
    @Transactional
    void getAllTeamsByShortTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where shortTitle not equals to DEFAULT_SHORT_TITLE
        defaultTeamShouldNotBeFound("shortTitle.notEquals=" + DEFAULT_SHORT_TITLE);

        // Get all the teamList where shortTitle not equals to UPDATED_SHORT_TITLE
        defaultTeamShouldBeFound("shortTitle.notEquals=" + UPDATED_SHORT_TITLE);
    }

    @Test
    @Transactional
    void getAllTeamsByShortTitleIsInShouldWork() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where shortTitle in DEFAULT_SHORT_TITLE or UPDATED_SHORT_TITLE
        defaultTeamShouldBeFound("shortTitle.in=" + DEFAULT_SHORT_TITLE + "," + UPDATED_SHORT_TITLE);

        // Get all the teamList where shortTitle equals to UPDATED_SHORT_TITLE
        defaultTeamShouldNotBeFound("shortTitle.in=" + UPDATED_SHORT_TITLE);
    }

    @Test
    @Transactional
    void getAllTeamsByShortTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where shortTitle is not null
        defaultTeamShouldBeFound("shortTitle.specified=true");

        // Get all the teamList where shortTitle is null
        defaultTeamShouldNotBeFound("shortTitle.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamsByShortTitleContainsSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where shortTitle contains DEFAULT_SHORT_TITLE
        defaultTeamShouldBeFound("shortTitle.contains=" + DEFAULT_SHORT_TITLE);

        // Get all the teamList where shortTitle contains UPDATED_SHORT_TITLE
        defaultTeamShouldNotBeFound("shortTitle.contains=" + UPDATED_SHORT_TITLE);
    }

    @Test
    @Transactional
    void getAllTeamsByShortTitleNotContainsSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where shortTitle does not contain DEFAULT_SHORT_TITLE
        defaultTeamShouldNotBeFound("shortTitle.doesNotContain=" + DEFAULT_SHORT_TITLE);

        // Get all the teamList where shortTitle does not contain UPDATED_SHORT_TITLE
        defaultTeamShouldBeFound("shortTitle.doesNotContain=" + UPDATED_SHORT_TITLE);
    }

    @Test
    @Transactional
    void getAllTeamsBySloganIsEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where slogan equals to DEFAULT_SLOGAN
        defaultTeamShouldBeFound("slogan.equals=" + DEFAULT_SLOGAN);

        // Get all the teamList where slogan equals to UPDATED_SLOGAN
        defaultTeamShouldNotBeFound("slogan.equals=" + UPDATED_SLOGAN);
    }

    @Test
    @Transactional
    void getAllTeamsBySloganIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where slogan not equals to DEFAULT_SLOGAN
        defaultTeamShouldNotBeFound("slogan.notEquals=" + DEFAULT_SLOGAN);

        // Get all the teamList where slogan not equals to UPDATED_SLOGAN
        defaultTeamShouldBeFound("slogan.notEquals=" + UPDATED_SLOGAN);
    }

    @Test
    @Transactional
    void getAllTeamsBySloganIsInShouldWork() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where slogan in DEFAULT_SLOGAN or UPDATED_SLOGAN
        defaultTeamShouldBeFound("slogan.in=" + DEFAULT_SLOGAN + "," + UPDATED_SLOGAN);

        // Get all the teamList where slogan equals to UPDATED_SLOGAN
        defaultTeamShouldNotBeFound("slogan.in=" + UPDATED_SLOGAN);
    }

    @Test
    @Transactional
    void getAllTeamsBySloganIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where slogan is not null
        defaultTeamShouldBeFound("slogan.specified=true");

        // Get all the teamList where slogan is null
        defaultTeamShouldNotBeFound("slogan.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamsBySloganContainsSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where slogan contains DEFAULT_SLOGAN
        defaultTeamShouldBeFound("slogan.contains=" + DEFAULT_SLOGAN);

        // Get all the teamList where slogan contains UPDATED_SLOGAN
        defaultTeamShouldNotBeFound("slogan.contains=" + UPDATED_SLOGAN);
    }

    @Test
    @Transactional
    void getAllTeamsBySloganNotContainsSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where slogan does not contain DEFAULT_SLOGAN
        defaultTeamShouldNotBeFound("slogan.doesNotContain=" + DEFAULT_SLOGAN);

        // Get all the teamList where slogan does not contain UPDATED_SLOGAN
        defaultTeamShouldBeFound("slogan.doesNotContain=" + UPDATED_SLOGAN);
    }

    @Test
    @Transactional
    void getAllTeamsByContactIsEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where contact equals to DEFAULT_CONTACT
        defaultTeamShouldBeFound("contact.equals=" + DEFAULT_CONTACT);

        // Get all the teamList where contact equals to UPDATED_CONTACT
        defaultTeamShouldNotBeFound("contact.equals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllTeamsByContactIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where contact not equals to DEFAULT_CONTACT
        defaultTeamShouldNotBeFound("contact.notEquals=" + DEFAULT_CONTACT);

        // Get all the teamList where contact not equals to UPDATED_CONTACT
        defaultTeamShouldBeFound("contact.notEquals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllTeamsByContactIsInShouldWork() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where contact in DEFAULT_CONTACT or UPDATED_CONTACT
        defaultTeamShouldBeFound("contact.in=" + DEFAULT_CONTACT + "," + UPDATED_CONTACT);

        // Get all the teamList where contact equals to UPDATED_CONTACT
        defaultTeamShouldNotBeFound("contact.in=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllTeamsByContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where contact is not null
        defaultTeamShouldBeFound("contact.specified=true");

        // Get all the teamList where contact is null
        defaultTeamShouldNotBeFound("contact.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamsByContactContainsSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where contact contains DEFAULT_CONTACT
        defaultTeamShouldBeFound("contact.contains=" + DEFAULT_CONTACT);

        // Get all the teamList where contact contains UPDATED_CONTACT
        defaultTeamShouldNotBeFound("contact.contains=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllTeamsByContactNotContainsSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where contact does not contain DEFAULT_CONTACT
        defaultTeamShouldNotBeFound("contact.doesNotContain=" + DEFAULT_CONTACT);

        // Get all the teamList where contact does not contain UPDATED_CONTACT
        defaultTeamShouldBeFound("contact.doesNotContain=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllTeamsByExpirationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where expirationDate equals to DEFAULT_EXPIRATION_DATE
        defaultTeamShouldBeFound("expirationDate.equals=" + DEFAULT_EXPIRATION_DATE);

        // Get all the teamList where expirationDate equals to UPDATED_EXPIRATION_DATE
        defaultTeamShouldNotBeFound("expirationDate.equals=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    void getAllTeamsByExpirationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where expirationDate not equals to DEFAULT_EXPIRATION_DATE
        defaultTeamShouldNotBeFound("expirationDate.notEquals=" + DEFAULT_EXPIRATION_DATE);

        // Get all the teamList where expirationDate not equals to UPDATED_EXPIRATION_DATE
        defaultTeamShouldBeFound("expirationDate.notEquals=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    void getAllTeamsByExpirationDateIsInShouldWork() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where expirationDate in DEFAULT_EXPIRATION_DATE or UPDATED_EXPIRATION_DATE
        defaultTeamShouldBeFound("expirationDate.in=" + DEFAULT_EXPIRATION_DATE + "," + UPDATED_EXPIRATION_DATE);

        // Get all the teamList where expirationDate equals to UPDATED_EXPIRATION_DATE
        defaultTeamShouldNotBeFound("expirationDate.in=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    void getAllTeamsByExpirationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where expirationDate is not null
        defaultTeamShouldBeFound("expirationDate.specified=true");

        // Get all the teamList where expirationDate is null
        defaultTeamShouldNotBeFound("expirationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamsByOfficialIsEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where official equals to DEFAULT_OFFICIAL
        defaultTeamShouldBeFound("official.equals=" + DEFAULT_OFFICIAL);

        // Get all the teamList where official equals to UPDATED_OFFICIAL
        defaultTeamShouldNotBeFound("official.equals=" + UPDATED_OFFICIAL);
    }

    @Test
    @Transactional
    void getAllTeamsByOfficialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where official not equals to DEFAULT_OFFICIAL
        defaultTeamShouldNotBeFound("official.notEquals=" + DEFAULT_OFFICIAL);

        // Get all the teamList where official not equals to UPDATED_OFFICIAL
        defaultTeamShouldBeFound("official.notEquals=" + UPDATED_OFFICIAL);
    }

    @Test
    @Transactional
    void getAllTeamsByOfficialIsInShouldWork() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where official in DEFAULT_OFFICIAL or UPDATED_OFFICIAL
        defaultTeamShouldBeFound("official.in=" + DEFAULT_OFFICIAL + "," + UPDATED_OFFICIAL);

        // Get all the teamList where official equals to UPDATED_OFFICIAL
        defaultTeamShouldNotBeFound("official.in=" + UPDATED_OFFICIAL);
    }

    @Test
    @Transactional
    void getAllTeamsByOfficialIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where official is not null
        defaultTeamShouldBeFound("official.specified=true");

        // Get all the teamList where official is null
        defaultTeamShouldNotBeFound("official.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where createdAt equals to DEFAULT_CREATED_AT
        defaultTeamShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the teamList where createdAt equals to UPDATED_CREATED_AT
        defaultTeamShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllTeamsByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where createdAt not equals to DEFAULT_CREATED_AT
        defaultTeamShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the teamList where createdAt not equals to UPDATED_CREATED_AT
        defaultTeamShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllTeamsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultTeamShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the teamList where createdAt equals to UPDATED_CREATED_AT
        defaultTeamShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllTeamsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where createdAt is not null
        defaultTeamShouldBeFound("createdAt.specified=true");

        // Get all the teamList where createdAt is null
        defaultTeamShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultTeamShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the teamList where updatedAt equals to UPDATED_UPDATED_AT
        defaultTeamShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllTeamsByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultTeamShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the teamList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultTeamShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllTeamsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultTeamShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the teamList where updatedAt equals to UPDATED_UPDATED_AT
        defaultTeamShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllTeamsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where updatedAt is not null
        defaultTeamShouldBeFound("updatedAt.specified=true");

        // Get all the teamList where updatedAt is null
        defaultTeamShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamsBySkillsIsEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);
        TeamSkill skills;
        if (TestUtil.findAll(em, TeamSkill.class).isEmpty()) {
            skills = TeamSkillResourceIT.createEntity(em);
            em.persist(skills);
            em.flush();
        } else {
            skills = TestUtil.findAll(em, TeamSkill.class).get(0);
        }
        em.persist(skills);
        em.flush();
        team.addSkills(skills);
        teamRepository.saveAndFlush(team);
        Long skillsId = skills.getId();

        // Get all the teamList where skills equals to skillsId
        defaultTeamShouldBeFound("skillsId.equals=" + skillsId);

        // Get all the teamList where skills equals to (skillsId + 1)
        defaultTeamShouldNotBeFound("skillsId.equals=" + (skillsId + 1));
    }

    @Test
    @Transactional
    void getAllTeamsByImageIsEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);
        Image image;
        if (TestUtil.findAll(em, Image.class).isEmpty()) {
            image = ImageResourceIT.createEntity(em);
            em.persist(image);
            em.flush();
        } else {
            image = TestUtil.findAll(em, Image.class).get(0);
        }
        em.persist(image);
        em.flush();
        team.setImage(image);
        teamRepository.saveAndFlush(team);
        Long imageId = image.getId();

        // Get all the teamList where image equals to imageId
        defaultTeamShouldBeFound("imageId.equals=" + imageId);

        // Get all the teamList where image equals to (imageId + 1)
        defaultTeamShouldNotBeFound("imageId.equals=" + (imageId + 1));
    }

    @Test
    @Transactional
    void getAllTeamsByParticipationsIsEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);
        Dimension participations;
        if (TestUtil.findAll(em, Dimension.class).isEmpty()) {
            participations = DimensionResourceIT.createEntity(em);
            em.persist(participations);
            em.flush();
        } else {
            participations = TestUtil.findAll(em, Dimension.class).get(0);
        }
        em.persist(participations);
        em.flush();
        team.addParticipations(participations);
        teamRepository.saveAndFlush(team);
        Long participationsId = participations.getId();

        // Get all the teamList where participations equals to participationsId
        defaultTeamShouldBeFound("participationsId.equals=" + participationsId);

        // Get all the teamList where participations equals to (participationsId + 1)
        defaultTeamShouldNotBeFound("participationsId.equals=" + (participationsId + 1));
    }

    @Test
    @Transactional
    void getAllTeamsByGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);
        TeamGroup group;
        if (TestUtil.findAll(em, TeamGroup.class).isEmpty()) {
            group = TeamGroupResourceIT.createEntity(em);
            em.persist(group);
            em.flush();
        } else {
            group = TestUtil.findAll(em, TeamGroup.class).get(0);
        }
        em.persist(group);
        em.flush();
        team.setGroup(group);
        teamRepository.saveAndFlush(team);
        Long groupId = group.getId();

        // Get all the teamList where group equals to groupId
        defaultTeamShouldBeFound("groupId.equals=" + groupId);

        // Get all the teamList where group equals to (groupId + 1)
        defaultTeamShouldNotBeFound("groupId.equals=" + (groupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTeamShouldBeFound(String filter) throws Exception {
        restTeamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(team.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].shortTitle").value(hasItem(DEFAULT_SHORT_TITLE)))
            .andExpect(jsonPath("$.[*].slogan").value(hasItem(DEFAULT_SLOGAN)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].expirationDate").value(hasItem(DEFAULT_EXPIRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].official").value(hasItem(DEFAULT_OFFICIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));

        // Check, that the count call also returns 1
        restTeamMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTeamShouldNotBeFound(String filter) throws Exception {
        restTeamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTeamMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTeam() throws Exception {
        // Get the team
        restTeamMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTeam() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // Update the team
        Team updatedTeam = teamRepository.findById(team.getId()).get();
        // Disconnect from session so that the updates on updatedTeam are not directly saved in db
        em.detach(updatedTeam);
        updatedTeam
            .title(UPDATED_TITLE)
            .shortTitle(UPDATED_SHORT_TITLE)
            .slogan(UPDATED_SLOGAN)
            .contact(UPDATED_CONTACT)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .official(UPDATED_OFFICIAL)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        TeamDTO teamDTO = teamMapper.toDto(updatedTeam);

        restTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teamDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamDTO))
            )
            .andExpect(status().isOk());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTeam.getShortTitle()).isEqualTo(UPDATED_SHORT_TITLE);
        assertThat(testTeam.getSlogan()).isEqualTo(UPDATED_SLOGAN);
        assertThat(testTeam.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testTeam.getExpirationDate()).isEqualTo(UPDATED_EXPIRATION_DATE);
        assertThat(testTeam.getOfficial()).isEqualTo(UPDATED_OFFICIAL);
        assertThat(testTeam.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTeam.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(count.incrementAndGet());

        // Create the Team
        TeamDTO teamDTO = teamMapper.toDto(team);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teamDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(count.incrementAndGet());

        // Create the Team
        TeamDTO teamDTO = teamMapper.toDto(team);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(count.incrementAndGet());

        // Create the Team
        TeamDTO teamDTO = teamMapper.toDto(team);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teamDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTeamWithPatch() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // Update the team using partial update
        Team partialUpdatedTeam = new Team();
        partialUpdatedTeam.setId(team.getId());

        partialUpdatedTeam
            .slogan(UPDATED_SLOGAN)
            .contact(UPDATED_CONTACT)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .updatedAt(UPDATED_UPDATED_AT);

        restTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeam.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeam))
            )
            .andExpect(status().isOk());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTeam.getShortTitle()).isEqualTo(DEFAULT_SHORT_TITLE);
        assertThat(testTeam.getSlogan()).isEqualTo(UPDATED_SLOGAN);
        assertThat(testTeam.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testTeam.getExpirationDate()).isEqualTo(UPDATED_EXPIRATION_DATE);
        assertThat(testTeam.getOfficial()).isEqualTo(DEFAULT_OFFICIAL);
        assertThat(testTeam.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testTeam.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateTeamWithPatch() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // Update the team using partial update
        Team partialUpdatedTeam = new Team();
        partialUpdatedTeam.setId(team.getId());

        partialUpdatedTeam
            .title(UPDATED_TITLE)
            .shortTitle(UPDATED_SHORT_TITLE)
            .slogan(UPDATED_SLOGAN)
            .contact(UPDATED_CONTACT)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .official(UPDATED_OFFICIAL)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeam.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeam))
            )
            .andExpect(status().isOk());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTeam.getShortTitle()).isEqualTo(UPDATED_SHORT_TITLE);
        assertThat(testTeam.getSlogan()).isEqualTo(UPDATED_SLOGAN);
        assertThat(testTeam.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testTeam.getExpirationDate()).isEqualTo(UPDATED_EXPIRATION_DATE);
        assertThat(testTeam.getOfficial()).isEqualTo(UPDATED_OFFICIAL);
        assertThat(testTeam.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTeam.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(count.incrementAndGet());

        // Create the Team
        TeamDTO teamDTO = teamMapper.toDto(team);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, teamDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(count.incrementAndGet());

        // Create the Team
        TeamDTO teamDTO = teamMapper.toDto(team);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(count.incrementAndGet());

        // Create the Team
        TeamDTO teamDTO = teamMapper.toDto(team);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void deleteTeam() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        int databaseSizeBeforeDelete = teamRepository.findAll().size();

        // Delete the team
        restTeamMockMvc
            .perform(delete(ENTITY_API_URL_ID, team.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeDelete - 1);
    }

    // ### MODIFICATION-START ###
    @Test
    @Transactional
    @WithMockUser(username = "user", authorities = { "ROLE_USER" })
    void deleteTeamAsUserIsForbidden() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        int databaseSizeBeforeDelete = teamRepository.findAll().size();

        // Delete the team
        restTeamMockMvc
            .perform(delete(ENTITY_API_URL_ID, team.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());

        // Validate the database contains one less item
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeDelete);
    }

    @Test
    @Transactional
    @WithUnauthenticatedMockUser
    void deleteTeamAsAnonymousUserIsForbidden() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        int databaseSizeBeforeDelete = teamRepository.findAll().size();

        // Delete the team
        restTeamMockMvc
            .perform(delete(ENTITY_API_URL_ID, team.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());

        // Validate the database contains one less item
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeDelete);
    }
    // ### MODIFICATION-END ###

}
