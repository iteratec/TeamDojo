package com.iteratec.teamdojo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iteratec.teamdojo.IntegrationTest;
import com.iteratec.teamdojo.domain.Badge;
import com.iteratec.teamdojo.domain.Dimension;
import com.iteratec.teamdojo.domain.Level;
import com.iteratec.teamdojo.domain.Team;
import com.iteratec.teamdojo.repository.DimensionRepository;
import com.iteratec.teamdojo.service.criteria.DimensionCriteria;
import com.iteratec.teamdojo.service.dto.DimensionDTO;
import com.iteratec.teamdojo.service.mapper.DimensionMapper;
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
 * Integration tests for the {@link DimensionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DimensionResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private Dimension dimension;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dimension createEntity(EntityManager em) {
        Dimension dimension = new Dimension()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
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
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return dimension;
    }

    @BeforeEach
    public void initTest() {
        dimension = createEntity(em);
    }

    @Test
    @Transactional
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
        assertThat(testDimension.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDimension.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDimension.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testDimension.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
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
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = dimensionRepository.findAll().size();
        // set the field null
        dimension.setTitle(null);

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
    @Disabled("#42 ignored until issue is completely done")
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
    @Disabled("#42 ignored until issue is completely done")
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
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
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
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
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
    void getAllDimensionsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where title equals to DEFAULT_TITLE
        defaultDimensionShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the dimensionList where title equals to UPDATED_TITLE
        defaultDimensionShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllDimensionsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where title not equals to DEFAULT_TITLE
        defaultDimensionShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the dimensionList where title not equals to UPDATED_TITLE
        defaultDimensionShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllDimensionsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultDimensionShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the dimensionList where title equals to UPDATED_TITLE
        defaultDimensionShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllDimensionsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where title is not null
        defaultDimensionShouldBeFound("title.specified=true");

        // Get all the dimensionList where title is null
        defaultDimensionShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllDimensionsByTitleContainsSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where title contains DEFAULT_TITLE
        defaultDimensionShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the dimensionList where title contains UPDATED_TITLE
        defaultDimensionShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllDimensionsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where title does not contain DEFAULT_TITLE
        defaultDimensionShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the dimensionList where title does not contain UPDATED_TITLE
        defaultDimensionShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllDimensionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where description equals to DEFAULT_DESCRIPTION
        defaultDimensionShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the dimensionList where description equals to UPDATED_DESCRIPTION
        defaultDimensionShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDimensionsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where description not equals to DEFAULT_DESCRIPTION
        defaultDimensionShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the dimensionList where description not equals to UPDATED_DESCRIPTION
        defaultDimensionShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDimensionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultDimensionShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the dimensionList where description equals to UPDATED_DESCRIPTION
        defaultDimensionShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDimensionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where description is not null
        defaultDimensionShouldBeFound("description.specified=true");

        // Get all the dimensionList where description is null
        defaultDimensionShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllDimensionsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where description contains DEFAULT_DESCRIPTION
        defaultDimensionShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the dimensionList where description contains UPDATED_DESCRIPTION
        defaultDimensionShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDimensionsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where description does not contain DEFAULT_DESCRIPTION
        defaultDimensionShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the dimensionList where description does not contain UPDATED_DESCRIPTION
        defaultDimensionShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
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
        Level levels = LevelResourceIT.createEntity(em);
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
        Badge badges = BadgeResourceIT.createEntity(em);
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
        Team participants = TeamResourceIT.createEntity(em);
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
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
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
    void putNewDimension() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();

        // Update the dimension
        Dimension updatedDimension = dimensionRepository.findById(dimension.getId()).get();
        // Disconnect from session so that the updates on updatedDimension are not directly saved in db
        em.detach(updatedDimension);
        updatedDimension.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);
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
        assertThat(testDimension.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDimension.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDimension.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDimension.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
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
    void partialUpdateDimensionWithPatch() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();

        // Update the dimension using partial update
        Dimension partialUpdatedDimension = new Dimension();
        partialUpdatedDimension.setId(dimension.getId());

        partialUpdatedDimension.title(UPDATED_TITLE).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);

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
        assertThat(testDimension.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDimension.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDimension.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDimension.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateDimensionWithPatch() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();

        // Update the dimension using partial update
        Dimension partialUpdatedDimension = new Dimension();
        partialUpdatedDimension.setId(dimension.getId());

        partialUpdatedDimension
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
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
        assertThat(testDimension.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDimension.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDimension.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDimension.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
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
}
