package com.sparta.trello.domain.exam.service;

import com.sparta.trello.domain.exam.dto.ExamCreateRequestDto;
import com.sparta.trello.domain.exam.dto.ExamResponseDto;
import com.sparta.trello.domain.exam.dto.ExamUpdateRequestDto;
import com.sparta.trello.domain.exam.entity.Exam;
import com.sparta.trello.domain.exam.repository.ExamAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExamService {
    private final ExamAdapter examAdapter;

    // Exam 조회
    @Transactional(readOnly = true)
    public ExamResponseDto findById(Long examId) {
        Exam findExam = examAdapter.findById(examId);

        examAdapter.validateExam(); // ex) 회원 탈퇴 여부

        return ExamResponseDto.of(findExam);
    }

    // Exam 저장
    @Transactional
    public ExamResponseDto save(ExamCreateRequestDto requestDto) {
        Exam exam = Exam.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();

        Exam savedExam = examAdapter.save(exam);

        return ExamResponseDto.of(savedExam);
    }

    // Exam 수정
    @Transactional
    public ExamResponseDto update(Long examId, ExamUpdateRequestDto requestDto) {
        Exam findExam = examAdapter.findById(examId);

        examAdapter.validateExam(); // ex) 회원 탈퇴 여부

        findExam.update(requestDto);

        return ExamResponseDto.of(findExam);
    }

    // Exam 삭제
    @Transactional
    public void delete(Long examId) {
        Exam findExam = examAdapter.findById(examId);

        examAdapter.validateExam(); // ex) 회원 탈퇴 여부

        examAdapter.delete(findExam);
    }
}