package com.sparta.trello.domain.board.service;

import com.sparta.trello.UpdateBoardRequest;
import com.sparta.trello.domain.board.dto.BoardResponseDto;
import com.sparta.trello.domain.board.dto.CreateBoardRequest;
import com.sparta.trello.domain.board.dto.InviteBoardRequestDto;
import com.sparta.trello.domain.board.entity.Board;
import com.sparta.trello.domain.board.repository.BoardAdapter;
import com.sparta.trello.domain.boardMember.entity.BoardMember;
import com.sparta.trello.domain.boardMember.entity.BoardRole;
import com.sparta.trello.domain.boardMember.repository.BoardMemberAdapter;
import com.sparta.trello.domain.user.adapter.UserAdapter;
import com.sparta.trello.domain.user.entity.User;
import com.sparta.trello.exception.custom.boardMember.detail.BoardMemberCodeEnum;
import com.sparta.trello.exception.custom.boardMember.detail.BoardMemberDetailCustomException;
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
    public BoardResponseDto getBoard(Long boardId, User user) {
        Board board = boardAdapter.findById(boardId);
        boardMemberAdapter.findByBoardAndUser(board, user);
        return new BoardResponseDto(board);
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getAllBoards(User user) {
        List<BoardMember> boardMember = boardMemberAdapter.findByUser(user);
        List<Board> boards = new ArrayList<>();
        for(BoardMember member : boardMember) {
            boards.add(member.getBoard());
        }
        return boards.stream().map(BoardResponseDto::new).toList();
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
}