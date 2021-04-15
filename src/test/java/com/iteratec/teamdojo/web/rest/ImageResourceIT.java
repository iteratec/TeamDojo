package com.iteratec.teamdojo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iteratec.teamdojo.IntegrationTest;
import com.iteratec.teamdojo.domain.Image;
import com.iteratec.teamdojo.repository.ImageRepository;
import com.iteratec.teamdojo.service.criteria.ImageCriteria;
import com.iteratec.teamdojo.service.dto.ImageDTO;
import com.iteratec.teamdojo.service.mapper.ImageMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ImageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ImageResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SMALL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SMALL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SMALL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SMALL_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_MEDIUM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MEDIUM = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_MEDIUM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MEDIUM_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_LARGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LARGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LARGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LARGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/images";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restImageMockMvc;

    private Image image;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Image createEntity(EntityManager em) {
        Image image = new Image()
            .title(DEFAULT_TITLE)
            .small(DEFAULT_SMALL)
            .smallContentType(DEFAULT_SMALL_CONTENT_TYPE)
            .medium(DEFAULT_MEDIUM)
            .mediumContentType(DEFAULT_MEDIUM_CONTENT_TYPE)
            .large(DEFAULT_LARGE)
            .largeContentType(DEFAULT_LARGE_CONTENT_TYPE)
            .hash(DEFAULT_HASH)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return image;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Image createUpdatedEntity(EntityManager em) {
        Image image = new Image()
            .title(UPDATED_TITLE)
            .small(UPDATED_SMALL)
            .smallContentType(UPDATED_SMALL_CONTENT_TYPE)
            .medium(UPDATED_MEDIUM)
            .mediumContentType(UPDATED_MEDIUM_CONTENT_TYPE)
            .large(UPDATED_LARGE)
            .largeContentType(UPDATED_LARGE_CONTENT_TYPE)
            .hash(UPDATED_HASH)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return image;
    }

    @BeforeEach
    public void initTest() {
        image = createEntity(em);
    }

    @Test
    @Transactional
    void createImage() throws Exception {
        int databaseSizeBeforeCreate = imageRepository.findAll().size();
        // Create the Image
        ImageDTO imageDTO = imageMapper.toDto(image);
        restImageMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeCreate + 1);
        Image testImage = imageList.get(imageList.size() - 1);
        assertThat(testImage.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testImage.getSmall()).isEqualTo(DEFAULT_SMALL);
        assertThat(testImage.getSmallContentType()).isEqualTo(DEFAULT_SMALL_CONTENT_TYPE);
        assertThat(testImage.getMedium()).isEqualTo(DEFAULT_MEDIUM);
        assertThat(testImage.getMediumContentType()).isEqualTo(DEFAULT_MEDIUM_CONTENT_TYPE);
        assertThat(testImage.getLarge()).isEqualTo(DEFAULT_LARGE);
        assertThat(testImage.getLargeContentType()).isEqualTo(DEFAULT_LARGE_CONTENT_TYPE);
        assertThat(testImage.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testImage.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testImage.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createImageWithExistingId() throws Exception {
        // Create the Image with an existing ID
        image.setId(1L);
        ImageDTO imageDTO = imageMapper.toDto(image);

        int databaseSizeBeforeCreate = imageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restImageMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageRepository.findAll().size();
        // set the field null
        image.setTitle(null);

        // Create the Image, which fails.
        ImageDTO imageDTO = imageMapper.toDto(image);

        restImageMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imageDTO))
            )
            .andExpect(status().isBadRequest());

        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageRepository.findAll().size();
        // set the field null
        image.setCreatedAt(null);

        // Create the Image, which fails.
        ImageDTO imageDTO = imageMapper.toDto(image);

        restImageMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imageDTO))
            )
            .andExpect(status().isBadRequest());

        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageRepository.findAll().size();
        // set the field null
        image.setUpdatedAt(null);

        // Create the Image, which fails.
        ImageDTO imageDTO = imageMapper.toDto(image);

        restImageMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imageDTO))
            )
            .andExpect(status().isBadRequest());

        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllImages() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList
        restImageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(image.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].smallContentType").value(hasItem(DEFAULT_SMALL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].small").value(hasItem(Base64Utils.encodeToString(DEFAULT_SMALL))))
            .andExpect(jsonPath("$.[*].mediumContentType").value(hasItem(DEFAULT_MEDIUM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].medium").value(hasItem(Base64Utils.encodeToString(DEFAULT_MEDIUM))))
            .andExpect(jsonPath("$.[*].largeContentType").value(hasItem(DEFAULT_LARGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].large").value(hasItem(Base64Utils.encodeToString(DEFAULT_LARGE))))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getImage() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get the image
        restImageMockMvc
            .perform(get(ENTITY_API_URL_ID, image.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(image.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.smallContentType").value(DEFAULT_SMALL_CONTENT_TYPE))
            .andExpect(jsonPath("$.small").value(Base64Utils.encodeToString(DEFAULT_SMALL)))
            .andExpect(jsonPath("$.mediumContentType").value(DEFAULT_MEDIUM_CONTENT_TYPE))
            .andExpect(jsonPath("$.medium").value(Base64Utils.encodeToString(DEFAULT_MEDIUM)))
            .andExpect(jsonPath("$.largeContentType").value(DEFAULT_LARGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.large").value(Base64Utils.encodeToString(DEFAULT_LARGE)))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getImagesByIdFiltering() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        Long id = image.getId();

        defaultImageShouldBeFound("id.equals=" + id);
        defaultImageShouldNotBeFound("id.notEquals=" + id);

        defaultImageShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultImageShouldNotBeFound("id.greaterThan=" + id);

        defaultImageShouldBeFound("id.lessThanOrEqual=" + id);
        defaultImageShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllImagesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where title equals to DEFAULT_TITLE
        defaultImageShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the imageList where title equals to UPDATED_TITLE
        defaultImageShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllImagesByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where title not equals to DEFAULT_TITLE
        defaultImageShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the imageList where title not equals to UPDATED_TITLE
        defaultImageShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllImagesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultImageShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the imageList where title equals to UPDATED_TITLE
        defaultImageShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllImagesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where title is not null
        defaultImageShouldBeFound("title.specified=true");

        // Get all the imageList where title is null
        defaultImageShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllImagesByTitleContainsSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where title contains DEFAULT_TITLE
        defaultImageShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the imageList where title contains UPDATED_TITLE
        defaultImageShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllImagesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where title does not contain DEFAULT_TITLE
        defaultImageShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the imageList where title does not contain UPDATED_TITLE
        defaultImageShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllImagesByHashIsEqualToSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where hash equals to DEFAULT_HASH
        defaultImageShouldBeFound("hash.equals=" + DEFAULT_HASH);

        // Get all the imageList where hash equals to UPDATED_HASH
        defaultImageShouldNotBeFound("hash.equals=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllImagesByHashIsNotEqualToSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where hash not equals to DEFAULT_HASH
        defaultImageShouldNotBeFound("hash.notEquals=" + DEFAULT_HASH);

        // Get all the imageList where hash not equals to UPDATED_HASH
        defaultImageShouldBeFound("hash.notEquals=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllImagesByHashIsInShouldWork() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where hash in DEFAULT_HASH or UPDATED_HASH
        defaultImageShouldBeFound("hash.in=" + DEFAULT_HASH + "," + UPDATED_HASH);

        // Get all the imageList where hash equals to UPDATED_HASH
        defaultImageShouldNotBeFound("hash.in=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllImagesByHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where hash is not null
        defaultImageShouldBeFound("hash.specified=true");

        // Get all the imageList where hash is null
        defaultImageShouldNotBeFound("hash.specified=false");
    }

    @Test
    @Transactional
    void getAllImagesByHashContainsSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where hash contains DEFAULT_HASH
        defaultImageShouldBeFound("hash.contains=" + DEFAULT_HASH);

        // Get all the imageList where hash contains UPDATED_HASH
        defaultImageShouldNotBeFound("hash.contains=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllImagesByHashNotContainsSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where hash does not contain DEFAULT_HASH
        defaultImageShouldNotBeFound("hash.doesNotContain=" + DEFAULT_HASH);

        // Get all the imageList where hash does not contain UPDATED_HASH
        defaultImageShouldBeFound("hash.doesNotContain=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    void getAllImagesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where createdAt equals to DEFAULT_CREATED_AT
        defaultImageShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the imageList where createdAt equals to UPDATED_CREATED_AT
        defaultImageShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllImagesByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where createdAt not equals to DEFAULT_CREATED_AT
        defaultImageShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the imageList where createdAt not equals to UPDATED_CREATED_AT
        defaultImageShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllImagesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultImageShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the imageList where createdAt equals to UPDATED_CREATED_AT
        defaultImageShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllImagesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where createdAt is not null
        defaultImageShouldBeFound("createdAt.specified=true");

        // Get all the imageList where createdAt is null
        defaultImageShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllImagesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultImageShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the imageList where updatedAt equals to UPDATED_UPDATED_AT
        defaultImageShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllImagesByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultImageShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the imageList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultImageShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllImagesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultImageShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the imageList where updatedAt equals to UPDATED_UPDATED_AT
        defaultImageShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void getAllImagesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where updatedAt is not null
        defaultImageShouldBeFound("updatedAt.specified=true");

        // Get all the imageList where updatedAt is null
        defaultImageShouldNotBeFound("updatedAt.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultImageShouldBeFound(String filter) throws Exception {
        restImageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(image.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].smallContentType").value(hasItem(DEFAULT_SMALL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].small").value(hasItem(Base64Utils.encodeToString(DEFAULT_SMALL))))
            .andExpect(jsonPath("$.[*].mediumContentType").value(hasItem(DEFAULT_MEDIUM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].medium").value(hasItem(Base64Utils.encodeToString(DEFAULT_MEDIUM))))
            .andExpect(jsonPath("$.[*].largeContentType").value(hasItem(DEFAULT_LARGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].large").value(hasItem(Base64Utils.encodeToString(DEFAULT_LARGE))))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));

        // Check, that the count call also returns 1
        restImageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultImageShouldNotBeFound(String filter) throws Exception {
        restImageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restImageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingImage() throws Exception {
        // Get the image
        restImageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewImage() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        int databaseSizeBeforeUpdate = imageRepository.findAll().size();

        // Update the image
        Image updatedImage = imageRepository.findById(image.getId()).get();
        // Disconnect from session so that the updates on updatedImage are not directly saved in db
        em.detach(updatedImage);
        updatedImage
            .title(UPDATED_TITLE)
            .small(UPDATED_SMALL)
            .smallContentType(UPDATED_SMALL_CONTENT_TYPE)
            .medium(UPDATED_MEDIUM)
            .mediumContentType(UPDATED_MEDIUM_CONTENT_TYPE)
            .large(UPDATED_LARGE)
            .largeContentType(UPDATED_LARGE_CONTENT_TYPE)
            .hash(UPDATED_HASH)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        ImageDTO imageDTO = imageMapper.toDto(updatedImage);

        restImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, imageDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imageDTO))
            )
            .andExpect(status().isOk());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeUpdate);
        Image testImage = imageList.get(imageList.size() - 1);
        assertThat(testImage.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testImage.getSmall()).isEqualTo(UPDATED_SMALL);
        assertThat(testImage.getSmallContentType()).isEqualTo(UPDATED_SMALL_CONTENT_TYPE);
        assertThat(testImage.getMedium()).isEqualTo(UPDATED_MEDIUM);
        assertThat(testImage.getMediumContentType()).isEqualTo(UPDATED_MEDIUM_CONTENT_TYPE);
        assertThat(testImage.getLarge()).isEqualTo(UPDATED_LARGE);
        assertThat(testImage.getLargeContentType()).isEqualTo(UPDATED_LARGE_CONTENT_TYPE);
        assertThat(testImage.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testImage.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testImage.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingImage() throws Exception {
        int databaseSizeBeforeUpdate = imageRepository.findAll().size();
        image.setId(count.incrementAndGet());

        // Create the Image
        ImageDTO imageDTO = imageMapper.toDto(image);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, imageDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchImage() throws Exception {
        int databaseSizeBeforeUpdate = imageRepository.findAll().size();
        image.setId(count.incrementAndGet());

        // Create the Image
        ImageDTO imageDTO = imageMapper.toDto(image);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamImage() throws Exception {
        int databaseSizeBeforeUpdate = imageRepository.findAll().size();
        image.setId(count.incrementAndGet());

        // Create the Image
        ImageDTO imageDTO = imageMapper.toDto(image);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImageMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(imageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateImageWithPatch() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        int databaseSizeBeforeUpdate = imageRepository.findAll().size();

        // Update the image using partial update
        Image partialUpdatedImage = new Image();
        partialUpdatedImage.setId(image.getId());

        partialUpdatedImage
            .medium(UPDATED_MEDIUM)
            .mediumContentType(UPDATED_MEDIUM_CONTENT_TYPE)
            .large(UPDATED_LARGE)
            .largeContentType(UPDATED_LARGE_CONTENT_TYPE)
            .hash(UPDATED_HASH)
            .createdAt(UPDATED_CREATED_AT);

        restImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImage.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedImage))
            )
            .andExpect(status().isOk());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeUpdate);
        Image testImage = imageList.get(imageList.size() - 1);
        assertThat(testImage.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testImage.getSmall()).isEqualTo(DEFAULT_SMALL);
        assertThat(testImage.getSmallContentType()).isEqualTo(DEFAULT_SMALL_CONTENT_TYPE);
        assertThat(testImage.getMedium()).isEqualTo(UPDATED_MEDIUM);
        assertThat(testImage.getMediumContentType()).isEqualTo(UPDATED_MEDIUM_CONTENT_TYPE);
        assertThat(testImage.getLarge()).isEqualTo(UPDATED_LARGE);
        assertThat(testImage.getLargeContentType()).isEqualTo(UPDATED_LARGE_CONTENT_TYPE);
        assertThat(testImage.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testImage.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testImage.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateImageWithPatch() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        int databaseSizeBeforeUpdate = imageRepository.findAll().size();

        // Update the image using partial update
        Image partialUpdatedImage = new Image();
        partialUpdatedImage.setId(image.getId());

        partialUpdatedImage
            .title(UPDATED_TITLE)
            .small(UPDATED_SMALL)
            .smallContentType(UPDATED_SMALL_CONTENT_TYPE)
            .medium(UPDATED_MEDIUM)
            .mediumContentType(UPDATED_MEDIUM_CONTENT_TYPE)
            .large(UPDATED_LARGE)
            .largeContentType(UPDATED_LARGE_CONTENT_TYPE)
            .hash(UPDATED_HASH)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImage.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedImage))
            )
            .andExpect(status().isOk());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeUpdate);
        Image testImage = imageList.get(imageList.size() - 1);
        assertThat(testImage.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testImage.getSmall()).isEqualTo(UPDATED_SMALL);
        assertThat(testImage.getSmallContentType()).isEqualTo(UPDATED_SMALL_CONTENT_TYPE);
        assertThat(testImage.getMedium()).isEqualTo(UPDATED_MEDIUM);
        assertThat(testImage.getMediumContentType()).isEqualTo(UPDATED_MEDIUM_CONTENT_TYPE);
        assertThat(testImage.getLarge()).isEqualTo(UPDATED_LARGE);
        assertThat(testImage.getLargeContentType()).isEqualTo(UPDATED_LARGE_CONTENT_TYPE);
        assertThat(testImage.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testImage.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testImage.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingImage() throws Exception {
        int databaseSizeBeforeUpdate = imageRepository.findAll().size();
        image.setId(count.incrementAndGet());

        // Create the Image
        ImageDTO imageDTO = imageMapper.toDto(image);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, imageDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(imageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchImage() throws Exception {
        int databaseSizeBeforeUpdate = imageRepository.findAll().size();
        image.setId(count.incrementAndGet());

        // Create the Image
        ImageDTO imageDTO = imageMapper.toDto(image);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(imageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamImage() throws Exception {
        int databaseSizeBeforeUpdate = imageRepository.findAll().size();
        image.setId(count.incrementAndGet());

        // Create the Image
        ImageDTO imageDTO = imageMapper.toDto(image);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(imageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteImage() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        int databaseSizeBeforeDelete = imageRepository.findAll().size();

        // Delete the image
        restImageMockMvc
            .perform(delete(ENTITY_API_URL_ID, image.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
