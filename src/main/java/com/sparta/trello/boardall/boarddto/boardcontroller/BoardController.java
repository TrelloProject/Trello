package com.sparta.trello.boardall.boarddto.boardcontroller;

import com.sparta.trello.UpdateBoardRequest;
import com.sparta.trello.auth.UserDetailsImpl;
import com.sparta.trello.boardall.boarddto.AddBoardRequest;
import com.sparta.trello.boardall.boarddto.boardResponse.BoardResponse;
import com.sparta.trello.boardall.boarddto.boardservice.BoardService;
import com.sparta.trello.common.response.DataResponseDto;
import com.sparta.trello.common.response.MessageResponseDto;
import com.sparta.trello.common.response.ResponseUtils;
import com.sparta.trello.domain.board.dto.InviteBoardRequestDto;
import com.sparta.trello.domain.exam.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/boards")
    public ResponseEntity<DataResponseDto<Board>> addBoard(@RequestBody AddBoardRequest request,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Board saveBoard = boardService.addBoard(request, userDetails.getUser());
        return ResponseUtils.of(HttpStatus.OK, "보드 생성에 성공 했습니다.", saveBoard);
    }
    @DeleteMapping("/boards/{id}")
    public ResponseEntity<MessageResponseDto> deleteBoard(@PathVariable Long id,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.deleteBoard(id, userDetails.getUser());
        return ResponseUtils.of(HttpStatus.OK, "보드 삭제에 성공했습니다.");
    }

    @GetMapping("/boards/{id}")
    public ResponseEntity<DataResponseDto<List<BoardResponse>>> findAllBoards(@RequestBody Long id,
                                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<BoardResponse> boards = boardService.findAllBoards(userDetails.getUser())
                .stream()
                .map(BoardResponse::new)
                .toList();

        return ResponseUtils.of(HttpStatus.OK, "보드 조회에 성공했습니다.", boards);
    }

    @PutMapping("/boards/{id}")
    public ResponseEntity<DataResponseDto<Board>> updateBoard(@PathVariable Long id, @RequestBody UpdateBoardRequest request,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Board updateBoard = boardService.update(id, request, userDetails.getUser());

        return ResponseUtils.of(HttpStatus.OK, "보드 수정에 성공했습니다.", updateBoard);
    }

    @PostMapping("/boards/{boardId}/invitation")
    public ResponseEntity<MessageResponseDto> inviteUser (@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                          @RequestBody InviteBoardRequestDto requestDto, @PathVariable Long boardId) {

        boardService.inviteUser(requestDto, boardId, userDetails.getUser());

        return ResponseUtils.of(HttpStatus.OK, "보드에 초대 했습니다.");
    }
}
