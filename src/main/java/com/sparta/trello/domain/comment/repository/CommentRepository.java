package com.sparta.trello.domain.comment.repository;

import com.sparta.trello.domain.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByCardId(Long cardId);
}