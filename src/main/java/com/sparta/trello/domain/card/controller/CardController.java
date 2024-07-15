package com.sparta.trello.domain.card.controller;

import com.sparta.trello.auth.UserDetailsImpl;
import com.sparta.trello.common.response.DataResponseDto;
import com.sparta.trello.common.response.MessageResponseDto;
import com.sparta.trello.common.response.ResponseUtils;
import com.sparta.trello.domain.card.dto.CardResponseDto;
import com.sparta.trello.domain.card.dto.CreateCardRequestDto;
import com.sparta.trello.domain.card.dto.MoveCardRequestDto;
import com.sparta.trello.domain.card.dto.UpdateCardRequestDto;
import com.sparta.trello.domain.card.entity.Card;
import com.sparta.trello.domain.card.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<MessageResponseDto> createCard(
        @RequestBody @Valid CreateCardRequestDto createCardRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        cardService.createCard(createCardRequestDto, userDetails.getUser());
        return ResponseUtils.createOk();
    }

    @GetMapping("/{cardId}")
    @ResponseBody
    public CardResponseDto getCard(
        @PathVariable Long cardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cardService.getCard(cardId, userDetails.getUser());
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<MessageResponseDto> updateCard(
        @PathVariable Long cardId,
        @RequestBody @Valid UpdateCardRequestDto updateCardRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        cardService.updateCard(cardId, updateCardRequestDto, userDetails.getUser());
        return ResponseUtils.updateOk();
    }

    @PatchMapping("/{cardId}")
    public ResponseEntity<MessageResponseDto> moveCard(
        @PathVariable Long cardId,
        @RequestBody @Valid MoveCardRequestDto moveCardRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        cardService.moveCard(cardId, moveCardRequestDto, userDetails.getUser());
        return ResponseUtils.updateOk();
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<MessageResponseDto> deleteCard(
        @PathVariable Long cardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        cardService.deleteCard(cardId, userDetails.getUser());
        return ResponseUtils.deleteOk();
    }
}