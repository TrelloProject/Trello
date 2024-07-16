package com.sparta.trello.domain.deck.adapter;

import com.sparta.trello.domain.board.entity.Board;
import com.sparta.trello.domain.board.repository.BoardRepository;
import com.sparta.trello.domain.deck.entity.Deck;
import com.sparta.trello.domain.deck.repository.DeckRepository;
import com.sparta.trello.exception.custom.deck.detail.DeckCodeEnum;
import com.sparta.trello.exception.custom.deck.detail.DeckDetailCustomException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeckAdapter {

    private final DeckRepository deckRepository;
    private final BoardRepository boardRepository;

    public Deck findById(Long deckId) {
        return deckRepository.findById(deckId).orElseThrow(() ->
            new DeckDetailCustomException(DeckCodeEnum.DECK_NOT_FOUND));
    }

    public List<Deck> findByBoard(Board board) {
        return deckRepository.findByBoard(board);
    }

    public List<Deck> findAllByBoardId(Long boardId) {
        return deckRepository.findAllByBoardId(boardId);
    }

    public Long findByCountBoardIdDeck(Board board) {
        return deckRepository.findByCountBoardIdDeck(board);
    }

    public Deck findByNextId(Long nextId, Long boardId) {
        return deckRepository.findByNextIdAndBoardId(nextId, boardId).orElseThrow(() ->
            new DeckDetailCustomException(DeckCodeEnum.DECK_NOT_FOUND));
    }

    public Deck save(Deck deck) {
        return deckRepository.save(deck);
    }

    public void delete(Deck deck) {
        deckRepository.delete(deck);
    }

    public Deck findDeckAtIndex(List<Deck> deckList, Long index, Long headDeckId) {
        Long currentId = headDeckId;
        Deck currentDeck = null;

        for (int i = 0; i <= index; i++) {
            Long finalCurrentId = currentId;
            currentDeck = deckList.stream().filter(deck -> deck.getId().equals(finalCurrentId))
                .findFirst().orElse(null);
            if (currentDeck == null) {
                break;
            }
            currentId = currentDeck.getNextId();
        }

        return currentDeck;
    }

    public void updateNextId(Deck currentDeck, Board board, List<Deck> deckList) {
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

    public List<Deck> getSortedDecks(Long boardId) {
        List<Deck> decks = findAllByBoardId(boardId);

        Map<Long, Deck> deckMap = decks.stream()
            .collect(Collectors.toMap(Deck::getId, deck -> deck));

        // 보드의 헤드 덱 찾기
        Long headDeckId = boardRepository.findById(boardId)
            .orElseThrow(() -> new DeckDetailCustomException(
                DeckCodeEnum.DECK_NOT_FOUND)).getHeadDeckId();
        Deck headDeck = deckMap.get(headDeckId);

        // nextId를 기준으로 덱 정렬
        List<Deck> sortedDecks = new LinkedList<>();
        Deck currDeck = headDeck;
        while (currDeck != null) {
            sortedDecks.add(currDeck);
            currDeck = deckMap.get(currDeck.getNextId());
        }

        return sortedDecks;
    }
}