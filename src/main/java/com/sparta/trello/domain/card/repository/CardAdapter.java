package com.sparta.trello.domain.card.repository;

import com.sparta.trello.domain.card.entity.Card;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CardAdapter {

    private final CardRepository cardRepository;

    public Card findById(Long cardId) {
        return cardRepository.findById(cardId)
            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 카드 ID입니다.")); // 커스텀 예외 추가 예정
    }
}