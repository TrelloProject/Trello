package com.sparta.trello.domain.boardMember.repository;

import com.sparta.trello.domain.boardMember.BoardMember;
import com.sparta.trello.domain.exam.entity.Board;
import com.sparta.trello.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BoardMemberRepository extends JpaRepository<BoardMember, Long> {
    Optional<BoardMember> findByBoardAndUser(Board board, User user);
    List<BoardMember> findByUser(User user);
}
