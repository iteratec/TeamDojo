package com.iteratec.teamdojo.service;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.service.dto.CommentDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.iteratec.teamdojo.domain.Comment}.
 */
@GeneratedByJHipster
public interface CommentService {
    /**
     * Save a comment.
     *
     * @param commentDTO the entity to save.
     * @return the persisted entity.
     */
    CommentDTO save(CommentDTO commentDTO);

    /**
     * Updates a comment.
     *
     * @param commentDTO the entity to update.
     * @return the persisted entity.
     */
    CommentDTO update(CommentDTO commentDTO);

    /**
     * Partially updates a comment.
     *
     * @param commentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CommentDTO> partialUpdate(CommentDTO commentDTO);

    /**
     * Get all the comments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CommentDTO> findAll(Pageable pageable);

    /**
     * Get all the comments with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CommentDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" comment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CommentDTO> findOne(Long id);

    /**
     * Delete the "id" comment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
