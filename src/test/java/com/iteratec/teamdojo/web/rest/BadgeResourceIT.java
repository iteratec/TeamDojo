package com.iteratec.teamdojo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iteratec.teamdojo.IntegrationTest;
import com.iteratec.teamdojo.domain.Badge;
import com.iteratec.teamdojo.domain.BadgeSkill;
import com.iteratec.teamdojo.domain.Dimension;
import com.iteratec.teamdojo.domain.Image;
import com.iteratec.teamdojo.repository.BadgeRepository;
import com.iteratec.teamdojo.service.BadgeService;
import com.iteratec.teamdojo.service.criteria.BadgeCriteria;
// ### MODIFICATION-START ###
import com.iteratec.teamdojo.service.custom.ExtendedBadgeService;
// ### MODIFICATION-END ###
import com.iteratec.teamdojo.service.dto.BadgeDTO;
import com.iteratec.teamdojo.service.mapper.BadgeMapper;
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
import org.junit.jupiter.api.Disabled;
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
 * Integration tests for the {@link BadgeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BadgeResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_AVAILABLE_UNTIL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_AVAILABLE_UNTIL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_AVAILABLE_AMOUNT = 1;
    private static final Integer UPDATED_AVAILABLE_AMOUNT = 2;
    private static final Integer SMALLER_AVAILABLE_AMOUNT = 1 - 1;

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

    private static final String ENTITY_API_URL = "/api/badges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BadgeRepository badgeRepository;

    @Mock
    private BadgeRepository badgeRepositoryMock;

    @Autowired
    private BadgeMapper badgeMapper;

    @Mock
    private BadgeService badgeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBadgeMockMvc;

    // ### MODIFICATION-START ###
    @Autowired
    private ExtendedBadgeService extendedBadgeService;

    // ### MODIFICATION-END ###

    private Badge badge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Badge createEntity(EntityManager em) {
        Badge badge = new Badge()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .availableUntil(DEFAULT_AVAILABLE_UNTIL)
            .availableAmount(DEFAULT_AVAILABLE_AMOUNT)
            .requiredScore(DEFAULT_REQUIRED_SCORE)
            .instantMultiplier(DEFAULT_INSTANT_MULTIPLIER)
            .completionBonus(DEFAULT_COMPLETION_BONUS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return badge;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Badge createUpdatedEntity(EntityManager em) {
        Badge badge = new Badge()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .availableUntil(UPDATED_AVAILABLE_UNTIL)
            .availableAmount(UPDATED_AVAILABLE_AMOUNT)
            .requiredScore(UPDATED_REQUIRED_SCORE)
            .instantMultiplier(UPDATED_INSTANT_MULTIPLIER)
            .completionBonus(UPDATED_COMPLETION_BONUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return badge;
    }

    @BeforeEach
    public void initTest() {
        badge = createEntity(em);
    }

    // ### MODIFICATION-START ###
    @BeforeEach
    public void initInstantProvider() {
        extendedBadgeService.setTime(StaticInstantProvider.forFixedTime(CUSTOM_CREATED_AND_UPDATED_AT));
    }

    // ### MODIFICATION-END ###

    @Test
    @Transactional
    void createBadge() throws Exception {
        int databaseSizeBeforeCreate = badgeRepository.findAll().size();
        // Create the Badge
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);
        restBadgeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(badgeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Badge in the database
        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeCreate + 1);
        Badge testBadge = badgeList.get(badgeList.size() - 1);
        assertThat(testBadge.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBadge.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBadge.getAvailableUntil()).isEqualTo(DEFAULT_AVAILABLE_UNTIL);
        assertThat(testBadge.getAvailableAmount()).isEqualTo(DEFAULT_AVAILABLE_AMOUNT);
        assertThat(testBadge.getRequiredScore()).isEqualTo(DEFAULT_REQUIRED_SCORE);
        assertThat(testBadge.getInstantMultiplier()).isEqualTo(DEFAULT_INSTANT_MULTIPLIER);
        assertThat(testBadge.getCompletionBonus()).isEqualTo(DEFAULT_COMPLETION_BONUS);
        // ### MODIFICATION-START ###
        assertThat(testBadge.getCreatedAt()).isEqualTo(CUSTOM_CREATED_AND_UPDATED_AT);
        assertThat(testBadge.getUpdatedAt()).isEqualTo(CUSTOM_CREATED_AND_UPDATED_AT);
        // ### MODIFICATION-END ###
    }

    @Test
    @Transactional
    void createBadgeWithExistingId() throws Exception {
        // Create the Badge with an existing ID
        badge.setId(1L);
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        int databaseSizeBeforeCreate = badgeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBadgeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(badgeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Badge in the database
        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = badgeRepository.findAll().size();
        // set the field null
        badge.setTitle(null);

        // Create the Badge, which fails.
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        restBadgeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(badgeDTO))
            )
            .andExpect(status().isBadRequest());

        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRequiredScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = badgeRepository.findAll().size();
        // set the field null
        badge.setRequiredScore(null);

        // Create the Badge, which fails.
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        restBadgeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(badgeDTO))
            )
            .andExpect(status().isBadRequest());

        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInstantMultiplierIsRequired() throws Exception {
        int databaseSizeBeforeTest = badgeRepository.findAll().size();
        // set the field null
        badge.setInstantMultiplier(null);

        // Create the Badge, which fails.
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        restBadgeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(badgeDTO))
            )
            .andExpect(status().isBadRequest());

        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @Disabled("Ignored because we removed the validation for this field in the DTO.")
    // ### MODIFICATION-END ###
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = badgeRepository.findAll().size();
        // set the field null
        badge.setCreatedAt(null);

        // Create the Badge, which fails.
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        restBadgeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(badgeDTO))
            )
            .andExpect(status().isBadRequest());

        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @Disabled("Ignored because we removed the validation for this field in the DTO.")
    // ### MODIFICATION-END ###
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = badgeRepository.findAll().size();
        // set the field null
        badge.setUpdatedAt(null);

        // Create the Badge, which fails.
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        restBadgeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(badgeDTO))
            )
            .andExpect(status().isBadRequest());

        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBadges() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList
        restBadgeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(badge.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].availableUntil").value(hasItem(DEFAULT_AVAILABLE_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].availableAmount").value(hasItem(DEFAULT_AVAILABLE_AMOUNT)))
            .andExpect(jsonPath("$.[*].requiredScore").value(hasItem(DEFAULT_REQUIRED_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].instantMultiplier").value(hasItem(DEFAULT_INSTANT_MULTIPLIER.doubleValue())))
            .andExpect(jsonPath("$.[*].completionBonus").value(hasItem(DEFAULT_COMPLETION_BONUS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBadgesWithEagerRelationshipsIsEnabled() throws Exception {
        when(badgeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBadgeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(badgeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBadgesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(badgeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBadgeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(badgeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getBadge() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get the badge
        restBadgeMockMvc
            .perform(get(ENTITY_API_URL_ID, badge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(badge.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.availableUntil").value(DEFAULT_AVAILABLE_UNTIL.toString()))
            .andExpect(jsonPath("$.availableAmount").value(DEFAULT_AVAILABLE_AMOUNT))
            .andExpect(jsonPath("$.requiredScore").value(DEFAULT_REQUIRED_SCORE.doubleValue()))
            .andExpect(jsonPath("$.instantMultiplier").value(DEFAULT_INSTANT_MULTIPLIER.doubleValue()))
            .andExpect(jsonPath("$.completionBonus").value(DEFAULT_COMPLETION_BONUS))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getBadgesByIdFiltering() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        Long id = badge.getId();

        defaultBadgeShouldBeFound("id.equals=" + id);
        defaultBadgeShouldNotBeFound("id.notEquals=" + id);

        defaultBadgeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBadgeShouldNotBeFound("id.greaterThan=" + id);

        defaultBadgeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBadgeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBadgesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where title equals to DEFAULT_TITLE
        defaultBadgeShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the badgeList where title equals to UPDATED_TITLE
        defaultBadgeShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBadgesByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where title not equals to DEFAULT_TITLE
        defaultBadgeShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the badgeList where title not equals to UPDATED_TITLE
        defaultBadgeShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBadgesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultBadgeShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the badgeList where title equals to UPDATED_TITLE
        defaultBadgeShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBadgesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where title is not null
        defaultBadgeShouldBeFound("title.specified=true");

        // Get all the badgeList where title is null
        defaultBadgeShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllBadgesByTitleContainsSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where title contains DEFAULT_TITLE
        defaultBadgeShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the badgeList where title contains UPDATED_TITLE
        defaultBadgeShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBadgesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where title does not contain DEFAULT_TITLE
        defaultBadgeShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the badgeList where title does not contain UPDATED_TITLE
        defaultBadgeShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBadgesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where description equals to DEFAULT_DESCRIPTION
        defaultBadgeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the badgeList where description equals to UPDATED_DESCRIPTION
        defaultBadgeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBadgesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where description not equals to DEFAULT_DESCRIPTION
        defaultBadgeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the badgeList where description not equals to UPDATED_DESCRIPTION
        defaultBadgeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBadgesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultBadgeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the badgeList where description equals to UPDATED_DESCRIPTION
        defaultBadgeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBadgesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where description is not null
        defaultBadgeShouldBeFound("description.specified=true");

        // Get all the badgeList where description is null
        defaultBadgeShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllBadgesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where description contains DEFAULT_DESCRIPTION
        defaultBadgeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the badgeList where description contains UPDATED_DESCRIPTION
        defaultBadgeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBadgesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where description does not contain DEFAULT_DESCRIPTION
        defaultBadgeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the badgeList where description does not contain UPDATED_DESCRIPTION
        defaultBadgeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBadgesByAvailableUntilIsEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where availableUntil equals to DEFAULT_AVAILABLE_UNTIL
        defaultBadgeShouldBeFound("availableUntil.equals=" + DEFAULT_AVAILABLE_UNTIL);

        // Get all the badgeList where availableUntil equals to UPDATED_AVAILABLE_UNTIL
        defaultBadgeShouldNotBeFound("availableUntil.equals=" + UPDATED_AVAILABLE_UNTIL);
    }

    @Test
    @Transactional
    void getAllBadgesByAvailableUntilIsNotEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where availableUntil not equals to DEFAULT_AVAILABLE_UNTIL
        defaultBadgeShouldNotBeFound("availableUntil.notEquals=" + DEFAULT_AVAILABLE_UNTIL);

        // Get all the badgeList where availableUntil not equals to UPDATED_AVAILABLE_UNTIL
        defaultBadgeShouldBeFound("availableUntil.notEquals=" + UPDATED_AVAILABLE_UNTIL);
    }

    @Test
    @Transactional
    void getAllBadgesByAvailableUntilIsInShouldWork() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where availableUntil in DEFAULT_AVAILABLE_UNTIL or UPDATED_AVAILABLE_UNTIL
        defaultBadgeShouldBeFound("availableUntil.in=" + DEFAULT_AVAILABLE_UNTIL + "," + UPDATED_AVAILABLE_UNTIL);

        // Get all the badgeList where availableUntil equals to UPDATED_AVAILABLE_UNTIL
        defaultBadgeShouldNotBeFound("availableUntil.in=" + UPDATED_AVAILABLE_UNTIL);
    }

    @Test
    @Transactional
    void getAllBadgesByAvailableUntilIsNullOrNotNull() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where availableUntil is not null
        defaultBadgeShouldBeFound("availableUntil.specified=true");

        // Get all the badgeList where availableUntil is null
        defaultBadgeShouldNotBeFound("availableUntil.specified=false");
    }

    @Test
    @Transactional
    void getAllBadgesByAvailableAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where availableAmount equals to DEFAULT_AVAILABLE_AMOUNT
        defaultBadgeShouldBeFound("availableAmount.equals=" + DEFAULT_AVAILABLE_AMOUNT);

        // Get all the badgeList where availableAmount equals to UPDATED_AVAILABLE_AMOUNT
        defaultBadgeShouldNotBeFound("availableAmount.equals=" + UPDATED_AVAILABLE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllBadgesByAvailableAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where availableAmount not equals to DEFAULT_AVAILABLE_AMOUNT
        defaultBadgeShouldNotBeFound("availableAmount.notEquals=" + DEFAULT_AVAILABLE_AMOUNT);

        // Get all the badgeList where availableAmount not equals to UPDATED_AVAILABLE_AMOUNT
        defaultBadgeShouldBeFound("availableAmount.notEquals=" + UPDATED_AVAILABLE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllBadgesByAvailableAmountIsInShouldWork() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where availableAmount in DEFAULT_AVAILABLE_AMOUNT or UPDATED_AVAILABLE_AMOUNT
        defaultBadgeShouldBeFound("availableAmount.in=" + DEFAULT_AVAILABLE_AMOUNT + "," + UPDATED_AVAILABLE_AMOUNT);

        // Get all the badgeList where availableAmount equals to UPDATED_AVAILABLE_AMOUNT
        defaultBadgeShouldNotBeFound("availableAmount.in=" + UPDATED_AVAILABLE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllBadgesByAvailableAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where availableAmount is not null
        defaultBadgeShouldBeFound("availableAmount.specified=true");

        // Get all the badgeList where availableAmount is null
        defaultBadgeShouldNotBeFound("availableAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllBadgesByAvailableAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where availableAmount is greater than or equal to DEFAULT_AVAILABLE_AMOUNT
        defaultBadgeShouldBeFound("availableAmount.greaterThanOrEqual=" + DEFAULT_AVAILABLE_AMOUNT);

        // Get all the badgeList where availableAmount is greater than or equal to UPDATED_AVAILABLE_AMOUNT
        defaultBadgeShouldNotBeFound("availableAmount.greaterThanOrEqual=" + UPDATED_AVAILABLE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllBadgesByAvailableAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where availableAmount is less than or equal to DEFAULT_AVAILABLE_AMOUNT
        defaultBadgeShouldBeFound("availableAmount.lessThanOrEqual=" + DEFAULT_AVAILABLE_AMOUNT);

        // Get all the badgeList where availableAmount is less than or equal to SMALLER_AVAILABLE_AMOUNT
        defaultBadgeShouldNotBeFound("availableAmount.lessThanOrEqual=" + SMALLER_AVAILABLE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllBadgesByAvailableAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where availableAmount is less than DEFAULT_AVAILABLE_AMOUNT
        defaultBadgeShouldNotBeFound("availableAmount.lessThan=" + DEFAULT_AVAILABLE_AMOUNT);

        // Get all the badgeList where availableAmount is less than UPDATED_AVAILABLE_AMOUNT
        defaultBadgeShouldBeFound("availableAmount.lessThan=" + UPDATED_AVAILABLE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllBadgesByAvailableAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where availableAmount is greater than DEFAULT_AVAILABLE_AMOUNT
        defaultBadgeShouldNotBeFound("availableAmount.greaterThan=" + DEFAULT_AVAILABLE_AMOUNT);

        // Get all the badgeList where availableAmount is greater than SMALLER_AVAILABLE_AMOUNT
        defaultBadgeShouldBeFound("availableAmount.greaterThan=" + SMALLER_AVAILABLE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllBadgesByRequiredScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where requiredScore equals to DEFAULT_REQUIRED_SCORE
        defaultBadgeShouldBeFound("requiredScore.equals=" + DEFAULT_REQUIRED_SCORE);

        // Get all the badgeList where requiredScore equals to UPDATED_REQUIRED_SCORE
        defaultBadgeShouldNotBeFound("requiredScore.equals=" + UPDATED_REQUIRED_SCORE);
    }

    @Test
    @Transactional
    void getAllBadgesByRequiredScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where requiredScore not equals to DEFAULT_REQUIRED_SCORE
        defaultBadgeShouldNotBeFound("requiredScore.notEquals=" + DEFAULT_REQUIRED_SCORE);

        // Get all the badgeList where requiredScore not equals to UPDATED_REQUIRED_SCORE
        defaultBadgeShouldBeFound("requiredScore.notEquals=" + UPDATED_REQUIRED_SCORE);
    }

    @Test
    @Transactional
    void getAllBadgesByRequiredScoreIsInShouldWork() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where requiredScore in DEFAULT_REQUIRED_SCORE or UPDATED_REQUIRED_SCORE
        defaultBadgeShouldBeFound("requiredScore.in=" + DEFAULT_REQUIRED_SCORE + "," + UPDATED_REQUIRED_SCORE);

        // Get all the badgeList where requiredScore equals to UPDATED_REQUIRED_SCORE
        defaultBadgeShouldNotBeFound("requiredScore.in=" + UPDATED_REQUIRED_SCORE);
    }

    @Test
    @Transactional
    void getAllBadgesByRequiredScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where requiredScore is not null
        defaultBadgeShouldBeFound("requiredScore.specified=true");

        // Get all the badgeList where requiredScore is null
        defaultBadgeShouldNotBeFound("requiredScore.specified=false");
    }

    @Test
    @Transactional
    void getAllBadgesByRequiredScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where requiredScore is greater than or equal to DEFAULT_REQUIRED_SCORE
        defaultBadgeShouldBeFound("requiredScore.greaterThanOrEqual=" + DEFAULT_REQUIRED_SCORE);

        // Get all the badgeList where requiredScore is greater than or equal to (DEFAULT_REQUIRED_SCORE + 1)
        defaultBadgeShouldNotBeFound("requiredScore.greaterThanOrEqual=" + (DEFAULT_REQUIRED_SCORE + 1));
    }

    @Test
    @Transactional
    void getAllBadgesByRequiredScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where requiredScore is less than or equal to DEFAULT_REQUIRED_SCORE
        defaultBadgeShouldBeFound("requiredScore.lessThanOrEqual=" + DEFAULT_REQUIRED_SCORE);

        // Get all the badgeList where requiredScore is less than or equal to SMALLER_REQUIRED_SCORE
        defaultBadgeShouldNotBeFound("requiredScore.lessThanOrEqual=" + SMALLER_REQUIRED_SCORE);
    }

    @Test
    @Transactional
    void getAllBadgesByRequiredScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where requiredScore is less than DEFAULT_REQUIRED_SCORE
        defaultBadgeShouldNotBeFound("requiredScore.lessThan=" + DEFAULT_REQUIRED_SCORE);

        // Get all the badgeList where requiredScore is less than (DEFAULT_REQUIRED_SCORE + 1)
        defaultBadgeShouldBeFound("requiredScore.lessThan=" + (DEFAULT_REQUIRED_SCORE + 1));
    }

    @Test
    @Transactional
    void getAllBadgesByRequiredScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where requiredScore is greater than DEFAULT_REQUIRED_SCORE
        defaultBadgeShouldNotBeFound("requiredScore.greaterThan=" + DEFAULT_REQUIRED_SCORE);

        // Get all the badgeList where requiredScore is greater than SMALLER_REQUIRED_SCORE
        defaultBadgeShouldBeFound("requiredScore.greaterThan=" + SMALLER_REQUIRED_SCORE);
    }

    @Test
    @Transactional
    void getAllBadgesByInstantMultiplierIsEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where instantMultiplier equals to DEFAULT_INSTANT_MULTIPLIER
        defaultBadgeShouldBeFound("instantMultiplier.equals=" + DEFAULT_INSTANT_MULTIPLIER);

        // Get all the badgeList where instantMultiplier equals to UPDATED_INSTANT_MULTIPLIER
        defaultBadgeShouldNotBeFound("instantMultiplier.equals=" + UPDATED_INSTANT_MULTIPLIER);
    }

    @Test
    @Transactional
    void getAllBadgesByInstantMultiplierIsNotEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where instantMultiplier not equals to DEFAULT_INSTANT_MULTIPLIER
        defaultBadgeShouldNotBeFound("instantMultiplier.notEquals=" + DEFAULT_INSTANT_MULTIPLIER);

        // Get all the badgeList where instantMultiplier not equals to UPDATED_INSTANT_MULTIPLIER
        defaultBadgeShouldBeFound("instantMultiplier.notEquals=" + UPDATED_INSTANT_MULTIPLIER);
    }

    @Test
    @Transactional
    void getAllBadgesByInstantMultiplierIsInShouldWork() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where instantMultiplier in DEFAULT_INSTANT_MULTIPLIER or UPDATED_INSTANT_MULTIPLIER
        defaultBadgeShouldBeFound("instantMultiplier.in=" + DEFAULT_INSTANT_MULTIPLIER + "," + UPDATED_INSTANT_MULTIPLIER);

        // Get all the badgeList where instantMultiplier equals to UPDATED_INSTANT_MULTIPLIER
        defaultBadgeShouldNotBeFound("instantMultiplier.in=" + UPDATED_INSTANT_MULTIPLIER);
    }

    @Test
    @Transactional
    void getAllBadgesByInstantMultiplierIsNullOrNotNull() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where instantMultiplier is not null
        defaultBadgeShouldBeFound("instantMultiplier.specified=true");

        // Get all the badgeList where instantMultiplier is null
        defaultBadgeShouldNotBeFound("instantMultiplier.specified=false");
    }

    @Test
    @Transactional
    void getAllBadgesByInstantMultiplierIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where instantMultiplier is greater than or equal to DEFAULT_INSTANT_MULTIPLIER
        defaultBadgeShouldBeFound("instantMultiplier.greaterThanOrEqual=" + DEFAULT_INSTANT_MULTIPLIER);

        // Get all the badgeList where instantMultiplier is greater than or equal to UPDATED_INSTANT_MULTIPLIER
        defaultBadgeShouldNotBeFound("instantMultiplier.greaterThanOrEqual=" + UPDATED_INSTANT_MULTIPLIER);
    }

    @Test
    @Transactional
    void getAllBadgesByInstantMultiplierIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where instantMultiplier is less than or equal to DEFAULT_INSTANT_MULTIPLIER
        defaultBadgeShouldBeFound("instantMultiplier.lessThanOrEqual=" + DEFAULT_INSTANT_MULTIPLIER);

        // Get all the badgeList where instantMultiplier is less than or equal to SMALLER_INSTANT_MULTIPLIER
        defaultBadgeShouldNotBeFound("instantMultiplier.lessThanOrEqual=" + SMALLER_INSTANT_MULTIPLIER);
    }

    @Test
    @Transactional
    void getAllBadgesByInstantMultiplierIsLessThanSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where instantMultiplier is less than DEFAULT_INSTANT_MULTIPLIER
        defaultBadgeShouldNotBeFound("instantMultiplier.lessThan=" + DEFAULT_INSTANT_MULTIPLIER);

        // Get all the badgeList where instantMultiplier is less than UPDATED_INSTANT_MULTIPLIER
        defaultBadgeShouldBeFound("instantMultiplier.lessThan=" + UPDATED_INSTANT_MULTIPLIER);
    }

    @Test
    @Transactional
    void getAllBadgesByInstantMultiplierIsGreaterThanSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where instantMultiplier is greater than DEFAULT_INSTANT_MULTIPLIER
        defaultBadgeShouldNotBeFound("instantMultiplier.greaterThan=" + DEFAULT_INSTANT_MULTIPLIER);

        // Get all the badgeList where instantMultiplier is greater than SMALLER_INSTANT_MULTIPLIER
        defaultBadgeShouldBeFound("instantMultiplier.greaterThan=" + SMALLER_INSTANT_MULTIPLIER);
    }

    @Test
    @Transactional
    void getAllBadgesByCompletionBonusIsEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where completionBonus equals to DEFAULT_COMPLETION_BONUS
        defaultBadgeShouldBeFound("completionBonus.equals=" + DEFAULT_COMPLETION_BONUS);

        // Get all the badgeList where completionBonus equals to UPDATED_COMPLETION_BONUS
        defaultBadgeShouldNotBeFound("completionBonus.equals=" + UPDATED_COMPLETION_BONUS);
    }

    @Test
    @Transactional
    void getAllBadgesByCompletionBonusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where completionBonus not equals to DEFAULT_COMPLETION_BONUS
        defaultBadgeShouldNotBeFound("completionBonus.notEquals=" + DEFAULT_COMPLETION_BONUS);

        // Get all the badgeList where completionBonus not equals to UPDATED_COMPLETION_BONUS
        defaultBadgeShouldBeFound("completionBonus.notEquals=" + UPDATED_COMPLETION_BONUS);
    }

    @Test
    @Transactional
    void getAllBadgesByCompletionBonusIsInShouldWork() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where completionBonus in DEFAULT_COMPLETION_BONUS or UPDATED_COMPLETION_BONUS
        defaultBadgeShouldBeFound("completionBonus.in=" + DEFAULT_COMPLETION_BONUS + "," + UPDATED_COMPLETION_BONUS);

        // Get all the badgeList where completionBonus equals to UPDATED_COMPLETION_BONUS
        defaultBadgeShouldNotBeFound("completionBonus.in=" + UPDATED_COMPLETION_BONUS);
    }

    @Test
    @Transactional
    void getAllBadgesByCompletionBonusIsNullOrNotNull() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where completionBonus is not null
        defaultBadgeShouldBeFound("completionBonus.specified=true");

        // Get all the badgeList where completionBonus is null
        defaultBadgeShouldNotBeFound("completionBonus.specified=false");
    }

    @Test
    @Transactional
    void getAllBadgesByCompletionBonusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where completionBonus is greater than or equal to DEFAULT_COMPLETION_BONUS
        defaultBadgeShouldBeFound("completionBonus.greaterThanOrEqual=" + DEFAULT_COMPLETION_BONUS);

        // Get all the badgeList where completionBonus is greater than or equal to UPDATED_COMPLETION_BONUS
        defaultBadgeShouldNotBeFound("completionBonus.greaterThanOrEqual=" + UPDATED_COMPLETION_BONUS);
    }

    @Test
    @Transactional
    void getAllBadgesByCompletionBonusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where completionBonus is less than or equal to DEFAULT_COMPLETION_BONUS
        defaultBadgeShouldBeFound("completionBonus.lessThanOrEqual=" + DEFAULT_COMPLETION_BONUS);

        // Get all the badgeList where completionBonus is less than or equal to SMALLER_COMPLETION_BONUS
        defaultBadgeShouldNotBeFound("completionBonus.lessThanOrEqual=" + SMALLER_COMPLETION_BONUS);
    }

    @Test
    @Transactional
    void getAllBadgesByCompletionBonusIsLessThanSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where completionBonus is less than DEFAULT_COMPLETION_BONUS
        defaultBadgeShouldNotBeFound("completionBonus.lessThan=" + DEFAULT_COMPLETION_BONUS);

        // Get all the badgeList where completionBonus is less than UPDATED_COMPLETION_BONUS
        defaultBadgeShouldBeFound("completionBonus.lessThan=" + UPDATED_COMPLETION_BONUS);
    }

    @Test
    @Transactional
    void getAllBadgesByCompletionBonusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where completionBonus is greater than DEFAULT_COMPLETION_BONUS
        defaultBadgeShouldNotBeFound("completionBonus.greaterThan=" + DEFAULT_COMPLETION_BONUS);

        // Get all the badgeList where completionBonus is greater than SMALLER_COMPLETION_BONUS
        defaultBadgeShouldBeFound("completionBonus.greaterThan=" + SMALLER_COMPLETION_BONUS);
    }

    @Test
    @Transactional
    void getAllBadgesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where createdAt equals to DEFAULT_CREATED_AT
        defaultBadgeShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the badgeList where createdAt equals to UPDATED_CREATED_AT
        defaultBadgeShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllBadgesByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where createdAt not equals to DEFAULT_CREATED_AT
        defaultBadgeShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the badgeList where createdAt not equals to UPDATED_CREATED_AT
        defaultBadgeShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllBadgesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultBadgeShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the badgeList where createdAt equals to UPDATED_CREATED_AT
        defaultBadgeShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllBadgesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where createdAt is not null
        defaultBadgeShouldBeFound("createdAt.specified=true");

        // Get all the badgeList where createdAt is null
        defaultBadgeShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllBadgesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultBadgeShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the badgeList where updatedAt equals to UPDATED_UPDATED_AT
        defaultBadgeShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllBadgesByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultBadgeShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the badgeList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultBadgeShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllBadgesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultBadgeShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the badgeList where updatedAt equals to UPDATED_UPDATED_AT
        defaultBadgeShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllBadgesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where updatedAt is not null
        defaultBadgeShouldBeFound("updatedAt.specified=true");

        // Get all the badgeList where updatedAt is null
        defaultBadgeShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllBadgesBySkillsIsEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);
        BadgeSkill skills;
        if (TestUtil.findAll(em, BadgeSkill.class).isEmpty()) {
            skills = BadgeSkillResourceIT.createEntity(em);
            em.persist(skills);
            em.flush();
        } else {
            skills = TestUtil.findAll(em, BadgeSkill.class).get(0);
        }
        em.persist(skills);
        em.flush();
        badge.addSkills(skills);
        badgeRepository.saveAndFlush(badge);
        Long skillsId = skills.getId();

        // Get all the badgeList where skills equals to skillsId
        defaultBadgeShouldBeFound("skillsId.equals=" + skillsId);

        // Get all the badgeList where skills equals to (skillsId + 1)
        defaultBadgeShouldNotBeFound("skillsId.equals=" + (skillsId + 1));
    }

    @Test
    @Transactional
    void getAllBadgesByImageIsEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);
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
        badge.setImage(image);
        badgeRepository.saveAndFlush(badge);
        Long imageId = image.getId();

        // Get all the badgeList where image equals to imageId
        defaultBadgeShouldBeFound("imageId.equals=" + imageId);

        // Get all the badgeList where image equals to (imageId + 1)
        defaultBadgeShouldNotBeFound("imageId.equals=" + (imageId + 1));
    }

    @Test
    @Transactional
    void getAllBadgesByDimensionsIsEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);
        Dimension dimensions;
        if (TestUtil.findAll(em, Dimension.class).isEmpty()) {
            dimensions = DimensionResourceIT.createEntity(em);
            em.persist(dimensions);
            em.flush();
        } else {
            dimensions = TestUtil.findAll(em, Dimension.class).get(0);
        }
        em.persist(dimensions);
        em.flush();
        badge.addDimensions(dimensions);
        badgeRepository.saveAndFlush(badge);
        Long dimensionsId = dimensions.getId();

        // Get all the badgeList where dimensions equals to dimensionsId
        defaultBadgeShouldBeFound("dimensionsId.equals=" + dimensionsId);

        // Get all the badgeList where dimensions equals to (dimensionsId + 1)
        defaultBadgeShouldNotBeFound("dimensionsId.equals=" + (dimensionsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBadgeShouldBeFound(String filter) throws Exception {
        restBadgeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(badge.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].availableUntil").value(hasItem(DEFAULT_AVAILABLE_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].availableAmount").value(hasItem(DEFAULT_AVAILABLE_AMOUNT)))
            .andExpect(jsonPath("$.[*].requiredScore").value(hasItem(DEFAULT_REQUIRED_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].instantMultiplier").value(hasItem(DEFAULT_INSTANT_MULTIPLIER.doubleValue())))
            .andExpect(jsonPath("$.[*].completionBonus").value(hasItem(DEFAULT_COMPLETION_BONUS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));

        // Check, that the count call also returns 1
        restBadgeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBadgeShouldNotBeFound(String filter) throws Exception {
        restBadgeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBadgeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBadge() throws Exception {
        // Get the badge
        restBadgeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBadge() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        int databaseSizeBeforeUpdate = badgeRepository.findAll().size();

        // Update the badge
        Badge updatedBadge = badgeRepository.findById(badge.getId()).get();
        // Disconnect from session so that the updates on updatedBadge are not directly saved in db
        em.detach(updatedBadge);
        updatedBadge
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .availableUntil(UPDATED_AVAILABLE_UNTIL)
            .availableAmount(UPDATED_AVAILABLE_AMOUNT)
            .requiredScore(UPDATED_REQUIRED_SCORE)
            .instantMultiplier(UPDATED_INSTANT_MULTIPLIER)
            .completionBonus(UPDATED_COMPLETION_BONUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        BadgeDTO badgeDTO = badgeMapper.toDto(updatedBadge);

        restBadgeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, badgeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(badgeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Badge in the database
        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeUpdate);
        Badge testBadge = badgeList.get(badgeList.size() - 1);
        assertThat(testBadge.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBadge.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBadge.getAvailableUntil()).isEqualTo(UPDATED_AVAILABLE_UNTIL);
        assertThat(testBadge.getAvailableAmount()).isEqualTo(UPDATED_AVAILABLE_AMOUNT);
        assertThat(testBadge.getRequiredScore()).isEqualTo(UPDATED_REQUIRED_SCORE);
        assertThat(testBadge.getInstantMultiplier()).isEqualTo(UPDATED_INSTANT_MULTIPLIER);
        assertThat(testBadge.getCompletionBonus()).isEqualTo(UPDATED_COMPLETION_BONUS);
        // ### MODIFICATION-START ###
        assertThat(testBadge.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        // ### MODIFICATION-END ###
        assertThat(testBadge.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingBadge() throws Exception {
        int databaseSizeBeforeUpdate = badgeRepository.findAll().size();
        badge.setId(count.incrementAndGet());

        // Create the Badge
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBadgeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, badgeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(badgeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Badge in the database
        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBadge() throws Exception {
        int databaseSizeBeforeUpdate = badgeRepository.findAll().size();
        badge.setId(count.incrementAndGet());

        // Create the Badge
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBadgeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(badgeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Badge in the database
        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBadge() throws Exception {
        int databaseSizeBeforeUpdate = badgeRepository.findAll().size();
        badge.setId(count.incrementAndGet());

        // Create the Badge
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBadgeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(badgeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Badge in the database
        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBadgeWithPatch() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        int databaseSizeBeforeUpdate = badgeRepository.findAll().size();

        // Update the badge using partial update
        Badge partialUpdatedBadge = new Badge();
        partialUpdatedBadge.setId(badge.getId());

        partialUpdatedBadge
            .description(UPDATED_DESCRIPTION)
            .availableAmount(UPDATED_AVAILABLE_AMOUNT)
            .requiredScore(UPDATED_REQUIRED_SCORE)
            .instantMultiplier(UPDATED_INSTANT_MULTIPLIER)
            .completionBonus(UPDATED_COMPLETION_BONUS);

        restBadgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBadge.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBadge))
            )
            .andExpect(status().isOk());

        // Validate the Badge in the database
        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeUpdate);
        Badge testBadge = badgeList.get(badgeList.size() - 1);
        assertThat(testBadge.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBadge.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBadge.getAvailableUntil()).isEqualTo(DEFAULT_AVAILABLE_UNTIL);
        assertThat(testBadge.getAvailableAmount()).isEqualTo(UPDATED_AVAILABLE_AMOUNT);
        assertThat(testBadge.getRequiredScore()).isEqualTo(UPDATED_REQUIRED_SCORE);
        assertThat(testBadge.getInstantMultiplier()).isEqualTo(UPDATED_INSTANT_MULTIPLIER);
        assertThat(testBadge.getCompletionBonus()).isEqualTo(UPDATED_COMPLETION_BONUS);
        assertThat(testBadge.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testBadge.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateBadgeWithPatch() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        int databaseSizeBeforeUpdate = badgeRepository.findAll().size();

        // Update the badge using partial update
        Badge partialUpdatedBadge = new Badge();
        partialUpdatedBadge.setId(badge.getId());

        partialUpdatedBadge
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .availableUntil(UPDATED_AVAILABLE_UNTIL)
            .availableAmount(UPDATED_AVAILABLE_AMOUNT)
            .requiredScore(UPDATED_REQUIRED_SCORE)
            .instantMultiplier(UPDATED_INSTANT_MULTIPLIER)
            .completionBonus(UPDATED_COMPLETION_BONUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restBadgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBadge.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBadge))
            )
            .andExpect(status().isOk());

        // Validate the Badge in the database
        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeUpdate);
        Badge testBadge = badgeList.get(badgeList.size() - 1);
        assertThat(testBadge.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBadge.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBadge.getAvailableUntil()).isEqualTo(UPDATED_AVAILABLE_UNTIL);
        assertThat(testBadge.getAvailableAmount()).isEqualTo(UPDATED_AVAILABLE_AMOUNT);
        assertThat(testBadge.getRequiredScore()).isEqualTo(UPDATED_REQUIRED_SCORE);
        assertThat(testBadge.getInstantMultiplier()).isEqualTo(UPDATED_INSTANT_MULTIPLIER);
        assertThat(testBadge.getCompletionBonus()).isEqualTo(UPDATED_COMPLETION_BONUS);
        assertThat(testBadge.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testBadge.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingBadge() throws Exception {
        int databaseSizeBeforeUpdate = badgeRepository.findAll().size();
        badge.setId(count.incrementAndGet());

        // Create the Badge
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBadgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, badgeDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(badgeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Badge in the database
        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBadge() throws Exception {
        int databaseSizeBeforeUpdate = badgeRepository.findAll().size();
        badge.setId(count.incrementAndGet());

        // Create the Badge
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBadgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(badgeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Badge in the database
        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBadge() throws Exception {
        int databaseSizeBeforeUpdate = badgeRepository.findAll().size();
        badge.setId(count.incrementAndGet());

        // Create the Badge
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBadgeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(badgeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Badge in the database
        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBadge() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        int databaseSizeBeforeDelete = badgeRepository.findAll().size();

        // Delete the badge
        restBadgeMockMvc
            .perform(delete(ENTITY_API_URL_ID, badge.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
