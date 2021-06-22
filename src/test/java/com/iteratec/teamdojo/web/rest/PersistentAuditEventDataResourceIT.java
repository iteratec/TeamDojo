package com.iteratec.teamdojo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iteratec.teamdojo.IntegrationTest;
import com.iteratec.teamdojo.domain.PersistentAuditEvent;
import com.iteratec.teamdojo.domain.PersistentAuditEventData;
import com.iteratec.teamdojo.repository.PersistentAuditEventDataRepository;
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

/**
 * Integration tests for the {@link PersistentAuditEventDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersistentAuditEventDataResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/persistent-audit-event-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersistentAuditEventDataRepository persistentAuditEventDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersistentAuditEventDataMockMvc;

    private PersistentAuditEventData persistentAuditEventData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersistentAuditEventData createEntity(EntityManager em) {
        PersistentAuditEventData persistentAuditEventData = new PersistentAuditEventData().name(DEFAULT_NAME).value(DEFAULT_VALUE);
        // Add required entity
        PersistentAuditEvent persistentAuditEvent;
        if (TestUtil.findAll(em, PersistentAuditEvent.class).isEmpty()) {
            persistentAuditEvent = PersistentAuditEventResourceIT.createEntity(em);
            em.persist(persistentAuditEvent);
            em.flush();
        } else {
            persistentAuditEvent = TestUtil.findAll(em, PersistentAuditEvent.class).get(0);
        }
        persistentAuditEventData.setEvent(persistentAuditEvent);
        return persistentAuditEventData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersistentAuditEventData createUpdatedEntity(EntityManager em) {
        PersistentAuditEventData persistentAuditEventData = new PersistentAuditEventData().name(UPDATED_NAME).value(UPDATED_VALUE);
        // Add required entity
        PersistentAuditEvent persistentAuditEvent;
        if (TestUtil.findAll(em, PersistentAuditEvent.class).isEmpty()) {
            persistentAuditEvent = PersistentAuditEventResourceIT.createUpdatedEntity(em);
            em.persist(persistentAuditEvent);
            em.flush();
        } else {
            persistentAuditEvent = TestUtil.findAll(em, PersistentAuditEvent.class).get(0);
        }
        persistentAuditEventData.setEvent(persistentAuditEvent);
        return persistentAuditEventData;
    }

    @BeforeEach
    public void initTest() {
        persistentAuditEventData = createEntity(em);
    }

    @Test
    @Transactional
    void createPersistentAuditEventData() throws Exception {
        int databaseSizeBeforeCreate = persistentAuditEventDataRepository.findAll().size();
        // Create the PersistentAuditEventData
        restPersistentAuditEventDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(persistentAuditEventData))
            )
            .andExpect(status().isCreated());

        // Validate the PersistentAuditEventData in the database
        List<PersistentAuditEventData> persistentAuditEventDataList = persistentAuditEventDataRepository.findAll();
        assertThat(persistentAuditEventDataList).hasSize(databaseSizeBeforeCreate + 1);
        PersistentAuditEventData testPersistentAuditEventData = persistentAuditEventDataList.get(persistentAuditEventDataList.size() - 1);
        assertThat(testPersistentAuditEventData.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPersistentAuditEventData.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createPersistentAuditEventDataWithExistingId() throws Exception {
        // Create the PersistentAuditEventData with an existing ID
        persistentAuditEventData.setId(1L);

        int databaseSizeBeforeCreate = persistentAuditEventDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersistentAuditEventDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(persistentAuditEventData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersistentAuditEventData in the database
        List<PersistentAuditEventData> persistentAuditEventDataList = persistentAuditEventDataRepository.findAll();
        assertThat(persistentAuditEventDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = persistentAuditEventDataRepository.findAll().size();
        // set the field null
        persistentAuditEventData.setName(null);

        // Create the PersistentAuditEventData, which fails.

        restPersistentAuditEventDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(persistentAuditEventData))
            )
            .andExpect(status().isBadRequest());

        List<PersistentAuditEventData> persistentAuditEventDataList = persistentAuditEventDataRepository.findAll();
        assertThat(persistentAuditEventDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = persistentAuditEventDataRepository.findAll().size();
        // set the field null
        persistentAuditEventData.setValue(null);

        // Create the PersistentAuditEventData, which fails.

        restPersistentAuditEventDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(persistentAuditEventData))
            )
            .andExpect(status().isBadRequest());

        List<PersistentAuditEventData> persistentAuditEventDataList = persistentAuditEventDataRepository.findAll();
        assertThat(persistentAuditEventDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPersistentAuditEventData() throws Exception {
        // Initialize the database
        persistentAuditEventDataRepository.saveAndFlush(persistentAuditEventData);

        // Get all the persistentAuditEventDataList
        restPersistentAuditEventDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(persistentAuditEventData.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getPersistentAuditEventData() throws Exception {
        // Initialize the database
        persistentAuditEventDataRepository.saveAndFlush(persistentAuditEventData);

        // Get the persistentAuditEventData
        restPersistentAuditEventDataMockMvc
            .perform(get(ENTITY_API_URL_ID, persistentAuditEventData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(persistentAuditEventData.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingPersistentAuditEventData() throws Exception {
        // Get the persistentAuditEventData
        restPersistentAuditEventDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPersistentAuditEventData() throws Exception {
        // Initialize the database
        persistentAuditEventDataRepository.saveAndFlush(persistentAuditEventData);

        int databaseSizeBeforeUpdate = persistentAuditEventDataRepository.findAll().size();

        // Update the persistentAuditEventData
        PersistentAuditEventData updatedPersistentAuditEventData = persistentAuditEventDataRepository
            .findById(persistentAuditEventData.getId())
            .get();
        // Disconnect from session so that the updates on updatedPersistentAuditEventData are not directly saved in db
        em.detach(updatedPersistentAuditEventData);
        updatedPersistentAuditEventData.name(UPDATED_NAME).value(UPDATED_VALUE);

        restPersistentAuditEventDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPersistentAuditEventData.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPersistentAuditEventData))
            )
            .andExpect(status().isOk());

        // Validate the PersistentAuditEventData in the database
        List<PersistentAuditEventData> persistentAuditEventDataList = persistentAuditEventDataRepository.findAll();
        assertThat(persistentAuditEventDataList).hasSize(databaseSizeBeforeUpdate);
        PersistentAuditEventData testPersistentAuditEventData = persistentAuditEventDataList.get(persistentAuditEventDataList.size() - 1);
        assertThat(testPersistentAuditEventData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPersistentAuditEventData.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingPersistentAuditEventData() throws Exception {
        int databaseSizeBeforeUpdate = persistentAuditEventDataRepository.findAll().size();
        persistentAuditEventData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersistentAuditEventDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, persistentAuditEventData.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(persistentAuditEventData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersistentAuditEventData in the database
        List<PersistentAuditEventData> persistentAuditEventDataList = persistentAuditEventDataRepository.findAll();
        assertThat(persistentAuditEventDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersistentAuditEventData() throws Exception {
        int databaseSizeBeforeUpdate = persistentAuditEventDataRepository.findAll().size();
        persistentAuditEventData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersistentAuditEventDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(persistentAuditEventData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersistentAuditEventData in the database
        List<PersistentAuditEventData> persistentAuditEventDataList = persistentAuditEventDataRepository.findAll();
        assertThat(persistentAuditEventDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersistentAuditEventData() throws Exception {
        int databaseSizeBeforeUpdate = persistentAuditEventDataRepository.findAll().size();
        persistentAuditEventData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersistentAuditEventDataMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(persistentAuditEventData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersistentAuditEventData in the database
        List<PersistentAuditEventData> persistentAuditEventDataList = persistentAuditEventDataRepository.findAll();
        assertThat(persistentAuditEventDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersistentAuditEventDataWithPatch() throws Exception {
        // Initialize the database
        persistentAuditEventDataRepository.saveAndFlush(persistentAuditEventData);

        int databaseSizeBeforeUpdate = persistentAuditEventDataRepository.findAll().size();

        // Update the persistentAuditEventData using partial update
        PersistentAuditEventData partialUpdatedPersistentAuditEventData = new PersistentAuditEventData();
        partialUpdatedPersistentAuditEventData.setId(persistentAuditEventData.getId());

        partialUpdatedPersistentAuditEventData.name(UPDATED_NAME);

        restPersistentAuditEventDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersistentAuditEventData.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersistentAuditEventData))
            )
            .andExpect(status().isOk());

        // Validate the PersistentAuditEventData in the database
        List<PersistentAuditEventData> persistentAuditEventDataList = persistentAuditEventDataRepository.findAll();
        assertThat(persistentAuditEventDataList).hasSize(databaseSizeBeforeUpdate);
        PersistentAuditEventData testPersistentAuditEventData = persistentAuditEventDataList.get(persistentAuditEventDataList.size() - 1);
        assertThat(testPersistentAuditEventData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPersistentAuditEventData.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void fullUpdatePersistentAuditEventDataWithPatch() throws Exception {
        // Initialize the database
        persistentAuditEventDataRepository.saveAndFlush(persistentAuditEventData);

        int databaseSizeBeforeUpdate = persistentAuditEventDataRepository.findAll().size();

        // Update the persistentAuditEventData using partial update
        PersistentAuditEventData partialUpdatedPersistentAuditEventData = new PersistentAuditEventData();
        partialUpdatedPersistentAuditEventData.setId(persistentAuditEventData.getId());

        partialUpdatedPersistentAuditEventData.name(UPDATED_NAME).value(UPDATED_VALUE);

        restPersistentAuditEventDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersistentAuditEventData.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersistentAuditEventData))
            )
            .andExpect(status().isOk());

        // Validate the PersistentAuditEventData in the database
        List<PersistentAuditEventData> persistentAuditEventDataList = persistentAuditEventDataRepository.findAll();
        assertThat(persistentAuditEventDataList).hasSize(databaseSizeBeforeUpdate);
        PersistentAuditEventData testPersistentAuditEventData = persistentAuditEventDataList.get(persistentAuditEventDataList.size() - 1);
        assertThat(testPersistentAuditEventData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPersistentAuditEventData.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingPersistentAuditEventData() throws Exception {
        int databaseSizeBeforeUpdate = persistentAuditEventDataRepository.findAll().size();
        persistentAuditEventData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersistentAuditEventDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, persistentAuditEventData.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(persistentAuditEventData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersistentAuditEventData in the database
        List<PersistentAuditEventData> persistentAuditEventDataList = persistentAuditEventDataRepository.findAll();
        assertThat(persistentAuditEventDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersistentAuditEventData() throws Exception {
        int databaseSizeBeforeUpdate = persistentAuditEventDataRepository.findAll().size();
        persistentAuditEventData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersistentAuditEventDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(persistentAuditEventData))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersistentAuditEventData in the database
        List<PersistentAuditEventData> persistentAuditEventDataList = persistentAuditEventDataRepository.findAll();
        assertThat(persistentAuditEventDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersistentAuditEventData() throws Exception {
        int databaseSizeBeforeUpdate = persistentAuditEventDataRepository.findAll().size();
        persistentAuditEventData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersistentAuditEventDataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(persistentAuditEventData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersistentAuditEventData in the database
        List<PersistentAuditEventData> persistentAuditEventDataList = persistentAuditEventDataRepository.findAll();
        assertThat(persistentAuditEventDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersistentAuditEventData() throws Exception {
        // Initialize the database
        persistentAuditEventDataRepository.saveAndFlush(persistentAuditEventData);

        int databaseSizeBeforeDelete = persistentAuditEventDataRepository.findAll().size();

        // Delete the persistentAuditEventData
        restPersistentAuditEventDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, persistentAuditEventData.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersistentAuditEventData> persistentAuditEventDataList = persistentAuditEventDataRepository.findAll();
        assertThat(persistentAuditEventDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
