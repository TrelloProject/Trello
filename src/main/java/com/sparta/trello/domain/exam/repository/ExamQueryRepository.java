package com.sparta.trello.domain.exam.repository;

import com.sparta.trello.domain.exam.entity.Exam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamQueryRepository {
    List<Exam> findAllExam();
}