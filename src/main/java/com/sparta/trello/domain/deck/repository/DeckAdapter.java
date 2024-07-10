package com.sparta.trello.domain.deck.repository;

import com.sparta.trello.domain.board.entity.Board;
import com.sparta.trello.domain.deck.Entity.Deck;
import com.sparta.trello.exception.custom.deck.detail.DeckCodeEnum;
import com.sparta.trello.exception.custom.deck.detail.DeckDetailCustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeckAdapter {
    private DeckRepository deckRepository;

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

    public Deck findPreviousDeck(List<Deck> deckList, Deck currentDeck) {
        for (Deck deck : deckList) {
            if (deck.getNextId().equals(currentDeck.getId())) {
                return deck;
            }
        }
        return null;
    }

    public void updateNextId(Deck currentDeck, Board board, List<Deck> deckList){
        Deck prevDeck = findPreviousDeck(deckList, currentDeck);

        if (prevDeck != null) {
            prevDeck.updateNextId(currentDeck.getNextId());
        } else {
            // currentDeck이 헤드 덱인 경우
            //board.setHeadDeckId(currentDeck.getNextId());
        }
    }

}
