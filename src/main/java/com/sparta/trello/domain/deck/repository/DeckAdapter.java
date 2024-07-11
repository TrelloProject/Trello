package com.sparta.trello.domain.deck.repository;

import com.sparta.trello.domain.board.entity.Board;
import com.sparta.trello.domain.deck.Entity.Deck;
import com.sparta.trello.exception.custom.deck.detail.DeckCodeEnum;
import com.sparta.trello.exception.custom.deck.detail.DeckDetailCustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeckAdapter {
    private final DeckRepository deckRepository;

    public Deck findById(Long deckId){
        return deckRepository.findById(deckId).orElseThrow(() ->
                new DeckDetailCustomException(DeckCodeEnum.DECK_NOT_FOUND));
    }

    public List<Deck> findByAll(){
        return deckRepository.findAll();
    }

    public List<Deck> findByBoard(Board board){
        return deckRepository.findByBoard(board);
    }

    public Long findByCountBoardIdDeck(Board board){
        return deckRepository.findByCountBoardIdDeck(board);
    }

    public Deck findByNextId(Long nextId){
        return deckRepository.findByNextId(nextId).orElseThrow(() ->
                new DeckDetailCustomException(DeckCodeEnum.DECK_NOT_FOUND));
    }

    public Deck save(Deck deck){
        return deckRepository.save(deck);
    }

    public void delete(Deck deck){
        deckRepository.delete(deck);
    }

    public Deck findDeckAtIndex(List<Deck> deckList, Long index, Long headDeckId) {
        Long currentId = headDeckId;
        Deck currentDeck = null;

        for (int i = 0; i <= index; i++) {
            Long finalCurrentId = currentId;
            currentDeck = deckList.stream().filter(deck -> deck.getId().equals(finalCurrentId)).findFirst().orElse(null);
            if (currentDeck == null) {
                break;
            }
            currentId = currentDeck.getNextId();
        }

        return currentDeck;
    }

    public void updateNextId(Deck currentDeck, Board board, List<Deck> deckList){
        Deck prevDeck = findPreviousDeck(deckList, currentDeck);

        if (prevDeck != null) {
            prevDeck.updateNextId(currentDeck.getNextId());
        } else {
            // currentDeck이 헤드 덱인 경우
            board.updateHeadDeckId(currentDeck.getNextId());
        }
    }

    private Deck findPreviousDeck(List<Deck> deckList, Deck currentDeck) {
        for (Deck deck : deckList) {
            if (deck.getNextId() != null && deck.getNextId().equals(currentDeck.getId())) {
                return deck;
            }
        }
        return null;
    }

}
