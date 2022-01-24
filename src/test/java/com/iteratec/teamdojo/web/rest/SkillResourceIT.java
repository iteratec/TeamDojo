package com.iteratec.teamdojo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iteratec.teamdojo.IntegrationTest;
import com.iteratec.teamdojo.domain.BadgeSkill;
import com.iteratec.teamdojo.domain.LevelSkill;
import com.iteratec.teamdojo.domain.Skill;
import com.iteratec.teamdojo.domain.TeamSkill;
import com.iteratec.teamdojo.domain.Training;
import com.iteratec.teamdojo.repository.SkillRepository;
import com.iteratec.teamdojo.service.criteria.SkillCriteria;
import com.iteratec.teamdojo.service.dto.SkillDTO;
import com.iteratec.teamdojo.service.mapper.SkillMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
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
 * Integration tests for the {@link SkillResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SkillResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMPLEMENTATION = "AAAAAAAAAA";
    private static final String UPDATED_IMPLEMENTATION = "BBBBBBBBBB";

    private static final String DEFAULT_VALIDATION = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_EXPIRY_PERIOD = 1;
    private static final Integer UPDATED_EXPIRY_PERIOD = 2;
    private static final Integer SMALLER_EXPIRY_PERIOD = 1 - 1;

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final Integer DEFAULT_SCORE = 0;
    private static final Integer UPDATED_SCORE = 1;
    private static final Integer SMALLER_SCORE = 0 - 1;

    private static final Double DEFAULT_RATE_SCORE = 0D;
    private static final Double UPDATED_RATE_SCORE = 1D;
    private static final Double SMALLER_RATE_SCORE = 0D - 1D;

    private static final Integer DEFAULT_RATE_COUNT = 0;
    private static final Integer UPDATED_RATE_COUNT = 1;
    private static final Integer SMALLER_RATE_COUNT = 0 - 1;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/skills";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private SkillMapper skillMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSkillMockMvc;

    private Skill skill;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Skill createEntity(EntityManager em) {
        Skill skill = new Skill()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .implementation(DEFAULT_IMPLEMENTATION)
            .validation(DEFAULT_VALIDATION)
            .expiryPeriod(DEFAULT_EXPIRY_PERIOD)
            .contact(DEFAULT_CONTACT)
            .score(DEFAULT_SCORE)
            .rateScore(DEFAULT_RATE_SCORE)
            .rateCount(DEFAULT_RATE_COUNT)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return skill;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Skill createUpdatedEntity(EntityManager em) {
        Skill skill = new Skill()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .implementation(UPDATED_IMPLEMENTATION)
            .validation(UPDATED_VALIDATION)
            .expiryPeriod(UPDATED_EXPIRY_PERIOD)
            .contact(UPDATED_CONTACT)
            .score(UPDATED_SCORE)
            .rateScore(UPDATED_RATE_SCORE)
            .rateCount(UPDATED_RATE_COUNT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return skill;
    }

    @BeforeEach
    public void initTest() {
        skill = createEntity(em);
    }

    @Test
    @Transactional
    void createSkill() throws Exception {
        int databaseSizeBeforeCreate = skillRepository.findAll().size();
        // Create the Skill
        SkillDTO skillDTO = skillMapper.toDto(skill);
        restSkillMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeCreate + 1);
        Skill testSkill = skillList.get(skillList.size() - 1);
        assertThat(testSkill.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSkill.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSkill.getImplementation()).isEqualTo(DEFAULT_IMPLEMENTATION);
        assertThat(testSkill.getValidation()).isEqualTo(DEFAULT_VALIDATION);
        assertThat(testSkill.getExpiryPeriod()).isEqualTo(DEFAULT_EXPIRY_PERIOD);
        assertThat(testSkill.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testSkill.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testSkill.getRateScore()).isEqualTo(DEFAULT_RATE_SCORE);
        assertThat(testSkill.getRateCount()).isEqualTo(DEFAULT_RATE_COUNT);
        assertThat(testSkill.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testSkill.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createSkillWithExistingId() throws Exception {
        // Create the Skill with an existing ID
        skill.setId(1L);
        SkillDTO skillDTO = skillMapper.toDto(skill);

        int databaseSizeBeforeCreate = skillRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillRepository.findAll().size();
        // set the field null
        skill.setTitle(null);

        // Create the Skill, which fails.
        SkillDTO skillDTO = skillMapper.toDto(skill);

        restSkillMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isBadRequest());

        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillRepository.findAll().size();
        // set the field null
        skill.setScore(null);

        // Create the Skill, which fails.
        SkillDTO skillDTO = skillMapper.toDto(skill);

        restSkillMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isBadRequest());

        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRateCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillRepository.findAll().size();
        // set the field null
        skill.setRateCount(null);

        // Create the Skill, which fails.
        SkillDTO skillDTO = skillMapper.toDto(skill);

        restSkillMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isBadRequest());

        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @Disabled("Ignored because we removed the validation for this field in the DTO.")
    // ### MODIFICATION-END ###
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillRepository.findAll().size();
        // set the field null
        skill.setCreatedAt(null);

        // Create the Skill, which fails.
        SkillDTO skillDTO = skillMapper.toDto(skill);

        restSkillMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isBadRequest());

        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    // ### MODIFICATION-START ###
    @Disabled("Ignored because we removed the validation for this field in the DTO.")
    // ### MODIFICATION-END ###
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillRepository.findAll().size();
        // set the field null
        skill.setUpdatedAt(null);

        // Create the Skill, which fails.
        SkillDTO skillDTO = skillMapper.toDto(skill);

        restSkillMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isBadRequest());

        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSkills() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList
        restSkillMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skill.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].implementation").value(hasItem(DEFAULT_IMPLEMENTATION)))
            .andExpect(jsonPath("$.[*].validation").value(hasItem(DEFAULT_VALIDATION)))
            .andExpect(jsonPath("$.[*].expiryPeriod").value(hasItem(DEFAULT_EXPIRY_PERIOD)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].rateScore").value(hasItem(DEFAULT_RATE_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].rateCount").value(hasItem(DEFAULT_RATE_COUNT)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getSkill() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get the skill
        restSkillMockMvc
            .perform(get(ENTITY_API_URL_ID, skill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(skill.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.implementation").value(DEFAULT_IMPLEMENTATION))
            .andExpect(jsonPath("$.validation").value(DEFAULT_VALIDATION))
            .andExpect(jsonPath("$.expiryPeriod").value(DEFAULT_EXPIRY_PERIOD))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.rateScore").value(DEFAULT_RATE_SCORE.doubleValue()))
            .andExpect(jsonPath("$.rateCount").value(DEFAULT_RATE_COUNT))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getSkillsByIdFiltering() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        Long id = skill.getId();

        defaultSkillShouldBeFound("id.equals=" + id);
        defaultSkillShouldNotBeFound("id.notEquals=" + id);

        defaultSkillShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSkillShouldNotBeFound("id.greaterThan=" + id);

        defaultSkillShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSkillShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSkillsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where title equals to DEFAULT_TITLE
        defaultSkillShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the skillList where title equals to UPDATED_TITLE
        defaultSkillShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSkillsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where title not equals to DEFAULT_TITLE
        defaultSkillShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the skillList where title not equals to UPDATED_TITLE
        defaultSkillShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSkillsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultSkillShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the skillList where title equals to UPDATED_TITLE
        defaultSkillShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSkillsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where title is not null
        defaultSkillShouldBeFound("title.specified=true");

        // Get all the skillList where title is null
        defaultSkillShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByTitleContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where title contains DEFAULT_TITLE
        defaultSkillShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the skillList where title contains UPDATED_TITLE
        defaultSkillShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSkillsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where title does not contain DEFAULT_TITLE
        defaultSkillShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the skillList where title does not contain UPDATED_TITLE
        defaultSkillShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where description equals to DEFAULT_DESCRIPTION
        defaultSkillShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the skillList where description equals to UPDATED_DESCRIPTION
        defaultSkillShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where description not equals to DEFAULT_DESCRIPTION
        defaultSkillShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the skillList where description not equals to UPDATED_DESCRIPTION
        defaultSkillShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultSkillShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the skillList where description equals to UPDATED_DESCRIPTION
        defaultSkillShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where description is not null
        defaultSkillShouldBeFound("description.specified=true");

        // Get all the skillList where description is null
        defaultSkillShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where description contains DEFAULT_DESCRIPTION
        defaultSkillShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the skillList where description contains UPDATED_DESCRIPTION
        defaultSkillShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where description does not contain DEFAULT_DESCRIPTION
        defaultSkillShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the skillList where description does not contain UPDATED_DESCRIPTION
        defaultSkillShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSkillsByImplementationIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where implementation equals to DEFAULT_IMPLEMENTATION
        defaultSkillShouldBeFound("implementation.equals=" + DEFAULT_IMPLEMENTATION);

        // Get all the skillList where implementation equals to UPDATED_IMPLEMENTATION
        defaultSkillShouldNotBeFound("implementation.equals=" + UPDATED_IMPLEMENTATION);
    }

    @Test
    @Transactional
    void getAllSkillsByImplementationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where implementation not equals to DEFAULT_IMPLEMENTATION
        defaultSkillShouldNotBeFound("implementation.notEquals=" + DEFAULT_IMPLEMENTATION);

        // Get all the skillList where implementation not equals to UPDATED_IMPLEMENTATION
        defaultSkillShouldBeFound("implementation.notEquals=" + UPDATED_IMPLEMENTATION);
    }

    @Test
    @Transactional
    void getAllSkillsByImplementationIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where implementation in DEFAULT_IMPLEMENTATION or UPDATED_IMPLEMENTATION
        defaultSkillShouldBeFound("implementation.in=" + DEFAULT_IMPLEMENTATION + "," + UPDATED_IMPLEMENTATION);

        // Get all the skillList where implementation equals to UPDATED_IMPLEMENTATION
        defaultSkillShouldNotBeFound("implementation.in=" + UPDATED_IMPLEMENTATION);
    }

    @Test
    @Transactional
    void getAllSkillsByImplementationIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where implementation is not null
        defaultSkillShouldBeFound("implementation.specified=true");

        // Get all the skillList where implementation is null
        defaultSkillShouldNotBeFound("implementation.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByImplementationContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where implementation contains DEFAULT_IMPLEMENTATION
        defaultSkillShouldBeFound("implementation.contains=" + DEFAULT_IMPLEMENTATION);

        // Get all the skillList where implementation contains UPDATED_IMPLEMENTATION
        defaultSkillShouldNotBeFound("implementation.contains=" + UPDATED_IMPLEMENTATION);
    }

    @Test
    @Transactional
    void getAllSkillsByImplementationNotContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where implementation does not contain DEFAULT_IMPLEMENTATION
        defaultSkillShouldNotBeFound("implementation.doesNotContain=" + DEFAULT_IMPLEMENTATION);

        // Get all the skillList where implementation does not contain UPDATED_IMPLEMENTATION
        defaultSkillShouldBeFound("implementation.doesNotContain=" + UPDATED_IMPLEMENTATION);
    }

    @Test
    @Transactional
    void getAllSkillsByValidationIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where validation equals to DEFAULT_VALIDATION
        defaultSkillShouldBeFound("validation.equals=" + DEFAULT_VALIDATION);

        // Get all the skillList where validation equals to UPDATED_VALIDATION
        defaultSkillShouldNotBeFound("validation.equals=" + UPDATED_VALIDATION);
    }

    @Test
    @Transactional
    void getAllSkillsByValidationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where validation not equals to DEFAULT_VALIDATION
        defaultSkillShouldNotBeFound("validation.notEquals=" + DEFAULT_VALIDATION);

        // Get all the skillList where validation not equals to UPDATED_VALIDATION
        defaultSkillShouldBeFound("validation.notEquals=" + UPDATED_VALIDATION);
    }

    @Test
    @Transactional
    void getAllSkillsByValidationIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where validation in DEFAULT_VALIDATION or UPDATED_VALIDATION
        defaultSkillShouldBeFound("validation.in=" + DEFAULT_VALIDATION + "," + UPDATED_VALIDATION);

        // Get all the skillList where validation equals to UPDATED_VALIDATION
        defaultSkillShouldNotBeFound("validation.in=" + UPDATED_VALIDATION);
    }

    @Test
    @Transactional
    void getAllSkillsByValidationIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where validation is not null
        defaultSkillShouldBeFound("validation.specified=true");

        // Get all the skillList where validation is null
        defaultSkillShouldNotBeFound("validation.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByValidationContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where validation contains DEFAULT_VALIDATION
        defaultSkillShouldBeFound("validation.contains=" + DEFAULT_VALIDATION);

        // Get all the skillList where validation contains UPDATED_VALIDATION
        defaultSkillShouldNotBeFound("validation.contains=" + UPDATED_VALIDATION);
    }

    @Test
    @Transactional
    void getAllSkillsByValidationNotContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where validation does not contain DEFAULT_VALIDATION
        defaultSkillShouldNotBeFound("validation.doesNotContain=" + DEFAULT_VALIDATION);

        // Get all the skillList where validation does not contain UPDATED_VALIDATION
        defaultSkillShouldBeFound("validation.doesNotContain=" + UPDATED_VALIDATION);
    }

    @Test
    @Transactional
    void getAllSkillsByExpiryPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where expiryPeriod equals to DEFAULT_EXPIRY_PERIOD
        defaultSkillShouldBeFound("expiryPeriod.equals=" + DEFAULT_EXPIRY_PERIOD);

        // Get all the skillList where expiryPeriod equals to UPDATED_EXPIRY_PERIOD
        defaultSkillShouldNotBeFound("expiryPeriod.equals=" + UPDATED_EXPIRY_PERIOD);
    }

    @Test
    @Transactional
    void getAllSkillsByExpiryPeriodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where expiryPeriod not equals to DEFAULT_EXPIRY_PERIOD
        defaultSkillShouldNotBeFound("expiryPeriod.notEquals=" + DEFAULT_EXPIRY_PERIOD);

        // Get all the skillList where expiryPeriod not equals to UPDATED_EXPIRY_PERIOD
        defaultSkillShouldBeFound("expiryPeriod.notEquals=" + UPDATED_EXPIRY_PERIOD);
    }

    @Test
    @Transactional
    void getAllSkillsByExpiryPeriodIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where expiryPeriod in DEFAULT_EXPIRY_PERIOD or UPDATED_EXPIRY_PERIOD
        defaultSkillShouldBeFound("expiryPeriod.in=" + DEFAULT_EXPIRY_PERIOD + "," + UPDATED_EXPIRY_PERIOD);

        // Get all the skillList where expiryPeriod equals to UPDATED_EXPIRY_PERIOD
        defaultSkillShouldNotBeFound("expiryPeriod.in=" + UPDATED_EXPIRY_PERIOD);
    }

    @Test
    @Transactional
    void getAllSkillsByExpiryPeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where expiryPeriod is not null
        defaultSkillShouldBeFound("expiryPeriod.specified=true");

        // Get all the skillList where expiryPeriod is null
        defaultSkillShouldNotBeFound("expiryPeriod.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByExpiryPeriodIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where expiryPeriod is greater than or equal to DEFAULT_EXPIRY_PERIOD
        defaultSkillShouldBeFound("expiryPeriod.greaterThanOrEqual=" + DEFAULT_EXPIRY_PERIOD);

        // Get all the skillList where expiryPeriod is greater than or equal to UPDATED_EXPIRY_PERIOD
        defaultSkillShouldNotBeFound("expiryPeriod.greaterThanOrEqual=" + UPDATED_EXPIRY_PERIOD);
    }

    @Test
    @Transactional
    void getAllSkillsByExpiryPeriodIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where expiryPeriod is less than or equal to DEFAULT_EXPIRY_PERIOD
        defaultSkillShouldBeFound("expiryPeriod.lessThanOrEqual=" + DEFAULT_EXPIRY_PERIOD);

        // Get all the skillList where expiryPeriod is less than or equal to SMALLER_EXPIRY_PERIOD
        defaultSkillShouldNotBeFound("expiryPeriod.lessThanOrEqual=" + SMALLER_EXPIRY_PERIOD);
    }

    @Test
    @Transactional
    void getAllSkillsByExpiryPeriodIsLessThanSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where expiryPeriod is less than DEFAULT_EXPIRY_PERIOD
        defaultSkillShouldNotBeFound("expiryPeriod.lessThan=" + DEFAULT_EXPIRY_PERIOD);

        // Get all the skillList where expiryPeriod is less than UPDATED_EXPIRY_PERIOD
        defaultSkillShouldBeFound("expiryPeriod.lessThan=" + UPDATED_EXPIRY_PERIOD);
    }

    @Test
    @Transactional
    void getAllSkillsByExpiryPeriodIsGreaterThanSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where expiryPeriod is greater than DEFAULT_EXPIRY_PERIOD
        defaultSkillShouldNotBeFound("expiryPeriod.greaterThan=" + DEFAULT_EXPIRY_PERIOD);

        // Get all the skillList where expiryPeriod is greater than SMALLER_EXPIRY_PERIOD
        defaultSkillShouldBeFound("expiryPeriod.greaterThan=" + SMALLER_EXPIRY_PERIOD);
    }

    @Test
    @Transactional
    void getAllSkillsByContactIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where contact equals to DEFAULT_CONTACT
        defaultSkillShouldBeFound("contact.equals=" + DEFAULT_CONTACT);

        // Get all the skillList where contact equals to UPDATED_CONTACT
        defaultSkillShouldNotBeFound("contact.equals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllSkillsByContactIsNotEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where contact not equals to DEFAULT_CONTACT
        defaultSkillShouldNotBeFound("contact.notEquals=" + DEFAULT_CONTACT);

        // Get all the skillList where contact not equals to UPDATED_CONTACT
        defaultSkillShouldBeFound("contact.notEquals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllSkillsByContactIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where contact in DEFAULT_CONTACT or UPDATED_CONTACT
        defaultSkillShouldBeFound("contact.in=" + DEFAULT_CONTACT + "," + UPDATED_CONTACT);

        // Get all the skillList where contact equals to UPDATED_CONTACT
        defaultSkillShouldNotBeFound("contact.in=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllSkillsByContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where contact is not null
        defaultSkillShouldBeFound("contact.specified=true");

        // Get all the skillList where contact is null
        defaultSkillShouldNotBeFound("contact.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByContactContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where contact contains DEFAULT_CONTACT
        defaultSkillShouldBeFound("contact.contains=" + DEFAULT_CONTACT);

        // Get all the skillList where contact contains UPDATED_CONTACT
        defaultSkillShouldNotBeFound("contact.contains=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllSkillsByContactNotContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where contact does not contain DEFAULT_CONTACT
        defaultSkillShouldNotBeFound("contact.doesNotContain=" + DEFAULT_CONTACT);

        // Get all the skillList where contact does not contain UPDATED_CONTACT
        defaultSkillShouldBeFound("contact.doesNotContain=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllSkillsByScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where score equals to DEFAULT_SCORE
        defaultSkillShouldBeFound("score.equals=" + DEFAULT_SCORE);

        // Get all the skillList where score equals to UPDATED_SCORE
        defaultSkillShouldNotBeFound("score.equals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllSkillsByScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where score not equals to DEFAULT_SCORE
        defaultSkillShouldNotBeFound("score.notEquals=" + DEFAULT_SCORE);

        // Get all the skillList where score not equals to UPDATED_SCORE
        defaultSkillShouldBeFound("score.notEquals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllSkillsByScoreIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where score in DEFAULT_SCORE or UPDATED_SCORE
        defaultSkillShouldBeFound("score.in=" + DEFAULT_SCORE + "," + UPDATED_SCORE);

        // Get all the skillList where score equals to UPDATED_SCORE
        defaultSkillShouldNotBeFound("score.in=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllSkillsByScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where score is not null
        defaultSkillShouldBeFound("score.specified=true");

        // Get all the skillList where score is null
        defaultSkillShouldNotBeFound("score.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where score is greater than or equal to DEFAULT_SCORE
        defaultSkillShouldBeFound("score.greaterThanOrEqual=" + DEFAULT_SCORE);

        // Get all the skillList where score is greater than or equal to UPDATED_SCORE
        defaultSkillShouldNotBeFound("score.greaterThanOrEqual=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllSkillsByScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where score is less than or equal to DEFAULT_SCORE
        defaultSkillShouldBeFound("score.lessThanOrEqual=" + DEFAULT_SCORE);

        // Get all the skillList where score is less than or equal to SMALLER_SCORE
        defaultSkillShouldNotBeFound("score.lessThanOrEqual=" + SMALLER_SCORE);
    }

    @Test
    @Transactional
    void getAllSkillsByScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where score is less than DEFAULT_SCORE
        defaultSkillShouldNotBeFound("score.lessThan=" + DEFAULT_SCORE);

        // Get all the skillList where score is less than UPDATED_SCORE
        defaultSkillShouldBeFound("score.lessThan=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllSkillsByScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where score is greater than DEFAULT_SCORE
        defaultSkillShouldNotBeFound("score.greaterThan=" + DEFAULT_SCORE);

        // Get all the skillList where score is greater than SMALLER_SCORE
        defaultSkillShouldBeFound("score.greaterThan=" + SMALLER_SCORE);
    }

    @Test
    @Transactional
    void getAllSkillsByRateScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateScore equals to DEFAULT_RATE_SCORE
        defaultSkillShouldBeFound("rateScore.equals=" + DEFAULT_RATE_SCORE);

        // Get all the skillList where rateScore equals to UPDATED_RATE_SCORE
        defaultSkillShouldNotBeFound("rateScore.equals=" + UPDATED_RATE_SCORE);
    }

    @Test
    @Transactional
    void getAllSkillsByRateScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateScore not equals to DEFAULT_RATE_SCORE
        defaultSkillShouldNotBeFound("rateScore.notEquals=" + DEFAULT_RATE_SCORE);

        // Get all the skillList where rateScore not equals to UPDATED_RATE_SCORE
        defaultSkillShouldBeFound("rateScore.notEquals=" + UPDATED_RATE_SCORE);
    }

    @Test
    @Transactional
    void getAllSkillsByRateScoreIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateScore in DEFAULT_RATE_SCORE or UPDATED_RATE_SCORE
        defaultSkillShouldBeFound("rateScore.in=" + DEFAULT_RATE_SCORE + "," + UPDATED_RATE_SCORE);

        // Get all the skillList where rateScore equals to UPDATED_RATE_SCORE
        defaultSkillShouldNotBeFound("rateScore.in=" + UPDATED_RATE_SCORE);
    }

    @Test
    @Transactional
    void getAllSkillsByRateScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateScore is not null
        defaultSkillShouldBeFound("rateScore.specified=true");

        // Get all the skillList where rateScore is null
        defaultSkillShouldNotBeFound("rateScore.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByRateScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateScore is greater than or equal to DEFAULT_RATE_SCORE
        defaultSkillShouldBeFound("rateScore.greaterThanOrEqual=" + DEFAULT_RATE_SCORE);

        // Get all the skillList where rateScore is greater than or equal to (DEFAULT_RATE_SCORE + 1)
        defaultSkillShouldNotBeFound("rateScore.greaterThanOrEqual=" + (DEFAULT_RATE_SCORE + 1));
    }

    @Test
    @Transactional
    void getAllSkillsByRateScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateScore is less than or equal to DEFAULT_RATE_SCORE
        defaultSkillShouldBeFound("rateScore.lessThanOrEqual=" + DEFAULT_RATE_SCORE);

        // Get all the skillList where rateScore is less than or equal to SMALLER_RATE_SCORE
        defaultSkillShouldNotBeFound("rateScore.lessThanOrEqual=" + SMALLER_RATE_SCORE);
    }

    @Test
    @Transactional
    void getAllSkillsByRateScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateScore is less than DEFAULT_RATE_SCORE
        defaultSkillShouldNotBeFound("rateScore.lessThan=" + DEFAULT_RATE_SCORE);

        // Get all the skillList where rateScore is less than (DEFAULT_RATE_SCORE + 1)
        defaultSkillShouldBeFound("rateScore.lessThan=" + (DEFAULT_RATE_SCORE + 1));
    }

    @Test
    @Transactional
    void getAllSkillsByRateScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateScore is greater than DEFAULT_RATE_SCORE
        defaultSkillShouldNotBeFound("rateScore.greaterThan=" + DEFAULT_RATE_SCORE);

        // Get all the skillList where rateScore is greater than SMALLER_RATE_SCORE
        defaultSkillShouldBeFound("rateScore.greaterThan=" + SMALLER_RATE_SCORE);
    }

    @Test
    @Transactional
    void getAllSkillsByRateCountIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateCount equals to DEFAULT_RATE_COUNT
        defaultSkillShouldBeFound("rateCount.equals=" + DEFAULT_RATE_COUNT);

        // Get all the skillList where rateCount equals to UPDATED_RATE_COUNT
        defaultSkillShouldNotBeFound("rateCount.equals=" + UPDATED_RATE_COUNT);
    }

    @Test
    @Transactional
    void getAllSkillsByRateCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateCount not equals to DEFAULT_RATE_COUNT
        defaultSkillShouldNotBeFound("rateCount.notEquals=" + DEFAULT_RATE_COUNT);

        // Get all the skillList where rateCount not equals to UPDATED_RATE_COUNT
        defaultSkillShouldBeFound("rateCount.notEquals=" + UPDATED_RATE_COUNT);
    }

    @Test
    @Transactional
    void getAllSkillsByRateCountIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateCount in DEFAULT_RATE_COUNT or UPDATED_RATE_COUNT
        defaultSkillShouldBeFound("rateCount.in=" + DEFAULT_RATE_COUNT + "," + UPDATED_RATE_COUNT);

        // Get all the skillList where rateCount equals to UPDATED_RATE_COUNT
        defaultSkillShouldNotBeFound("rateCount.in=" + UPDATED_RATE_COUNT);
    }

    @Test
    @Transactional
    void getAllSkillsByRateCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateCount is not null
        defaultSkillShouldBeFound("rateCount.specified=true");

        // Get all the skillList where rateCount is null
        defaultSkillShouldNotBeFound("rateCount.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByRateCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateCount is greater than or equal to DEFAULT_RATE_COUNT
        defaultSkillShouldBeFound("rateCount.greaterThanOrEqual=" + DEFAULT_RATE_COUNT);

        // Get all the skillList where rateCount is greater than or equal to UPDATED_RATE_COUNT
        defaultSkillShouldNotBeFound("rateCount.greaterThanOrEqual=" + UPDATED_RATE_COUNT);
    }

    @Test
    @Transactional
    void getAllSkillsByRateCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateCount is less than or equal to DEFAULT_RATE_COUNT
        defaultSkillShouldBeFound("rateCount.lessThanOrEqual=" + DEFAULT_RATE_COUNT);

        // Get all the skillList where rateCount is less than or equal to SMALLER_RATE_COUNT
        defaultSkillShouldNotBeFound("rateCount.lessThanOrEqual=" + SMALLER_RATE_COUNT);
    }

    @Test
    @Transactional
    void getAllSkillsByRateCountIsLessThanSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateCount is less than DEFAULT_RATE_COUNT
        defaultSkillShouldNotBeFound("rateCount.lessThan=" + DEFAULT_RATE_COUNT);

        // Get all the skillList where rateCount is less than UPDATED_RATE_COUNT
        defaultSkillShouldBeFound("rateCount.lessThan=" + UPDATED_RATE_COUNT);
    }

    @Test
    @Transactional
    void getAllSkillsByRateCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateCount is greater than DEFAULT_RATE_COUNT
        defaultSkillShouldNotBeFound("rateCount.greaterThan=" + DEFAULT_RATE_COUNT);

        // Get all the skillList where rateCount is greater than SMALLER_RATE_COUNT
        defaultSkillShouldBeFound("rateCount.greaterThan=" + SMALLER_RATE_COUNT);
    }

    @Test
    @Transactional
    void getAllSkillsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where createdAt equals to DEFAULT_CREATED_AT
        defaultSkillShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the skillList where createdAt equals to UPDATED_CREATED_AT
        defaultSkillShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllSkillsByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where createdAt not equals to DEFAULT_CREATED_AT
        defaultSkillShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the skillList where createdAt not equals to UPDATED_CREATED_AT
        defaultSkillShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllSkillsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultSkillShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the skillList where createdAt equals to UPDATED_CREATED_AT
        defaultSkillShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllSkillsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where createdAt is not null
        defaultSkillShouldBeFound("createdAt.specified=true");

        // Get all the skillList where createdAt is null
        defaultSkillShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultSkillShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the skillList where updatedAt equals to UPDATED_UPDATED_AT
        defaultSkillShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllSkillsByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultSkillShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the skillList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultSkillShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllSkillsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultSkillShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the skillList where updatedAt equals to UPDATED_UPDATED_AT
        defaultSkillShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllSkillsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where updatedAt is not null
        defaultSkillShouldBeFound("updatedAt.specified=true");

        // Get all the skillList where updatedAt is null
        defaultSkillShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByBadgesIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);
        BadgeSkill badges = BadgeSkillResourceIT.createEntity(em);
        em.persist(badges);
        em.flush();
        skill.addBadges(badges);
        skillRepository.saveAndFlush(skill);
        Long badgesId = badges.getId();

        // Get all the skillList where badges equals to badgesId
        defaultSkillShouldBeFound("badgesId.equals=" + badgesId);

        // Get all the skillList where badges equals to (badgesId + 1)
        defaultSkillShouldNotBeFound("badgesId.equals=" + (badgesId + 1));
    }

    @Test
    @Transactional
    void getAllSkillsByLevelsIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);
        LevelSkill levels = LevelSkillResourceIT.createEntity(em);
        em.persist(levels);
        em.flush();
        skill.addLevels(levels);
        skillRepository.saveAndFlush(skill);
        Long levelsId = levels.getId();

        // Get all the skillList where levels equals to levelsId
        defaultSkillShouldBeFound("levelsId.equals=" + levelsId);

        // Get all the skillList where levels equals to (levelsId + 1)
        defaultSkillShouldNotBeFound("levelsId.equals=" + (levelsId + 1));
    }

    @Test
    @Transactional
    void getAllSkillsByTeamsIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);
        TeamSkill teams = TeamSkillResourceIT.createEntity(em);
        em.persist(teams);
        em.flush();
        skill.addTeams(teams);
        skillRepository.saveAndFlush(skill);
        Long teamsId = teams.getId();

        // Get all the skillList where teams equals to teamsId
        defaultSkillShouldBeFound("teamsId.equals=" + teamsId);

        // Get all the skillList where teams equals to (teamsId + 1)
        defaultSkillShouldNotBeFound("teamsId.equals=" + (teamsId + 1));
    }

    @Test
    @Transactional
    void getAllSkillsByTrainingsIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);
        Training trainings = TrainingResourceIT.createEntity(em);
        em.persist(trainings);
        em.flush();
        skill.addTrainings(trainings);
        skillRepository.saveAndFlush(skill);
        Long trainingsId = trainings.getId();

        // Get all the skillList where trainings equals to trainingsId
        defaultSkillShouldBeFound("trainingsId.equals=" + trainingsId);

        // Get all the skillList where trainings equals to (trainingsId + 1)
        defaultSkillShouldNotBeFound("trainingsId.equals=" + (trainingsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSkillShouldBeFound(String filter) throws Exception {
        restSkillMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skill.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].implementation").value(hasItem(DEFAULT_IMPLEMENTATION)))
            .andExpect(jsonPath("$.[*].validation").value(hasItem(DEFAULT_VALIDATION)))
            .andExpect(jsonPath("$.[*].expiryPeriod").value(hasItem(DEFAULT_EXPIRY_PERIOD)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].rateScore").value(hasItem(DEFAULT_RATE_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].rateCount").value(hasItem(DEFAULT_RATE_COUNT)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));

        // Check, that the count call also returns 1
        restSkillMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSkillShouldNotBeFound(String filter) throws Exception {
        restSkillMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSkillMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSkill() throws Exception {
        // Get the skill
        restSkillMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSkill() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        int databaseSizeBeforeUpdate = skillRepository.findAll().size();

        // Update the skill
        Skill updatedSkill = skillRepository.findById(skill.getId()).get();
        // Disconnect from session so that the updates on updatedSkill are not directly saved in db
        em.detach(updatedSkill);
        updatedSkill
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .implementation(UPDATED_IMPLEMENTATION)
            .validation(UPDATED_VALIDATION)
            .expiryPeriod(UPDATED_EXPIRY_PERIOD)
            .contact(UPDATED_CONTACT)
            .score(UPDATED_SCORE)
            .rateScore(UPDATED_RATE_SCORE)
            .rateCount(UPDATED_RATE_COUNT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        SkillDTO skillDTO = skillMapper.toDto(updatedSkill);

        restSkillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, skillDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isOk());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
        Skill testSkill = skillList.get(skillList.size() - 1);
        assertThat(testSkill.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSkill.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSkill.getImplementation()).isEqualTo(UPDATED_IMPLEMENTATION);
        assertThat(testSkill.getValidation()).isEqualTo(UPDATED_VALIDATION);
        assertThat(testSkill.getExpiryPeriod()).isEqualTo(UPDATED_EXPIRY_PERIOD);
        assertThat(testSkill.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testSkill.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testSkill.getRateScore()).isEqualTo(UPDATED_RATE_SCORE);
        assertThat(testSkill.getRateCount()).isEqualTo(UPDATED_RATE_COUNT);
        assertThat(testSkill.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testSkill.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingSkill() throws Exception {
        int databaseSizeBeforeUpdate = skillRepository.findAll().size();
        skill.setId(count.incrementAndGet());

        // Create the Skill
        SkillDTO skillDTO = skillMapper.toDto(skill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, skillDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSkill() throws Exception {
        int databaseSizeBeforeUpdate = skillRepository.findAll().size();
        skill.setId(count.incrementAndGet());

        // Create the Skill
        SkillDTO skillDTO = skillMapper.toDto(skill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSkill() throws Exception {
        int databaseSizeBeforeUpdate = skillRepository.findAll().size();
        skill.setId(count.incrementAndGet());

        // Create the Skill
        SkillDTO skillDTO = skillMapper.toDto(skill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSkillWithPatch() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        int databaseSizeBeforeUpdate = skillRepository.findAll().size();

        // Update the skill using partial update
        Skill partialUpdatedSkill = new Skill();
        partialUpdatedSkill.setId(skill.getId());

        partialUpdatedSkill
            .title(UPDATED_TITLE)
            .implementation(UPDATED_IMPLEMENTATION)
            .score(UPDATED_SCORE)
            .rateScore(UPDATED_RATE_SCORE)
            .rateCount(UPDATED_RATE_COUNT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSkill.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSkill))
            )
            .andExpect(status().isOk());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
        Skill testSkill = skillList.get(skillList.size() - 1);
        assertThat(testSkill.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSkill.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSkill.getImplementation()).isEqualTo(UPDATED_IMPLEMENTATION);
        assertThat(testSkill.getValidation()).isEqualTo(DEFAULT_VALIDATION);
        assertThat(testSkill.getExpiryPeriod()).isEqualTo(DEFAULT_EXPIRY_PERIOD);
        assertThat(testSkill.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testSkill.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testSkill.getRateScore()).isEqualTo(UPDATED_RATE_SCORE);
        assertThat(testSkill.getRateCount()).isEqualTo(UPDATED_RATE_COUNT);
        assertThat(testSkill.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testSkill.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateSkillWithPatch() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        int databaseSizeBeforeUpdate = skillRepository.findAll().size();

        // Update the skill using partial update
        Skill partialUpdatedSkill = new Skill();
        partialUpdatedSkill.setId(skill.getId());

        partialUpdatedSkill
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .implementation(UPDATED_IMPLEMENTATION)
            .validation(UPDATED_VALIDATION)
            .expiryPeriod(UPDATED_EXPIRY_PERIOD)
            .contact(UPDATED_CONTACT)
            .score(UPDATED_SCORE)
            .rateScore(UPDATED_RATE_SCORE)
            .rateCount(UPDATED_RATE_COUNT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSkill.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSkill))
            )
            .andExpect(status().isOk());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
        Skill testSkill = skillList.get(skillList.size() - 1);
        assertThat(testSkill.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSkill.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSkill.getImplementation()).isEqualTo(UPDATED_IMPLEMENTATION);
        assertThat(testSkill.getValidation()).isEqualTo(UPDATED_VALIDATION);
        assertThat(testSkill.getExpiryPeriod()).isEqualTo(UPDATED_EXPIRY_PERIOD);
        assertThat(testSkill.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testSkill.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testSkill.getRateScore()).isEqualTo(UPDATED_RATE_SCORE);
        assertThat(testSkill.getRateCount()).isEqualTo(UPDATED_RATE_COUNT);
        assertThat(testSkill.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testSkill.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingSkill() throws Exception {
        int databaseSizeBeforeUpdate = skillRepository.findAll().size();
        skill.setId(count.incrementAndGet());

        // Create the Skill
        SkillDTO skillDTO = skillMapper.toDto(skill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, skillDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSkill() throws Exception {
        int databaseSizeBeforeUpdate = skillRepository.findAll().size();
        skill.setId(count.incrementAndGet());

        // Create the Skill
        SkillDTO skillDTO = skillMapper.toDto(skill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSkill() throws Exception {
        int databaseSizeBeforeUpdate = skillRepository.findAll().size();
        skill.setId(count.incrementAndGet());

        // Create the Skill
        SkillDTO skillDTO = skillMapper.toDto(skill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(skillDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSkill() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        int databaseSizeBeforeDelete = skillRepository.findAll().size();

        // Delete the skill
        restSkillMockMvc
            .perform(delete(ENTITY_API_URL_ID, skill.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
