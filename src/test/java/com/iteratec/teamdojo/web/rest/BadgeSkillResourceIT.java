package com.iteratec.teamdojo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.IntegrationTest;
import com.iteratec.teamdojo.domain.Badge;
import com.iteratec.teamdojo.domain.BadgeSkill;
import com.iteratec.teamdojo.domain.Skill;
import com.iteratec.teamdojo.repository.BadgeSkillRepository;
import com.iteratec.teamdojo.service.BadgeSkillService;
import com.iteratec.teamdojo.service.dto.BadgeSkillDTO;
import com.iteratec.teamdojo.service.mapper.BadgeSkillMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BadgeSkillResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
@GeneratedByJHipster
class BadgeSkillResourceIT {

    private static final String ENTITY_API_URL = "/api/badge-skills";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BadgeSkillRepository badgeSkillRepository;

    @Mock
    private BadgeSkillRepository badgeSkillRepositoryMock;

    @Autowired
    private BadgeSkillMapper badgeSkillMapper;

    @Mock
    private BadgeSkillService badgeSkillServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBadgeSkillMockMvc;

    private BadgeSkill badgeSkill;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BadgeSkill createEntity(EntityManager em) {
        BadgeSkill badgeSkill = new BadgeSkill();
        // Add required entity
        Badge badge;
        if (TestUtil.findAll(em, Badge.class).isEmpty()) {
            badge = BadgeResourceIT.createEntity(em);
            em.persist(badge);
            em.flush();
        } else {
            badge = TestUtil.findAll(em, Badge.class).get(0);
        }
        badgeSkill.setBadge(badge);
        // Add required entity
        Skill skill;
        if (TestUtil.findAll(em, Skill.class).isEmpty()) {
            skill = SkillResourceIT.createEntity(em);
            em.persist(skill);
            em.flush();
        } else {
            skill = TestUtil.findAll(em, Skill.class).get(0);
        }
        badgeSkill.setSkill(skill);
        return badgeSkill;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BadgeSkill createUpdatedEntity(EntityManager em) {
        BadgeSkill badgeSkill = new BadgeSkill();
        // Add required entity
        Badge badge;
        if (TestUtil.findAll(em, Badge.class).isEmpty()) {
            badge = BadgeResourceIT.createUpdatedEntity(em);
            em.persist(badge);
            em.flush();
        } else {
            badge = TestUtil.findAll(em, Badge.class).get(0);
        }
        badgeSkill.setBadge(badge);
        // Add required entity
        Skill skill;
        if (TestUtil.findAll(em, Skill.class).isEmpty()) {
            skill = SkillResourceIT.createUpdatedEntity(em);
            em.persist(skill);
            em.flush();
        } else {
            skill = TestUtil.findAll(em, Skill.class).get(0);
        }
        badgeSkill.setSkill(skill);
        return badgeSkill;
    }

    @BeforeEach
    public void initTest() {
        badgeSkill = createEntity(em);
    }

    @Test
    @Transactional
    void createBadgeSkill() throws Exception {
        int databaseSizeBeforeCreate = badgeSkillRepository.findAll().size();
        // Create the BadgeSkill
        BadgeSkillDTO badgeSkillDTO = badgeSkillMapper.toDto(badgeSkill);
        restBadgeSkillMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(badgeSkillDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BadgeSkill in the database
        List<BadgeSkill> badgeSkillList = badgeSkillRepository.findAll();
        assertThat(badgeSkillList).hasSize(databaseSizeBeforeCreate + 1);
        BadgeSkill testBadgeSkill = badgeSkillList.get(badgeSkillList.size() - 1);
    }

    @Test
    @Transactional
    void createBadgeSkillWithExistingId() throws Exception {
        // Create the BadgeSkill with an existing ID
        badgeSkill.setId(1L);
        BadgeSkillDTO badgeSkillDTO = badgeSkillMapper.toDto(badgeSkill);

        int databaseSizeBeforeCreate = badgeSkillRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBadgeSkillMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(badgeSkillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BadgeSkill in the database
        List<BadgeSkill> badgeSkillList = badgeSkillRepository.findAll();
        assertThat(badgeSkillList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBadgeSkills() throws Exception {
        // Initialize the database
        badgeSkillRepository.saveAndFlush(badgeSkill);

        // Get all the badgeSkillList
        restBadgeSkillMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(badgeSkill.getId().intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBadgeSkillsWithEagerRelationshipsIsEnabled() throws Exception {
        when(badgeSkillServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBadgeSkillMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(badgeSkillServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBadgeSkillsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(badgeSkillServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBadgeSkillMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(badgeSkillRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getBadgeSkill() throws Exception {
        // Initialize the database
        badgeSkillRepository.saveAndFlush(badgeSkill);

        // Get the badgeSkill
        restBadgeSkillMockMvc
            .perform(get(ENTITY_API_URL_ID, badgeSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(badgeSkill.getId().intValue()));
    }

    @Test
    @Transactional
    void getBadgeSkillsByIdFiltering() throws Exception {
        // Initialize the database
        badgeSkillRepository.saveAndFlush(badgeSkill);

        Long id = badgeSkill.getId();

        defaultBadgeSkillShouldBeFound("id.equals=" + id);
        defaultBadgeSkillShouldNotBeFound("id.notEquals=" + id);

        defaultBadgeSkillShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBadgeSkillShouldNotBeFound("id.greaterThan=" + id);

        defaultBadgeSkillShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBadgeSkillShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBadgeSkillsByBadgeIsEqualToSomething() throws Exception {
        Badge badge;
        if (TestUtil.findAll(em, Badge.class).isEmpty()) {
            badgeSkillRepository.saveAndFlush(badgeSkill);
            badge = BadgeResourceIT.createEntity(em);
        } else {
            badge = TestUtil.findAll(em, Badge.class).get(0);
        }
        em.persist(badge);
        em.flush();
        badgeSkill.setBadge(badge);
        badgeSkillRepository.saveAndFlush(badgeSkill);
        Long badgeId = badge.getId();
        // Get all the badgeSkillList where badge equals to badgeId
        defaultBadgeSkillShouldBeFound("badgeId.equals=" + badgeId);

        // Get all the badgeSkillList where badge equals to (badgeId + 1)
        defaultBadgeSkillShouldNotBeFound("badgeId.equals=" + (badgeId + 1));
    }

    @Test
    @Transactional
    void getAllBadgeSkillsBySkillIsEqualToSomething() throws Exception {
        Skill skill;
        if (TestUtil.findAll(em, Skill.class).isEmpty()) {
            badgeSkillRepository.saveAndFlush(badgeSkill);
            skill = SkillResourceIT.createEntity(em);
        } else {
            skill = TestUtil.findAll(em, Skill.class).get(0);
        }
        em.persist(skill);
        em.flush();
        badgeSkill.setSkill(skill);
        badgeSkillRepository.saveAndFlush(badgeSkill);
        Long skillId = skill.getId();
        // Get all the badgeSkillList where skill equals to skillId
        defaultBadgeSkillShouldBeFound("skillId.equals=" + skillId);

        // Get all the badgeSkillList where skill equals to (skillId + 1)
        defaultBadgeSkillShouldNotBeFound("skillId.equals=" + (skillId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBadgeSkillShouldBeFound(String filter) throws Exception {
        restBadgeSkillMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(badgeSkill.getId().intValue())));

        // Check, that the count call also returns 1
        restBadgeSkillMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBadgeSkillShouldNotBeFound(String filter) throws Exception {
        restBadgeSkillMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBadgeSkillMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBadgeSkill() throws Exception {
        // Get the badgeSkill
        restBadgeSkillMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBadgeSkill() throws Exception {
        // Initialize the database
        badgeSkillRepository.saveAndFlush(badgeSkill);

        int databaseSizeBeforeUpdate = badgeSkillRepository.findAll().size();

        // Update the badgeSkill
        BadgeSkill updatedBadgeSkill = badgeSkillRepository.findById(badgeSkill.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBadgeSkill are not directly saved in db
        em.detach(updatedBadgeSkill);
        BadgeSkillDTO badgeSkillDTO = badgeSkillMapper.toDto(updatedBadgeSkill);

        restBadgeSkillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, badgeSkillDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(badgeSkillDTO))
            )
            .andExpect(status().isOk());

        // Validate the BadgeSkill in the database
        List<BadgeSkill> badgeSkillList = badgeSkillRepository.findAll();
        assertThat(badgeSkillList).hasSize(databaseSizeBeforeUpdate);
        BadgeSkill testBadgeSkill = badgeSkillList.get(badgeSkillList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingBadgeSkill() throws Exception {
        int databaseSizeBeforeUpdate = badgeSkillRepository.findAll().size();
        badgeSkill.setId(count.incrementAndGet());

        // Create the BadgeSkill
        BadgeSkillDTO badgeSkillDTO = badgeSkillMapper.toDto(badgeSkill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBadgeSkillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, badgeSkillDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(badgeSkillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BadgeSkill in the database
        List<BadgeSkill> badgeSkillList = badgeSkillRepository.findAll();
        assertThat(badgeSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBadgeSkill() throws Exception {
        int databaseSizeBeforeUpdate = badgeSkillRepository.findAll().size();
        badgeSkill.setId(count.incrementAndGet());

        // Create the BadgeSkill
        BadgeSkillDTO badgeSkillDTO = badgeSkillMapper.toDto(badgeSkill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBadgeSkillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(badgeSkillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BadgeSkill in the database
        List<BadgeSkill> badgeSkillList = badgeSkillRepository.findAll();
        assertThat(badgeSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBadgeSkill() throws Exception {
        int databaseSizeBeforeUpdate = badgeSkillRepository.findAll().size();
        badgeSkill.setId(count.incrementAndGet());

        // Create the BadgeSkill
        BadgeSkillDTO badgeSkillDTO = badgeSkillMapper.toDto(badgeSkill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBadgeSkillMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(badgeSkillDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BadgeSkill in the database
        List<BadgeSkill> badgeSkillList = badgeSkillRepository.findAll();
        assertThat(badgeSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBadgeSkillWithPatch() throws Exception {
        // Initialize the database
        badgeSkillRepository.saveAndFlush(badgeSkill);

        int databaseSizeBeforeUpdate = badgeSkillRepository.findAll().size();

        // Update the badgeSkill using partial update
        BadgeSkill partialUpdatedBadgeSkill = new BadgeSkill();
        partialUpdatedBadgeSkill.setId(badgeSkill.getId());

        restBadgeSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBadgeSkill.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBadgeSkill))
            )
            .andExpect(status().isOk());

        // Validate the BadgeSkill in the database
        List<BadgeSkill> badgeSkillList = badgeSkillRepository.findAll();
        assertThat(badgeSkillList).hasSize(databaseSizeBeforeUpdate);
        BadgeSkill testBadgeSkill = badgeSkillList.get(badgeSkillList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateBadgeSkillWithPatch() throws Exception {
        // Initialize the database
        badgeSkillRepository.saveAndFlush(badgeSkill);

        int databaseSizeBeforeUpdate = badgeSkillRepository.findAll().size();

        // Update the badgeSkill using partial update
        BadgeSkill partialUpdatedBadgeSkill = new BadgeSkill();
        partialUpdatedBadgeSkill.setId(badgeSkill.getId());

        restBadgeSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBadgeSkill.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBadgeSkill))
            )
            .andExpect(status().isOk());

        // Validate the BadgeSkill in the database
        List<BadgeSkill> badgeSkillList = badgeSkillRepository.findAll();
        assertThat(badgeSkillList).hasSize(databaseSizeBeforeUpdate);
        BadgeSkill testBadgeSkill = badgeSkillList.get(badgeSkillList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingBadgeSkill() throws Exception {
        int databaseSizeBeforeUpdate = badgeSkillRepository.findAll().size();
        badgeSkill.setId(count.incrementAndGet());

        // Create the BadgeSkill
        BadgeSkillDTO badgeSkillDTO = badgeSkillMapper.toDto(badgeSkill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBadgeSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, badgeSkillDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(badgeSkillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BadgeSkill in the database
        List<BadgeSkill> badgeSkillList = badgeSkillRepository.findAll();
        assertThat(badgeSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBadgeSkill() throws Exception {
        int databaseSizeBeforeUpdate = badgeSkillRepository.findAll().size();
        badgeSkill.setId(count.incrementAndGet());

        // Create the BadgeSkill
        BadgeSkillDTO badgeSkillDTO = badgeSkillMapper.toDto(badgeSkill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBadgeSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(badgeSkillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BadgeSkill in the database
        List<BadgeSkill> badgeSkillList = badgeSkillRepository.findAll();
        assertThat(badgeSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBadgeSkill() throws Exception {
        int databaseSizeBeforeUpdate = badgeSkillRepository.findAll().size();
        badgeSkill.setId(count.incrementAndGet());

        // Create the BadgeSkill
        BadgeSkillDTO badgeSkillDTO = badgeSkillMapper.toDto(badgeSkill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBadgeSkillMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(badgeSkillDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BadgeSkill in the database
        List<BadgeSkill> badgeSkillList = badgeSkillRepository.findAll();
        assertThat(badgeSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBadgeSkill() throws Exception {
        // Initialize the database
        badgeSkillRepository.saveAndFlush(badgeSkill);

        int databaseSizeBeforeDelete = badgeSkillRepository.findAll().size();

        // Delete the badgeSkill
        restBadgeSkillMockMvc
            .perform(delete(ENTITY_API_URL_ID, badgeSkill.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BadgeSkill> badgeSkillList = badgeSkillRepository.findAll();
        assertThat(badgeSkillList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
