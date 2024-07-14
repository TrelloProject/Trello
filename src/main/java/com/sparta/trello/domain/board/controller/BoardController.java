package com.sparta.trello.domain.board.controller;

import com.sparta.trello.UpdateBoardRequest;
import com.sparta.trello.auth.UserDetailsImpl;
import com.sparta.trello.common.response.DataResponseDto;
import com.sparta.trello.common.response.MessageResponseDto;
import com.sparta.trello.common.response.ResponseUtils;
import com.sparta.trello.domain.board.dto.BoardResponseDto;
import com.sparta.trello.domain.board.dto.CreateBoardRequest;
import com.sparta.trello.domain.board.dto.InviteBoardRequestDto;
import com.sparta.trello.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<DataResponseDto<BoardResponseDto>> createBoard(@RequestBody CreateBoardRequest request,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BoardResponseDto responseDto = boardService.createBoard(request, userDetails.getUser());
        return ResponseUtils.of(HttpStatus.OK, "보드 생성에 성공 했습니다.", responseDto);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<MessageResponseDto> deleteBoard(@PathVariable Long boardId,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.deleteBoard(boardId, userDetails.getUser());
        return ResponseUtils.of(HttpStatus.OK, "보드 삭제에 성공했습니다.");
    }

    @GetMapping
    public ResponseEntity<DataResponseDto<List<BoardResponseDto>>> getAllBoards(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<BoardResponseDto> boards = boardService.getAllBoards(userDetails.getUser());
        return ResponseUtils.of(HttpStatus.OK, "보드 조회에 성공했습니다.", boards);
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<DataResponseDto<BoardResponseDto>> updateBoard(@PathVariable Long boardId, @RequestBody UpdateBoardRequest request,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {

        BoardResponseDto updateBoard = boardService.updateBoard(boardId, request, userDetails.getUser());
        return ResponseUtils.of(HttpStatus.OK, "보드 수정에 성공했습니다.", updateBoard);
    }

    @PostMapping("/{boardId}/invitation")
    public ResponseEntity<MessageResponseDto> inviteUser (@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                          @RequestBody InviteBoardRequestDto requestDto, @PathVariable Long boardId) {

        boardService.inviteUser(requestDto, boardId, userDetails.getUser());
        return ResponseUtils.of(HttpStatus.OK, "보드에 초대 했습니다.");
    }
}