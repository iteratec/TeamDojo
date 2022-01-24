package com.iteratec.teamdojo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iteratec.teamdojo.IntegrationTest;
import com.iteratec.teamdojo.domain.Dimension;
import com.iteratec.teamdojo.domain.Image;
import com.iteratec.teamdojo.domain.Level;
import com.iteratec.teamdojo.domain.Level;
import com.iteratec.teamdojo.domain.LevelSkill;
import com.iteratec.teamdojo.repository.LevelRepository;
import com.iteratec.teamdojo.service.criteria.LevelCriteria;
// ### MODIFICATION-START ###
import com.iteratec.teamdojo.service.custom.ExtendedLevelService;
// ### MODIFICATION-END ###
import com.iteratec.teamdojo.service.dto.LevelDTO;
import com.iteratec.teamdojo.service.mapper.LevelMapper;
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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LevelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LevelResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

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

    @Autowired
    private LevelMapper levelMapper;

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
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
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
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
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
        assertThat(testLevel.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testLevel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
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
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelRepository.findAll().size();
        // set the field null
        level.setTitle(null);

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
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].requiredScore").value(hasItem(DEFAULT_REQUIRED_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].instantMultiplier").value(hasItem(DEFAULT_INSTANT_MULTIPLIER.doubleValue())))
            .andExpect(jsonPath("$.[*].completionBonus").value(hasItem(DEFAULT_COMPLETION_BONUS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
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
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
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
    void getAllLevelsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where title equals to DEFAULT_TITLE
        defaultLevelShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the levelList where title equals to UPDATED_TITLE
        defaultLevelShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllLevelsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where title not equals to DEFAULT_TITLE
        defaultLevelShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the levelList where title not equals to UPDATED_TITLE
        defaultLevelShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllLevelsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultLevelShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the levelList where title equals to UPDATED_TITLE
        defaultLevelShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllLevelsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where title is not null
        defaultLevelShouldBeFound("title.specified=true");

        // Get all the levelList where title is null
        defaultLevelShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllLevelsByTitleContainsSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where title contains DEFAULT_TITLE
        defaultLevelShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the levelList where title contains UPDATED_TITLE
        defaultLevelShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllLevelsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where title does not contain DEFAULT_TITLE
        defaultLevelShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the levelList where title does not contain UPDATED_TITLE
        defaultLevelShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllLevelsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where description equals to DEFAULT_DESCRIPTION
        defaultLevelShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the levelList where description equals to UPDATED_DESCRIPTION
        defaultLevelShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLevelsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where description not equals to DEFAULT_DESCRIPTION
        defaultLevelShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the levelList where description not equals to UPDATED_DESCRIPTION
        defaultLevelShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLevelsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultLevelShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the levelList where description equals to UPDATED_DESCRIPTION
        defaultLevelShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLevelsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where description is not null
        defaultLevelShouldBeFound("description.specified=true");

        // Get all the levelList where description is null
        defaultLevelShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllLevelsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where description contains DEFAULT_DESCRIPTION
        defaultLevelShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the levelList where description contains UPDATED_DESCRIPTION
        defaultLevelShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLevelsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where description does not contain DEFAULT_DESCRIPTION
        defaultLevelShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the levelList where description does not contain UPDATED_DESCRIPTION
        defaultLevelShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
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
    void getAllLevelsByRequiredScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where requiredScore not equals to DEFAULT_REQUIRED_SCORE
        defaultLevelShouldNotBeFound("requiredScore.notEquals=" + DEFAULT_REQUIRED_SCORE);

        // Get all the levelList where requiredScore not equals to UPDATED_REQUIRED_SCORE
        defaultLevelShouldBeFound("requiredScore.notEquals=" + UPDATED_REQUIRED_SCORE);
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
    void getAllLevelsByInstantMultiplierIsNotEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where instantMultiplier not equals to DEFAULT_INSTANT_MULTIPLIER
        defaultLevelShouldNotBeFound("instantMultiplier.notEquals=" + DEFAULT_INSTANT_MULTIPLIER);

        // Get all the levelList where instantMultiplier not equals to UPDATED_INSTANT_MULTIPLIER
        defaultLevelShouldBeFound("instantMultiplier.notEquals=" + UPDATED_INSTANT_MULTIPLIER);
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
    void getAllLevelsByCompletionBonusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where completionBonus not equals to DEFAULT_COMPLETION_BONUS
        defaultLevelShouldNotBeFound("completionBonus.notEquals=" + DEFAULT_COMPLETION_BONUS);

        // Get all the levelList where completionBonus not equals to UPDATED_COMPLETION_BONUS
        defaultLevelShouldBeFound("completionBonus.notEquals=" + UPDATED_COMPLETION_BONUS);
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
    void getAllLevelsByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where createdAt not equals to DEFAULT_CREATED_AT
        defaultLevelShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the levelList where createdAt not equals to UPDATED_CREATED_AT
        defaultLevelShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
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
    void getAllLevelsByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultLevelShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the levelList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultLevelShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
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
        // Initialize the database
        levelRepository.saveAndFlush(level);
        Level dependsOn = LevelResourceIT.createEntity(em);
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
        // Initialize the database
        levelRepository.saveAndFlush(level);
        LevelSkill skills = LevelSkillResourceIT.createEntity(em);
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
        // Initialize the database
        levelRepository.saveAndFlush(level);
        Image image = ImageResourceIT.createEntity(em);
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
        // Initialize the database
        levelRepository.saveAndFlush(level);
        Dimension dimension = DimensionResourceIT.createEntity(em);
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

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLevelShouldBeFound(String filter) throws Exception {
        restLevelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(level.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
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
    void putNewLevel() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        int databaseSizeBeforeUpdate = levelRepository.findAll().size();

        // Update the level
        Level updatedLevel = levelRepository.findById(level.getId()).get();
        // Disconnect from session so that the updates on updatedLevel are not directly saved in db
        em.detach(updatedLevel);
        updatedLevel
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
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
        assertThat(testLevel.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLevel.getRequiredScore()).isEqualTo(UPDATED_REQUIRED_SCORE);
        assertThat(testLevel.getInstantMultiplier()).isEqualTo(UPDATED_INSTANT_MULTIPLIER);
        assertThat(testLevel.getCompletionBonus()).isEqualTo(UPDATED_COMPLETION_BONUS);
        // ### MODIFICATION-START ###
        assertThat(testLevel.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        // ### MODIFICATION-END ###
        assertThat(testLevel.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
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
    void partialUpdateLevelWithPatch() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        int databaseSizeBeforeUpdate = levelRepository.findAll().size();

        // Update the level using partial update
        Level partialUpdatedLevel = new Level();
        partialUpdatedLevel.setId(level.getId());

        partialUpdatedLevel.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).requiredScore(UPDATED_REQUIRED_SCORE);

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
        assertThat(testLevel.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLevel.getRequiredScore()).isEqualTo(UPDATED_REQUIRED_SCORE);
        assertThat(testLevel.getInstantMultiplier()).isEqualTo(DEFAULT_INSTANT_MULTIPLIER);
        assertThat(testLevel.getCompletionBonus()).isEqualTo(DEFAULT_COMPLETION_BONUS);
        assertThat(testLevel.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLevel.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateLevelWithPatch() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        int databaseSizeBeforeUpdate = levelRepository.findAll().size();

        // Update the level using partial update
        Level partialUpdatedLevel = new Level();
        partialUpdatedLevel.setId(level.getId());

        partialUpdatedLevel
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
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
        assertThat(testLevel.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLevel.getRequiredScore()).isEqualTo(UPDATED_REQUIRED_SCORE);
        assertThat(testLevel.getInstantMultiplier()).isEqualTo(UPDATED_INSTANT_MULTIPLIER);
        assertThat(testLevel.getCompletionBonus()).isEqualTo(UPDATED_COMPLETION_BONUS);
        assertThat(testLevel.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLevel.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
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
}
