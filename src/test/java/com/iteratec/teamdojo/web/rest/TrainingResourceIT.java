package com.iteratec.teamdojo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.IntegrationTest;
import com.iteratec.teamdojo.domain.Skill;
import com.iteratec.teamdojo.domain.Training;
import com.iteratec.teamdojo.repository.TrainingRepository;
import com.iteratec.teamdojo.service.TrainingService;
import com.iteratec.teamdojo.service.criteria.TrainingCriteria;
// ### MODIFICATION-START ###
import com.iteratec.teamdojo.service.custom.ExtendedTrainingService;
// ### MODIFICATION-END ###
import com.iteratec.teamdojo.service.dto.TrainingDTO;
import com.iteratec.teamdojo.service.mapper.TrainingMapper;
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
 * Integration tests for the {@link TrainingResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
@GeneratedByJHipster
class TrainingResourceIT {

    private static final String DEFAULT_TITLE_EN = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_EN = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_DE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_DE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_EN = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_EN = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_DE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_DE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_UNTIL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_UNTIL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_OFFICIAL = false;
    private static final Boolean UPDATED_IS_OFFICIAL = true;

    private static final String DEFAULT_SUGGESTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_SUGGESTED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    // ### MODIFICATION-START ###
    private static final Instant CUSTOM_CREATED_AND_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    // ### MODIFICATION-END ###

    private static final String ENTITY_API_URL = "/api/trainings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TrainingRepository trainingRepository;

    @Mock
    private TrainingRepository trainingRepositoryMock;

    @Autowired
    private TrainingMapper trainingMapper;

    @Mock
    private TrainingService trainingServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrainingMockMvc;

    // ### MODIFICATION-START ###
    @Autowired
    private ExtendedTrainingService extendedTrainingService;

    // ### MODIFICATION-END ###

    private Training training;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Training createEntity(EntityManager em) {
        Training training = new Training()
            .titleEN(DEFAULT_TITLE_EN)
            .titleDE(DEFAULT_TITLE_DE)
            .descriptionEN(DEFAULT_DESCRIPTION_EN)
            .descriptionDE(DEFAULT_DESCRIPTION_DE)
            .contact(DEFAULT_CONTACT)
            .link(DEFAULT_LINK)
            .validUntil(DEFAULT_VALID_UNTIL)
            .isOfficial(DEFAULT_IS_OFFICIAL)
            .suggestedBy(DEFAULT_SUGGESTED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return training;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Training createUpdatedEntity(EntityManager em) {
        Training training = new Training()
            .titleEN(UPDATED_TITLE_EN)
            .titleDE(UPDATED_TITLE_DE)
            .descriptionEN(UPDATED_DESCRIPTION_EN)
            .descriptionDE(UPDATED_DESCRIPTION_DE)
            .contact(UPDATED_CONTACT)
            .link(UPDATED_LINK)
            .validUntil(UPDATED_VALID_UNTIL)
            .isOfficial(UPDATED_IS_OFFICIAL)
            .suggestedBy(UPDATED_SUGGESTED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return training;
    }

    @BeforeEach
    public void initTest() {
        training = createEntity(em);
    }

    // ### MODIFICATION-START ###
    @BeforeEach
    public void initInstantProvider() {
        extendedTrainingService.setTime(StaticInstantProvider.forFixedTime(CUSTOM_CREATED_AND_UPDATED_AT));
    }

    // ### MODIFICATION-END ###

    @Test
    @Transactional
    void createTraining() throws Exception {
        int databaseSizeBeforeCreate = trainingRepository.findAll().size();
        // Create the Training
        TrainingDTO trainingDTO = trainingMapper.toDto(training);
        restTrainingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainingDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeCreate + 1);
        Training testTraining = trainingList.get(trainingList.size() - 1);
        assertThat(testTraining.getTitleEN()).isEqualTo(DEFAULT_TITLE_EN);
        assertThat(testTraining.getTitleDE()).isEqualTo(DEFAULT_TITLE_DE);
        assertThat(testTraining.getDescriptionEN()).isEqualTo(DEFAULT_DESCRIPTION_EN);
        assertThat(testTraining.getDescriptionDE()).isEqualTo(DEFAULT_DESCRIPTION_DE);
        assertThat(testTraining.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testTraining.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testTraining.getValidUntil()).isEqualTo(DEFAULT_VALID_UNTIL);
        assertThat(testTraining.getIsOfficial()).isEqualTo(DEFAULT_IS_OFFICIAL);
        assertThat(testTraining.getSuggestedBy()).isEqualTo(DEFAULT_SUGGESTED_BY);
        // ### MODIFICATION-START ###
        assertThat(testTraining.getCreatedAt()).isEqualTo(CUSTOM_CREATED_AND_UPDATED_AT);
        assertThat(testTraining.getUpdatedAt()).isEqualTo(CUSTOM_CREATED_AND_UPDATED_AT);
        // ### MODIFICATION-END ###
    }

    @Test
    @Transactional
    void createTrainingWithExistingId() throws Exception {
        // Create the Training with an existing ID
        training.setId(1L);
        TrainingDTO trainingDTO = trainingMapper.toDto(training);

        int databaseSizeBeforeCreate = trainingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleENIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingRepository.findAll().size();
        // set the field null
        training.setTitleEN(null);

        // Create the Training, which fails.
        TrainingDTO trainingDTO = trainingMapper.toDto(training);

        restTrainingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainingDTO))
            )
            .andExpect(status().isBadRequest());

        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsOfficialIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingRepository.findAll().size();
        // set the field null
        training.setIsOfficial(null);

        // Create the Training, which fails.
        TrainingDTO trainingDTO = trainingMapper.toDto(training);

        restTrainingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainingDTO))
            )
            .andExpect(status().isBadRequest());

        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @Disabled("Ignored because we removed the validation for this field in the DTO.")
    // ### MODIFICATION-END ###
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingRepository.findAll().size();
        // set the field null
        training.setCreatedAt(null);

        // Create the Training, which fails.
        TrainingDTO trainingDTO = trainingMapper.toDto(training);

        restTrainingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainingDTO))
            )
            .andExpect(status().isBadRequest());

        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @Disabled("Ignored because we removed the validation for this field in the DTO.")
    // ### MODIFICATION-END ###
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingRepository.findAll().size();
        // set the field null
        training.setUpdatedAt(null);

        // Create the Training, which fails.
        TrainingDTO trainingDTO = trainingMapper.toDto(training);

        restTrainingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainingDTO))
            )
            .andExpect(status().isBadRequest());

        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTrainings() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList
        restTrainingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(training.getId().intValue())))
            .andExpect(jsonPath("$.[*].titleEN").value(hasItem(DEFAULT_TITLE_EN)))
            .andExpect(jsonPath("$.[*].titleDE").value(hasItem(DEFAULT_TITLE_DE)))
            .andExpect(jsonPath("$.[*].descriptionEN").value(hasItem(DEFAULT_DESCRIPTION_EN)))
            .andExpect(jsonPath("$.[*].descriptionDE").value(hasItem(DEFAULT_DESCRIPTION_DE)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].validUntil").value(hasItem(DEFAULT_VALID_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].isOfficial").value(hasItem(DEFAULT_IS_OFFICIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].suggestedBy").value(hasItem(DEFAULT_SUGGESTED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTrainingsWithEagerRelationshipsIsEnabled() throws Exception {
        when(trainingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTrainingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(trainingServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTrainingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(trainingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTrainingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(trainingServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get the training
        restTrainingMockMvc
            .perform(get(ENTITY_API_URL_ID, training.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(training.getId().intValue()))
            .andExpect(jsonPath("$.titleEN").value(DEFAULT_TITLE_EN))
            .andExpect(jsonPath("$.titleDE").value(DEFAULT_TITLE_DE))
            .andExpect(jsonPath("$.descriptionEN").value(DEFAULT_DESCRIPTION_EN))
            .andExpect(jsonPath("$.descriptionDE").value(DEFAULT_DESCRIPTION_DE))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK))
            .andExpect(jsonPath("$.validUntil").value(DEFAULT_VALID_UNTIL.toString()))
            .andExpect(jsonPath("$.isOfficial").value(DEFAULT_IS_OFFICIAL.booleanValue()))
            .andExpect(jsonPath("$.suggestedBy").value(DEFAULT_SUGGESTED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getTrainingsByIdFiltering() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        Long id = training.getId();

        defaultTrainingShouldBeFound("id.equals=" + id);
        defaultTrainingShouldNotBeFound("id.notEquals=" + id);

        defaultTrainingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTrainingShouldNotBeFound("id.greaterThan=" + id);

        defaultTrainingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTrainingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTrainingsByTitleENIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where titleEN equals to DEFAULT_TITLE_EN
        defaultTrainingShouldBeFound("titleEN.equals=" + DEFAULT_TITLE_EN);

        // Get all the trainingList where titleEN equals to UPDATED_TITLE_EN
        defaultTrainingShouldNotBeFound("titleEN.equals=" + UPDATED_TITLE_EN);
    }

    @Test
    @Transactional
    void getAllTrainingsByTitleENIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where titleEN not equals to DEFAULT_TITLE_EN
        defaultTrainingShouldNotBeFound("titleEN.notEquals=" + DEFAULT_TITLE_EN);

        // Get all the trainingList where titleEN not equals to UPDATED_TITLE_EN
        defaultTrainingShouldBeFound("titleEN.notEquals=" + UPDATED_TITLE_EN);
    }

    @Test
    @Transactional
    void getAllTrainingsByTitleENIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where titleEN in DEFAULT_TITLE_EN or UPDATED_TITLE_EN
        defaultTrainingShouldBeFound("titleEN.in=" + DEFAULT_TITLE_EN + "," + UPDATED_TITLE_EN);

        // Get all the trainingList where titleEN equals to UPDATED_TITLE_EN
        defaultTrainingShouldNotBeFound("titleEN.in=" + UPDATED_TITLE_EN);
    }

    @Test
    @Transactional
    void getAllTrainingsByTitleENIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where titleEN is not null
        defaultTrainingShouldBeFound("titleEN.specified=true");

        // Get all the trainingList where titleEN is null
        defaultTrainingShouldNotBeFound("titleEN.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByTitleENContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where titleEN contains DEFAULT_TITLE_EN
        defaultTrainingShouldBeFound("titleEN.contains=" + DEFAULT_TITLE_EN);

        // Get all the trainingList where titleEN contains UPDATED_TITLE_EN
        defaultTrainingShouldNotBeFound("titleEN.contains=" + UPDATED_TITLE_EN);
    }

    @Test
    @Transactional
    void getAllTrainingsByTitleENNotContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where titleEN does not contain DEFAULT_TITLE_EN
        defaultTrainingShouldNotBeFound("titleEN.doesNotContain=" + DEFAULT_TITLE_EN);

        // Get all the trainingList where titleEN does not contain UPDATED_TITLE_EN
        defaultTrainingShouldBeFound("titleEN.doesNotContain=" + UPDATED_TITLE_EN);
    }

    @Test
    @Transactional
    void getAllTrainingsByTitleDEIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where titleDE equals to DEFAULT_TITLE_DE
        defaultTrainingShouldBeFound("titleDE.equals=" + DEFAULT_TITLE_DE);

        // Get all the trainingList where titleDE equals to UPDATED_TITLE_DE
        defaultTrainingShouldNotBeFound("titleDE.equals=" + UPDATED_TITLE_DE);
    }

    @Test
    @Transactional
    void getAllTrainingsByTitleDEIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where titleDE not equals to DEFAULT_TITLE_DE
        defaultTrainingShouldNotBeFound("titleDE.notEquals=" + DEFAULT_TITLE_DE);

        // Get all the trainingList where titleDE not equals to UPDATED_TITLE_DE
        defaultTrainingShouldBeFound("titleDE.notEquals=" + UPDATED_TITLE_DE);
    }

    @Test
    @Transactional
    void getAllTrainingsByTitleDEIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where titleDE in DEFAULT_TITLE_DE or UPDATED_TITLE_DE
        defaultTrainingShouldBeFound("titleDE.in=" + DEFAULT_TITLE_DE + "," + UPDATED_TITLE_DE);

        // Get all the trainingList where titleDE equals to UPDATED_TITLE_DE
        defaultTrainingShouldNotBeFound("titleDE.in=" + UPDATED_TITLE_DE);
    }

    @Test
    @Transactional
    void getAllTrainingsByTitleDEIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where titleDE is not null
        defaultTrainingShouldBeFound("titleDE.specified=true");

        // Get all the trainingList where titleDE is null
        defaultTrainingShouldNotBeFound("titleDE.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByTitleDEContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where titleDE contains DEFAULT_TITLE_DE
        defaultTrainingShouldBeFound("titleDE.contains=" + DEFAULT_TITLE_DE);

        // Get all the trainingList where titleDE contains UPDATED_TITLE_DE
        defaultTrainingShouldNotBeFound("titleDE.contains=" + UPDATED_TITLE_DE);
    }

    @Test
    @Transactional
    void getAllTrainingsByTitleDENotContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where titleDE does not contain DEFAULT_TITLE_DE
        defaultTrainingShouldNotBeFound("titleDE.doesNotContain=" + DEFAULT_TITLE_DE);

        // Get all the trainingList where titleDE does not contain UPDATED_TITLE_DE
        defaultTrainingShouldBeFound("titleDE.doesNotContain=" + UPDATED_TITLE_DE);
    }

    @Test
    @Transactional
    void getAllTrainingsByDescriptionENIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where descriptionEN equals to DEFAULT_DESCRIPTION_EN
        defaultTrainingShouldBeFound("descriptionEN.equals=" + DEFAULT_DESCRIPTION_EN);

        // Get all the trainingList where descriptionEN equals to UPDATED_DESCRIPTION_EN
        defaultTrainingShouldNotBeFound("descriptionEN.equals=" + UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    void getAllTrainingsByDescriptionENIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where descriptionEN not equals to DEFAULT_DESCRIPTION_EN
        defaultTrainingShouldNotBeFound("descriptionEN.notEquals=" + DEFAULT_DESCRIPTION_EN);

        // Get all the trainingList where descriptionEN not equals to UPDATED_DESCRIPTION_EN
        defaultTrainingShouldBeFound("descriptionEN.notEquals=" + UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    void getAllTrainingsByDescriptionENIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where descriptionEN in DEFAULT_DESCRIPTION_EN or UPDATED_DESCRIPTION_EN
        defaultTrainingShouldBeFound("descriptionEN.in=" + DEFAULT_DESCRIPTION_EN + "," + UPDATED_DESCRIPTION_EN);

        // Get all the trainingList where descriptionEN equals to UPDATED_DESCRIPTION_EN
        defaultTrainingShouldNotBeFound("descriptionEN.in=" + UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    void getAllTrainingsByDescriptionENIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where descriptionEN is not null
        defaultTrainingShouldBeFound("descriptionEN.specified=true");

        // Get all the trainingList where descriptionEN is null
        defaultTrainingShouldNotBeFound("descriptionEN.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByDescriptionENContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where descriptionEN contains DEFAULT_DESCRIPTION_EN
        defaultTrainingShouldBeFound("descriptionEN.contains=" + DEFAULT_DESCRIPTION_EN);

        // Get all the trainingList where descriptionEN contains UPDATED_DESCRIPTION_EN
        defaultTrainingShouldNotBeFound("descriptionEN.contains=" + UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    void getAllTrainingsByDescriptionENNotContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where descriptionEN does not contain DEFAULT_DESCRIPTION_EN
        defaultTrainingShouldNotBeFound("descriptionEN.doesNotContain=" + DEFAULT_DESCRIPTION_EN);

        // Get all the trainingList where descriptionEN does not contain UPDATED_DESCRIPTION_EN
        defaultTrainingShouldBeFound("descriptionEN.doesNotContain=" + UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    void getAllTrainingsByDescriptionDEIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where descriptionDE equals to DEFAULT_DESCRIPTION_DE
        defaultTrainingShouldBeFound("descriptionDE.equals=" + DEFAULT_DESCRIPTION_DE);

        // Get all the trainingList where descriptionDE equals to UPDATED_DESCRIPTION_DE
        defaultTrainingShouldNotBeFound("descriptionDE.equals=" + UPDATED_DESCRIPTION_DE);
    }

    @Test
    @Transactional
    void getAllTrainingsByDescriptionDEIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where descriptionDE not equals to DEFAULT_DESCRIPTION_DE
        defaultTrainingShouldNotBeFound("descriptionDE.notEquals=" + DEFAULT_DESCRIPTION_DE);

        // Get all the trainingList where descriptionDE not equals to UPDATED_DESCRIPTION_DE
        defaultTrainingShouldBeFound("descriptionDE.notEquals=" + UPDATED_DESCRIPTION_DE);
    }

    @Test
    @Transactional
    void getAllTrainingsByDescriptionDEIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where descriptionDE in DEFAULT_DESCRIPTION_DE or UPDATED_DESCRIPTION_DE
        defaultTrainingShouldBeFound("descriptionDE.in=" + DEFAULT_DESCRIPTION_DE + "," + UPDATED_DESCRIPTION_DE);

        // Get all the trainingList where descriptionDE equals to UPDATED_DESCRIPTION_DE
        defaultTrainingShouldNotBeFound("descriptionDE.in=" + UPDATED_DESCRIPTION_DE);
    }

    @Test
    @Transactional
    void getAllTrainingsByDescriptionDEIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where descriptionDE is not null
        defaultTrainingShouldBeFound("descriptionDE.specified=true");

        // Get all the trainingList where descriptionDE is null
        defaultTrainingShouldNotBeFound("descriptionDE.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByDescriptionDEContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where descriptionDE contains DEFAULT_DESCRIPTION_DE
        defaultTrainingShouldBeFound("descriptionDE.contains=" + DEFAULT_DESCRIPTION_DE);

        // Get all the trainingList where descriptionDE contains UPDATED_DESCRIPTION_DE
        defaultTrainingShouldNotBeFound("descriptionDE.contains=" + UPDATED_DESCRIPTION_DE);
    }

    @Test
    @Transactional
    void getAllTrainingsByDescriptionDENotContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where descriptionDE does not contain DEFAULT_DESCRIPTION_DE
        defaultTrainingShouldNotBeFound("descriptionDE.doesNotContain=" + DEFAULT_DESCRIPTION_DE);

        // Get all the trainingList where descriptionDE does not contain UPDATED_DESCRIPTION_DE
        defaultTrainingShouldBeFound("descriptionDE.doesNotContain=" + UPDATED_DESCRIPTION_DE);
    }

    @Test
    @Transactional
    void getAllTrainingsByContactIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where contact equals to DEFAULT_CONTACT
        defaultTrainingShouldBeFound("contact.equals=" + DEFAULT_CONTACT);

        // Get all the trainingList where contact equals to UPDATED_CONTACT
        defaultTrainingShouldNotBeFound("contact.equals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllTrainingsByContactIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where contact not equals to DEFAULT_CONTACT
        defaultTrainingShouldNotBeFound("contact.notEquals=" + DEFAULT_CONTACT);

        // Get all the trainingList where contact not equals to UPDATED_CONTACT
        defaultTrainingShouldBeFound("contact.notEquals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllTrainingsByContactIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where contact in DEFAULT_CONTACT or UPDATED_CONTACT
        defaultTrainingShouldBeFound("contact.in=" + DEFAULT_CONTACT + "," + UPDATED_CONTACT);

        // Get all the trainingList where contact equals to UPDATED_CONTACT
        defaultTrainingShouldNotBeFound("contact.in=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllTrainingsByContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where contact is not null
        defaultTrainingShouldBeFound("contact.specified=true");

        // Get all the trainingList where contact is null
        defaultTrainingShouldNotBeFound("contact.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByContactContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where contact contains DEFAULT_CONTACT
        defaultTrainingShouldBeFound("contact.contains=" + DEFAULT_CONTACT);

        // Get all the trainingList where contact contains UPDATED_CONTACT
        defaultTrainingShouldNotBeFound("contact.contains=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllTrainingsByContactNotContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where contact does not contain DEFAULT_CONTACT
        defaultTrainingShouldNotBeFound("contact.doesNotContain=" + DEFAULT_CONTACT);

        // Get all the trainingList where contact does not contain UPDATED_CONTACT
        defaultTrainingShouldBeFound("contact.doesNotContain=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllTrainingsByLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where link equals to DEFAULT_LINK
        defaultTrainingShouldBeFound("link.equals=" + DEFAULT_LINK);

        // Get all the trainingList where link equals to UPDATED_LINK
        defaultTrainingShouldNotBeFound("link.equals=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    void getAllTrainingsByLinkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where link not equals to DEFAULT_LINK
        defaultTrainingShouldNotBeFound("link.notEquals=" + DEFAULT_LINK);

        // Get all the trainingList where link not equals to UPDATED_LINK
        defaultTrainingShouldBeFound("link.notEquals=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    void getAllTrainingsByLinkIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where link in DEFAULT_LINK or UPDATED_LINK
        defaultTrainingShouldBeFound("link.in=" + DEFAULT_LINK + "," + UPDATED_LINK);

        // Get all the trainingList where link equals to UPDATED_LINK
        defaultTrainingShouldNotBeFound("link.in=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    void getAllTrainingsByLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where link is not null
        defaultTrainingShouldBeFound("link.specified=true");

        // Get all the trainingList where link is null
        defaultTrainingShouldNotBeFound("link.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByLinkContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where link contains DEFAULT_LINK
        defaultTrainingShouldBeFound("link.contains=" + DEFAULT_LINK);

        // Get all the trainingList where link contains UPDATED_LINK
        defaultTrainingShouldNotBeFound("link.contains=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    void getAllTrainingsByLinkNotContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where link does not contain DEFAULT_LINK
        defaultTrainingShouldNotBeFound("link.doesNotContain=" + DEFAULT_LINK);

        // Get all the trainingList where link does not contain UPDATED_LINK
        defaultTrainingShouldBeFound("link.doesNotContain=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    void getAllTrainingsByValidUntilIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where validUntil equals to DEFAULT_VALID_UNTIL
        defaultTrainingShouldBeFound("validUntil.equals=" + DEFAULT_VALID_UNTIL);

        // Get all the trainingList where validUntil equals to UPDATED_VALID_UNTIL
        defaultTrainingShouldNotBeFound("validUntil.equals=" + UPDATED_VALID_UNTIL);
    }

    @Test
    @Transactional
    void getAllTrainingsByValidUntilIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where validUntil not equals to DEFAULT_VALID_UNTIL
        defaultTrainingShouldNotBeFound("validUntil.notEquals=" + DEFAULT_VALID_UNTIL);

        // Get all the trainingList where validUntil not equals to UPDATED_VALID_UNTIL
        defaultTrainingShouldBeFound("validUntil.notEquals=" + UPDATED_VALID_UNTIL);
    }

    @Test
    @Transactional
    void getAllTrainingsByValidUntilIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where validUntil in DEFAULT_VALID_UNTIL or UPDATED_VALID_UNTIL
        defaultTrainingShouldBeFound("validUntil.in=" + DEFAULT_VALID_UNTIL + "," + UPDATED_VALID_UNTIL);

        // Get all the trainingList where validUntil equals to UPDATED_VALID_UNTIL
        defaultTrainingShouldNotBeFound("validUntil.in=" + UPDATED_VALID_UNTIL);
    }

    @Test
    @Transactional
    void getAllTrainingsByValidUntilIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where validUntil is not null
        defaultTrainingShouldBeFound("validUntil.specified=true");

        // Get all the trainingList where validUntil is null
        defaultTrainingShouldNotBeFound("validUntil.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByIsOfficialIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where isOfficial equals to DEFAULT_IS_OFFICIAL
        defaultTrainingShouldBeFound("isOfficial.equals=" + DEFAULT_IS_OFFICIAL);

        // Get all the trainingList where isOfficial equals to UPDATED_IS_OFFICIAL
        defaultTrainingShouldNotBeFound("isOfficial.equals=" + UPDATED_IS_OFFICIAL);
    }

    @Test
    @Transactional
    void getAllTrainingsByIsOfficialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where isOfficial not equals to DEFAULT_IS_OFFICIAL
        defaultTrainingShouldNotBeFound("isOfficial.notEquals=" + DEFAULT_IS_OFFICIAL);

        // Get all the trainingList where isOfficial not equals to UPDATED_IS_OFFICIAL
        defaultTrainingShouldBeFound("isOfficial.notEquals=" + UPDATED_IS_OFFICIAL);
    }

    @Test
    @Transactional
    void getAllTrainingsByIsOfficialIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where isOfficial in DEFAULT_IS_OFFICIAL or UPDATED_IS_OFFICIAL
        defaultTrainingShouldBeFound("isOfficial.in=" + DEFAULT_IS_OFFICIAL + "," + UPDATED_IS_OFFICIAL);

        // Get all the trainingList where isOfficial equals to UPDATED_IS_OFFICIAL
        defaultTrainingShouldNotBeFound("isOfficial.in=" + UPDATED_IS_OFFICIAL);
    }

    @Test
    @Transactional
    void getAllTrainingsByIsOfficialIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where isOfficial is not null
        defaultTrainingShouldBeFound("isOfficial.specified=true");

        // Get all the trainingList where isOfficial is null
        defaultTrainingShouldNotBeFound("isOfficial.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsBySuggestedByIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where suggestedBy equals to DEFAULT_SUGGESTED_BY
        defaultTrainingShouldBeFound("suggestedBy.equals=" + DEFAULT_SUGGESTED_BY);

        // Get all the trainingList where suggestedBy equals to UPDATED_SUGGESTED_BY
        defaultTrainingShouldNotBeFound("suggestedBy.equals=" + UPDATED_SUGGESTED_BY);
    }

    @Test
    @Transactional
    void getAllTrainingsBySuggestedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where suggestedBy not equals to DEFAULT_SUGGESTED_BY
        defaultTrainingShouldNotBeFound("suggestedBy.notEquals=" + DEFAULT_SUGGESTED_BY);

        // Get all the trainingList where suggestedBy not equals to UPDATED_SUGGESTED_BY
        defaultTrainingShouldBeFound("suggestedBy.notEquals=" + UPDATED_SUGGESTED_BY);
    }

    @Test
    @Transactional
    void getAllTrainingsBySuggestedByIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where suggestedBy in DEFAULT_SUGGESTED_BY or UPDATED_SUGGESTED_BY
        defaultTrainingShouldBeFound("suggestedBy.in=" + DEFAULT_SUGGESTED_BY + "," + UPDATED_SUGGESTED_BY);

        // Get all the trainingList where suggestedBy equals to UPDATED_SUGGESTED_BY
        defaultTrainingShouldNotBeFound("suggestedBy.in=" + UPDATED_SUGGESTED_BY);
    }

    @Test
    @Transactional
    void getAllTrainingsBySuggestedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where suggestedBy is not null
        defaultTrainingShouldBeFound("suggestedBy.specified=true");

        // Get all the trainingList where suggestedBy is null
        defaultTrainingShouldNotBeFound("suggestedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsBySuggestedByContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where suggestedBy contains DEFAULT_SUGGESTED_BY
        defaultTrainingShouldBeFound("suggestedBy.contains=" + DEFAULT_SUGGESTED_BY);

        // Get all the trainingList where suggestedBy contains UPDATED_SUGGESTED_BY
        defaultTrainingShouldNotBeFound("suggestedBy.contains=" + UPDATED_SUGGESTED_BY);
    }

    @Test
    @Transactional
    void getAllTrainingsBySuggestedByNotContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where suggestedBy does not contain DEFAULT_SUGGESTED_BY
        defaultTrainingShouldNotBeFound("suggestedBy.doesNotContain=" + DEFAULT_SUGGESTED_BY);

        // Get all the trainingList where suggestedBy does not contain UPDATED_SUGGESTED_BY
        defaultTrainingShouldBeFound("suggestedBy.doesNotContain=" + UPDATED_SUGGESTED_BY);
    }

    @Test
    @Transactional
    void getAllTrainingsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where createdAt equals to DEFAULT_CREATED_AT
        defaultTrainingShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the trainingList where createdAt equals to UPDATED_CREATED_AT
        defaultTrainingShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllTrainingsByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where createdAt not equals to DEFAULT_CREATED_AT
        defaultTrainingShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the trainingList where createdAt not equals to UPDATED_CREATED_AT
        defaultTrainingShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllTrainingsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultTrainingShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the trainingList where createdAt equals to UPDATED_CREATED_AT
        defaultTrainingShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllTrainingsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where createdAt is not null
        defaultTrainingShouldBeFound("createdAt.specified=true");

        // Get all the trainingList where createdAt is null
        defaultTrainingShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultTrainingShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the trainingList where updatedAt equals to UPDATED_UPDATED_AT
        defaultTrainingShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllTrainingsByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultTrainingShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the trainingList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultTrainingShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllTrainingsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultTrainingShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the trainingList where updatedAt equals to UPDATED_UPDATED_AT
        defaultTrainingShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllTrainingsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where updatedAt is not null
        defaultTrainingShouldBeFound("updatedAt.specified=true");

        // Get all the trainingList where updatedAt is null
        defaultTrainingShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsBySkillIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);
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
        training.addSkill(skill);
        trainingRepository.saveAndFlush(training);
        Long skillId = skill.getId();

        // Get all the trainingList where skill equals to skillId
        defaultTrainingShouldBeFound("skillId.equals=" + skillId);

        // Get all the trainingList where skill equals to (skillId + 1)
        defaultTrainingShouldNotBeFound("skillId.equals=" + (skillId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTrainingShouldBeFound(String filter) throws Exception {
        restTrainingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(training.getId().intValue())))
            .andExpect(jsonPath("$.[*].titleEN").value(hasItem(DEFAULT_TITLE_EN)))
            .andExpect(jsonPath("$.[*].titleDE").value(hasItem(DEFAULT_TITLE_DE)))
            .andExpect(jsonPath("$.[*].descriptionEN").value(hasItem(DEFAULT_DESCRIPTION_EN)))
            .andExpect(jsonPath("$.[*].descriptionDE").value(hasItem(DEFAULT_DESCRIPTION_DE)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].validUntil").value(hasItem(DEFAULT_VALID_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].isOfficial").value(hasItem(DEFAULT_IS_OFFICIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].suggestedBy").value(hasItem(DEFAULT_SUGGESTED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));

        // Check, that the count call also returns 1
        restTrainingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTrainingShouldNotBeFound(String filter) throws Exception {
        restTrainingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTrainingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTraining() throws Exception {
        // Get the training
        restTrainingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();

        // Update the training
        Training updatedTraining = trainingRepository.findById(training.getId()).get();
        // Disconnect from session so that the updates on updatedTraining are not directly saved in db
        em.detach(updatedTraining);
        updatedTraining
            .titleEN(UPDATED_TITLE_EN)
            .titleDE(UPDATED_TITLE_DE)
            .descriptionEN(UPDATED_DESCRIPTION_EN)
            .descriptionDE(UPDATED_DESCRIPTION_DE)
            .contact(UPDATED_CONTACT)
            .link(UPDATED_LINK)
            .validUntil(UPDATED_VALID_UNTIL)
            .isOfficial(UPDATED_IS_OFFICIAL)
            .suggestedBy(UPDATED_SUGGESTED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        TrainingDTO trainingDTO = trainingMapper.toDto(updatedTraining);

        restTrainingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainingDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainingDTO))
            )
            .andExpect(status().isOk());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
        Training testTraining = trainingList.get(trainingList.size() - 1);
        assertThat(testTraining.getTitleEN()).isEqualTo(UPDATED_TITLE_EN);
        assertThat(testTraining.getTitleDE()).isEqualTo(UPDATED_TITLE_DE);
        assertThat(testTraining.getDescriptionEN()).isEqualTo(UPDATED_DESCRIPTION_EN);
        assertThat(testTraining.getDescriptionDE()).isEqualTo(UPDATED_DESCRIPTION_DE);
        assertThat(testTraining.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testTraining.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testTraining.getValidUntil()).isEqualTo(UPDATED_VALID_UNTIL);
        assertThat(testTraining.getIsOfficial()).isEqualTo(UPDATED_IS_OFFICIAL);
        assertThat(testTraining.getSuggestedBy()).isEqualTo(UPDATED_SUGGESTED_BY);
        // ### MODIFICATION-START ###
        assertThat(testTraining.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        // ### MODIFICATION-END ###
        assertThat(testTraining.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingTraining() throws Exception {
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
        training.setId(count.incrementAndGet());

        // Create the Training
        TrainingDTO trainingDTO = trainingMapper.toDto(training);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainingDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTraining() throws Exception {
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
        training.setId(count.incrementAndGet());

        // Create the Training
        TrainingDTO trainingDTO = trainingMapper.toDto(training);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTraining() throws Exception {
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
        training.setId(count.incrementAndGet());

        // Create the Training
        TrainingDTO trainingDTO = trainingMapper.toDto(training);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrainingWithPatch() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();

        // Update the training using partial update
        Training partialUpdatedTraining = new Training();
        partialUpdatedTraining.setId(training.getId());

        partialUpdatedTraining
            .titleEN(UPDATED_TITLE_EN)
            .titleDE(UPDATED_TITLE_DE)
            .descriptionDE(UPDATED_DESCRIPTION_DE)
            .contact(UPDATED_CONTACT)
            .validUntil(UPDATED_VALID_UNTIL)
            .suggestedBy(UPDATED_SUGGESTED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restTrainingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTraining.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTraining))
            )
            .andExpect(status().isOk());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
        Training testTraining = trainingList.get(trainingList.size() - 1);
        assertThat(testTraining.getTitleEN()).isEqualTo(UPDATED_TITLE_EN);
        assertThat(testTraining.getTitleDE()).isEqualTo(UPDATED_TITLE_DE);
        assertThat(testTraining.getDescriptionEN()).isEqualTo(DEFAULT_DESCRIPTION_EN);
        assertThat(testTraining.getDescriptionDE()).isEqualTo(UPDATED_DESCRIPTION_DE);
        assertThat(testTraining.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testTraining.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testTraining.getValidUntil()).isEqualTo(UPDATED_VALID_UNTIL);
        assertThat(testTraining.getIsOfficial()).isEqualTo(DEFAULT_IS_OFFICIAL);
        assertThat(testTraining.getSuggestedBy()).isEqualTo(UPDATED_SUGGESTED_BY);
        assertThat(testTraining.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTraining.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateTrainingWithPatch() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();

        // Update the training using partial update
        Training partialUpdatedTraining = new Training();
        partialUpdatedTraining.setId(training.getId());

        partialUpdatedTraining
            .titleEN(UPDATED_TITLE_EN)
            .titleDE(UPDATED_TITLE_DE)
            .descriptionEN(UPDATED_DESCRIPTION_EN)
            .descriptionDE(UPDATED_DESCRIPTION_DE)
            .contact(UPDATED_CONTACT)
            .link(UPDATED_LINK)
            .validUntil(UPDATED_VALID_UNTIL)
            .isOfficial(UPDATED_IS_OFFICIAL)
            .suggestedBy(UPDATED_SUGGESTED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restTrainingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTraining.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTraining))
            )
            .andExpect(status().isOk());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
        Training testTraining = trainingList.get(trainingList.size() - 1);
        assertThat(testTraining.getTitleEN()).isEqualTo(UPDATED_TITLE_EN);
        assertThat(testTraining.getTitleDE()).isEqualTo(UPDATED_TITLE_DE);
        assertThat(testTraining.getDescriptionEN()).isEqualTo(UPDATED_DESCRIPTION_EN);
        assertThat(testTraining.getDescriptionDE()).isEqualTo(UPDATED_DESCRIPTION_DE);
        assertThat(testTraining.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testTraining.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testTraining.getValidUntil()).isEqualTo(UPDATED_VALID_UNTIL);
        assertThat(testTraining.getIsOfficial()).isEqualTo(UPDATED_IS_OFFICIAL);
        assertThat(testTraining.getSuggestedBy()).isEqualTo(UPDATED_SUGGESTED_BY);
        assertThat(testTraining.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTraining.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingTraining() throws Exception {
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
        training.setId(count.incrementAndGet());

        // Create the Training
        TrainingDTO trainingDTO = trainingMapper.toDto(training);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trainingDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTraining() throws Exception {
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
        training.setId(count.incrementAndGet());

        // Create the Training
        TrainingDTO trainingDTO = trainingMapper.toDto(training);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTraining() throws Exception {
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
        training.setId(count.incrementAndGet());

        // Create the Training
        TrainingDTO trainingDTO = trainingMapper.toDto(training);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        int databaseSizeBeforeDelete = trainingRepository.findAll().size();

        // Delete the training
        restTrainingMockMvc
            .perform(delete(ENTITY_API_URL_ID, training.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
