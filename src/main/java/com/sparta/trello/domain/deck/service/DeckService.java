package com.sparta.trello.domain.deck.service;

import com.sparta.trello.domain.board.entity.Board;
import com.sparta.trello.domain.board.repository.BoardRepository;
import com.sparta.trello.domain.deck.Entity.Deck;
import com.sparta.trello.domain.deck.dto.DeckMoveRequestDto;
import com.sparta.trello.domain.deck.dto.DeckRequestDto;
import com.sparta.trello.domain.deck.repository.DeckAdapter;
import com.sparta.trello.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeckService {

    private DeckAdapter deckAdapter;
    private BoardRepository boardRepository;

    /**
     * deck 생성
     * @param user 로그인 유저
     * @param boardId deck을 생성할 보드의 id
     * @param requestDto 생성할 deck 정보
     */
    @Transactional
    public void createDeck(User user, Long boardId, DeckRequestDto requestDto) {
        //유저 검증 필요

        Board board = boardRepository.findById(boardId).orElseThrow(() ->
                new IllegalArgumentException("없음"));

        Long deckCount = deckAdapter.findByCountBoardIdDeck(board);

        if(deckCount.equals(0L)){

            Deck deck = Deck.builder()
                    .title(requestDto.getTitle())
                    .nextId(0L)
                    .board(board)
                    .build();
            deck = deckAdapter.save(deck);

            // 보드의 head_deck_id 업데이트
            //board.setHeadDeckId(deck.getId());
        }
        else{
            Deck prevDeck = deckAdapter.findByNextId(0L);

            Deck deck = Deck.builder()
                    .title(requestDto.getTitle())
                    .nextId(0L)
                    .board(board)
                    .build();

            prevDeck.updateNextId(deck.getId());
            deckAdapter.save(prevDeck);
            deckAdapter.save(deck);
        }
    }

    /**
     * deck 수정
     * @param user 로그인 유저
     * @param deckId 수정할 deck id
     * @param requestDto 수정할 deck 정보
     */
    @Transactional
    public void updateDeck(User user, Long deckId, DeckRequestDto requestDto){
        //유저 검증 필요

        Deck deck = deckAdapter.findById(deckId);

        deck.updateTitle(requestDto.getTitle());
    }

    /**
     * deck 삭제
     * @param user 로그인 유저
     * @param deckId 삭제할 deck id
     */
    @Transactional
    public void deleteDeck(User user, Long deckId){
        //유저 검증 필요

        Deck currentDeck = deckAdapter.findById(deckId);
        Board board = currentDeck.getBoard();
        List<Deck> deckList = deckAdapter.findByBoard(board); // 특정 보드의 덱들만 가져옴

        deckAdapter.updateNextId(currentDeck, board, deckList);

        deckAdapter.delete(currentDeck);
    }

    /**
     * deck 이동
     * @param user 로그인 유저
     * @param deckId 이동할 deck id
     * @param index 이동할 position 위치
     */
    @Transactional
    public void moveDeck(User user, Long deckId, Long index){
        //유저 검증 필요
        Deck currentDeck = deckAdapter.findById(deckId);
        Board board = currentDeck.getBoard();
        List<Deck> deckList = deckAdapter.findByBoard(board); // 특정 보드의 덱들만 가져옴

        deckAdapter.updateNextId(currentDeck, board, deckList);

        if (index == 0) {
            // deck을 첫 번째 위치로 이동
            Deck headDeck = deckAdapter.findById(board.getHeadDeckId());
            currentDeck.updateNextId(board.getHeadDeckId());
            //board.setHeadDeckId(currentDeck.getId());
        } else {
            // deck을 중간 또는 마지막 위치로 이동
            Deck targetDeck = deckList.get((int)(index - 1));
            currentDeck.updateNextId(targetDeck.getNextId());
            targetDeck.updateNextId(currentDeck.getId());
        }

    }
}
