package com.sparta.trello.domain.board.service;

import com.sparta.trello.domain.board.dto.BoardDto;
import com.sparta.trello.domain.board.dto.BoardItemsDto;
import com.sparta.trello.domain.board.dto.BoardResponseDto;
import com.sparta.trello.domain.board.dto.CreateBoardRequestDto;
import com.sparta.trello.domain.board.dto.InviteBoardRequestDto;
import com.sparta.trello.domain.board.dto.UpdateBoardRequestDto;
import com.sparta.trello.domain.board.entity.Board;
import com.sparta.trello.domain.board.repository.BoardAdapter;
import com.sparta.trello.domain.boardMember.entity.BoardMember;
import com.sparta.trello.domain.boardMember.entity.BoardRole;
import com.sparta.trello.domain.boardMember.repository.BoardMemberAdapter;
import com.sparta.trello.domain.boardMember.repository.BoardMemberRepository;
import com.sparta.trello.domain.card.adapter.CardAdapter;
import com.sparta.trello.domain.card.dto.CardDto;
import com.sparta.trello.domain.deck.adapter.DeckAdapter;
import com.sparta.trello.domain.deck.dto.DeckDto;
import com.sparta.trello.domain.user.adapter.UserAdapter;
import com.sparta.trello.domain.user.entity.User;
import com.sparta.trello.exception.custom.boardMember.detail.BoardMemberCodeEnum;
import com.sparta.trello.exception.custom.boardMember.detail.BoardMemberDetailCustomException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public BoardResponseDto createBoard(CreateBoardRequestDto request, User user) {
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
        boardMemberAdapter.validateBoardManager(member);
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
    public BoardResponseDto updateBoard(Long boardId, UpdateBoardRequestDto request, User user) {
        Board board = boardAdapter.findById(boardId);
        BoardMember member = boardMemberAdapter.findByBoardAndUser(board, user);
        boardMemberAdapter.validateBoardManager(member);
        board.update(request.getTitle(), request.getDescription());

        return new BoardResponseDto(board);
    }

    @Transactional
    public void inviteUser(InviteBoardRequestDto requestDto, Long boardId, User user) {
        Board board = boardAdapter.findById(boardId);
        BoardMember member = boardMemberAdapter.findByBoardAndUser(board, user);
        boardMemberAdapter.validateBoardManager(member);

        User inviteUser = userAdapter.findByUsername(requestDto.getUsername());
        BoardMember findUserMember = boardMemberAdapter.validateUserMember(board, inviteUser);
        if (findUserMember != null) {
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
        Board board = boardAdapter.findById(boardId);

        List<DeckDto> sortedDeckDtos = deckAdapter.getSortedDecks(boardId).stream()
            .map(DeckDto::new).toList();

        for (DeckDto deckDto : sortedDeckDtos) {
            List<CardDto> sortedCardDtos = cardAdapter.getSortedCards(deckDto.getId()).stream()
                .map(CardDto::new).toList();

            // Create a new BoardItemsDto for each deck and add it to the list
            BoardItemsDto boardItems = new BoardItemsDto(deckDto, sortedCardDtos);
            boardItemsList.add(boardItems);
        }

        return new BoardDto(board.getId(), board.getTitle(), board.getDescription(),
            boardItemsList);
    }
}