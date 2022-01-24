package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.domain.Comment;
import com.iteratec.teamdojo.repository.CommentRepository;
import com.iteratec.teamdojo.service.custom.ExtendedCommentService;
import com.iteratec.teamdojo.service.dto.CommentDTO;
import com.iteratec.teamdojo.service.impl.CommentServiceImpl;
import com.iteratec.teamdojo.service.mapper.CommentMapper;
import lombok.NonNull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ExtendedCommentServiceImpl extends CommentServiceImpl implements ExtendedCommentService {

    private final AuditableDataTracker<CommentDTO, Comment> tracker;

    public ExtendedCommentServiceImpl(final CommentRepository repo, final CommentMapper mapper) {
        super(repo, mapper);
        this.tracker = new AuditableDataTracker<>(mapper, repo::findById);
    }

    @Override
    public void setTime(@NonNull InstantProvider time) {
        tracker.setTime(time);
    }

    @Override
    public CommentDTO save(final CommentDTO dto) {
        tracker.modifyCreatedAndUpdatedAt(dto);
        return super.save(dto);
    }
}
