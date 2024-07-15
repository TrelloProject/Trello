package com.sparta.trello.domain.card.service;

import com.sparta.trello.domain.board.entity.Board;
import com.sparta.trello.domain.boardMember.entity.BoardMember;
import com.sparta.trello.domain.boardMember.repository.BoardMemberAdapter;
import com.sparta.trello.domain.card.adapter.CardAdapter;
import com.sparta.trello.domain.card.dto.CardResponseDto;
import com.sparta.trello.domain.card.dto.CreateCardRequestDto;
import com.sparta.trello.domain.card.dto.MoveCardRequestDto;
import com.sparta.trello.domain.card.dto.UpdateCardRequestDto;
import com.sparta.trello.domain.card.entity.Card;
import com.sparta.trello.domain.comment.entity.Comment;
import com.sparta.trello.domain.comment.repository.CommentAdapter;
import com.sparta.trello.domain.deck.entity.Deck;
import com.sparta.trello.domain.deck.repository.DeckAdapter;
import com.sparta.trello.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class CardService {

    private final CardAdapter cardAdapter;
    private final DeckAdapter deckAdapter;
    private final CommentAdapter commentAdapter;
    private final BoardMemberAdapter boardMemberAdapter;

    @Transactional
    public void createCard(CreateCardRequestDto createCardRequestDto, User user) {
        Deck deck = deckAdapter.findById(createCardRequestDto.getDeckId());

        // 보드 멤버 검증
        Board board = deck.getBoard();
        BoardMember boardMember = boardMemberAdapter.findByBoardAndUser(board, user);
        boardMemberAdapter.validateBoardMember(boardMember);

        // 덱에서 가장 마지막 카드 조회
        Optional<Card> lastCardOpt = cardAdapter.findLastCardByDeckId(deck.getId());

        // 새로운 카드 생성
        Card newCard = Card.builder()
            .deck(deck)
            .user(user)
            .title(createCardRequestDto.getTitle())
            .description(createCardRequestDto.getDescription())
            .startDate(createCardRequestDto.getStartDate())
            .dueDate(createCardRequestDto.getDueDate())
            .build();

        // 생성된 카드 저장
        newCard = cardAdapter.save(newCard);

        if (lastCardOpt.isEmpty()) { // 덱이 비어있는 경우
            deck.setHeadCardId(newCard.getId()); // 덱의 헤드 카드 아이디를 생성된 카드의 아이디로 설정
            deckAdapter.save(deck); // 덱 업데이트
        } else {
            Card lastCard = lastCardOpt.get();

            lastCard.setNextId(newCard.getId()); // 마지막 카드의 다음 아이디를 생성된 카드의 아이디로 설정
            cardAdapter.save(lastCard); // 마지막 카드 업데이트
        }
        log.info("Card created with id: {}", newCard.getId());
    }

    @Transactional(readOnly = true)
    public CardResponseDto getCard(Long cardId, User user) {
        Card card = cardAdapter.findById(cardId);

        // 보드 멤버 검증
        Board board = card.getDeck().getBoard();
        BoardMember boardMember = boardMemberAdapter.findByBoardAndUser(board, user);
        boardMemberAdapter.validateBoardMember(boardMember);

        List<Comment> comments = commentAdapter.findByCardId(cardId);
//        cardAdapter.validateCardOwnership(card, user);
        return new CardResponseDto(card, comments);
    }

    @Transactional
    public void updateCard(Long cardId, UpdateCardRequestDto updateCardRequestDto, User user) {
        Card card = cardAdapter.findById(cardId);

        // 보드 멤버 검증
        Board board = card.getDeck().getBoard();
        BoardMember boardMember = boardMemberAdapter.findByBoardAndUser(board, user);
        boardMemberAdapter.validateBoardMember(boardMember);

        card.update(updateCardRequestDto);
        cardAdapter.save(card);
        log.info("Card updated with id: {}", card.getId());
    }

    @Transactional
    public void moveCard(Long cardId, MoveCardRequestDto moveCardRequestDto, User user) {
        Card card = cardAdapter.findById(cardId);

        // 보드 멤버 검증
        Board board = card.getDeck().getBoard();
        BoardMember boardMember = boardMemberAdapter.findByBoardAndUser(board, user);
        boardMemberAdapter.validateBoardMember(boardMember);

        // 현재 덱
        Deck currDeck = card.getDeck();
        // 이동할 덱
        Deck targetDeck = (moveCardRequestDto.getDeckId() == null) ? currDeck
            : deckAdapter.findById(moveCardRequestDto.getDeckId());
        // 이동할 위치
        int index = moveCardRequestDto.getIndex();

        // 이동할 카드의 앞 뒤 연결
        if (currDeck.getHeadCardId().equals(cardId)) { // 이동할 카드가 헤드일 경우
            currDeck.setHeadCardId(card.getNextId());
            deckAdapter.save(currDeck);
        } else {
            Card prevCard = cardAdapter.findCardByNextId(cardId);
            prevCard.setNextId(card.getNextId());
            cardAdapter.save(prevCard);
        }

        // 타겟 덱에서의 위치 계산
        Card prevCard = null;
        if (index > 0) {
            prevCard = cardAdapter.findCardByDeckIdAndIndex(targetDeck.getId(), index - 1);
        }
        if (prevCard == null) { // index 0인 경우 또는 empty deck
            // 덱의 헤드로 이동
            card.setNextId(targetDeck.getHeadCardId());
            targetDeck.setHeadCardId(card.getId());
        } else {
            // 중간 위치로 이동
            card.setNextId(prevCard.getNextId());
            prevCard.setNextId(card.getId());
            cardAdapter.save(prevCard);
        }

        card.setDeck(targetDeck);
        cardAdapter.save(card);
        deckAdapter.save(targetDeck);

        log.info("Card moved with id: {} to deck id: {} at index: {}", card.getId(),
            targetDeck.getId(), index);
    }

    @Transactional
    public void deleteCard(Long cardId, User user) {
        Card card = cardAdapter.findById(cardId);

        // 보드 멤버 검증
        Board board = card.getDeck().getBoard();
        BoardMember boardMember = boardMemberAdapter.findByBoardAndUser(board, user);
        boardMemberAdapter.validateBoardMember(boardMember);

        Deck deck = card.getDeck();
        if (deck.getHeadCardId().equals(cardId)) {
            deck.setHeadCardId(card.getNextId());
            deckAdapter.save(deck);
        } else {
            Card prevCard = cardAdapter.findCardByNextId(cardId);

            if (prevCard != null) {
                prevCard.setNextId(card.getNextId());
                cardAdapter.save(prevCard);
            }
        }
        cardAdapter.delete(card);
        log.info("Card deleted with id: {}", cardId);
    }
}