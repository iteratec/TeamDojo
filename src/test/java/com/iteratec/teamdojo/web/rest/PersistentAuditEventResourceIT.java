package com.iteratec.teamdojo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iteratec.teamdojo.IntegrationTest;
import com.iteratec.teamdojo.domain.PersistentAuditEvent;
import com.iteratec.teamdojo.repository.PersistentAuditEventRepository;
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

/**
 * Integration tests for the {@link PersistentAuditEventResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersistentAuditEventResourceIT {

    private static final String DEFAULT_PRINCIPAL = "AAAAAAAAAA";
    private static final String UPDATED_PRINCIPAL = "BBBBBBBBBB";

    private static final Instant DEFAULT_AUDIT_EVENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_AUDIT_EVENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_AUDIT_EVENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_AUDIT_EVENT_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/persistent-audit-events";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersistentAuditEventRepository persistentAuditEventRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersistentAuditEventMockMvc;

    private PersistentAuditEvent persistentAuditEvent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersistentAuditEvent createEntity(EntityManager em) {
        PersistentAuditEvent persistentAuditEvent = new PersistentAuditEvent()
            .principal(DEFAULT_PRINCIPAL)
            .auditEventDate(DEFAULT_AUDIT_EVENT_DATE)
            .auditEventType(DEFAULT_AUDIT_EVENT_TYPE);
        return persistentAuditEvent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersistentAuditEvent createUpdatedEntity(EntityManager em) {
        PersistentAuditEvent persistentAuditEvent = new PersistentAuditEvent()
            .principal(UPDATED_PRINCIPAL)
            .auditEventDate(UPDATED_AUDIT_EVENT_DATE)
            .auditEventType(UPDATED_AUDIT_EVENT_TYPE);
        return persistentAuditEvent;
    }

    @BeforeEach
    public void initTest() {
        persistentAuditEvent = createEntity(em);
    }

    @Test
    @Transactional
    void createPersistentAuditEvent() throws Exception {
        int databaseSizeBeforeCreate = persistentAuditEventRepository.findAll().size();
        // Create the PersistentAuditEvent
        restPersistentAuditEventMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(persistentAuditEvent))
            )
            .andExpect(status().isCreated());

        // Validate the PersistentAuditEvent in the database
        List<PersistentAuditEvent> persistentAuditEventList = persistentAuditEventRepository.findAll();
        assertThat(persistentAuditEventList).hasSize(databaseSizeBeforeCreate + 1);
        PersistentAuditEvent testPersistentAuditEvent = persistentAuditEventList.get(persistentAuditEventList.size() - 1);
        assertThat(testPersistentAuditEvent.getPrincipal()).isEqualTo(DEFAULT_PRINCIPAL);
        assertThat(testPersistentAuditEvent.getAuditEventDate()).isEqualTo(DEFAULT_AUDIT_EVENT_DATE);
        assertThat(testPersistentAuditEvent.getAuditEventType()).isEqualTo(DEFAULT_AUDIT_EVENT_TYPE);
    }

    @Test
    @Transactional
    void createPersistentAuditEventWithExistingId() throws Exception {
        // Create the PersistentAuditEvent with an existing ID
        persistentAuditEvent.setId(1L);

        int databaseSizeBeforeCreate = persistentAuditEventRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersistentAuditEventMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(persistentAuditEvent))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersistentAuditEvent in the database
        List<PersistentAuditEvent> persistentAuditEventList = persistentAuditEventRepository.findAll();
        assertThat(persistentAuditEventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPrincipalIsRequired() throws Exception {
        int databaseSizeBeforeTest = persistentAuditEventRepository.findAll().size();
        // set the field null
        persistentAuditEvent.setPrincipal(null);

        // Create the PersistentAuditEvent, which fails.

        restPersistentAuditEventMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(persistentAuditEvent))
            )
            .andExpect(status().isBadRequest());

        List<PersistentAuditEvent> persistentAuditEventList = persistentAuditEventRepository.findAll();
        assertThat(persistentAuditEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPersistentAuditEvents() throws Exception {
        // Initialize the database
        persistentAuditEventRepository.saveAndFlush(persistentAuditEvent);

        // Get all the persistentAuditEventList
        restPersistentAuditEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(persistentAuditEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL)))
            .andExpect(jsonPath("$.[*].auditEventDate").value(hasItem(DEFAULT_AUDIT_EVENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].auditEventType").value(hasItem(DEFAULT_AUDIT_EVENT_TYPE)));
    }

    @Test
    @Transactional
    void getPersistentAuditEvent() throws Exception {
        // Initialize the database
        persistentAuditEventRepository.saveAndFlush(persistentAuditEvent);

        // Get the persistentAuditEvent
        restPersistentAuditEventMockMvc
            .perform(get(ENTITY_API_URL_ID, persistentAuditEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(persistentAuditEvent.getId().intValue()))
            .andExpect(jsonPath("$.principal").value(DEFAULT_PRINCIPAL))
            .andExpect(jsonPath("$.auditEventDate").value(DEFAULT_AUDIT_EVENT_DATE.toString()))
            .andExpect(jsonPath("$.auditEventType").value(DEFAULT_AUDIT_EVENT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingPersistentAuditEvent() throws Exception {
        // Get the persistentAuditEvent
        restPersistentAuditEventMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPersistentAuditEvent() throws Exception {
        // Initialize the database
        persistentAuditEventRepository.saveAndFlush(persistentAuditEvent);

        int databaseSizeBeforeUpdate = persistentAuditEventRepository.findAll().size();

        // Update the persistentAuditEvent
        PersistentAuditEvent updatedPersistentAuditEvent = persistentAuditEventRepository.findById(persistentAuditEvent.getId()).get();
        // Disconnect from session so that the updates on updatedPersistentAuditEvent are not directly saved in db
        em.detach(updatedPersistentAuditEvent);
        updatedPersistentAuditEvent
            .principal(UPDATED_PRINCIPAL)
            .auditEventDate(UPDATED_AUDIT_EVENT_DATE)
            .auditEventType(UPDATED_AUDIT_EVENT_TYPE);

        restPersistentAuditEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPersistentAuditEvent.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPersistentAuditEvent))
            )
            .andExpect(status().isOk());

        // Validate the PersistentAuditEvent in the database
        List<PersistentAuditEvent> persistentAuditEventList = persistentAuditEventRepository.findAll();
        assertThat(persistentAuditEventList).hasSize(databaseSizeBeforeUpdate);
        PersistentAuditEvent testPersistentAuditEvent = persistentAuditEventList.get(persistentAuditEventList.size() - 1);
        assertThat(testPersistentAuditEvent.getPrincipal()).isEqualTo(UPDATED_PRINCIPAL);
        assertThat(testPersistentAuditEvent.getAuditEventDate()).isEqualTo(UPDATED_AUDIT_EVENT_DATE);
        assertThat(testPersistentAuditEvent.getAuditEventType()).isEqualTo(UPDATED_AUDIT_EVENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingPersistentAuditEvent() throws Exception {
        int databaseSizeBeforeUpdate = persistentAuditEventRepository.findAll().size();
        persistentAuditEvent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersistentAuditEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, persistentAuditEvent.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(persistentAuditEvent))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersistentAuditEvent in the database
        List<PersistentAuditEvent> persistentAuditEventList = persistentAuditEventRepository.findAll();
        assertThat(persistentAuditEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersistentAuditEvent() throws Exception {
        int databaseSizeBeforeUpdate = persistentAuditEventRepository.findAll().size();
        persistentAuditEvent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersistentAuditEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(persistentAuditEvent))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersistentAuditEvent in the database
        List<PersistentAuditEvent> persistentAuditEventList = persistentAuditEventRepository.findAll();
        assertThat(persistentAuditEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersistentAuditEvent() throws Exception {
        int databaseSizeBeforeUpdate = persistentAuditEventRepository.findAll().size();
        persistentAuditEvent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersistentAuditEventMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(persistentAuditEvent))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersistentAuditEvent in the database
        List<PersistentAuditEvent> persistentAuditEventList = persistentAuditEventRepository.findAll();
        assertThat(persistentAuditEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersistentAuditEventWithPatch() throws Exception {
        // Initialize the database
        persistentAuditEventRepository.saveAndFlush(persistentAuditEvent);

        int databaseSizeBeforeUpdate = persistentAuditEventRepository.findAll().size();

        // Update the persistentAuditEvent using partial update
        PersistentAuditEvent partialUpdatedPersistentAuditEvent = new PersistentAuditEvent();
        partialUpdatedPersistentAuditEvent.setId(persistentAuditEvent.getId());

        partialUpdatedPersistentAuditEvent.auditEventDate(UPDATED_AUDIT_EVENT_DATE).auditEventType(UPDATED_AUDIT_EVENT_TYPE);

        restPersistentAuditEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersistentAuditEvent.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersistentAuditEvent))
            )
            .andExpect(status().isOk());

        // Validate the PersistentAuditEvent in the database
        List<PersistentAuditEvent> persistentAuditEventList = persistentAuditEventRepository.findAll();
        assertThat(persistentAuditEventList).hasSize(databaseSizeBeforeUpdate);
        PersistentAuditEvent testPersistentAuditEvent = persistentAuditEventList.get(persistentAuditEventList.size() - 1);
        assertThat(testPersistentAuditEvent.getPrincipal()).isEqualTo(DEFAULT_PRINCIPAL);
        assertThat(testPersistentAuditEvent.getAuditEventDate()).isEqualTo(UPDATED_AUDIT_EVENT_DATE);
        assertThat(testPersistentAuditEvent.getAuditEventType()).isEqualTo(UPDATED_AUDIT_EVENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdatePersistentAuditEventWithPatch() throws Exception {
        // Initialize the database
        persistentAuditEventRepository.saveAndFlush(persistentAuditEvent);

        int databaseSizeBeforeUpdate = persistentAuditEventRepository.findAll().size();

        // Update the persistentAuditEvent using partial update
        PersistentAuditEvent partialUpdatedPersistentAuditEvent = new PersistentAuditEvent();
        partialUpdatedPersistentAuditEvent.setId(persistentAuditEvent.getId());

        partialUpdatedPersistentAuditEvent
            .principal(UPDATED_PRINCIPAL)
            .auditEventDate(UPDATED_AUDIT_EVENT_DATE)
            .auditEventType(UPDATED_AUDIT_EVENT_TYPE);

        restPersistentAuditEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersistentAuditEvent.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersistentAuditEvent))
            )
            .andExpect(status().isOk());

        // Validate the PersistentAuditEvent in the database
        List<PersistentAuditEvent> persistentAuditEventList = persistentAuditEventRepository.findAll();
        assertThat(persistentAuditEventList).hasSize(databaseSizeBeforeUpdate);
        PersistentAuditEvent testPersistentAuditEvent = persistentAuditEventList.get(persistentAuditEventList.size() - 1);
        assertThat(testPersistentAuditEvent.getPrincipal()).isEqualTo(UPDATED_PRINCIPAL);
        assertThat(testPersistentAuditEvent.getAuditEventDate()).isEqualTo(UPDATED_AUDIT_EVENT_DATE);
        assertThat(testPersistentAuditEvent.getAuditEventType()).isEqualTo(UPDATED_AUDIT_EVENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingPersistentAuditEvent() throws Exception {
        int databaseSizeBeforeUpdate = persistentAuditEventRepository.findAll().size();
        persistentAuditEvent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersistentAuditEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, persistentAuditEvent.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(persistentAuditEvent))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersistentAuditEvent in the database
        List<PersistentAuditEvent> persistentAuditEventList = persistentAuditEventRepository.findAll();
        assertThat(persistentAuditEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersistentAuditEvent() throws Exception {
        int databaseSizeBeforeUpdate = persistentAuditEventRepository.findAll().size();
        persistentAuditEvent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersistentAuditEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(persistentAuditEvent))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersistentAuditEvent in the database
        List<PersistentAuditEvent> persistentAuditEventList = persistentAuditEventRepository.findAll();
        assertThat(persistentAuditEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersistentAuditEvent() throws Exception {
        int databaseSizeBeforeUpdate = persistentAuditEventRepository.findAll().size();
        persistentAuditEvent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersistentAuditEventMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(persistentAuditEvent))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersistentAuditEvent in the database
        List<PersistentAuditEvent> persistentAuditEventList = persistentAuditEventRepository.findAll();
        assertThat(persistentAuditEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersistentAuditEvent() throws Exception {
        // Initialize the database
        persistentAuditEventRepository.saveAndFlush(persistentAuditEvent);

        int databaseSizeBeforeDelete = persistentAuditEventRepository.findAll().size();

        // Delete the persistentAuditEvent
        restPersistentAuditEventMockMvc
            .perform(delete(ENTITY_API_URL_ID, persistentAuditEvent.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersistentAuditEvent> persistentAuditEventList = persistentAuditEventRepository.findAll();
        assertThat(persistentAuditEventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
