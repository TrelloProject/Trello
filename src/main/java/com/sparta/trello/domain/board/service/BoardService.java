package com.sparta.trello.domain.board.service;

import com.sparta.trello.UpdateBoardRequest;
import com.sparta.trello.domain.board.dto.BoardDto;
import com.sparta.trello.domain.board.dto.BoardItemsDto;
import com.sparta.trello.domain.board.dto.BoardResponseDto;
import com.sparta.trello.domain.board.dto.CreateBoardRequest;
import com.sparta.trello.domain.board.dto.InviteBoardRequestDto;
import com.sparta.trello.domain.board.entity.Board;
import com.sparta.trello.domain.board.repository.BoardAdapter;
import com.sparta.trello.domain.boardMember.entity.BoardMember;
import com.sparta.trello.domain.boardMember.entity.BoardRole;
import com.sparta.trello.domain.boardMember.repository.BoardMemberAdapter;
import com.sparta.trello.domain.boardMember.repository.BoardMemberQueryRepository;
import com.sparta.trello.domain.boardMember.repository.BoardMemberRepository;
import com.sparta.trello.domain.card.adapter.CardAdapter;
import com.sparta.trello.domain.card.dto.CardDto;
import com.sparta.trello.domain.card.entity.Card;
import com.sparta.trello.domain.deck.dto.DeckDto;
import com.sparta.trello.domain.deck.entity.Deck;
import com.sparta.trello.domain.deck.repository.DeckAdapter;
import com.sparta.trello.domain.user.adapter.UserAdapter;
import com.sparta.trello.domain.user.entity.User;
import com.sparta.trello.exception.custom.boardMember.detail.BoardMemberCodeEnum;
import com.sparta.trello.exception.custom.boardMember.detail.BoardMemberDetailCustomException;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class BoardService {

    private final BoardAdapter boardAdapter;
    private final BoardMemberAdapter boardMemberAdapter;
    private final UserAdapter userAdapter;
    private final BoardMemberRepository boardMemberRepository;
    private final DeckAdapter deckAdapter;
    private final CardAdapter cardAdapter;

    @Transactional
    public BoardResponseDto createBoard(CreateBoardRequest request, User user) {
        Board board = Board.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .build();

        BoardMember boardMember = BoardMember.builder()
                .boardRole(BoardRole.MANAGER)
                .user(user)
                .board(board).build();
        board = boardAdapter.save(board);
        boardMemberAdapter.save(boardMember);

        return new BoardResponseDto(board);
    }

    @Transactional
    public void deleteBoard(Long boardId, User user) {
        Board board = boardAdapter.findById(boardId);
        BoardMember member = boardMemberAdapter.findByBoardAndUser(board, user);
        boardMemberAdapter.validateBoardMember(member);
        boardAdapter.delete(board);
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getAllBoards(User user) {
        List<BoardMember> boardMembers = boardMemberRepository.findByUserId(user.getId());
        return boardMembers.stream()
            .map(member -> new BoardResponseDto(member.getBoard()))
            .toList();
    }

    @Transactional
    public BoardResponseDto updateBoard(Long boardId, UpdateBoardRequest request, User user) {
        Board board = boardAdapter.findById(boardId);
        BoardMember member = boardMemberAdapter.findByBoardAndUser(board, user);
        boardMemberAdapter.validateBoardMember(member);
        board.update(request.getTitle(), request.getDescription());

        return new BoardResponseDto(board);
    }

    @Transactional
    public void inviteUser(InviteBoardRequestDto requestDto, Long boardId, User user) {
        Board board = boardAdapter.findById(boardId);
        BoardMember member = boardMemberAdapter.findByBoardAndUser(board, user);
        boardMemberAdapter.validateBoardMember(member);

        User inviteUser = userAdapter.findByUsername(requestDto.getUsername());
        BoardMember findUserMember = boardMemberAdapter.validateUserMember(board, inviteUser);
        if(findUserMember != null) {
            throw new BoardMemberDetailCustomException(BoardMemberCodeEnum.BOARD_MEMBER_NOT_FOUND);
        }
        BoardMember manager = BoardMember.builder()
                .boardRole(BoardRole.USER)
                .user(inviteUser)
                .board(board).build();
        boardMemberAdapter.save(manager);
    }

    @Transactional(readOnly = true)
    public BoardDto getBoard(Long boardId) {
        List<BoardItemsDto> boardItemsList = new ArrayList<>();

        Board findBoard = boardAdapter.findById(boardId);
        Long currentDeckId = findBoard.getHeadDeckId();
        List<Deck> findDecks = deckAdapter.findByBoard(findBoard);
        Map<Long, Deck> deckMap = findDecks.stream().collect(Collectors.toMap(Deck::getId, deck -> deck));

        List<DeckDto> sortedDeckDtos = new ArrayList<>();
        while (currentDeckId != null) {
            Deck currentDeck = deckMap.get(currentDeckId);
            if (currentDeck == null) {
                break; // 현재 Deck ID에 해당하는 Deck이 없으면 중단
            }
            sortedDeckDtos.add(new DeckDto(currentDeck));
            currentDeckId = currentDeck.getNextId();
        }

        for (DeckDto deckDto : sortedDeckDtos) {
            Deck deck = deckMap.get(deckDto.getId());
            List<Card> allCards = cardAdapter.findAllByDeckId(deck.getId());
            Map<Long, Card> cardMap = allCards.stream().collect(Collectors.toMap(Card::getId, card -> card));

            List<CardDto> sortedCardDtos = new ArrayList<>();
            Long currentCardId = deck.getHeadCardId();
            while (currentCardId != null) {
                Card currentCard = cardMap.get(currentCardId);
                if (currentCard == null) {
                    break; // 현재 Card ID에 해당하는 Card가 없으면 중단
                }
                sortedCardDtos.add(new CardDto(currentCard));
                currentCardId = currentCard.getNextId();
            }

            // Create a new BoardItemsDto for each deck and add it to the list
            BoardItemsDto boardItems = new BoardItemsDto(deckDto, sortedCardDtos);
            boardItemsList.add(boardItems);
        }

        return new BoardDto(findBoard.getId(), findBoard.getTitle(), findBoard.getDescription(), boardItemsList);
    }



}