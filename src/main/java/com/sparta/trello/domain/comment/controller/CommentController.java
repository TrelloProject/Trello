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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<DataResponseDto<CommentResponseDto>> createComment(
        @Valid @RequestBody CreateCommentRequestDto createCommentRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseUtils.createOk(
            commentService.createComment(createCommentRequestDto, userDetails.getUser()));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<DataResponseDto<CommentResponseDto>> updateComment(
        @PathVariable Long commentId,
        @Valid @RequestBody UpdateCommentRequestDto updateCommentRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseUtils.updateOk(
            commentService.updateComment(commentId, updateCommentRequestDto,
                userDetails.getUser()));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponseDto> deleteComment(@PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        commentService.deleteComment(commentId, userDetails.getUser());
        return ResponseUtils.deleteOk();
    }
}