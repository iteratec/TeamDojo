package com.iteratec.teamdojo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.IntegrationTest;
import com.iteratec.teamdojo.domain.Badge;
import com.iteratec.teamdojo.domain.Dimension;
import com.iteratec.teamdojo.domain.Level;
import com.iteratec.teamdojo.domain.Team;
import com.iteratec.teamdojo.repository.DimensionRepository;
import com.iteratec.teamdojo.service.criteria.DimensionCriteria;
// ### MODIFICATION-START ###
import com.iteratec.teamdojo.service.custom.ExtendedDimensionService;
// ### MODIFICATION-END ###
import com.iteratec.teamdojo.service.dto.DimensionDTO;
import com.iteratec.teamdojo.service.mapper.DimensionMapper;
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
 * Integration tests for the {@link DimensionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
@GeneratedByJHipster
class DimensionResourceIT {

    private static final String DEFAULT_TITLE_EN = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_EN = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_DE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_DE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_EN = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_EN = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_DE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_DE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    // ### MODIFICATION-START ###
    private static final Instant CUSTOM_CREATED_AND_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    // ### MODIFICATION-END ###

    private static final String ENTITY_API_URL = "/api/dimensions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DimensionRepository dimensionRepository;

    @Autowired
    private DimensionMapper dimensionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDimensionMockMvc;

    // ### MODIFICATION-START ###
    @Autowired
    private ExtendedDimensionService extendedDimensionService;

    // ### MODIFICATION-END ###

    private Dimension dimension;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dimension createEntity(EntityManager em) {
        Dimension dimension = new Dimension()
            .titleEN(DEFAULT_TITLE_EN)
            .titleDE(DEFAULT_TITLE_DE)
            .descriptionEN(DEFAULT_DESCRIPTION_EN)
            .descriptionDE(DEFAULT_DESCRIPTION_DE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return dimension;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dimension createUpdatedEntity(EntityManager em) {
        Dimension dimension = new Dimension()
            .titleEN(UPDATED_TITLE_EN)
            .titleDE(UPDATED_TITLE_DE)
            .descriptionEN(UPDATED_DESCRIPTION_EN)
            .descriptionDE(UPDATED_DESCRIPTION_DE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return dimension;
    }

    @BeforeEach
    public void initTest() {
        dimension = createEntity(em);
    }

    // ### MODIFICATION-START ###
    @BeforeEach
    public void initInstantProvider() {
        extendedDimensionService.setTime(StaticInstantProvider.forFixedTime(CUSTOM_CREATED_AND_UPDATED_AT));
    }

    // ### MODIFICATION-END ###

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void createDimension() throws Exception {
        int databaseSizeBeforeCreate = dimensionRepository.findAll().size();
        // Create the Dimension
        DimensionDTO dimensionDTO = dimensionMapper.toDto(dimension);
        restDimensionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dimensionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeCreate + 1);
        Dimension testDimension = dimensionList.get(dimensionList.size() - 1);
        assertThat(testDimension.getTitleEN()).isEqualTo(DEFAULT_TITLE_EN);
        assertThat(testDimension.getTitleDE()).isEqualTo(DEFAULT_TITLE_DE);
        assertThat(testDimension.getDescriptionEN()).isEqualTo(DEFAULT_DESCRIPTION_EN);
        assertThat(testDimension.getDescriptionDE()).isEqualTo(DEFAULT_DESCRIPTION_DE);
        // ### MODIFICATION-START ###
        assertThat(testDimension.getCreatedAt()).isEqualTo(CUSTOM_CREATED_AND_UPDATED_AT);
        assertThat(testDimension.getUpdatedAt()).isEqualTo(CUSTOM_CREATED_AND_UPDATED_AT);
        // ### MODIFICATION-END ###
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void createDimensionWithExistingId() throws Exception {
        // Create the Dimension with an existing ID
        dimension.setId(1L);
        DimensionDTO dimensionDTO = dimensionMapper.toDto(dimension);

        int databaseSizeBeforeCreate = dimensionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDimensionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dimensionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleENIsRequired() throws Exception {
        int databaseSizeBeforeTest = dimensionRepository.findAll().size();
        // set the field null
        dimension.setTitleEN(null);

        // Create the Dimension, which fails.
        DimensionDTO dimensionDTO = dimensionMapper.toDto(dimension);

        restDimensionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dimensionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @Disabled("Ignored because we removed the validation for this field in the DTO.")
    // ### MODIFICATION-END ###
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = dimensionRepository.findAll().size();
        // set the field null
        dimension.setCreatedAt(null);

        // Create the Dimension, which fails.
        DimensionDTO dimensionDTO = dimensionMapper.toDto(dimension);

        restDimensionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dimensionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @Disabled("Ignored because we removed the validation for this field in the DTO.")
    // ### MODIFICATION-END ###
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = dimensionRepository.findAll().size();
        // set the field null
        dimension.setUpdatedAt(null);

        // Create the Dimension, which fails.
        DimensionDTO dimensionDTO = dimensionMapper.toDto(dimension);

        restDimensionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dimensionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDimensions() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList
        restDimensionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dimension.getId().intValue())))
            .andExpect(jsonPath("$.[*].titleEN").value(hasItem(DEFAULT_TITLE_EN)))
            .andExpect(jsonPath("$.[*].titleDE").value(hasItem(DEFAULT_TITLE_DE)))
            .andExpect(jsonPath("$.[*].descriptionEN").value(hasItem(DEFAULT_DESCRIPTION_EN)))
            .andExpect(jsonPath("$.[*].descriptionDE").value(hasItem(DEFAULT_DESCRIPTION_DE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getDimension() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get the dimension
        restDimensionMockMvc
            .perform(get(ENTITY_API_URL_ID, dimension.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dimension.getId().intValue()))
            .andExpect(jsonPath("$.titleEN").value(DEFAULT_TITLE_EN))
            .andExpect(jsonPath("$.titleDE").value(DEFAULT_TITLE_DE))
            .andExpect(jsonPath("$.descriptionEN").value(DEFAULT_DESCRIPTION_EN))
            .andExpect(jsonPath("$.descriptionDE").value(DEFAULT_DESCRIPTION_DE))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getDimensionsByIdFiltering() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        Long id = dimension.getId();

        defaultDimensionShouldBeFound("id.equals=" + id);
        defaultDimensionShouldNotBeFound("id.notEquals=" + id);

        defaultDimensionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDimensionShouldNotBeFound("id.greaterThan=" + id);

        defaultDimensionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDimensionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDimensionsByTitleENIsEqualToSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where titleEN equals to DEFAULT_TITLE_EN
        defaultDimensionShouldBeFound("titleEN.equals=" + DEFAULT_TITLE_EN);

        // Get all the dimensionList where titleEN equals to UPDATED_TITLE_EN
        defaultDimensionShouldNotBeFound("titleEN.equals=" + UPDATED_TITLE_EN);
    }

    @Test
    @Transactional
    void getAllDimensionsByTitleENIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where titleEN not equals to DEFAULT_TITLE_EN
        defaultDimensionShouldNotBeFound("titleEN.notEquals=" + DEFAULT_TITLE_EN);

        // Get all the dimensionList where titleEN not equals to UPDATED_TITLE_EN
        defaultDimensionShouldBeFound("titleEN.notEquals=" + UPDATED_TITLE_EN);
    }

    @Test
    @Transactional
    void getAllDimensionsByTitleENIsInShouldWork() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where titleEN in DEFAULT_TITLE_EN or UPDATED_TITLE_EN
        defaultDimensionShouldBeFound("titleEN.in=" + DEFAULT_TITLE_EN + "," + UPDATED_TITLE_EN);

        // Get all the dimensionList where titleEN equals to UPDATED_TITLE_EN
        defaultDimensionShouldNotBeFound("titleEN.in=" + UPDATED_TITLE_EN);
    }

    @Test
    @Transactional
    void getAllDimensionsByTitleENIsNullOrNotNull() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where titleEN is not null
        defaultDimensionShouldBeFound("titleEN.specified=true");

        // Get all the dimensionList where titleEN is null
        defaultDimensionShouldNotBeFound("titleEN.specified=false");
    }

    @Test
    @Transactional
    void getAllDimensionsByTitleENContainsSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where titleEN contains DEFAULT_TITLE_EN
        defaultDimensionShouldBeFound("titleEN.contains=" + DEFAULT_TITLE_EN);

        // Get all the dimensionList where titleEN contains UPDATED_TITLE_EN
        defaultDimensionShouldNotBeFound("titleEN.contains=" + UPDATED_TITLE_EN);
    }

    @Test
    @Transactional
    void getAllDimensionsByTitleENNotContainsSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where titleEN does not contain DEFAULT_TITLE_EN
        defaultDimensionShouldNotBeFound("titleEN.doesNotContain=" + DEFAULT_TITLE_EN);

        // Get all the dimensionList where titleEN does not contain UPDATED_TITLE_EN
        defaultDimensionShouldBeFound("titleEN.doesNotContain=" + UPDATED_TITLE_EN);
    }

    @Test
    @Transactional
    void getAllDimensionsByTitleDEIsEqualToSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where titleDE equals to DEFAULT_TITLE_DE
        defaultDimensionShouldBeFound("titleDE.equals=" + DEFAULT_TITLE_DE);

        // Get all the dimensionList where titleDE equals to UPDATED_TITLE_DE
        defaultDimensionShouldNotBeFound("titleDE.equals=" + UPDATED_TITLE_DE);
    }

    @Test
    @Transactional
    void getAllDimensionsByTitleDEIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where titleDE not equals to DEFAULT_TITLE_DE
        defaultDimensionShouldNotBeFound("titleDE.notEquals=" + DEFAULT_TITLE_DE);

        // Get all the dimensionList where titleDE not equals to UPDATED_TITLE_DE
        defaultDimensionShouldBeFound("titleDE.notEquals=" + UPDATED_TITLE_DE);
    }

    @Test
    @Transactional
    void getAllDimensionsByTitleDEIsInShouldWork() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where titleDE in DEFAULT_TITLE_DE or UPDATED_TITLE_DE
        defaultDimensionShouldBeFound("titleDE.in=" + DEFAULT_TITLE_DE + "," + UPDATED_TITLE_DE);

        // Get all the dimensionList where titleDE equals to UPDATED_TITLE_DE
        defaultDimensionShouldNotBeFound("titleDE.in=" + UPDATED_TITLE_DE);
    }

    @Test
    @Transactional
    void getAllDimensionsByTitleDEIsNullOrNotNull() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where titleDE is not null
        defaultDimensionShouldBeFound("titleDE.specified=true");

        // Get all the dimensionList where titleDE is null
        defaultDimensionShouldNotBeFound("titleDE.specified=false");
    }

    @Test
    @Transactional
    void getAllDimensionsByTitleDEContainsSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where titleDE contains DEFAULT_TITLE_DE
        defaultDimensionShouldBeFound("titleDE.contains=" + DEFAULT_TITLE_DE);

        // Get all the dimensionList where titleDE contains UPDATED_TITLE_DE
        defaultDimensionShouldNotBeFound("titleDE.contains=" + UPDATED_TITLE_DE);
    }

    @Test
    @Transactional
    void getAllDimensionsByTitleDENotContainsSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where titleDE does not contain DEFAULT_TITLE_DE
        defaultDimensionShouldNotBeFound("titleDE.doesNotContain=" + DEFAULT_TITLE_DE);

        // Get all the dimensionList where titleDE does not contain UPDATED_TITLE_DE
        defaultDimensionShouldBeFound("titleDE.doesNotContain=" + UPDATED_TITLE_DE);
    }

    @Test
    @Transactional
    void getAllDimensionsByDescriptionENIsEqualToSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where descriptionEN equals to DEFAULT_DESCRIPTION_EN
        defaultDimensionShouldBeFound("descriptionEN.equals=" + DEFAULT_DESCRIPTION_EN);

        // Get all the dimensionList where descriptionEN equals to UPDATED_DESCRIPTION_EN
        defaultDimensionShouldNotBeFound("descriptionEN.equals=" + UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    void getAllDimensionsByDescriptionENIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where descriptionEN not equals to DEFAULT_DESCRIPTION_EN
        defaultDimensionShouldNotBeFound("descriptionEN.notEquals=" + DEFAULT_DESCRIPTION_EN);

        // Get all the dimensionList where descriptionEN not equals to UPDATED_DESCRIPTION_EN
        defaultDimensionShouldBeFound("descriptionEN.notEquals=" + UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    void getAllDimensionsByDescriptionENIsInShouldWork() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where descriptionEN in DEFAULT_DESCRIPTION_EN or UPDATED_DESCRIPTION_EN
        defaultDimensionShouldBeFound("descriptionEN.in=" + DEFAULT_DESCRIPTION_EN + "," + UPDATED_DESCRIPTION_EN);

        // Get all the dimensionList where descriptionEN equals to UPDATED_DESCRIPTION_EN
        defaultDimensionShouldNotBeFound("descriptionEN.in=" + UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    void getAllDimensionsByDescriptionENIsNullOrNotNull() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where descriptionEN is not null
        defaultDimensionShouldBeFound("descriptionEN.specified=true");

        // Get all the dimensionList where descriptionEN is null
        defaultDimensionShouldNotBeFound("descriptionEN.specified=false");
    }

    @Test
    @Transactional
    void getAllDimensionsByDescriptionENContainsSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where descriptionEN contains DEFAULT_DESCRIPTION_EN
        defaultDimensionShouldBeFound("descriptionEN.contains=" + DEFAULT_DESCRIPTION_EN);

        // Get all the dimensionList where descriptionEN contains UPDATED_DESCRIPTION_EN
        defaultDimensionShouldNotBeFound("descriptionEN.contains=" + UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    void getAllDimensionsByDescriptionENNotContainsSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where descriptionEN does not contain DEFAULT_DESCRIPTION_EN
        defaultDimensionShouldNotBeFound("descriptionEN.doesNotContain=" + DEFAULT_DESCRIPTION_EN);

        // Get all the dimensionList where descriptionEN does not contain UPDATED_DESCRIPTION_EN
        defaultDimensionShouldBeFound("descriptionEN.doesNotContain=" + UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    void getAllDimensionsByDescriptionDEIsEqualToSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where descriptionDE equals to DEFAULT_DESCRIPTION_DE
        defaultDimensionShouldBeFound("descriptionDE.equals=" + DEFAULT_DESCRIPTION_DE);

        // Get all the dimensionList where descriptionDE equals to UPDATED_DESCRIPTION_DE
        defaultDimensionShouldNotBeFound("descriptionDE.equals=" + UPDATED_DESCRIPTION_DE);
    }

    @Test
    @Transactional
    void getAllDimensionsByDescriptionDEIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where descriptionDE not equals to DEFAULT_DESCRIPTION_DE
        defaultDimensionShouldNotBeFound("descriptionDE.notEquals=" + DEFAULT_DESCRIPTION_DE);

        // Get all the dimensionList where descriptionDE not equals to UPDATED_DESCRIPTION_DE
        defaultDimensionShouldBeFound("descriptionDE.notEquals=" + UPDATED_DESCRIPTION_DE);
    }

    @Test
    @Transactional
    void getAllDimensionsByDescriptionDEIsInShouldWork() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where descriptionDE in DEFAULT_DESCRIPTION_DE or UPDATED_DESCRIPTION_DE
        defaultDimensionShouldBeFound("descriptionDE.in=" + DEFAULT_DESCRIPTION_DE + "," + UPDATED_DESCRIPTION_DE);

        // Get all the dimensionList where descriptionDE equals to UPDATED_DESCRIPTION_DE
        defaultDimensionShouldNotBeFound("descriptionDE.in=" + UPDATED_DESCRIPTION_DE);
    }

    @Test
    @Transactional
    void getAllDimensionsByDescriptionDEIsNullOrNotNull() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where descriptionDE is not null
        defaultDimensionShouldBeFound("descriptionDE.specified=true");

        // Get all the dimensionList where descriptionDE is null
        defaultDimensionShouldNotBeFound("descriptionDE.specified=false");
    }

    @Test
    @Transactional
    void getAllDimensionsByDescriptionDEContainsSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where descriptionDE contains DEFAULT_DESCRIPTION_DE
        defaultDimensionShouldBeFound("descriptionDE.contains=" + DEFAULT_DESCRIPTION_DE);

        // Get all the dimensionList where descriptionDE contains UPDATED_DESCRIPTION_DE
        defaultDimensionShouldNotBeFound("descriptionDE.contains=" + UPDATED_DESCRIPTION_DE);
    }

    @Test
    @Transactional
    void getAllDimensionsByDescriptionDENotContainsSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where descriptionDE does not contain DEFAULT_DESCRIPTION_DE
        defaultDimensionShouldNotBeFound("descriptionDE.doesNotContain=" + DEFAULT_DESCRIPTION_DE);

        // Get all the dimensionList where descriptionDE does not contain UPDATED_DESCRIPTION_DE
        defaultDimensionShouldBeFound("descriptionDE.doesNotContain=" + UPDATED_DESCRIPTION_DE);
    }

    @Test
    @Transactional
    void getAllDimensionsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where createdAt equals to DEFAULT_CREATED_AT
        defaultDimensionShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the dimensionList where createdAt equals to UPDATED_CREATED_AT
        defaultDimensionShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllDimensionsByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where createdAt not equals to DEFAULT_CREATED_AT
        defaultDimensionShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the dimensionList where createdAt not equals to UPDATED_CREATED_AT
        defaultDimensionShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllDimensionsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultDimensionShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the dimensionList where createdAt equals to UPDATED_CREATED_AT
        defaultDimensionShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllDimensionsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where createdAt is not null
        defaultDimensionShouldBeFound("createdAt.specified=true");

        // Get all the dimensionList where createdAt is null
        defaultDimensionShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllDimensionsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultDimensionShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the dimensionList where updatedAt equals to UPDATED_UPDATED_AT
        defaultDimensionShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllDimensionsByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultDimensionShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the dimensionList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultDimensionShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllDimensionsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultDimensionShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the dimensionList where updatedAt equals to UPDATED_UPDATED_AT
        defaultDimensionShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllDimensionsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where updatedAt is not null
        defaultDimensionShouldBeFound("updatedAt.specified=true");

        // Get all the dimensionList where updatedAt is null
        defaultDimensionShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllDimensionsByLevelsIsEqualToSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);
        Level levels;
        if (TestUtil.findAll(em, Level.class).isEmpty()) {
            levels = LevelResourceIT.createEntity(em);
            em.persist(levels);
            em.flush();
        } else {
            levels = TestUtil.findAll(em, Level.class).get(0);
        }
        em.persist(levels);
        em.flush();
        dimension.addLevels(levels);
        dimensionRepository.saveAndFlush(dimension);
        Long levelsId = levels.getId();

        // Get all the dimensionList where levels equals to levelsId
        defaultDimensionShouldBeFound("levelsId.equals=" + levelsId);

        // Get all the dimensionList where levels equals to (levelsId + 1)
        defaultDimensionShouldNotBeFound("levelsId.equals=" + (levelsId + 1));
    }

    @Test
    @Transactional
    void getAllDimensionsByBadgesIsEqualToSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);
        Badge badges;
        if (TestUtil.findAll(em, Badge.class).isEmpty()) {
            badges = BadgeResourceIT.createEntity(em);
            em.persist(badges);
            em.flush();
        } else {
            badges = TestUtil.findAll(em, Badge.class).get(0);
        }
        em.persist(badges);
        em.flush();
        dimension.addBadges(badges);
        dimensionRepository.saveAndFlush(dimension);
        Long badgesId = badges.getId();

        // Get all the dimensionList where badges equals to badgesId
        defaultDimensionShouldBeFound("badgesId.equals=" + badgesId);

        // Get all the dimensionList where badges equals to (badgesId + 1)
        defaultDimensionShouldNotBeFound("badgesId.equals=" + (badgesId + 1));
    }

    @Test
    @Transactional
    void getAllDimensionsByParticipantsIsEqualToSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);
        Team participants;
        if (TestUtil.findAll(em, Team.class).isEmpty()) {
            participants = TeamResourceIT.createEntity(em);
            em.persist(participants);
            em.flush();
        } else {
            participants = TestUtil.findAll(em, Team.class).get(0);
        }
        em.persist(participants);
        em.flush();
        dimension.addParticipants(participants);
        dimensionRepository.saveAndFlush(dimension);
        Long participantsId = participants.getId();

        // Get all the dimensionList where participants equals to participantsId
        defaultDimensionShouldBeFound("participantsId.equals=" + participantsId);

        // Get all the dimensionList where participants equals to (participantsId + 1)
        defaultDimensionShouldNotBeFound("participantsId.equals=" + (participantsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDimensionShouldBeFound(String filter) throws Exception {
        restDimensionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dimension.getId().intValue())))
            .andExpect(jsonPath("$.[*].titleEN").value(hasItem(DEFAULT_TITLE_EN)))
            .andExpect(jsonPath("$.[*].titleDE").value(hasItem(DEFAULT_TITLE_DE)))
            .andExpect(jsonPath("$.[*].descriptionEN").value(hasItem(DEFAULT_DESCRIPTION_EN)))
            .andExpect(jsonPath("$.[*].descriptionDE").value(hasItem(DEFAULT_DESCRIPTION_DE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));

        // Check, that the count call also returns 1
        restDimensionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDimensionShouldNotBeFound(String filter) throws Exception {
        restDimensionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDimensionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDimension() throws Exception {
        // Get the dimension
        restDimensionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void putNewDimension() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();

        // Update the dimension
        Dimension updatedDimension = dimensionRepository.findById(dimension.getId()).get();
        // Disconnect from session so that the updates on updatedDimension are not directly saved in db
        em.detach(updatedDimension);
        updatedDimension
            .titleEN(UPDATED_TITLE_EN)
            .titleDE(UPDATED_TITLE_DE)
            .descriptionEN(UPDATED_DESCRIPTION_EN)
            .descriptionDE(UPDATED_DESCRIPTION_DE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        DimensionDTO dimensionDTO = dimensionMapper.toDto(updatedDimension);

        restDimensionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dimensionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dimensionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate);
        Dimension testDimension = dimensionList.get(dimensionList.size() - 1);
        assertThat(testDimension.getTitleEN()).isEqualTo(UPDATED_TITLE_EN);
        assertThat(testDimension.getTitleDE()).isEqualTo(UPDATED_TITLE_DE);
        assertThat(testDimension.getDescriptionEN()).isEqualTo(UPDATED_DESCRIPTION_EN);
        assertThat(testDimension.getDescriptionDE()).isEqualTo(UPDATED_DESCRIPTION_DE);
        assertThat(testDimension.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDimension.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void putNonExistingDimension() throws Exception {
        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();
        dimension.setId(count.incrementAndGet());

        // Create the Dimension
        DimensionDTO dimensionDTO = dimensionMapper.toDto(dimension);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDimensionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dimensionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dimensionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void putWithIdMismatchDimension() throws Exception {
        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();
        dimension.setId(count.incrementAndGet());

        // Create the Dimension
        DimensionDTO dimensionDTO = dimensionMapper.toDto(dimension);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDimensionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dimensionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void putWithMissingIdPathParamDimension() throws Exception {
        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();
        dimension.setId(count.incrementAndGet());

        // Create the Dimension
        DimensionDTO dimensionDTO = dimensionMapper.toDto(dimension);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDimensionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dimensionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void partialUpdateDimensionWithPatch() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();

        // Update the dimension using partial update
        Dimension partialUpdatedDimension = new Dimension();
        partialUpdatedDimension.setId(dimension.getId());

        partialUpdatedDimension.updatedAt(UPDATED_UPDATED_AT);

        restDimensionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDimension.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDimension))
            )
            .andExpect(status().isOk());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate);
        Dimension testDimension = dimensionList.get(dimensionList.size() - 1);
        assertThat(testDimension.getTitleEN()).isEqualTo(DEFAULT_TITLE_EN);
        assertThat(testDimension.getTitleDE()).isEqualTo(DEFAULT_TITLE_DE);
        assertThat(testDimension.getDescriptionEN()).isEqualTo(DEFAULT_DESCRIPTION_EN);
        assertThat(testDimension.getDescriptionDE()).isEqualTo(DEFAULT_DESCRIPTION_DE);
        assertThat(testDimension.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testDimension.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void fullUpdateDimensionWithPatch() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();

        // Update the dimension using partial update
        Dimension partialUpdatedDimension = new Dimension();
        partialUpdatedDimension.setId(dimension.getId());

        partialUpdatedDimension
            .titleEN(UPDATED_TITLE_EN)
            .titleDE(UPDATED_TITLE_DE)
            .descriptionEN(UPDATED_DESCRIPTION_EN)
            .descriptionDE(UPDATED_DESCRIPTION_DE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restDimensionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDimension.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDimension))
            )
            .andExpect(status().isOk());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate);
        Dimension testDimension = dimensionList.get(dimensionList.size() - 1);
        assertThat(testDimension.getTitleEN()).isEqualTo(UPDATED_TITLE_EN);
        assertThat(testDimension.getTitleDE()).isEqualTo(UPDATED_TITLE_DE);
        assertThat(testDimension.getDescriptionEN()).isEqualTo(UPDATED_DESCRIPTION_EN);
        assertThat(testDimension.getDescriptionDE()).isEqualTo(UPDATED_DESCRIPTION_DE);
        assertThat(testDimension.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDimension.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void patchNonExistingDimension() throws Exception {
        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();
        dimension.setId(count.incrementAndGet());

        // Create the Dimension
        DimensionDTO dimensionDTO = dimensionMapper.toDto(dimension);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDimensionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dimensionDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dimensionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void patchWithIdMismatchDimension() throws Exception {
        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();
        dimension.setId(count.incrementAndGet());

        // Create the Dimension
        DimensionDTO dimensionDTO = dimensionMapper.toDto(dimension);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDimensionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dimensionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void patchWithMissingIdPathParamDimension() throws Exception {
        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();
        dimension.setId(count.incrementAndGet());

        // Create the Dimension
        DimensionDTO dimensionDTO = dimensionMapper.toDto(dimension);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDimensionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dimensionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @WithMockUser(username = "admin", authorities = { "ROLE_ADMIN" })
    // ### MODIFICATION-END ###
    void deleteDimension() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        int databaseSizeBeforeDelete = dimensionRepository.findAll().size();

        // Delete the dimension
        restDimensionMockMvc
            .perform(delete(ENTITY_API_URL_ID, dimension.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    // ### MODIFICATION-START ###
    @Test
    @Transactional
    @WithMockUser(username = "user", authorities = { "ROLE_USER" })
    void deleteDimensionAsUserIsForbidden() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        int databaseSizeBeforeDelete = dimensionRepository.findAll().size();

        // Delete the dimension
        restDimensionMockMvc
            .perform(delete(ENTITY_API_URL_ID, dimension.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());

        // Validate the database contains one less item
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeDelete);
    }

    @Test
    @Transactional
    @WithUnauthenticatedMockUser
    void deleteDimensionAsAnonymousUserIsForbidden() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        int databaseSizeBeforeDelete = dimensionRepository.findAll().size();

        // Delete the dimension
        restDimensionMockMvc
            .perform(delete(ENTITY_API_URL_ID, dimension.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());

        // Validate the database contains one less item
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeDelete);
    }
    // ### MODIFICATION-END ###
}
