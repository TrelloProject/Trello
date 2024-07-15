package com.sparta.trello.domain.boardMember.repository;

import com.sparta.trello.domain.boardMember.entity.BoardMember;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMemberQueryRepository {

    List<BoardMember> findByBoardIdTwoBoardMember(Long boardId, List<Long> userIds);
    List<BoardMember> findByUserId(Long userId);
}