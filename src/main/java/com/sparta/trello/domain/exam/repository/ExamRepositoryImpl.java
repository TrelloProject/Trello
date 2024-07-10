package com.sparta.trello.domain.exam.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.trello.domain.exam.entity.Exam;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ExamRepositoryImpl implements ExamQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    @Override
    public List<Exam> findAllExam() {
        return List.of();
    }
}