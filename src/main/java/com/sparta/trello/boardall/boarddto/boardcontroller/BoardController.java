package com.sparta.trello.boardall.boarddto.boardcontroller;

import com.sparta.trello.UpdateBoardRequest;
import com.sparta.trello.boardall.boarddto.AddBoardRequest;
import com.sparta.trello.boardall.boarddto.boardResponse.BoardResponse;
import com.sparta.trello.boardall.boarddto.boardservice.BoardService;
import com.sparta.trello.common.response.DataResponseDto;
import com.sparta.trello.common.response.MessageResponseDto;
import com.sparta.trello.common.response.ResponseUtils;
import com.sparta.trello.domain.exam.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board")
    public ResponseEntity<DataResponseDto<Board>> addBoard(@RequestBody AddBoardRequest request,
                                                           ) {
        Board saveBoard = boardService.addBoard(request);
        return ResponseUtils.of(HttpStatus.OK, "보드 생성에 성공 했습니다.", saveBoard);
    }
    @DeleteMapping("/board/{id}")
    public ResponseEntity<MessageResponseDto> deleteBoard(@PathVariable Long id) {
        boardService.delete(id);
        return ResponseUtils.of(HttpStatus.OK, "보드 삭제에 성공했습니다.");
    }

    @GetMapping("/board/{id}")
    public ResponseEntity<DataResponseDto<List<BoardResponse>>> findAllBoards(@RequestBody Long id) {
        List<BoardResponse> boards = boardService.findAll()
                .stream()
                .map(BoardResponse::new)
                .toList();

        return ResponseUtils.of(HttpStatus.OK, "보드 조회에 성공했습니다.", boards);
    }

    @PutMapping("/board/{id}")
    public ResponseEntity<DataResponseDto<Board>> updateBoard(@PathVariable Long id, @RequestBody UpdateBoardRequest request) {
        Board updateBoard = boardService.update(id, request);

        return ResponseUtils.of(HttpStatus.OK, "보드 수정에 성공했습니다.", updateBoard);
    }
}
