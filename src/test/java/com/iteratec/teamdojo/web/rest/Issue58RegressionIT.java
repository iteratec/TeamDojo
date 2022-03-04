package com.iteratec.teamdojo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.iteratec.teamdojo.IntegrationTest;
import com.iteratec.teamdojo.domain.Comment;
import com.iteratec.teamdojo.repository.CommentRepository;
import com.iteratec.teamdojo.service.custom.ExtendedCommentService;
import com.iteratec.teamdojo.service.dto.CommentDTO;
import com.iteratec.teamdojo.service.dto.SkillDTO;
import com.iteratec.teamdojo.service.dto.TeamDTO;
import com.iteratec.teamdojo.service.mapper.SkillMapper;
import com.iteratec.teamdojo.test.util.StaticInstantProvider;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
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
 *
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class Issue58RegressionIT {

    private static final Instant CUSTOM_CREATED_AND_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private SkillMapper skillMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommentMockMvc;

    @Autowired
    private ExtendedCommentService extendedCommentService;

    @BeforeEach
    public void initInstantProvider() {
        extendedCommentService.setTime(StaticInstantProvider.forFixedTime(CUSTOM_CREATED_AND_UPDATED_AT));
    }

    @Test
    @Transactional
    @Disabled("This reproduces issue #58.")
    void createComment() throws Exception {
        final var databaseSizeBeforeCreate = commentRepository.findAll().size();

        final var skill = new SkillDTO();
        skill.setTitle("Automate everything'");
        skill.setDescription("Avoid human errors, ensure you can react fast");
        skill.setImplementation("Use Continuous deployment and continuous delivery");
        skill.setValidation("You can go on vacation without any risk");
        skill.setScore(1);
        em.persist(skillMapper.toEntity(skill));

        final var comment = new CommentDTO();
        comment.setText("test");
        // Setting an empty team provokes the TransientPropertyValueException because the given team is not persisted yet.
        comment.setTeam(new TeamDTO());
        comment.setSkill(skill);

        restCommentMockMvc
            .perform(
                post("/api/comments")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comment))
            )
            .andExpect(status().isCreated());

        final var commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeCreate + 1);
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getText()).isEqualTo("test");
        assertThat(testComment.getCreatedAt()).isEqualTo(CUSTOM_CREATED_AND_UPDATED_AT);
        assertThat(testComment.getUpdatedAt()).isEqualTo(CUSTOM_CREATED_AND_UPDATED_AT);
    }
}
