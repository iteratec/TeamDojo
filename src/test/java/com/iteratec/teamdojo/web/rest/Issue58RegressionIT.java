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
import com.iteratec.teamdojo.service.mapper.CommentMapper;
import com.iteratec.teamdojo.test.util.StaticInstantProvider;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
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
 *
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class Issue58RegressionIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final Instant CUSTOM_CREATED_AND_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final String ENTITY_API_URL = "/api/comments";

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommentMockMvc;

    @Autowired
    private ExtendedCommentService extendedCommentService;

    private Comment comment;

    @BeforeEach
    public void initTest() {
        comment = CommentResourceIT.createEntity(em);
    }

    @BeforeEach
    public void initInstantProvider() {
        extendedCommentService.setTime(StaticInstantProvider.forFixedTime(CUSTOM_CREATED_AND_UPDATED_AT));
    }

    @Test
    @Transactional
    void createComment() throws Exception {
        int databaseSizeBeforeCreate = commentRepository.findAll().size();
        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);
        restCommentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeCreate + 1);
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testComment.getCreatedAt()).isEqualTo(CUSTOM_CREATED_AND_UPDATED_AT);
        assertThat(testComment.getUpdatedAt()).isEqualTo(CUSTOM_CREATED_AND_UPDATED_AT);
    }
}
