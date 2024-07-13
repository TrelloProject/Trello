package com.sparta.trello.domain.boardMember.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.trello.domain.boardMember.entity.BoardMember;
import com.sparta.trello.domain.boardMember.entity.QBoardMember;
import com.sparta.trello.domain.user.entity.QUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BoardMemberQueryRepositoryImpl implements BoardMemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BoardMember> findByTwoBoardMember(List<Long> userIds) {
        QBoardMember qBoardMember = QBoardMember.boardMember;
        QUser qUser = QUser.user;

        return queryFactory.selectFrom(qBoardMember)
            .where(qUser.id.in(userIds))
            .fetch();
    }
}