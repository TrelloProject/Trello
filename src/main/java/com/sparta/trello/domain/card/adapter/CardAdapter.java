package com.sparta.trello.domain.card.adapter;

import com.sparta.trello.domain.card.entity.Card;
import com.sparta.trello.domain.card.repository.CardRepository;
import com.sparta.trello.domain.deck.repository.DeckRepository;
import com.sparta.trello.domain.user.entity.User;
import com.sparta.trello.exception.custom.card.CardCodeEnum;
import com.sparta.trello.exception.custom.card.detail.CardIndexOutOfBoundsException;
import com.sparta.trello.exception.custom.card.detail.CardNoPermissionException;
import com.sparta.trello.exception.custom.card.detail.CardNotFoundException;
import com.sparta.trello.exception.custom.deck.detail.DeckCodeEnum;
import com.sparta.trello.exception.custom.deck.detail.DeckDetailCustomException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class CardAdapter {

    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;

    public Card save(Card card) {
        return cardRepository.save(card);
    }

    public void delete(Card card) {
        cardRepository.delete(card);
    }

    public void validateCardOwnership(Card card, User user) {
        if (!card.getUser().getId().equals(user.getId())) {
            throw new CardNoPermissionException(CardCodeEnum.CARD_NO_PERMISSION);
        }
    }

    public Card findById(Long cardId) {
        return cardRepository.findById(cardId)
            .orElseThrow(() -> new CardNotFoundException(CardCodeEnum.CARD_NOT_FOUND));
    }

    public Optional<Card> findLastCardByDeckId(Long deckId) {
        List<Card> sortedCards = getSortedCards(deckId);
        if (sortedCards.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(sortedCards.get(sortedCards.size() - 1));
    }

    public Card findCardByNextId(Long cardId) {
        return cardRepository.findCardByNextId(cardId);
    }

    public List<Card> findAllByDeckId(Long deckId) {
        return cardRepository.findAllByDeckId(deckId);
    }

    public Card findCardByDeckIdAndIndex(Long deckId, int index) {
        List<Card> sortedCards = getSortedCards(deckId);

        if (index < 0 || index >= sortedCards.size()) {
            throw new CardIndexOutOfBoundsException(CardCodeEnum.CARD_INDEX_OUT_OF_BOUNDS_ERROR);
        }

        return sortedCards.get(index);
    }

    private List<Card> getSortedCards(Long deckId) {
        List<Card> cards = findAllByDeckId(deckId);

        if (cards.isEmpty()) {
            throw new DeckDetailCustomException(DeckCodeEnum.DECK_NOT_FOUND);
        }

        Map<Long, Card> cardMap = cards.stream()
            .collect(Collectors.toMap(Card::getId, card -> card));

        // 덱의 헤드 카드 찾기
        Long headCardId = deckRepository.findById(deckId)
            .orElseThrow(() -> new DeckDetailCustomException(
                DeckCodeEnum.DECK_NOT_FOUND)).getHeadCardId();
        Card headCard = cardMap.get(headCardId);

        // nextId를 기준으로 카드 정렬
        List<Card> sortedCards = new LinkedList<>();
        Card currCard = headCard;
        while (currCard != null) {
            sortedCards.add(currCard);
            currCard = cardMap.get(currCard.getNextId());
        }

        return sortedCards;
    }
}