package com.iteratec.teamdojo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.IntegrationTest;
import com.iteratec.teamdojo.domain.Level;
import com.iteratec.teamdojo.domain.LevelSkill;
import com.iteratec.teamdojo.domain.Skill;
import com.iteratec.teamdojo.repository.LevelSkillRepository;
import com.iteratec.teamdojo.service.criteria.LevelSkillCriteria;
import com.iteratec.teamdojo.service.dto.LevelSkillDTO;
import com.iteratec.teamdojo.service.mapper.LevelSkillMapper;
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
 * Integration tests for the {@link LevelSkillResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
@GeneratedByJHipster
class LevelSkillResourceIT {

    private static final String ENTITY_API_URL = "/api/level-skills";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LevelSkillRepository levelSkillRepository;

    @Autowired
    private LevelSkillMapper levelSkillMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLevelSkillMockMvc;

    private LevelSkill levelSkill;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LevelSkill createEntity(EntityManager em) {
        LevelSkill levelSkill = new LevelSkill();
        // Add required entity
        Skill skill;
        if (TestUtil.findAll(em, Skill.class).isEmpty()) {
            skill = SkillResourceIT.createEntity(em);
            em.persist(skill);
            em.flush();
        } else {
            skill = TestUtil.findAll(em, Skill.class).get(0);
        }
        levelSkill.setSkill(skill);
        // Add required entity
        Level level;
        if (TestUtil.findAll(em, Level.class).isEmpty()) {
            level = LevelResourceIT.createEntity(em);
            em.persist(level);
            em.flush();
        } else {
            level = TestUtil.findAll(em, Level.class).get(0);
        }
        levelSkill.setLevel(level);
        return levelSkill;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LevelSkill createUpdatedEntity(EntityManager em) {
        LevelSkill levelSkill = new LevelSkill();
        // Add required entity
        Skill skill;
        if (TestUtil.findAll(em, Skill.class).isEmpty()) {
            skill = SkillResourceIT.createUpdatedEntity(em);
            em.persist(skill);
            em.flush();
        } else {
            skill = TestUtil.findAll(em, Skill.class).get(0);
        }
        levelSkill.setSkill(skill);
        // Add required entity
        Level level;
        if (TestUtil.findAll(em, Level.class).isEmpty()) {
            level = LevelResourceIT.createUpdatedEntity(em);
            em.persist(level);
            em.flush();
        } else {
            level = TestUtil.findAll(em, Level.class).get(0);
        }
        levelSkill.setLevel(level);
        return levelSkill;
    }

    @BeforeEach
    public void initTest() {
        levelSkill = createEntity(em);
    }

    @Test
    @Transactional
    void createLevelSkill() throws Exception {
        int databaseSizeBeforeCreate = levelSkillRepository.findAll().size();
        // Create the LevelSkill
        LevelSkillDTO levelSkillDTO = levelSkillMapper.toDto(levelSkill);
        restLevelSkillMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelSkillDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LevelSkill in the database
        List<LevelSkill> levelSkillList = levelSkillRepository.findAll();
        assertThat(levelSkillList).hasSize(databaseSizeBeforeCreate + 1);
        LevelSkill testLevelSkill = levelSkillList.get(levelSkillList.size() - 1);
    }

    @Test
    @Transactional
    void createLevelSkillWithExistingId() throws Exception {
        // Create the LevelSkill with an existing ID
        levelSkill.setId(1L);
        LevelSkillDTO levelSkillDTO = levelSkillMapper.toDto(levelSkill);

        int databaseSizeBeforeCreate = levelSkillRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLevelSkillMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelSkillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelSkill in the database
        List<LevelSkill> levelSkillList = levelSkillRepository.findAll();
        assertThat(levelSkillList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLevelSkills() throws Exception {
        // Initialize the database
        levelSkillRepository.saveAndFlush(levelSkill);

        // Get all the levelSkillList
        restLevelSkillMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(levelSkill.getId().intValue())));
    }

    @Test
    @Transactional
    void getLevelSkill() throws Exception {
        // Initialize the database
        levelSkillRepository.saveAndFlush(levelSkill);

        // Get the levelSkill
        restLevelSkillMockMvc
            .perform(get(ENTITY_API_URL_ID, levelSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(levelSkill.getId().intValue()));
    }

    @Test
    @Transactional
    void getLevelSkillsByIdFiltering() throws Exception {
        // Initialize the database
        levelSkillRepository.saveAndFlush(levelSkill);

        Long id = levelSkill.getId();

        defaultLevelSkillShouldBeFound("id.equals=" + id);
        defaultLevelSkillShouldNotBeFound("id.notEquals=" + id);

        defaultLevelSkillShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLevelSkillShouldNotBeFound("id.greaterThan=" + id);

        defaultLevelSkillShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLevelSkillShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLevelSkillsBySkillIsEqualToSomething() throws Exception {
        // Initialize the database
        levelSkillRepository.saveAndFlush(levelSkill);
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
        levelSkill.setSkill(skill);
        levelSkillRepository.saveAndFlush(levelSkill);
        Long skillId = skill.getId();

        // Get all the levelSkillList where skill equals to skillId
        defaultLevelSkillShouldBeFound("skillId.equals=" + skillId);

        // Get all the levelSkillList where skill equals to (skillId + 1)
        defaultLevelSkillShouldNotBeFound("skillId.equals=" + (skillId + 1));
    }

    @Test
    @Transactional
    void getAllLevelSkillsByLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        levelSkillRepository.saveAndFlush(levelSkill);
        Level level;
        if (TestUtil.findAll(em, Level.class).isEmpty()) {
            level = LevelResourceIT.createEntity(em);
            em.persist(level);
            em.flush();
        } else {
            level = TestUtil.findAll(em, Level.class).get(0);
        }
        em.persist(level);
        em.flush();
        levelSkill.setLevel(level);
        levelSkillRepository.saveAndFlush(levelSkill);
        Long levelId = level.getId();

        // Get all the levelSkillList where level equals to levelId
        defaultLevelSkillShouldBeFound("levelId.equals=" + levelId);

        // Get all the levelSkillList where level equals to (levelId + 1)
        defaultLevelSkillShouldNotBeFound("levelId.equals=" + (levelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLevelSkillShouldBeFound(String filter) throws Exception {
        restLevelSkillMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(levelSkill.getId().intValue())));

        // Check, that the count call also returns 1
        restLevelSkillMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLevelSkillShouldNotBeFound(String filter) throws Exception {
        restLevelSkillMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLevelSkillMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLevelSkill() throws Exception {
        // Get the levelSkill
        restLevelSkillMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLevelSkill() throws Exception {
        // Initialize the database
        levelSkillRepository.saveAndFlush(levelSkill);

        int databaseSizeBeforeUpdate = levelSkillRepository.findAll().size();

        // Update the levelSkill
        LevelSkill updatedLevelSkill = levelSkillRepository.findById(levelSkill.getId()).get();
        // Disconnect from session so that the updates on updatedLevelSkill are not directly saved in db
        em.detach(updatedLevelSkill);
        LevelSkillDTO levelSkillDTO = levelSkillMapper.toDto(updatedLevelSkill);

        restLevelSkillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, levelSkillDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelSkillDTO))
            )
            .andExpect(status().isOk());

        // Validate the LevelSkill in the database
        List<LevelSkill> levelSkillList = levelSkillRepository.findAll();
        assertThat(levelSkillList).hasSize(databaseSizeBeforeUpdate);
        LevelSkill testLevelSkill = levelSkillList.get(levelSkillList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingLevelSkill() throws Exception {
        int databaseSizeBeforeUpdate = levelSkillRepository.findAll().size();
        levelSkill.setId(count.incrementAndGet());

        // Create the LevelSkill
        LevelSkillDTO levelSkillDTO = levelSkillMapper.toDto(levelSkill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLevelSkillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, levelSkillDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelSkillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelSkill in the database
        List<LevelSkill> levelSkillList = levelSkillRepository.findAll();
        assertThat(levelSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLevelSkill() throws Exception {
        int databaseSizeBeforeUpdate = levelSkillRepository.findAll().size();
        levelSkill.setId(count.incrementAndGet());

        // Create the LevelSkill
        LevelSkillDTO levelSkillDTO = levelSkillMapper.toDto(levelSkill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelSkillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelSkillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelSkill in the database
        List<LevelSkill> levelSkillList = levelSkillRepository.findAll();
        assertThat(levelSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLevelSkill() throws Exception {
        int databaseSizeBeforeUpdate = levelSkillRepository.findAll().size();
        levelSkill.setId(count.incrementAndGet());

        // Create the LevelSkill
        LevelSkillDTO levelSkillDTO = levelSkillMapper.toDto(levelSkill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelSkillMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(levelSkillDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LevelSkill in the database
        List<LevelSkill> levelSkillList = levelSkillRepository.findAll();
        assertThat(levelSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLevelSkillWithPatch() throws Exception {
        // Initialize the database
        levelSkillRepository.saveAndFlush(levelSkill);

        int databaseSizeBeforeUpdate = levelSkillRepository.findAll().size();

        // Update the levelSkill using partial update
        LevelSkill partialUpdatedLevelSkill = new LevelSkill();
        partialUpdatedLevelSkill.setId(levelSkill.getId());

        restLevelSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLevelSkill.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLevelSkill))
            )
            .andExpect(status().isOk());

        // Validate the LevelSkill in the database
        List<LevelSkill> levelSkillList = levelSkillRepository.findAll();
        assertThat(levelSkillList).hasSize(databaseSizeBeforeUpdate);
        LevelSkill testLevelSkill = levelSkillList.get(levelSkillList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateLevelSkillWithPatch() throws Exception {
        // Initialize the database
        levelSkillRepository.saveAndFlush(levelSkill);

        int databaseSizeBeforeUpdate = levelSkillRepository.findAll().size();

        // Update the levelSkill using partial update
        LevelSkill partialUpdatedLevelSkill = new LevelSkill();
        partialUpdatedLevelSkill.setId(levelSkill.getId());

        restLevelSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLevelSkill.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLevelSkill))
            )
            .andExpect(status().isOk());

        // Validate the LevelSkill in the database
        List<LevelSkill> levelSkillList = levelSkillRepository.findAll();
        assertThat(levelSkillList).hasSize(databaseSizeBeforeUpdate);
        LevelSkill testLevelSkill = levelSkillList.get(levelSkillList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingLevelSkill() throws Exception {
        int databaseSizeBeforeUpdate = levelSkillRepository.findAll().size();
        levelSkill.setId(count.incrementAndGet());

        // Create the LevelSkill
        LevelSkillDTO levelSkillDTO = levelSkillMapper.toDto(levelSkill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLevelSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, levelSkillDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelSkillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelSkill in the database
        List<LevelSkill> levelSkillList = levelSkillRepository.findAll();
        assertThat(levelSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLevelSkill() throws Exception {
        int databaseSizeBeforeUpdate = levelSkillRepository.findAll().size();
        levelSkill.setId(count.incrementAndGet());

        // Create the LevelSkill
        LevelSkillDTO levelSkillDTO = levelSkillMapper.toDto(levelSkill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelSkillDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LevelSkill in the database
        List<LevelSkill> levelSkillList = levelSkillRepository.findAll();
        assertThat(levelSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLevelSkill() throws Exception {
        int databaseSizeBeforeUpdate = levelSkillRepository.findAll().size();
        levelSkill.setId(count.incrementAndGet());

        // Create the LevelSkill
        LevelSkillDTO levelSkillDTO = levelSkillMapper.toDto(levelSkill);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLevelSkillMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(levelSkillDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LevelSkill in the database
        List<LevelSkill> levelSkillList = levelSkillRepository.findAll();
        assertThat(levelSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLevelSkill() throws Exception {
        // Initialize the database
        levelSkillRepository.saveAndFlush(levelSkill);

        int databaseSizeBeforeDelete = levelSkillRepository.findAll().size();

        // Delete the levelSkill
        restLevelSkillMockMvc
            .perform(delete(ENTITY_API_URL_ID, levelSkill.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LevelSkill> levelSkillList = levelSkillRepository.findAll();
        assertThat(levelSkillList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
