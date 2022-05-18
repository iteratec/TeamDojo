package com.iteratec.teamdojo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.IntegrationTest;
import com.iteratec.teamdojo.domain.BadgeSkill;
import com.iteratec.teamdojo.domain.LevelSkill;
import com.iteratec.teamdojo.domain.Skill;
import com.iteratec.teamdojo.domain.TeamSkill;
import com.iteratec.teamdojo.domain.Training;
import com.iteratec.teamdojo.repository.SkillRepository;
import com.iteratec.teamdojo.service.criteria.SkillCriteria;
// ### MODIFICATION-START ###
import com.iteratec.teamdojo.service.custom.ExtendedSkillService;
// ### MODIFICATION-END ###
import com.iteratec.teamdojo.service.dto.SkillDTO;
import com.iteratec.teamdojo.service.mapper.SkillMapper;
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
 * Integration tests for the {@link SkillResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
@GeneratedByJHipster
class SkillResourceIT {

    private static final String DEFAULT_TITLE_EN = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_EN = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_DE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_DE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_EN = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_EN = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_DE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_DE = "BBBBBBBBBB";

    private static final String DEFAULT_IMPLEMENTATION_EN = "AAAAAAAAAA";
    private static final String UPDATED_IMPLEMENTATION_EN = "BBBBBBBBBB";

    private static final String DEFAULT_IMPLEMENTATION_DE = "AAAAAAAAAA";
    private static final String UPDATED_IMPLEMENTATION_DE = "BBBBBBBBBB";

    private static final String DEFAULT_VALIDATION_EN = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATION_EN = "BBBBBBBBBB";

    private static final String DEFAULT_VALIDATION_DE = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATION_DE = "BBBBBBBBBB";

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

    // ### MODIFICATION-START ###
    private static final Instant CUSTOM_CREATED_AND_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    // ### MODIFICATION-END ###

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

    // ### MODIFICATION-START ###
    @Autowired
    private ExtendedSkillService extendedSkillService;

    // ### MODIFICATION-END ###

    private Skill skill;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Skill createEntity(EntityManager em) {
        Skill skill = new Skill()
            .titleEN(DEFAULT_TITLE_EN)
            .titleDE(DEFAULT_TITLE_DE)
            .descriptionEN(DEFAULT_DESCRIPTION_EN)
            .descriptionDE(DEFAULT_DESCRIPTION_DE)
            .implementationEN(DEFAULT_IMPLEMENTATION_EN)
            .implementationDE(DEFAULT_IMPLEMENTATION_DE)
            .validationEN(DEFAULT_VALIDATION_EN)
            .validationDE(DEFAULT_VALIDATION_DE)
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
            .titleEN(UPDATED_TITLE_EN)
            .titleDE(UPDATED_TITLE_DE)
            .descriptionEN(UPDATED_DESCRIPTION_EN)
            .descriptionDE(UPDATED_DESCRIPTION_DE)
            .implementationEN(UPDATED_IMPLEMENTATION_EN)
            .implementationDE(UPDATED_IMPLEMENTATION_DE)
            .validationEN(UPDATED_VALIDATION_EN)
            .validationDE(UPDATED_VALIDATION_DE)
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

    // ### MODIFICATION-START ###
    @BeforeEach
    public void initInstantProvider() {
        extendedSkillService.setTime(StaticInstantProvider.forFixedTime(CUSTOM_CREATED_AND_UPDATED_AT));
    }

    // ### MODIFICATION-END ###

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
        assertThat(testSkill.getTitleEN()).isEqualTo(DEFAULT_TITLE_EN);
        assertThat(testSkill.getTitleDE()).isEqualTo(DEFAULT_TITLE_DE);
        assertThat(testSkill.getDescriptionEN()).isEqualTo(DEFAULT_DESCRIPTION_EN);
        assertThat(testSkill.getDescriptionDE()).isEqualTo(DEFAULT_DESCRIPTION_DE);
        assertThat(testSkill.getImplementationEN()).isEqualTo(DEFAULT_IMPLEMENTATION_EN);
        assertThat(testSkill.getImplementationDE()).isEqualTo(DEFAULT_IMPLEMENTATION_DE);
        assertThat(testSkill.getValidationEN()).isEqualTo(DEFAULT_VALIDATION_EN);
        assertThat(testSkill.getValidationDE()).isEqualTo(DEFAULT_VALIDATION_DE);
        assertThat(testSkill.getExpiryPeriod()).isEqualTo(DEFAULT_EXPIRY_PERIOD);
        assertThat(testSkill.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testSkill.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testSkill.getRateScore()).isEqualTo(DEFAULT_RATE_SCORE);
        assertThat(testSkill.getRateCount()).isEqualTo(DEFAULT_RATE_COUNT);
        // ### MODIFICATION-START ###
        assertThat(testSkill.getCreatedAt()).isEqualTo(CUSTOM_CREATED_AND_UPDATED_AT);
        assertThat(testSkill.getUpdatedAt()).isEqualTo(CUSTOM_CREATED_AND_UPDATED_AT);
        // ### MODIFICATION-END ###
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
    void checkTitleENIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillRepository.findAll().size();
        // set the field null
        skill.setTitleEN(null);

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
            .andExpect(jsonPath("$.[*].titleEN").value(hasItem(DEFAULT_TITLE_EN)))
            .andExpect(jsonPath("$.[*].titleDE").value(hasItem(DEFAULT_TITLE_DE)))
            .andExpect(jsonPath("$.[*].descriptionEN").value(hasItem(DEFAULT_DESCRIPTION_EN)))
            .andExpect(jsonPath("$.[*].descriptionDE").value(hasItem(DEFAULT_DESCRIPTION_DE)))
            .andExpect(jsonPath("$.[*].implementationEN").value(hasItem(DEFAULT_IMPLEMENTATION_EN)))
            .andExpect(jsonPath("$.[*].implementationDE").value(hasItem(DEFAULT_IMPLEMENTATION_DE)))
            .andExpect(jsonPath("$.[*].validationEN").value(hasItem(DEFAULT_VALIDATION_EN)))
            .andExpect(jsonPath("$.[*].validationDE").value(hasItem(DEFAULT_VALIDATION_DE)))
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
            .andExpect(jsonPath("$.titleEN").value(DEFAULT_TITLE_EN))
            .andExpect(jsonPath("$.titleDE").value(DEFAULT_TITLE_DE))
            .andExpect(jsonPath("$.descriptionEN").value(DEFAULT_DESCRIPTION_EN))
            .andExpect(jsonPath("$.descriptionDE").value(DEFAULT_DESCRIPTION_DE))
            .andExpect(jsonPath("$.implementationEN").value(DEFAULT_IMPLEMENTATION_EN))
            .andExpect(jsonPath("$.implementationDE").value(DEFAULT_IMPLEMENTATION_DE))
            .andExpect(jsonPath("$.validationEN").value(DEFAULT_VALIDATION_EN))
            .andExpect(jsonPath("$.validationDE").value(DEFAULT_VALIDATION_DE))
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
    void getAllSkillsByTitleENIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where titleEN equals to DEFAULT_TITLE_EN
        defaultSkillShouldBeFound("titleEN.equals=" + DEFAULT_TITLE_EN);

        // Get all the skillList where titleEN equals to UPDATED_TITLE_EN
        defaultSkillShouldNotBeFound("titleEN.equals=" + UPDATED_TITLE_EN);
    }

    @Test
    @Transactional
    void getAllSkillsByTitleENIsNotEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where titleEN not equals to DEFAULT_TITLE_EN
        defaultSkillShouldNotBeFound("titleEN.notEquals=" + DEFAULT_TITLE_EN);

        // Get all the skillList where titleEN not equals to UPDATED_TITLE_EN
        defaultSkillShouldBeFound("titleEN.notEquals=" + UPDATED_TITLE_EN);
    }

    @Test
    @Transactional
    void getAllSkillsByTitleENIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where titleEN in DEFAULT_TITLE_EN or UPDATED_TITLE_EN
        defaultSkillShouldBeFound("titleEN.in=" + DEFAULT_TITLE_EN + "," + UPDATED_TITLE_EN);

        // Get all the skillList where titleEN equals to UPDATED_TITLE_EN
        defaultSkillShouldNotBeFound("titleEN.in=" + UPDATED_TITLE_EN);
    }

    @Test
    @Transactional
    void getAllSkillsByTitleENIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where titleEN is not null
        defaultSkillShouldBeFound("titleEN.specified=true");

        // Get all the skillList where titleEN is null
        defaultSkillShouldNotBeFound("titleEN.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByTitleENContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where titleEN contains DEFAULT_TITLE_EN
        defaultSkillShouldBeFound("titleEN.contains=" + DEFAULT_TITLE_EN);

        // Get all the skillList where titleEN contains UPDATED_TITLE_EN
        defaultSkillShouldNotBeFound("titleEN.contains=" + UPDATED_TITLE_EN);
    }

    @Test
    @Transactional
    void getAllSkillsByTitleENNotContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where titleEN does not contain DEFAULT_TITLE_EN
        defaultSkillShouldNotBeFound("titleEN.doesNotContain=" + DEFAULT_TITLE_EN);

        // Get all the skillList where titleEN does not contain UPDATED_TITLE_EN
        defaultSkillShouldBeFound("titleEN.doesNotContain=" + UPDATED_TITLE_EN);
    }

    @Test
    @Transactional
    void getAllSkillsByTitleDEIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where titleDE equals to DEFAULT_TITLE_DE
        defaultSkillShouldBeFound("titleDE.equals=" + DEFAULT_TITLE_DE);

        // Get all the skillList where titleDE equals to UPDATED_TITLE_DE
        defaultSkillShouldNotBeFound("titleDE.equals=" + UPDATED_TITLE_DE);
    }

    @Test
    @Transactional
    void getAllSkillsByTitleDEIsNotEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where titleDE not equals to DEFAULT_TITLE_DE
        defaultSkillShouldNotBeFound("titleDE.notEquals=" + DEFAULT_TITLE_DE);

        // Get all the skillList where titleDE not equals to UPDATED_TITLE_DE
        defaultSkillShouldBeFound("titleDE.notEquals=" + UPDATED_TITLE_DE);
    }

    @Test
    @Transactional
    void getAllSkillsByTitleDEIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where titleDE in DEFAULT_TITLE_DE or UPDATED_TITLE_DE
        defaultSkillShouldBeFound("titleDE.in=" + DEFAULT_TITLE_DE + "," + UPDATED_TITLE_DE);

        // Get all the skillList where titleDE equals to UPDATED_TITLE_DE
        defaultSkillShouldNotBeFound("titleDE.in=" + UPDATED_TITLE_DE);
    }

    @Test
    @Transactional
    void getAllSkillsByTitleDEIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where titleDE is not null
        defaultSkillShouldBeFound("titleDE.specified=true");

        // Get all the skillList where titleDE is null
        defaultSkillShouldNotBeFound("titleDE.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByTitleDEContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where titleDE contains DEFAULT_TITLE_DE
        defaultSkillShouldBeFound("titleDE.contains=" + DEFAULT_TITLE_DE);

        // Get all the skillList where titleDE contains UPDATED_TITLE_DE
        defaultSkillShouldNotBeFound("titleDE.contains=" + UPDATED_TITLE_DE);
    }

    @Test
    @Transactional
    void getAllSkillsByTitleDENotContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where titleDE does not contain DEFAULT_TITLE_DE
        defaultSkillShouldNotBeFound("titleDE.doesNotContain=" + DEFAULT_TITLE_DE);

        // Get all the skillList where titleDE does not contain UPDATED_TITLE_DE
        defaultSkillShouldBeFound("titleDE.doesNotContain=" + UPDATED_TITLE_DE);
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionENIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where descriptionEN equals to DEFAULT_DESCRIPTION_EN
        defaultSkillShouldBeFound("descriptionEN.equals=" + DEFAULT_DESCRIPTION_EN);

        // Get all the skillList where descriptionEN equals to UPDATED_DESCRIPTION_EN
        defaultSkillShouldNotBeFound("descriptionEN.equals=" + UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionENIsNotEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where descriptionEN not equals to DEFAULT_DESCRIPTION_EN
        defaultSkillShouldNotBeFound("descriptionEN.notEquals=" + DEFAULT_DESCRIPTION_EN);

        // Get all the skillList where descriptionEN not equals to UPDATED_DESCRIPTION_EN
        defaultSkillShouldBeFound("descriptionEN.notEquals=" + UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionENIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where descriptionEN in DEFAULT_DESCRIPTION_EN or UPDATED_DESCRIPTION_EN
        defaultSkillShouldBeFound("descriptionEN.in=" + DEFAULT_DESCRIPTION_EN + "," + UPDATED_DESCRIPTION_EN);

        // Get all the skillList where descriptionEN equals to UPDATED_DESCRIPTION_EN
        defaultSkillShouldNotBeFound("descriptionEN.in=" + UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionENIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where descriptionEN is not null
        defaultSkillShouldBeFound("descriptionEN.specified=true");

        // Get all the skillList where descriptionEN is null
        defaultSkillShouldNotBeFound("descriptionEN.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionENContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where descriptionEN contains DEFAULT_DESCRIPTION_EN
        defaultSkillShouldBeFound("descriptionEN.contains=" + DEFAULT_DESCRIPTION_EN);

        // Get all the skillList where descriptionEN contains UPDATED_DESCRIPTION_EN
        defaultSkillShouldNotBeFound("descriptionEN.contains=" + UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionENNotContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where descriptionEN does not contain DEFAULT_DESCRIPTION_EN
        defaultSkillShouldNotBeFound("descriptionEN.doesNotContain=" + DEFAULT_DESCRIPTION_EN);

        // Get all the skillList where descriptionEN does not contain UPDATED_DESCRIPTION_EN
        defaultSkillShouldBeFound("descriptionEN.doesNotContain=" + UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionDEIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where descriptionDE equals to DEFAULT_DESCRIPTION_DE
        defaultSkillShouldBeFound("descriptionDE.equals=" + DEFAULT_DESCRIPTION_DE);

        // Get all the skillList where descriptionDE equals to UPDATED_DESCRIPTION_DE
        defaultSkillShouldNotBeFound("descriptionDE.equals=" + UPDATED_DESCRIPTION_DE);
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionDEIsNotEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where descriptionDE not equals to DEFAULT_DESCRIPTION_DE
        defaultSkillShouldNotBeFound("descriptionDE.notEquals=" + DEFAULT_DESCRIPTION_DE);

        // Get all the skillList where descriptionDE not equals to UPDATED_DESCRIPTION_DE
        defaultSkillShouldBeFound("descriptionDE.notEquals=" + UPDATED_DESCRIPTION_DE);
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionDEIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where descriptionDE in DEFAULT_DESCRIPTION_DE or UPDATED_DESCRIPTION_DE
        defaultSkillShouldBeFound("descriptionDE.in=" + DEFAULT_DESCRIPTION_DE + "," + UPDATED_DESCRIPTION_DE);

        // Get all the skillList where descriptionDE equals to UPDATED_DESCRIPTION_DE
        defaultSkillShouldNotBeFound("descriptionDE.in=" + UPDATED_DESCRIPTION_DE);
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionDEIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where descriptionDE is not null
        defaultSkillShouldBeFound("descriptionDE.specified=true");

        // Get all the skillList where descriptionDE is null
        defaultSkillShouldNotBeFound("descriptionDE.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionDEContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where descriptionDE contains DEFAULT_DESCRIPTION_DE
        defaultSkillShouldBeFound("descriptionDE.contains=" + DEFAULT_DESCRIPTION_DE);

        // Get all the skillList where descriptionDE contains UPDATED_DESCRIPTION_DE
        defaultSkillShouldNotBeFound("descriptionDE.contains=" + UPDATED_DESCRIPTION_DE);
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionDENotContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where descriptionDE does not contain DEFAULT_DESCRIPTION_DE
        defaultSkillShouldNotBeFound("descriptionDE.doesNotContain=" + DEFAULT_DESCRIPTION_DE);

        // Get all the skillList where descriptionDE does not contain UPDATED_DESCRIPTION_DE
        defaultSkillShouldBeFound("descriptionDE.doesNotContain=" + UPDATED_DESCRIPTION_DE);
    }

    @Test
    @Transactional
    void getAllSkillsByImplementationENIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where implementationEN equals to DEFAULT_IMPLEMENTATION_EN
        defaultSkillShouldBeFound("implementationEN.equals=" + DEFAULT_IMPLEMENTATION_EN);

        // Get all the skillList where implementationEN equals to UPDATED_IMPLEMENTATION_EN
        defaultSkillShouldNotBeFound("implementationEN.equals=" + UPDATED_IMPLEMENTATION_EN);
    }

    @Test
    @Transactional
    void getAllSkillsByImplementationENIsNotEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where implementationEN not equals to DEFAULT_IMPLEMENTATION_EN
        defaultSkillShouldNotBeFound("implementationEN.notEquals=" + DEFAULT_IMPLEMENTATION_EN);

        // Get all the skillList where implementationEN not equals to UPDATED_IMPLEMENTATION_EN
        defaultSkillShouldBeFound("implementationEN.notEquals=" + UPDATED_IMPLEMENTATION_EN);
    }

    @Test
    @Transactional
    void getAllSkillsByImplementationENIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where implementationEN in DEFAULT_IMPLEMENTATION_EN or UPDATED_IMPLEMENTATION_EN
        defaultSkillShouldBeFound("implementationEN.in=" + DEFAULT_IMPLEMENTATION_EN + "," + UPDATED_IMPLEMENTATION_EN);

        // Get all the skillList where implementationEN equals to UPDATED_IMPLEMENTATION_EN
        defaultSkillShouldNotBeFound("implementationEN.in=" + UPDATED_IMPLEMENTATION_EN);
    }

    @Test
    @Transactional
    void getAllSkillsByImplementationENIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where implementationEN is not null
        defaultSkillShouldBeFound("implementationEN.specified=true");

        // Get all the skillList where implementationEN is null
        defaultSkillShouldNotBeFound("implementationEN.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByImplementationENContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where implementationEN contains DEFAULT_IMPLEMENTATION_EN
        defaultSkillShouldBeFound("implementationEN.contains=" + DEFAULT_IMPLEMENTATION_EN);

        // Get all the skillList where implementationEN contains UPDATED_IMPLEMENTATION_EN
        defaultSkillShouldNotBeFound("implementationEN.contains=" + UPDATED_IMPLEMENTATION_EN);
    }

    @Test
    @Transactional
    void getAllSkillsByImplementationENNotContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where implementationEN does not contain DEFAULT_IMPLEMENTATION_EN
        defaultSkillShouldNotBeFound("implementationEN.doesNotContain=" + DEFAULT_IMPLEMENTATION_EN);

        // Get all the skillList where implementationEN does not contain UPDATED_IMPLEMENTATION_EN
        defaultSkillShouldBeFound("implementationEN.doesNotContain=" + UPDATED_IMPLEMENTATION_EN);
    }

    @Test
    @Transactional
    void getAllSkillsByImplementationDEIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where implementationDE equals to DEFAULT_IMPLEMENTATION_DE
        defaultSkillShouldBeFound("implementationDE.equals=" + DEFAULT_IMPLEMENTATION_DE);

        // Get all the skillList where implementationDE equals to UPDATED_IMPLEMENTATION_DE
        defaultSkillShouldNotBeFound("implementationDE.equals=" + UPDATED_IMPLEMENTATION_DE);
    }

    @Test
    @Transactional
    void getAllSkillsByImplementationDEIsNotEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where implementationDE not equals to DEFAULT_IMPLEMENTATION_DE
        defaultSkillShouldNotBeFound("implementationDE.notEquals=" + DEFAULT_IMPLEMENTATION_DE);

        // Get all the skillList where implementationDE not equals to UPDATED_IMPLEMENTATION_DE
        defaultSkillShouldBeFound("implementationDE.notEquals=" + UPDATED_IMPLEMENTATION_DE);
    }

    @Test
    @Transactional
    void getAllSkillsByImplementationDEIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where implementationDE in DEFAULT_IMPLEMENTATION_DE or UPDATED_IMPLEMENTATION_DE
        defaultSkillShouldBeFound("implementationDE.in=" + DEFAULT_IMPLEMENTATION_DE + "," + UPDATED_IMPLEMENTATION_DE);

        // Get all the skillList where implementationDE equals to UPDATED_IMPLEMENTATION_DE
        defaultSkillShouldNotBeFound("implementationDE.in=" + UPDATED_IMPLEMENTATION_DE);
    }

    @Test
    @Transactional
    void getAllSkillsByImplementationDEIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where implementationDE is not null
        defaultSkillShouldBeFound("implementationDE.specified=true");

        // Get all the skillList where implementationDE is null
        defaultSkillShouldNotBeFound("implementationDE.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByImplementationDEContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where implementationDE contains DEFAULT_IMPLEMENTATION_DE
        defaultSkillShouldBeFound("implementationDE.contains=" + DEFAULT_IMPLEMENTATION_DE);

        // Get all the skillList where implementationDE contains UPDATED_IMPLEMENTATION_DE
        defaultSkillShouldNotBeFound("implementationDE.contains=" + UPDATED_IMPLEMENTATION_DE);
    }

    @Test
    @Transactional
    void getAllSkillsByImplementationDENotContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where implementationDE does not contain DEFAULT_IMPLEMENTATION_DE
        defaultSkillShouldNotBeFound("implementationDE.doesNotContain=" + DEFAULT_IMPLEMENTATION_DE);

        // Get all the skillList where implementationDE does not contain UPDATED_IMPLEMENTATION_DE
        defaultSkillShouldBeFound("implementationDE.doesNotContain=" + UPDATED_IMPLEMENTATION_DE);
    }

    @Test
    @Transactional
    void getAllSkillsByValidationENIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where validationEN equals to DEFAULT_VALIDATION_EN
        defaultSkillShouldBeFound("validationEN.equals=" + DEFAULT_VALIDATION_EN);

        // Get all the skillList where validationEN equals to UPDATED_VALIDATION_EN
        defaultSkillShouldNotBeFound("validationEN.equals=" + UPDATED_VALIDATION_EN);
    }

    @Test
    @Transactional
    void getAllSkillsByValidationENIsNotEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where validationEN not equals to DEFAULT_VALIDATION_EN
        defaultSkillShouldNotBeFound("validationEN.notEquals=" + DEFAULT_VALIDATION_EN);

        // Get all the skillList where validationEN not equals to UPDATED_VALIDATION_EN
        defaultSkillShouldBeFound("validationEN.notEquals=" + UPDATED_VALIDATION_EN);
    }

    @Test
    @Transactional
    void getAllSkillsByValidationENIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where validationEN in DEFAULT_VALIDATION_EN or UPDATED_VALIDATION_EN
        defaultSkillShouldBeFound("validationEN.in=" + DEFAULT_VALIDATION_EN + "," + UPDATED_VALIDATION_EN);

        // Get all the skillList where validationEN equals to UPDATED_VALIDATION_EN
        defaultSkillShouldNotBeFound("validationEN.in=" + UPDATED_VALIDATION_EN);
    }

    @Test
    @Transactional
    void getAllSkillsByValidationENIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where validationEN is not null
        defaultSkillShouldBeFound("validationEN.specified=true");

        // Get all the skillList where validationEN is null
        defaultSkillShouldNotBeFound("validationEN.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByValidationENContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where validationEN contains DEFAULT_VALIDATION_EN
        defaultSkillShouldBeFound("validationEN.contains=" + DEFAULT_VALIDATION_EN);

        // Get all the skillList where validationEN contains UPDATED_VALIDATION_EN
        defaultSkillShouldNotBeFound("validationEN.contains=" + UPDATED_VALIDATION_EN);
    }

    @Test
    @Transactional
    void getAllSkillsByValidationENNotContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where validationEN does not contain DEFAULT_VALIDATION_EN
        defaultSkillShouldNotBeFound("validationEN.doesNotContain=" + DEFAULT_VALIDATION_EN);

        // Get all the skillList where validationEN does not contain UPDATED_VALIDATION_EN
        defaultSkillShouldBeFound("validationEN.doesNotContain=" + UPDATED_VALIDATION_EN);
    }

    @Test
    @Transactional
    void getAllSkillsByValidationDEIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where validationDE equals to DEFAULT_VALIDATION_DE
        defaultSkillShouldBeFound("validationDE.equals=" + DEFAULT_VALIDATION_DE);

        // Get all the skillList where validationDE equals to UPDATED_VALIDATION_DE
        defaultSkillShouldNotBeFound("validationDE.equals=" + UPDATED_VALIDATION_DE);
    }

    @Test
    @Transactional
    void getAllSkillsByValidationDEIsNotEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where validationDE not equals to DEFAULT_VALIDATION_DE
        defaultSkillShouldNotBeFound("validationDE.notEquals=" + DEFAULT_VALIDATION_DE);

        // Get all the skillList where validationDE not equals to UPDATED_VALIDATION_DE
        defaultSkillShouldBeFound("validationDE.notEquals=" + UPDATED_VALIDATION_DE);
    }

    @Test
    @Transactional
    void getAllSkillsByValidationDEIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where validationDE in DEFAULT_VALIDATION_DE or UPDATED_VALIDATION_DE
        defaultSkillShouldBeFound("validationDE.in=" + DEFAULT_VALIDATION_DE + "," + UPDATED_VALIDATION_DE);

        // Get all the skillList where validationDE equals to UPDATED_VALIDATION_DE
        defaultSkillShouldNotBeFound("validationDE.in=" + UPDATED_VALIDATION_DE);
    }

    @Test
    @Transactional
    void getAllSkillsByValidationDEIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where validationDE is not null
        defaultSkillShouldBeFound("validationDE.specified=true");

        // Get all the skillList where validationDE is null
        defaultSkillShouldNotBeFound("validationDE.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByValidationDEContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where validationDE contains DEFAULT_VALIDATION_DE
        defaultSkillShouldBeFound("validationDE.contains=" + DEFAULT_VALIDATION_DE);

        // Get all the skillList where validationDE contains UPDATED_VALIDATION_DE
        defaultSkillShouldNotBeFound("validationDE.contains=" + UPDATED_VALIDATION_DE);
    }

    @Test
    @Transactional
    void getAllSkillsByValidationDENotContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where validationDE does not contain DEFAULT_VALIDATION_DE
        defaultSkillShouldNotBeFound("validationDE.doesNotContain=" + DEFAULT_VALIDATION_DE);

        // Get all the skillList where validationDE does not contain UPDATED_VALIDATION_DE
        defaultSkillShouldBeFound("validationDE.doesNotContain=" + UPDATED_VALIDATION_DE);
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
        BadgeSkill badges;
        if (TestUtil.findAll(em, BadgeSkill.class).isEmpty()) {
            badges = BadgeSkillResourceIT.createEntity(em);
            em.persist(badges);
            em.flush();
        } else {
            badges = TestUtil.findAll(em, BadgeSkill.class).get(0);
        }
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
        LevelSkill levels;
        if (TestUtil.findAll(em, LevelSkill.class).isEmpty()) {
            levels = LevelSkillResourceIT.createEntity(em);
            em.persist(levels);
            em.flush();
        } else {
            levels = TestUtil.findAll(em, LevelSkill.class).get(0);
        }
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
        TeamSkill teams;
        if (TestUtil.findAll(em, TeamSkill.class).isEmpty()) {
            teams = TeamSkillResourceIT.createEntity(em);
            em.persist(teams);
            em.flush();
        } else {
            teams = TestUtil.findAll(em, TeamSkill.class).get(0);
        }
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
        Training trainings;
        if (TestUtil.findAll(em, Training.class).isEmpty()) {
            trainings = TrainingResourceIT.createEntity(em);
            em.persist(trainings);
            em.flush();
        } else {
            trainings = TestUtil.findAll(em, Training.class).get(0);
        }
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
            .andExpect(jsonPath("$.[*].titleEN").value(hasItem(DEFAULT_TITLE_EN)))
            .andExpect(jsonPath("$.[*].titleDE").value(hasItem(DEFAULT_TITLE_DE)))
            .andExpect(jsonPath("$.[*].descriptionEN").value(hasItem(DEFAULT_DESCRIPTION_EN)))
            .andExpect(jsonPath("$.[*].descriptionDE").value(hasItem(DEFAULT_DESCRIPTION_DE)))
            .andExpect(jsonPath("$.[*].implementationEN").value(hasItem(DEFAULT_IMPLEMENTATION_EN)))
            .andExpect(jsonPath("$.[*].implementationDE").value(hasItem(DEFAULT_IMPLEMENTATION_DE)))
            .andExpect(jsonPath("$.[*].validationEN").value(hasItem(DEFAULT_VALIDATION_EN)))
            .andExpect(jsonPath("$.[*].validationDE").value(hasItem(DEFAULT_VALIDATION_DE)))
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
            .titleEN(UPDATED_TITLE_EN)
            .titleDE(UPDATED_TITLE_DE)
            .descriptionEN(UPDATED_DESCRIPTION_EN)
            .descriptionDE(UPDATED_DESCRIPTION_DE)
            .implementationEN(UPDATED_IMPLEMENTATION_EN)
            .implementationDE(UPDATED_IMPLEMENTATION_DE)
            .validationEN(UPDATED_VALIDATION_EN)
            .validationDE(UPDATED_VALIDATION_DE)
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
        assertThat(testSkill.getTitleEN()).isEqualTo(UPDATED_TITLE_EN);
        assertThat(testSkill.getTitleDE()).isEqualTo(UPDATED_TITLE_DE);
        assertThat(testSkill.getDescriptionEN()).isEqualTo(UPDATED_DESCRIPTION_EN);
        assertThat(testSkill.getDescriptionDE()).isEqualTo(UPDATED_DESCRIPTION_DE);
        assertThat(testSkill.getImplementationEN()).isEqualTo(UPDATED_IMPLEMENTATION_EN);
        assertThat(testSkill.getImplementationDE()).isEqualTo(UPDATED_IMPLEMENTATION_DE);
        assertThat(testSkill.getValidationEN()).isEqualTo(UPDATED_VALIDATION_EN);
        assertThat(testSkill.getValidationDE()).isEqualTo(UPDATED_VALIDATION_DE);
        assertThat(testSkill.getExpiryPeriod()).isEqualTo(UPDATED_EXPIRY_PERIOD);
        assertThat(testSkill.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testSkill.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testSkill.getRateScore()).isEqualTo(UPDATED_RATE_SCORE);
        assertThat(testSkill.getRateCount()).isEqualTo(UPDATED_RATE_COUNT);
        // ### MODIFICATION-START ###
        assertThat(testSkill.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        // ### MODIFICATION-END ###
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
            .titleDE(UPDATED_TITLE_DE)
            .descriptionDE(UPDATED_DESCRIPTION_DE)
            .implementationEN(UPDATED_IMPLEMENTATION_EN)
            .validationEN(UPDATED_VALIDATION_EN)
            .validationDE(UPDATED_VALIDATION_DE)
            .expiryPeriod(UPDATED_EXPIRY_PERIOD)
            .contact(UPDATED_CONTACT)
            .score(UPDATED_SCORE)
            .rateCount(UPDATED_RATE_COUNT);

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
        assertThat(testSkill.getTitleEN()).isEqualTo(DEFAULT_TITLE_EN);
        assertThat(testSkill.getTitleDE()).isEqualTo(UPDATED_TITLE_DE);
        assertThat(testSkill.getDescriptionEN()).isEqualTo(DEFAULT_DESCRIPTION_EN);
        assertThat(testSkill.getDescriptionDE()).isEqualTo(UPDATED_DESCRIPTION_DE);
        assertThat(testSkill.getImplementationEN()).isEqualTo(UPDATED_IMPLEMENTATION_EN);
        assertThat(testSkill.getImplementationDE()).isEqualTo(DEFAULT_IMPLEMENTATION_DE);
        assertThat(testSkill.getValidationEN()).isEqualTo(UPDATED_VALIDATION_EN);
        assertThat(testSkill.getValidationDE()).isEqualTo(UPDATED_VALIDATION_DE);
        assertThat(testSkill.getExpiryPeriod()).isEqualTo(UPDATED_EXPIRY_PERIOD);
        assertThat(testSkill.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testSkill.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testSkill.getRateScore()).isEqualTo(DEFAULT_RATE_SCORE);
        assertThat(testSkill.getRateCount()).isEqualTo(UPDATED_RATE_COUNT);
        assertThat(testSkill.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testSkill.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
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
            .titleEN(UPDATED_TITLE_EN)
            .titleDE(UPDATED_TITLE_DE)
            .descriptionEN(UPDATED_DESCRIPTION_EN)
            .descriptionDE(UPDATED_DESCRIPTION_DE)
            .implementationEN(UPDATED_IMPLEMENTATION_EN)
            .implementationDE(UPDATED_IMPLEMENTATION_DE)
            .validationEN(UPDATED_VALIDATION_EN)
            .validationDE(UPDATED_VALIDATION_DE)
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
        assertThat(testSkill.getTitleEN()).isEqualTo(UPDATED_TITLE_EN);
        assertThat(testSkill.getTitleDE()).isEqualTo(UPDATED_TITLE_DE);
        assertThat(testSkill.getDescriptionEN()).isEqualTo(UPDATED_DESCRIPTION_EN);
        assertThat(testSkill.getDescriptionDE()).isEqualTo(UPDATED_DESCRIPTION_DE);
        assertThat(testSkill.getImplementationEN()).isEqualTo(UPDATED_IMPLEMENTATION_EN);
        assertThat(testSkill.getImplementationDE()).isEqualTo(UPDATED_IMPLEMENTATION_DE);
        assertThat(testSkill.getValidationEN()).isEqualTo(UPDATED_VALIDATION_EN);
        assertThat(testSkill.getValidationDE()).isEqualTo(UPDATED_VALIDATION_DE);
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
