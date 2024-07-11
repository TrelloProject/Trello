package com.sparta.trello.boardall.boarddto.boardservice;

import com.sparta.trello.UpdateBoardRequest;
import com.sparta.trello.boardall.boarddto.AddBoardRequest;
import com.sparta.trello.domain.boardManager.BoardManager;
import com.sparta.trello.domain.boardManager.BoardRole;
import com.sparta.trello.domain.exam.entity.Board;
import com.sparta.trello.exception.custom.exam.ExamCodeEnum;
import com.sparta.trello.exception.custom.exam.ExamException;
import com.sparta.trello.repersitory.BoardReporsitory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardReporsitory boardReporsitory;

    @Transactional
    public Board addBoard(AddBoardRequest request) {
        Board board = request.toEntity();
        BoardManager manager = BoardManager.builder()
                .boardRole(BoardRole.MANAGER)
                .user

        return boardReporsitory.save(board);
    }

    public void delete(Long id) {
        boardReporsitory.deleteById(id);
    }

    public List<Board> findAll() {
        return boardReporsitory.findAll();
    }

    @Transactional
    public Board update(long id, UpdateBoardRequest request) {
        Board board = boardReporsitory.findById(id)
                .orElseThrow(()-> new ExamException(ExamCodeEnum.EXAM_NOT_FOUND));
        board.update(request.getTitle(), request.getDescription());

        return board;
    }
}
