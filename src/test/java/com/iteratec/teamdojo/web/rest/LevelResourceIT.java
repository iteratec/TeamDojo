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
import com.iteratec.teamdojo.domain.Level;
import com.iteratec.teamdojo.domain.Level;
import com.iteratec.teamdojo.domain.LevelSkill;
import com.iteratec.teamdojo.repository.LevelRepository;
import com.iteratec.teamdojo.service.LevelService;
// ### MODIFICATION-START ###
import com.iteratec.teamdojo.service.custom.ExtendedLevelService;
// ### MODIFICATION-END ###
import com.iteratec.teamdojo.service.dto.LevelDTO;
import com.iteratec.teamdojo.service.mapper.LevelMapper;
import jakarta.persistence.EntityManager;
// ### MODIFICATION-START ###
import com.iteratec.teamdojo.test.util.StaticInstantProvider;
// ### MODIFICATION-END ###
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LevelResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
@GeneratedByJHipster
class LevelResourceIT {

    private static final String DEFAULT_TITLE_EN = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_EN = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_DE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_DE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_EN = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_EN = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_DE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_DE = "BBBBBBBBBB";

    private static final Double DEFAULT_REQUIRED_SCORE = 0D;
    private static final Double UPDATED_REQUIRED_SCORE = 1D;
    private static final Double SMALLER_REQUIRED_SCORE = 0D - 1D;

    private static final Double DEFAULT_INSTANT_MULTIPLIER = 0D;
    private static final Double UPDATED_INSTANT_MULTIPLIER = 1D;
    private static final Double SMALLER_INSTANT_MULTIPLIER = 0D - 1D;

    private static final Integer DEFAULT_COMPLETION_BONUS = 0;
    private static final Integer UPDATED_COMPLETION_BONUS = 1;
    private static final Integer SMALLER_COMPLETION_BONUS = 0 - 1;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    // ### MODIFICATION-START ###
    private static final Instant CUSTOM_CREATED_AND_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    // ### MODIFICATION-END ###

    private static final String ENTITY_API_URL = "/api/levels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LevelRepository levelRepository;

    @Mock
    private LevelRepository levelRepositoryMock;

    @Autowired
    private LevelMapper levelMapper;

    @Mock
    private LevelService levelServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLevelMockMvc;

    // ### MODIFICATION-START ###
    @Autowired
    private ExtendedLevelService extendedLevelService;

    // ### MODIFICATION-END ###

    private Level level;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Level createEntity(EntityManager em) {
        Level level = new Level()
            .titleEN(DEFAULT_TITLE_EN)
            .titleDE(DEFAULT_TITLE_DE)
            .descriptionEN(DEFAULT_DESCRIPTION_EN)
            .descriptionDE(DEFAULT_DESCRIPTION_DE)
            .requiredScore(DEFAULT_REQUIRED_SCORE)
            .instantMultiplier(DEFAULT_INSTANT_MULTIPLIER)
            .completionBonus(DEFAULT_COMPLETION_BONUS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        // Add required entity
        Dimension dimension;
        if (TestUtil.findAll(em, Dimension.class).isEmpty()) {
            dimension = DimensionResourceIT.createEntity(em);
            em.persist(dimension);
            em.flush();
        } else {
            dimension = TestUtil.findAll(em, Dimension.class).get(0);
        }
        level.setDimension(dimension);
        return level;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Level createUpdatedEntity(EntityManager em) {
        Level level = new Level()
            .titleEN(UPDATED_TITLE_EN)
            .titleDE(UPDATED_TITLE_DE)
            .descriptionEN(UPDATED_DESCRIPTION_EN)
            .descriptionDE(UPDATED_DESCRIPTION_DE)
            .requiredScore(UPDATED_REQUIRED_SCORE)
            .instantMultiplier(UPDATED_INSTANT_MULTIPLIER)
            .completionBonus(UPDATED_COMPLETION_BONUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        // Add required entity
        Dimension dimension;
        if (TestUtil.findAll(em, Dimension.class).isEmpty()) {
            dimension = DimensionResourceIT.createUpdatedEntity(em);
            em.persist(dimension);
            em.flush();
        } else {
            dimension = TestUtil.findAll(em, Dimension.class).get(0);
        }
        level.setDimension(dimension);
        return level;
    }

    @BeforeEach
    public void initTest() {
        level = createEntity(em);
    }

    // ### MODIFICATION-START ###
    @BeforeEach
    public void initInstantProvider() {
        extendedLevelService.setTime(StaticInstantProvider.forFixedTime(CUSTOM_CREATED_AND_UPDATED_AT));
    }

    // ### MODIFICATION-END ###

    @Test
    @Transactional
// ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void createLevel() throws Exception {
        int databaseSizeBeforeCreate = levelRepository.findAll().size();
        // Create the Level
        LevelDTO levelDTO = levelMapper.toDto(level);
        restLevelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Level in the database
        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeCreate + 1);
        Level testLevel = levelList.get(levelList.size() - 1);
        assertThat(testLevel.getTitleEN()).isEqualTo(DEFAULT_TITLE_EN);
        assertThat(testLevel.getTitleDE()).isEqualTo(DEFAULT_TITLE_DE);
        assertThat(testLevel.getDescriptionEN()).isEqualTo(DEFAULT_DESCRIPTION_EN);
        assertThat(testLevel.getDescriptionDE()).isEqualTo(DEFAULT_DESCRIPTION_DE);
        assertThat(testLevel.getRequiredScore()).isEqualTo(DEFAULT_REQUIRED_SCORE);
        assertThat(testLevel.getInstantMultiplier()).isEqualTo(DEFAULT_INSTANT_MULTIPLIER);
        assertThat(testLevel.getCompletionBonus()).isEqualTo(DEFAULT_COMPLETION_BONUS);
        // ### MODIFICATION-START ###
        assertThat(testLevel.getCreatedAt()).isEqualTo(CUSTOM_CREATED_AND_UPDATED_AT);
        assertThat(testLevel.getUpdatedAt()).isEqualTo(CUSTOM_CREATED_AND_UPDATED_AT);
// ### MODIFICATION-END ###
    }

    @Test
    @Transactional
// ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void createLevelWithExistingId() throws Exception {
        // Create the Level with an existing ID
        level.setId(1L);
        LevelDTO levelDTO = levelMapper.toDto(level);

        int databaseSizeBeforeCreate = levelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLevelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Level in the database
        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleENIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelRepository.findAll().size();
        // set the field null
        level.setTitleEN(null);

        // Create the Level, which fails.
        LevelDTO levelDTO = levelMapper.toDto(level);

        restLevelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelDTO))
            )
            .andExpect(status().isBadRequest());

        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRequiredScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelRepository.findAll().size();
        // set the field null
        level.setRequiredScore(null);

        // Create the Level, which fails.
        LevelDTO levelDTO = levelMapper.toDto(level);

        restLevelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelDTO))
            )
            .andExpect(status().isBadRequest());

        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInstantMultiplierIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelRepository.findAll().size();
        // set the field null
        level.setInstantMultiplier(null);

        // Create the Level, which fails.
        LevelDTO levelDTO = levelMapper.toDto(level);

        restLevelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelDTO))
            )
            .andExpect(status().isBadRequest());

        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
// ### MODIFICATION-START ###
    @Disabled("Ignored because we removed the validation for this field in the DTO.")
    // ### MODIFICATION-END ###
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelRepository.findAll().size();
        // set the field null
        level.setCreatedAt(null);

        // Create the Level, which fails.
        LevelDTO levelDTO = levelMapper.toDto(level);

        restLevelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelDTO))
            )
            .andExpect(status().isBadRequest());

        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
// ### MODIFICATION-START ###
    @Disabled("Ignored because we removed the validation for this field in the DTO.")
    // ### MODIFICATION-END ###
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelRepository.findAll().size();
        // set the field null
        level.setUpdatedAt(null);

        // Create the Level, which fails.
        LevelDTO levelDTO = levelMapper.toDto(level);

        restLevelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelDTO))
            )
            .andExpect(status().isBadRequest());

        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLevels() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList
        restLevelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(level.getId().intValue())))
            .andExpect(jsonPath("$.[*].titleEN").value(hasItem(DEFAULT_TITLE_EN)))
            .andExpect(jsonPath("$.[*].titleDE").value(hasItem(DEFAULT_TITLE_DE)))
            .andExpect(jsonPath("$.[*].descriptionEN").value(hasItem(DEFAULT_DESCRIPTION_EN)))
            .andExpect(jsonPath("$.[*].descriptionDE").value(hasItem(DEFAULT_DESCRIPTION_DE)))
            .andExpect(jsonPath("$.[*].requiredScore").value(hasItem(DEFAULT_REQUIRED_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].instantMultiplier").value(hasItem(DEFAULT_INSTANT_MULTIPLIER.doubleValue())))
            .andExpect(jsonPath("$.[*].completionBonus").value(hasItem(DEFAULT_COMPLETION_BONUS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLevelsWithEagerRelationshipsIsEnabled() throws Exception {
        when(levelServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLevelMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(levelServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLevelsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(levelServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLevelMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(levelRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getLevel() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get the level
        restLevelMockMvc
            .perform(get(ENTITY_API_URL_ID, level.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(level.getId().intValue()))
            .andExpect(jsonPath("$.titleEN").value(DEFAULT_TITLE_EN))
            .andExpect(jsonPath("$.titleDE").value(DEFAULT_TITLE_DE))
            .andExpect(jsonPath("$.descriptionEN").value(DEFAULT_DESCRIPTION_EN))
            .andExpect(jsonPath("$.descriptionDE").value(DEFAULT_DESCRIPTION_DE))
            .andExpect(jsonPath("$.requiredScore").value(DEFAULT_REQUIRED_SCORE.doubleValue()))
            .andExpect(jsonPath("$.instantMultiplier").value(DEFAULT_INSTANT_MULTIPLIER.doubleValue()))
            .andExpect(jsonPath("$.completionBonus").value(DEFAULT_COMPLETION_BONUS))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getLevelsByIdFiltering() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        Long id = level.getId();

        defaultLevelShouldBeFound("id.equals=" + id);
        defaultLevelShouldNotBeFound("id.notEquals=" + id);

        defaultLevelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLevelShouldNotBeFound("id.greaterThan=" + id);

        defaultLevelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLevelShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLevelsByTitleENIsEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where titleEN equals to DEFAULT_TITLE_EN
        defaultLevelShouldBeFound("titleEN.equals=" + DEFAULT_TITLE_EN);

        // Get all the levelList where titleEN equals to UPDATED_TITLE_EN
        defaultLevelShouldNotBeFound("titleEN.equals=" + UPDATED_TITLE_EN);
    }

    @Test
    @Transactional
    void getAllLevelsByTitleENIsInShouldWork() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where titleEN in DEFAULT_TITLE_EN or UPDATED_TITLE_EN
        defaultLevelShouldBeFound("titleEN.in=" + DEFAULT_TITLE_EN + "," + UPDATED_TITLE_EN);

        // Get all the levelList where titleEN equals to UPDATED_TITLE_EN
        defaultLevelShouldNotBeFound("titleEN.in=" + UPDATED_TITLE_EN);
    }

    @Test
    @Transactional
    void getAllLevelsByTitleENIsNullOrNotNull() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where titleEN is not null
        defaultLevelShouldBeFound("titleEN.specified=true");

        // Get all the levelList where titleEN is null
        defaultLevelShouldNotBeFound("titleEN.specified=false");
    }

    @Test
    @Transactional
    void getAllLevelsByTitleENContainsSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where titleEN contains DEFAULT_TITLE_EN
        defaultLevelShouldBeFound("titleEN.contains=" + DEFAULT_TITLE_EN);

        // Get all the levelList where titleEN contains UPDATED_TITLE_EN
        defaultLevelShouldNotBeFound("titleEN.contains=" + UPDATED_TITLE_EN);
    }

    @Test
    @Transactional
    void getAllLevelsByTitleENNotContainsSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where titleEN does not contain DEFAULT_TITLE_EN
        defaultLevelShouldNotBeFound("titleEN.doesNotContain=" + DEFAULT_TITLE_EN);

        // Get all the levelList where titleEN does not contain UPDATED_TITLE_EN
        defaultLevelShouldBeFound("titleEN.doesNotContain=" + UPDATED_TITLE_EN);
    }

    @Test
    @Transactional
    void getAllLevelsByTitleDEIsEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where titleDE equals to DEFAULT_TITLE_DE
        defaultLevelShouldBeFound("titleDE.equals=" + DEFAULT_TITLE_DE);

        // Get all the levelList where titleDE equals to UPDATED_TITLE_DE
        defaultLevelShouldNotBeFound("titleDE.equals=" + UPDATED_TITLE_DE);
    }

    @Test
    @Transactional
    void getAllLevelsByTitleDEIsInShouldWork() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where titleDE in DEFAULT_TITLE_DE or UPDATED_TITLE_DE
        defaultLevelShouldBeFound("titleDE.in=" + DEFAULT_TITLE_DE + "," + UPDATED_TITLE_DE);

        // Get all the levelList where titleDE equals to UPDATED_TITLE_DE
        defaultLevelShouldNotBeFound("titleDE.in=" + UPDATED_TITLE_DE);
    }

    @Test
    @Transactional
    void getAllLevelsByTitleDEIsNullOrNotNull() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where titleDE is not null
        defaultLevelShouldBeFound("titleDE.specified=true");

        // Get all the levelList where titleDE is null
        defaultLevelShouldNotBeFound("titleDE.specified=false");
    }

    @Test
    @Transactional
    void getAllLevelsByTitleDEContainsSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where titleDE contains DEFAULT_TITLE_DE
        defaultLevelShouldBeFound("titleDE.contains=" + DEFAULT_TITLE_DE);

        // Get all the levelList where titleDE contains UPDATED_TITLE_DE
        defaultLevelShouldNotBeFound("titleDE.contains=" + UPDATED_TITLE_DE);
    }

    @Test
    @Transactional
    void getAllLevelsByTitleDENotContainsSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where titleDE does not contain DEFAULT_TITLE_DE
        defaultLevelShouldNotBeFound("titleDE.doesNotContain=" + DEFAULT_TITLE_DE);

        // Get all the levelList where titleDE does not contain UPDATED_TITLE_DE
        defaultLevelShouldBeFound("titleDE.doesNotContain=" + UPDATED_TITLE_DE);
    }

    @Test
    @Transactional
    void getAllLevelsByDescriptionENIsEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where descriptionEN equals to DEFAULT_DESCRIPTION_EN
        defaultLevelShouldBeFound("descriptionEN.equals=" + DEFAULT_DESCRIPTION_EN);

        // Get all the levelList where descriptionEN equals to UPDATED_DESCRIPTION_EN
        defaultLevelShouldNotBeFound("descriptionEN.equals=" + UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    void getAllLevelsByDescriptionENIsInShouldWork() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where descriptionEN in DEFAULT_DESCRIPTION_EN or UPDATED_DESCRIPTION_EN
        defaultLevelShouldBeFound("descriptionEN.in=" + DEFAULT_DESCRIPTION_EN + "," + UPDATED_DESCRIPTION_EN);

        // Get all the levelList where descriptionEN equals to UPDATED_DESCRIPTION_EN
        defaultLevelShouldNotBeFound("descriptionEN.in=" + UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    void getAllLevelsByDescriptionENIsNullOrNotNull() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where descriptionEN is not null
        defaultLevelShouldBeFound("descriptionEN.specified=true");

        // Get all the levelList where descriptionEN is null
        defaultLevelShouldNotBeFound("descriptionEN.specified=false");
    }

    @Test
    @Transactional
    void getAllLevelsByDescriptionENContainsSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where descriptionEN contains DEFAULT_DESCRIPTION_EN
        defaultLevelShouldBeFound("descriptionEN.contains=" + DEFAULT_DESCRIPTION_EN);

        // Get all the levelList where descriptionEN contains UPDATED_DESCRIPTION_EN
        defaultLevelShouldNotBeFound("descriptionEN.contains=" + UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    void getAllLevelsByDescriptionENNotContainsSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where descriptionEN does not contain DEFAULT_DESCRIPTION_EN
        defaultLevelShouldNotBeFound("descriptionEN.doesNotContain=" + DEFAULT_DESCRIPTION_EN);

        // Get all the levelList where descriptionEN does not contain UPDATED_DESCRIPTION_EN
        defaultLevelShouldBeFound("descriptionEN.doesNotContain=" + UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    void getAllLevelsByDescriptionDEIsEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where descriptionDE equals to DEFAULT_DESCRIPTION_DE
        defaultLevelShouldBeFound("descriptionDE.equals=" + DEFAULT_DESCRIPTION_DE);

        // Get all the levelList where descriptionDE equals to UPDATED_DESCRIPTION_DE
        defaultLevelShouldNotBeFound("descriptionDE.equals=" + UPDATED_DESCRIPTION_DE);
    }

    @Test
    @Transactional
    void getAllLevelsByDescriptionDEIsInShouldWork() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where descriptionDE in DEFAULT_DESCRIPTION_DE or UPDATED_DESCRIPTION_DE
        defaultLevelShouldBeFound("descriptionDE.in=" + DEFAULT_DESCRIPTION_DE + "," + UPDATED_DESCRIPTION_DE);

        // Get all the levelList where descriptionDE equals to UPDATED_DESCRIPTION_DE
        defaultLevelShouldNotBeFound("descriptionDE.in=" + UPDATED_DESCRIPTION_DE);
    }

    @Test
    @Transactional
    void getAllLevelsByDescriptionDEIsNullOrNotNull() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where descriptionDE is not null
        defaultLevelShouldBeFound("descriptionDE.specified=true");

        // Get all the levelList where descriptionDE is null
        defaultLevelShouldNotBeFound("descriptionDE.specified=false");
    }

    @Test
    @Transactional
    void getAllLevelsByDescriptionDEContainsSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where descriptionDE contains DEFAULT_DESCRIPTION_DE
        defaultLevelShouldBeFound("descriptionDE.contains=" + DEFAULT_DESCRIPTION_DE);

        // Get all the levelList where descriptionDE contains UPDATED_DESCRIPTION_DE
        defaultLevelShouldNotBeFound("descriptionDE.contains=" + UPDATED_DESCRIPTION_DE);
    }

    @Test
    @Transactional
    void getAllLevelsByDescriptionDENotContainsSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where descriptionDE does not contain DEFAULT_DESCRIPTION_DE
        defaultLevelShouldNotBeFound("descriptionDE.doesNotContain=" + DEFAULT_DESCRIPTION_DE);

        // Get all the levelList where descriptionDE does not contain UPDATED_DESCRIPTION_DE
        defaultLevelShouldBeFound("descriptionDE.doesNotContain=" + UPDATED_DESCRIPTION_DE);
    }

    @Test
    @Transactional
    void getAllLevelsByRequiredScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where requiredScore equals to DEFAULT_REQUIRED_SCORE
        defaultLevelShouldBeFound("requiredScore.equals=" + DEFAULT_REQUIRED_SCORE);

        // Get all the levelList where requiredScore equals to UPDATED_REQUIRED_SCORE
        defaultLevelShouldNotBeFound("requiredScore.equals=" + UPDATED_REQUIRED_SCORE);
    }

    @Test
    @Transactional
    void getAllLevelsByRequiredScoreIsInShouldWork() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where requiredScore in DEFAULT_REQUIRED_SCORE or UPDATED_REQUIRED_SCORE
        defaultLevelShouldBeFound("requiredScore.in=" + DEFAULT_REQUIRED_SCORE + "," + UPDATED_REQUIRED_SCORE);

        // Get all the levelList where requiredScore equals to UPDATED_REQUIRED_SCORE
        defaultLevelShouldNotBeFound("requiredScore.in=" + UPDATED_REQUIRED_SCORE);
    }

    @Test
    @Transactional
    void getAllLevelsByRequiredScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where requiredScore is not null
        defaultLevelShouldBeFound("requiredScore.specified=true");

        // Get all the levelList where requiredScore is null
        defaultLevelShouldNotBeFound("requiredScore.specified=false");
    }

    @Test
    @Transactional
    void getAllLevelsByRequiredScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where requiredScore is greater than or equal to DEFAULT_REQUIRED_SCORE
        defaultLevelShouldBeFound("requiredScore.greaterThanOrEqual=" + DEFAULT_REQUIRED_SCORE);

        // Get all the levelList where requiredScore is greater than or equal to (DEFAULT_REQUIRED_SCORE + 1)
        defaultLevelShouldNotBeFound("requiredScore.greaterThanOrEqual=" + (DEFAULT_REQUIRED_SCORE + 1));
    }

    @Test
    @Transactional
    void getAllLevelsByRequiredScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where requiredScore is less than or equal to DEFAULT_REQUIRED_SCORE
        defaultLevelShouldBeFound("requiredScore.lessThanOrEqual=" + DEFAULT_REQUIRED_SCORE);

        // Get all the levelList where requiredScore is less than or equal to SMALLER_REQUIRED_SCORE
        defaultLevelShouldNotBeFound("requiredScore.lessThanOrEqual=" + SMALLER_REQUIRED_SCORE);
    }

    @Test
    @Transactional
    void getAllLevelsByRequiredScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where requiredScore is less than DEFAULT_REQUIRED_SCORE
        defaultLevelShouldNotBeFound("requiredScore.lessThan=" + DEFAULT_REQUIRED_SCORE);

        // Get all the levelList where requiredScore is less than (DEFAULT_REQUIRED_SCORE + 1)
        defaultLevelShouldBeFound("requiredScore.lessThan=" + (DEFAULT_REQUIRED_SCORE + 1));
    }

    @Test
    @Transactional
    void getAllLevelsByRequiredScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where requiredScore is greater than DEFAULT_REQUIRED_SCORE
        defaultLevelShouldNotBeFound("requiredScore.greaterThan=" + DEFAULT_REQUIRED_SCORE);

        // Get all the levelList where requiredScore is greater than SMALLER_REQUIRED_SCORE
        defaultLevelShouldBeFound("requiredScore.greaterThan=" + SMALLER_REQUIRED_SCORE);
    }

    @Test
    @Transactional
    void getAllLevelsByInstantMultiplierIsEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where instantMultiplier equals to DEFAULT_INSTANT_MULTIPLIER
        defaultLevelShouldBeFound("instantMultiplier.equals=" + DEFAULT_INSTANT_MULTIPLIER);

        // Get all the levelList where instantMultiplier equals to UPDATED_INSTANT_MULTIPLIER
        defaultLevelShouldNotBeFound("instantMultiplier.equals=" + UPDATED_INSTANT_MULTIPLIER);
    }

    @Test
    @Transactional
    void getAllLevelsByInstantMultiplierIsInShouldWork() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where instantMultiplier in DEFAULT_INSTANT_MULTIPLIER or UPDATED_INSTANT_MULTIPLIER
        defaultLevelShouldBeFound("instantMultiplier.in=" + DEFAULT_INSTANT_MULTIPLIER + "," + UPDATED_INSTANT_MULTIPLIER);

        // Get all the levelList where instantMultiplier equals to UPDATED_INSTANT_MULTIPLIER
        defaultLevelShouldNotBeFound("instantMultiplier.in=" + UPDATED_INSTANT_MULTIPLIER);
    }

    @Test
    @Transactional
    void getAllLevelsByInstantMultiplierIsNullOrNotNull() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where instantMultiplier is not null
        defaultLevelShouldBeFound("instantMultiplier.specified=true");

        // Get all the levelList where instantMultiplier is null
        defaultLevelShouldNotBeFound("instantMultiplier.specified=false");
    }

    @Test
    @Transactional
    void getAllLevelsByInstantMultiplierIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where instantMultiplier is greater than or equal to DEFAULT_INSTANT_MULTIPLIER
        defaultLevelShouldBeFound("instantMultiplier.greaterThanOrEqual=" + DEFAULT_INSTANT_MULTIPLIER);

        // Get all the levelList where instantMultiplier is greater than or equal to UPDATED_INSTANT_MULTIPLIER
        defaultLevelShouldNotBeFound("instantMultiplier.greaterThanOrEqual=" + UPDATED_INSTANT_MULTIPLIER);
    }

    @Test
    @Transactional
    void getAllLevelsByInstantMultiplierIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where instantMultiplier is less than or equal to DEFAULT_INSTANT_MULTIPLIER
        defaultLevelShouldBeFound("instantMultiplier.lessThanOrEqual=" + DEFAULT_INSTANT_MULTIPLIER);

        // Get all the levelList where instantMultiplier is less than or equal to SMALLER_INSTANT_MULTIPLIER
        defaultLevelShouldNotBeFound("instantMultiplier.lessThanOrEqual=" + SMALLER_INSTANT_MULTIPLIER);
    }

    @Test
    @Transactional
    void getAllLevelsByInstantMultiplierIsLessThanSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where instantMultiplier is less than DEFAULT_INSTANT_MULTIPLIER
        defaultLevelShouldNotBeFound("instantMultiplier.lessThan=" + DEFAULT_INSTANT_MULTIPLIER);

        // Get all the levelList where instantMultiplier is less than UPDATED_INSTANT_MULTIPLIER
        defaultLevelShouldBeFound("instantMultiplier.lessThan=" + UPDATED_INSTANT_MULTIPLIER);
    }

    @Test
    @Transactional
    void getAllLevelsByInstantMultiplierIsGreaterThanSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where instantMultiplier is greater than DEFAULT_INSTANT_MULTIPLIER
        defaultLevelShouldNotBeFound("instantMultiplier.greaterThan=" + DEFAULT_INSTANT_MULTIPLIER);

        // Get all the levelList where instantMultiplier is greater than SMALLER_INSTANT_MULTIPLIER
        defaultLevelShouldBeFound("instantMultiplier.greaterThan=" + SMALLER_INSTANT_MULTIPLIER);
    }

    @Test
    @Transactional
    void getAllLevelsByCompletionBonusIsEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where completionBonus equals to DEFAULT_COMPLETION_BONUS
        defaultLevelShouldBeFound("completionBonus.equals=" + DEFAULT_COMPLETION_BONUS);

        // Get all the levelList where completionBonus equals to UPDATED_COMPLETION_BONUS
        defaultLevelShouldNotBeFound("completionBonus.equals=" + UPDATED_COMPLETION_BONUS);
    }

    @Test
    @Transactional
    void getAllLevelsByCompletionBonusIsInShouldWork() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where completionBonus in DEFAULT_COMPLETION_BONUS or UPDATED_COMPLETION_BONUS
        defaultLevelShouldBeFound("completionBonus.in=" + DEFAULT_COMPLETION_BONUS + "," + UPDATED_COMPLETION_BONUS);

        // Get all the levelList where completionBonus equals to UPDATED_COMPLETION_BONUS
        defaultLevelShouldNotBeFound("completionBonus.in=" + UPDATED_COMPLETION_BONUS);
    }

    @Test
    @Transactional
    void getAllLevelsByCompletionBonusIsNullOrNotNull() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where completionBonus is not null
        defaultLevelShouldBeFound("completionBonus.specified=true");

        // Get all the levelList where completionBonus is null
        defaultLevelShouldNotBeFound("completionBonus.specified=false");
    }

    @Test
    @Transactional
    void getAllLevelsByCompletionBonusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where completionBonus is greater than or equal to DEFAULT_COMPLETION_BONUS
        defaultLevelShouldBeFound("completionBonus.greaterThanOrEqual=" + DEFAULT_COMPLETION_BONUS);

        // Get all the levelList where completionBonus is greater than or equal to UPDATED_COMPLETION_BONUS
        defaultLevelShouldNotBeFound("completionBonus.greaterThanOrEqual=" + UPDATED_COMPLETION_BONUS);
    }

    @Test
    @Transactional
    void getAllLevelsByCompletionBonusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where completionBonus is less than or equal to DEFAULT_COMPLETION_BONUS
        defaultLevelShouldBeFound("completionBonus.lessThanOrEqual=" + DEFAULT_COMPLETION_BONUS);

        // Get all the levelList where completionBonus is less than or equal to SMALLER_COMPLETION_BONUS
        defaultLevelShouldNotBeFound("completionBonus.lessThanOrEqual=" + SMALLER_COMPLETION_BONUS);
    }

    @Test
    @Transactional
    void getAllLevelsByCompletionBonusIsLessThanSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where completionBonus is less than DEFAULT_COMPLETION_BONUS
        defaultLevelShouldNotBeFound("completionBonus.lessThan=" + DEFAULT_COMPLETION_BONUS);

        // Get all the levelList where completionBonus is less than UPDATED_COMPLETION_BONUS
        defaultLevelShouldBeFound("completionBonus.lessThan=" + UPDATED_COMPLETION_BONUS);
    }

    @Test
    @Transactional
    void getAllLevelsByCompletionBonusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where completionBonus is greater than DEFAULT_COMPLETION_BONUS
        defaultLevelShouldNotBeFound("completionBonus.greaterThan=" + DEFAULT_COMPLETION_BONUS);

        // Get all the levelList where completionBonus is greater than SMALLER_COMPLETION_BONUS
        defaultLevelShouldBeFound("completionBonus.greaterThan=" + SMALLER_COMPLETION_BONUS);
    }

    @Test
    @Transactional
    void getAllLevelsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where createdAt equals to DEFAULT_CREATED_AT
        defaultLevelShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the levelList where createdAt equals to UPDATED_CREATED_AT
        defaultLevelShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLevelsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultLevelShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the levelList where createdAt equals to UPDATED_CREATED_AT
        defaultLevelShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllLevelsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where createdAt is not null
        defaultLevelShouldBeFound("createdAt.specified=true");

        // Get all the levelList where createdAt is null
        defaultLevelShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLevelsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultLevelShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the levelList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLevelShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLevelsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultLevelShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the levelList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLevelShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllLevelsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where updatedAt is not null
        defaultLevelShouldBeFound("updatedAt.specified=true");

        // Get all the levelList where updatedAt is null
        defaultLevelShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllLevelsByDependsOnIsEqualToSomething() throws Exception {
        Level dependsOn;
        if (TestUtil.findAll(em, Level.class).isEmpty()) {
            levelRepository.saveAndFlush(level);
            dependsOn = LevelResourceIT.createEntity(em);
        } else {
            dependsOn = TestUtil.findAll(em, Level.class).get(0);
        }
        em.persist(dependsOn);
        em.flush();
        level.setDependsOn(dependsOn);
        levelRepository.saveAndFlush(level);
        Long dependsOnId = dependsOn.getId();
        // Get all the levelList where dependsOn equals to dependsOnId
        defaultLevelShouldBeFound("dependsOnId.equals=" + dependsOnId);

        // Get all the levelList where dependsOn equals to (dependsOnId + 1)
        defaultLevelShouldNotBeFound("dependsOnId.equals=" + (dependsOnId + 1));
    }

    @Test
    @Transactional
    void getAllLevelsBySkillsIsEqualToSomething() throws Exception {
        LevelSkill skills;
        if (TestUtil.findAll(em, LevelSkill.class).isEmpty()) {
            levelRepository.saveAndFlush(level);
            skills = LevelSkillResourceIT.createEntity(em);
        } else {
            skills = TestUtil.findAll(em, LevelSkill.class).get(0);
        }
        em.persist(skills);
        em.flush();
        level.addSkills(skills);
        levelRepository.saveAndFlush(level);
        Long skillsId = skills.getId();
        // Get all the levelList where skills equals to skillsId
        defaultLevelShouldBeFound("skillsId.equals=" + skillsId);

        // Get all the levelList where skills equals to (skillsId + 1)
        defaultLevelShouldNotBeFound("skillsId.equals=" + (skillsId + 1));
    }

    @Test
    @Transactional
    void getAllLevelsByImageIsEqualToSomething() throws Exception {
        Image image;
        if (TestUtil.findAll(em, Image.class).isEmpty()) {
            levelRepository.saveAndFlush(level);
            image = ImageResourceIT.createEntity(em);
        } else {
            image = TestUtil.findAll(em, Image.class).get(0);
        }
        em.persist(image);
        em.flush();
        level.setImage(image);
        levelRepository.saveAndFlush(level);
        Long imageId = image.getId();
        // Get all the levelList where image equals to imageId
        defaultLevelShouldBeFound("imageId.equals=" + imageId);

        // Get all the levelList where image equals to (imageId + 1)
        defaultLevelShouldNotBeFound("imageId.equals=" + (imageId + 1));
    }

    @Test
    @Transactional
    void getAllLevelsByDimensionIsEqualToSomething() throws Exception {
        Dimension dimension;
        if (TestUtil.findAll(em, Dimension.class).isEmpty()) {
            levelRepository.saveAndFlush(level);
            dimension = DimensionResourceIT.createEntity(em);
        } else {
            dimension = TestUtil.findAll(em, Dimension.class).get(0);
        }
        em.persist(dimension);
        em.flush();
        level.setDimension(dimension);
        levelRepository.saveAndFlush(level);
        Long dimensionId = dimension.getId();
        // Get all the levelList where dimension equals to dimensionId
        defaultLevelShouldBeFound("dimensionId.equals=" + dimensionId);

        // Get all the levelList where dimension equals to (dimensionId + 1)
        defaultLevelShouldNotBeFound("dimensionId.equals=" + (dimensionId + 1));
    }

    @Test
    @Transactional
    void getAllLevelsByLevelIsEqualToSomething() throws Exception {
        Level level;
        if (TestUtil.findAll(em, Level.class).isEmpty()) {
            levelRepository.saveAndFlush(level);
            level = LevelResourceIT.createEntity(em);
        } else {
            level = TestUtil.findAll(em, Level.class).get(0);
        }
        em.persist(level);
        em.flush();
        level.setLevel(level);
        level.setDependsOn(level);
        levelRepository.saveAndFlush(level);
        Long levelId = level.getId();
        // Get all the levelList where level equals to levelId
        defaultLevelShouldBeFound("levelId.equals=" + levelId);

        // Get all the levelList where level equals to (levelId + 1)
        defaultLevelShouldNotBeFound("levelId.equals=" + (levelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLevelShouldBeFound(String filter) throws Exception {
        restLevelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(level.getId().intValue())))
            .andExpect(jsonPath("$.[*].titleEN").value(hasItem(DEFAULT_TITLE_EN)))
            .andExpect(jsonPath("$.[*].titleDE").value(hasItem(DEFAULT_TITLE_DE)))
            .andExpect(jsonPath("$.[*].descriptionEN").value(hasItem(DEFAULT_DESCRIPTION_EN)))
            .andExpect(jsonPath("$.[*].descriptionDE").value(hasItem(DEFAULT_DESCRIPTION_DE)))
            .andExpect(jsonPath("$.[*].requiredScore").value(hasItem(DEFAULT_REQUIRED_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].instantMultiplier").value(hasItem(DEFAULT_INSTANT_MULTIPLIER.doubleValue())))
            .andExpect(jsonPath("$.[*].completionBonus").value(hasItem(DEFAULT_COMPLETION_BONUS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));

        // Check, that the count call also returns 1
        restLevelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLevelShouldNotBeFound(String filter) throws Exception {
        restLevelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLevelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLevel() throws Exception {
        // Get the level
        restLevelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLevel() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        int databaseSizeBeforeUpdate = levelRepository.findAll().size();

        // Update the level
        Level updatedLevel = levelRepository.findById(level.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLevel are not directly saved in db
        em.detach(updatedLevel);
        updatedLevel
            .titleEN(UPDATED_TITLE_EN)
            .titleDE(UPDATED_TITLE_DE)
            .descriptionEN(UPDATED_DESCRIPTION_EN)
            .descriptionDE(UPDATED_DESCRIPTION_DE)
            .requiredScore(UPDATED_REQUIRED_SCORE)
            .instantMultiplier(UPDATED_INSTANT_MULTIPLIER)
            .completionBonus(UPDATED_COMPLETION_BONUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        LevelDTO levelDTO = levelMapper.toDto(updatedLevel);

        restLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, levelDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelDTO))
            )
            .andExpect(status().isOk());

        // Validate the Level in the database
        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeUpdate);
        Level testLevel = levelList.get(levelList.size() - 1);
        assertThat(testLevel.getTitleEN()).isEqualTo(UPDATED_TITLE_EN);
        assertThat(testLevel.getTitleDE()).isEqualTo(UPDATED_TITLE_DE);
        assertThat(testLevel.getDescriptionEN()).isEqualTo(UPDATED_DESCRIPTION_EN);
        assertThat(testLevel.getDescriptionDE()).isEqualTo(UPDATED_DESCRIPTION_DE);
        assertThat(testLevel.getRequiredScore()).isEqualTo(UPDATED_REQUIRED_SCORE);
        assertThat(testLevel.getInstantMultiplier()).isEqualTo(UPDATED_INSTANT_MULTIPLIER);
        assertThat(testLevel.getCompletionBonus()).isEqualTo(UPDATED_COMPLETION_BONUS);
        assertThat(testLevel.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLevel.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
// ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void putNonExistingLevel() throws Exception {
        int databaseSizeBeforeUpdate = levelRepository.findAll().size();
        level.setId(count.incrementAndGet());

        // Create the Level
        LevelDTO levelDTO = levelMapper.toDto(level);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, levelDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Level in the database
        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
// ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void putWithIdMismatchLevel() throws Exception {
        int databaseSizeBeforeUpdate = levelRepository.findAll().size();
        level.setId(count.incrementAndGet());

        // Create the Level
        LevelDTO levelDTO = levelMapper.toDto(level);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Level in the database
        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
// ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void putWithMissingIdPathParamLevel() throws Exception {
        int databaseSizeBeforeUpdate = levelRepository.findAll().size();
        level.setId(count.incrementAndGet());

        // Create the Level
        LevelDTO levelDTO = levelMapper.toDto(level);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Level in the database
        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
// ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void partialUpdateLevelWithPatch() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        int databaseSizeBeforeUpdate = levelRepository.findAll().size();

        // Update the level using partial update
        Level partialUpdatedLevel = new Level();
        partialUpdatedLevel.setId(level.getId());

        partialUpdatedLevel
            .descriptionEN(UPDATED_DESCRIPTION_EN)
            .descriptionDE(UPDATED_DESCRIPTION_DE)
            .instantMultiplier(UPDATED_INSTANT_MULTIPLIER)
            .createdAt(UPDATED_CREATED_AT);

        restLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLevel.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLevel))
            )
            .andExpect(status().isOk());

        // Validate the Level in the database
        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeUpdate);
        Level testLevel = levelList.get(levelList.size() - 1);
        assertThat(testLevel.getTitleEN()).isEqualTo(DEFAULT_TITLE_EN);
        assertThat(testLevel.getTitleDE()).isEqualTo(DEFAULT_TITLE_DE);
        assertThat(testLevel.getDescriptionEN()).isEqualTo(UPDATED_DESCRIPTION_EN);
        assertThat(testLevel.getDescriptionDE()).isEqualTo(UPDATED_DESCRIPTION_DE);
        assertThat(testLevel.getRequiredScore()).isEqualTo(DEFAULT_REQUIRED_SCORE);
        assertThat(testLevel.getInstantMultiplier()).isEqualTo(UPDATED_INSTANT_MULTIPLIER);
        assertThat(testLevel.getCompletionBonus()).isEqualTo(DEFAULT_COMPLETION_BONUS);
        assertThat(testLevel.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLevel.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
// ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void fullUpdateLevelWithPatch() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        int databaseSizeBeforeUpdate = levelRepository.findAll().size();

        // Update the level using partial update
        Level partialUpdatedLevel = new Level();
        partialUpdatedLevel.setId(level.getId());

        partialUpdatedLevel
            .titleEN(UPDATED_TITLE_EN)
            .titleDE(UPDATED_TITLE_DE)
            .descriptionEN(UPDATED_DESCRIPTION_EN)
            .descriptionDE(UPDATED_DESCRIPTION_DE)
            .requiredScore(UPDATED_REQUIRED_SCORE)
            .instantMultiplier(UPDATED_INSTANT_MULTIPLIER)
            .completionBonus(UPDATED_COMPLETION_BONUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLevel.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLevel))
            )
            .andExpect(status().isOk());

        // Validate the Level in the database
        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeUpdate);
        Level testLevel = levelList.get(levelList.size() - 1);
        assertThat(testLevel.getTitleEN()).isEqualTo(UPDATED_TITLE_EN);
        assertThat(testLevel.getTitleDE()).isEqualTo(UPDATED_TITLE_DE);
        assertThat(testLevel.getDescriptionEN()).isEqualTo(UPDATED_DESCRIPTION_EN);
        assertThat(testLevel.getDescriptionDE()).isEqualTo(UPDATED_DESCRIPTION_DE);
        assertThat(testLevel.getRequiredScore()).isEqualTo(UPDATED_REQUIRED_SCORE);
        assertThat(testLevel.getInstantMultiplier()).isEqualTo(UPDATED_INSTANT_MULTIPLIER);
        assertThat(testLevel.getCompletionBonus()).isEqualTo(UPDATED_COMPLETION_BONUS);
        assertThat(testLevel.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLevel.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
// ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void patchNonExistingLevel() throws Exception {
        int databaseSizeBeforeUpdate = levelRepository.findAll().size();
        level.setId(count.incrementAndGet());

        // Create the Level
        LevelDTO levelDTO = levelMapper.toDto(level);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, levelDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Level in the database
        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
// ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void patchWithIdMismatchLevel() throws Exception {
        int databaseSizeBeforeUpdate = levelRepository.findAll().size();
        level.setId(count.incrementAndGet());

        // Create the Level
        LevelDTO levelDTO = levelMapper.toDto(level);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Level in the database
        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
// ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void patchWithMissingIdPathParamLevel() throws Exception {
        int databaseSizeBeforeUpdate = levelRepository.findAll().size();
        level.setId(count.incrementAndGet());

        // Create the Level
        LevelDTO levelDTO = levelMapper.toDto(level);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Level in the database
        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
// ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void deleteLevel() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        int databaseSizeBeforeDelete = levelRepository.findAll().size();

        // Delete the level
        restLevelMockMvc
            .perform(delete(ENTITY_API_URL_ID, level.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    // ### MODIFICATION-START ###
    @Test
    @Transactional
    @WithMockUser(username = "user", authorities = { "ROLE_USER" })
    void deleteLevelAsUserIsForbidden() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        int databaseSizeBeforeDelete = levelRepository.findAll().size();

        // Delete the level
        restLevelMockMvc
            .perform(delete(ENTITY_API_URL_ID, level.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());

        // Validate the database contains one less item
        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeDelete);
    }

    @Test
    @Transactional
    @WithUnauthenticatedMockUser
    void deleteLevelAsAnonymousUserIsForbidden() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        int databaseSizeBeforeDelete = levelRepository.findAll().size();

        // Delete the level
        restLevelMockMvc
            .perform(delete(ENTITY_API_URL_ID, level.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());

        // Validate the database contains one less item
        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeDelete);
    }
    // ### MODIFICATION-END ###
}
