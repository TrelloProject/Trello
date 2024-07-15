package com.sparta.trello.domain.deck.service;

import com.sparta.trello.domain.board.entity.Board;
import com.sparta.trello.domain.board.repository.BoardAdapter;
import com.sparta.trello.domain.boardMember.entity.BoardMember;
import com.sparta.trello.domain.boardMember.repository.BoardMemberAdapter;
import com.sparta.trello.domain.deck.dto.DeckRequestDto;
import com.sparta.trello.domain.deck.entity.Deck;
import com.sparta.trello.domain.deck.repository.DeckAdapter;
import com.sparta.trello.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "deck 서비스")
public class DeckService {

    private final DeckAdapter deckAdapter;
    private final BoardAdapter boardAdapter;
    private final BoardMemberAdapter boardMemberAdapter;

    /**
     * deck 생성
     *
     * @param requestDto 생성할 deck 정보
     * @param user       로그인 유저
     */
    @Transactional
    public void createDeck(DeckRequestDto requestDto, User user) {
        Board board = boardAdapter.findById(requestDto.getBoardId());

        BoardMember boardMember = boardMemberAdapter.findByBoardAndUser(board, user);
        boardMemberAdapter.validateBoardManager(boardMember);

        Long deckCount = deckAdapter.findByCountBoardIdDeck(board);

        if (deckCount.equals(0L)) {

            Deck deck = Deck.builder()
                .title(requestDto.getTitle())
                .board(board)
                .build();
            deck = deckAdapter.save(deck);

            // 보드의 head_deck_id 업데이트
            board.updateHeadDeckId(deck.getId());
        } else {
            Deck prevDeck = deckAdapter.findByNextId(null, requestDto.getBoardId());

            Deck deck = Deck.builder()
                .title(requestDto.getTitle())
                .board(board)
                .build();
            deck = deckAdapter.save(deck);
            prevDeck.updateNextId(deck.getId());
        }
    }

    /**
     * deck 수정
     *
     * @param user       로그인 유저
     * @param deckId     수정할 deck id
     * @param requestDto 수정할 deck 정보
     */
    @Transactional
    public void updateDeck(Long deckId, DeckRequestDto requestDto, User user) {
        Deck deck = deckAdapter.findById(deckId);
        BoardMember boardMember = boardMemberAdapter.findByBoardAndUser(deck.getBoard(), user);

        boardMemberAdapter.validateBoardManager(boardMember);

        deck.updateTitle(requestDto.getTitle());
    }

    /**
     * deck 삭제
     *
     * @param user   로그인 유저
     * @param deckId 삭제할 deck id
     */
    @Transactional
    public void deleteDeck(Long deckId, User user) {
        Deck currentDeck = getDeckAndValidateBoardManager(deckId, user);
        Board board = currentDeck.getBoard();

        List<Deck> deckList = deckAdapter.findByBoard(board);

        deckAdapter.updateNextId(currentDeck, board, deckList);
        deckAdapter.delete(currentDeck);
    }

    /**
     * deck 이동
     *
     * @param user   로그인 유저
     * @param deckId 이동할 deck id
     * @param index  이동할 position 위치
     */
    @Transactional
    public void moveDeck(Long deckId, Long index, User user) {
        Deck currentDeck = getDeckAndValidateBoardManager(deckId, user);
        Board board = currentDeck.getBoard();

        List<Deck> deckList = deckAdapter.findByBoard(board);

        deckAdapter.updateNextId(currentDeck, board, deckList);

        if (index == 0) {
            // deck을 첫 번째 위치로 이동
            currentDeck.updateNextId(board.getHeadDeckId());
            board.updateHeadDeckId(currentDeck.getId());
        } else {
            // deck을 중간 또는 마지막 위치로 이동
            Deck targetDeck = deckAdapter.findDeckAtIndex(deckList, index - 1,
                board.getHeadDeckId());
            currentDeck.updateNextId(targetDeck.getNextId());
            targetDeck.updateNextId(currentDeck.getId());
        }

    }

    /**
     * 보드 권한 검증
     *
     * @param deckId 검증이 필요한 deck id
     * @param user   로그인 유저
     * @return 검증된 deck 반환
     */
    private Deck getDeckAndValidateBoardManager(Long deckId, User user) {
        Deck currentDeck = deckAdapter.findById(deckId);
        Board board = currentDeck.getBoard();

        BoardMember boardMember = boardMemberAdapter.findByBoardAndUser(board, user);
        boardMemberAdapter.validateBoardManager(boardMember);

        return currentDeck;
    }

}