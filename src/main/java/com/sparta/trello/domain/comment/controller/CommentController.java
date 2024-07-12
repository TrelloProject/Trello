package com.sparta.trello.domain.comment.controller;

import com.sparta.trello.auth.UserDetailsImpl;
import com.sparta.trello.common.response.MessageResponseDto;
import com.sparta.trello.common.response.ResponseUtils;
import com.sparta.trello.domain.comment.dto.CreateCommentRequestDto;
import com.sparta.trello.domain.comment.dto.UpdateCommentRequestDto;
import com.sparta.trello.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<MessageResponseDto> createComment(
        @Valid @RequestBody CreateCommentRequestDto createCommentRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        commentService.createComment(createCommentRequestDto, userDetails.getUser());
        return ResponseUtils.createOk();
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<MessageResponseDto> updateComment(
        @PathVariable Long commentId,
        @Valid @RequestBody UpdateCommentRequestDto updateCommentRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        commentService.updateComment(commentId, updateCommentRequestDto, userDetails.getUser());
        return ResponseUtils.updateOk();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponseDto> deleteComment(@PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        commentService.deleteComment(commentId, userDetails.getUser());
        return ResponseUtils.deleteOk();
    }
}