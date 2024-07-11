package com.sparta.trello.domain.comment.repository;

import com.sparta.trello.domain.comment.entity.Comment;
import com.sparta.trello.domain.user.entity.User;
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
            throw new IllegalArgumentException("사용자는 해당 댓글의 작성자가 아닙니다."); // 커스텀 예외 추가 예정
        }
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 댓글 ID입니다.")); // 커스텀 예외 추가 예정
    }
}