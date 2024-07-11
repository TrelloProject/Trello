package com.sparta.trello.boardall.boarddto.boardservice;

import com.sparta.trello.UpdateBoardRequest;
import com.sparta.trello.auth.UserDetailsImpl;
import com.sparta.trello.boardall.boarddto.AddBoardRequest;
import com.sparta.trello.domain.board.dto.InviteBoardRequestDto;
import com.sparta.trello.domain.boardMember.BoardMember;
import com.sparta.trello.domain.boardMember.BoardRole;
import com.sparta.trello.domain.boardMember.repository.BoardMemberRepository;
import com.sparta.trello.domain.exam.entity.Board;
import com.sparta.trello.domain.user.entity.User;
import com.sparta.trello.domain.user.repository.UserRepository;
import com.sparta.trello.exception.custom.exam.ExamCodeEnum;
import com.sparta.trello.exception.custom.exam.ExamException;
import com.sparta.trello.repersitory.BoardReporsitory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.border.Border;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardReporsitory boardReporsitory;
    private final BoardMemberRepository boardMemberRepository;
    private final UserRepository userRepository;

    @Transactional
    public Board addBoard(AddBoardRequest request, User user) {
        Board board = request.toEntity();
        BoardMember manager = BoardMember.builder()
                .boardRole(BoardRole.MANAGER)
                .user(user)
                .board(board).build();
        boardMemberRepository.save(manager);
        return boardReporsitory.save(board);
    }

    public void deleteBoard(Long id, User user) {
        Board board = boardReporsitory.findById(id).orElseThrow(()-> new ExamException(ExamCodeEnum.EXAM_NOT_FOUND));
        BoardMember member = boardMemberRepository.findByBoardAndUser(board, user).orElseThrow(()->new ExamException(ExamCodeEnum.EXAM_NOT_FOUND));
        if(member.getBoardRole() != BoardRole.MANAGER) {
            throw new ExamException(ExamCodeEnum.EXAM_NOT_FOUND);
        }
        boardReporsitory.delete(board);
    }

    public List<Board> findAllBoards(User user) {
        List<BoardMember> boardMember = boardMemberRepository.findByUser(user);
        List<Board> boards = new ArrayList<>();
        for(BoardMember member : boardMember) {
            boards.add(member.getBoard());
        }
        return boards;
    }

    @Transactional
    public Board update(long id, UpdateBoardRequest request, User user) {
        Board board = boardReporsitory.findById(id)
                .orElseThrow(()-> new ExamException(ExamCodeEnum.EXAM_NOT_FOUND));
        BoardMember member = boardMemberRepository.findByBoardAndUser(board, user).orElseThrow(()->new ExamException(ExamCodeEnum.EXAM_NOT_FOUND));
        if(member == null){
            throw new ExamException(ExamCodeEnum.EXAM_NOT_FOUND);
        }
        if(member.getBoardRole() != BoardRole.MANAGER) {
            throw new ExamException(ExamCodeEnum.EXAM_NOT_FOUND);
        }
        board.update(request.getTitle(), request.getDescription());

        return board;
    }

    public void inviteUser(InviteBoardRequestDto requestDto, Long boardId, User user) {
        Board board = boardReporsitory.findById(boardId).orElseThrow(()-> new ExamException(ExamCodeEnum.EXAM_NOT_FOUND));
        BoardMember member = boardMemberRepository.findByBoardAndUser(board, user).orElseThrow(()->new ExamException(ExamCodeEnum.EXAM_NOT_FOUND));
        if(member == null){
            throw new ExamException(ExamCodeEnum.EXAM_NOT_FOUND);
        }
        if(member.getBoardRole() != BoardRole.MANAGER) {
            throw new ExamException(ExamCodeEnum.EXAM_NOT_FOUND);
        }
        User inviteUser = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(()->new ExamException(ExamCodeEnum.EXAM_NOT_FOUND));
        BoardMember findUserMenber = boardMemberRepository.findByBoardAndUser(board, inviteUser).orElse(null);
        if(findUserMenber != null) {
            throw new ExamException(ExamCodeEnum.EXAM_NOT_FOUND);
        }
        BoardMember manager = BoardMember.builder()
                .boardRole(BoardRole.USER)
                .user(inviteUser)
                .board(board).build();
        boardMemberRepository.save(manager);
    }
}
