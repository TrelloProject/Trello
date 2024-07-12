package com.sparta.trello.domain.card.adapter;

import com.sparta.trello.domain.card.entity.Card;
import com.sparta.trello.domain.card.repository.CardRepository;
import com.sparta.trello.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class CardAdapter {

    private final CardRepository cardRepository;

    public Card save(Card card) {
        return cardRepository.save(card);
    }

    public void delete(Card card) {
        cardRepository.delete(card);
    }

    public void validateCardOwnership(Card card, User user) {
        if (!card.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("(임시 메시지)사용자의 권한이 없습니다."); // 커스텀 예외 추가 예정
        }
    }

    public Card findById(Long cardId) {
        return cardRepository.findById(cardId)
            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 카드 ID입니다.")); // 커스텀 예외 추가 예정
    }

    public Optional<Card> findLastCardByDeckId(Long deckId) {
        return cardRepository.findTopByDeckIdOrderByIdDesc(deckId);
    }

    public Card findCardByNextId(Long cardId) {
        return cardRepository.findCardByNextId(cardId);
    }

    public List<Card> findAllByDeckId(Long deckId) {
        return cardRepository.findAllByDeckId(deckId);
    }

    public Card findCardByDeckIdAndIndex(Long deckId, int index) {
        List<Card> cards = findAllByDeckId(deckId);
        if (index < 0 || index >= cards.size()) {
            throw new IllegalArgumentException("(임시 메시지) 인덱스 out of bound"); // 커스텀 예외 추가 예정
        }
        return cards.get(index);
    }
}