package com.sparta.trello.domain.exam.repository;

import com.sparta.trello.domain.exam.entity.Exam;
import com.sparta.trello.exception.custom.exam.ExamCodeEnum;
import com.sparta.trello.exception.custom.exam.detail.ExamDetailCustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExamAdapter {
    private final ExamRepository examRepository;

    public Exam findById(Long examId) {
        return examRepository.findById(examId)
                .orElseThrow(() -> new ExamDetailCustomException(ExamCodeEnum.EXAM_NOT_FOUND));
    }

    public Exam save(Exam exam) {
        return examRepository.save(exam);
    }

    public void delete(Exam findExam) {
        examRepository.delete(findExam);
    }

    public List<Exam> findAll() {
        return examRepository.findAllExam();
    }

    public void validateExam() {
        // exam 검증
        // ex) 사용자가 탈퇴 여부 검증
    }
}