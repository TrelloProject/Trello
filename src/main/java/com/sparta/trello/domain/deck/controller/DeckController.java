package com.sparta.trello.domain.deck.controller;

import com.sparta.trello.auth.UserDetailsImpl;
import com.sparta.trello.common.response.MessageResponseDto;
import com.sparta.trello.common.response.ResponseUtils;
import com.sparta.trello.domain.deck.dto.MoveDeckRequestDto;
import com.sparta.trello.domain.deck.dto.DeckRequestDto;
import com.sparta.trello.domain.deck.service.DeckService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/decks")
@Slf4j
public class DeckController {

    private final DeckService deckService;

    /**
     * deck 생성
     * @param user 로그인 유저
     * @param requestDto 생성할 deck 정보
     * @return 생성 성공 메세지 전송
     */
    @PostMapping("/{boardId}")
    public ResponseEntity<MessageResponseDto> createDeck(
            @AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable Long boardId,
            @Valid @RequestBody DeckRequestDto requestDto){

        deckService.createDeck(user.getUser(), boardId, requestDto);
        return ResponseUtils.createDeckOk();
    }

    /**
     * deck 수정
     * @param user 로그인 유저
     * @param requestDto 수정할 deck 정보
     * @param deckId 수정할 deck id
     * @return 수정 성공 메세지 전송
     */
    @PutMapping("/{deckId}")
    public ResponseEntity<MessageResponseDto> updateDeck(
            @PathVariable Long deckId,
            @AuthenticationPrincipal UserDetailsImpl user,
            @Valid @RequestBody DeckRequestDto requestDto){
        deckService.updateDeck(user.getUser(), deckId, requestDto);
        return ResponseUtils.updateDeckOk();
    }

    /**
     * deck 삭제
     * @param user 로그인 유저
     * @param deckId 삭제할 deck id
     * @return 삭제 성공 메세지 전송
     */
    @DeleteMapping("/{deckId}")
    public ResponseEntity<MessageResponseDto> deleteDeck(
            @PathVariable Long deckId,
            @AuthenticationPrincipal UserDetailsImpl user
           ){

        deckService.deleteDeck(user.getUser(), deckId);
        return ResponseUtils.deleteOk();
    }


    /**
     * deck 이동
     * @param user 로그인 유저
     * @param deckId 이동 deck id
     * @return 이동 성공 메세지 전송
     */
    @PatchMapping("/{deckId}")
    public ResponseEntity<MessageResponseDto> moveDeck(
            @PathVariable Long deckId,
            @RequestBody MoveDeckRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl user
    ){
        deckService.moveDeck(user.getUser(), deckId, requestDto.getPosition());
        return ResponseUtils.moveOk();
    }

}
