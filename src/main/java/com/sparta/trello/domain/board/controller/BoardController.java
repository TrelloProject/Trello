package com.sparta.trello.domain.board.controller;

import com.sparta.trello.auth.UserDetailsImpl;
import com.sparta.trello.common.response.DataResponseDto;
import com.sparta.trello.common.response.MessageResponseDto;
import com.sparta.trello.common.response.ResponseUtils;
import com.sparta.trello.domain.board.dto.BoardDto;
import com.sparta.trello.domain.board.dto.BoardResponseDto;
import com.sparta.trello.domain.board.dto.CreateBoardRequestDto;
import com.sparta.trello.domain.board.dto.InviteBoardRequestDto;
import com.sparta.trello.domain.board.dto.UpdateBoardRequestDto;
import com.sparta.trello.domain.board.service.BoardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{boardId}")
    public String getBoard(@PathVariable Long boardId, Model model) {
        BoardDto board = boardService.getBoard(boardId);
        model.addAttribute("board", board);
        return "detail";
    }

    @PostMapping
    public String createBoard(
        @RequestBody CreateBoardRequestDto request,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        RedirectAttributes redirectAttributes
    ) {
        BoardResponseDto responseDto = boardService.createBoard(request, userDetails.getUser());
        redirectAttributes.addAttribute("boardId", responseDto.getId());
        return "redirect:/boards/{boardId}";
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
    public ResponseEntity<DataResponseDto<BoardResponseDto>> updateBoard(@PathVariable Long boardId,
        @RequestBody UpdateBoardRequestDto request,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        BoardResponseDto updateBoard = boardService.updateBoard(boardId, request,
            userDetails.getUser());
        return ResponseUtils.of(HttpStatus.OK, "보드 수정에 성공했습니다.", updateBoard);
    }

    @PostMapping("/{boardId}/invitation")
    public ResponseEntity<MessageResponseDto> inviteUser(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody InviteBoardRequestDto requestDto,
        @PathVariable Long boardId
    ) {
        boardService.inviteUser(requestDto, boardId, userDetails.getUser());
        return ResponseUtils.of(HttpStatus.OK, "보드에 초대 했습니다.");
    }
}