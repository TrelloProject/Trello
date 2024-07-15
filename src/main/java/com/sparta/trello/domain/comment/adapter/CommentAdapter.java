package com.sparta.trello.domain.comment.adapter;

import com.sparta.trello.domain.comment.entity.Comment;
import com.sparta.trello.domain.comment.repository.CommentRepository;
import com.sparta.trello.domain.user.entity.User;
import com.sparta.trello.exception.custom.comment.CommentCodeEnum;
import com.sparta.trello.exception.custom.comment.detail.CommentNoPermissionException;
import com.sparta.trello.exception.custom.comment.detail.CommentNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CommentAdapter {

    private final CommentRepository commentRepository;

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    public void validateCommentOwnership(Comment comment, User user) {
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new CommentNoPermissionException(CommentCodeEnum.COMMENT_NO_PERMISSION);
        }
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
            .orElseThrow(() -> new CommentNotFoundException(CommentCodeEnum.COMMENT_NOT_FOUND));
    }

    public List<Comment> findByCardId(Long cardId) {
        return commentRepository.findByCardId(cardId);
    }
}