package com.sparta.trello.domain.comment.controller;

import com.sparta.trello.auth.UserDetailsImpl;
import com.sparta.trello.common.response.DataResponseDto;
import com.sparta.trello.common.response.MessageResponseDto;
import com.sparta.trello.common.response.ResponseUtils;
import com.sparta.trello.domain.comment.dto.CommentResponseDto;
import com.sparta.trello.domain.comment.dto.CreateCommentRequestDto;
import com.sparta.trello.domain.comment.dto.UpdateCommentRequestDto;
import com.sparta.trello.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<DataResponseDto<CommentResponseDto>> createComment(
        @Valid @RequestBody CreateCommentRequestDto createCommentRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CommentResponseDto responseDto = commentService.createComment(createCommentRequestDto, userDetails.getUser());
        return ResponseUtils.createOk(responseDto);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<DataResponseDto<CommentResponseDto>> updateComment(
        @PathVariable Long commentId,
        @Valid @RequestBody UpdateCommentRequestDto updateCommentRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CommentResponseDto responseDto = commentService.updateComment(commentId, updateCommentRequestDto, userDetails.getUser());
        return ResponseUtils.updateOk(responseDto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponseDto> deleteComment(@PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        commentService.deleteComment(commentId, userDetails.getUser());
        return ResponseUtils.deleteOk();
    }
}