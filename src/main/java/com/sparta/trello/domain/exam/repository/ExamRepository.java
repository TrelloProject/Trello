package com.sparta.trello.domain.exam.repository;

import com.sparta.trello.domain.exam.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long>, ExamQueryRepository {
}