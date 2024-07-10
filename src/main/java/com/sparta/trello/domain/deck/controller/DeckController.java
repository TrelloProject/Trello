package com.sparta.trello.domain.deck.controller;

import com.sparta.trello.common.response.MessageResponseDto;
import com.sparta.trello.common.response.ResponseUtils;
import com.sparta.trello.domain.deck.dto.DeckRequestDto;
import com.sparta.trello.domain.deck.service.DeckService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/decks")
public class DeckController {

    private final DeckService deckService;

    /**
     * deck 생성
//     * @param user 로그인 유저
     * @param requestDto 생성할 deck 정보
     * @return 생성 성공 메세지 전송
     */
    @PostMapping
    public ResponseEntity<MessageResponseDto> createDeck(
//            @AuthenticationPrincipal AuthenticationUser user,
            @Valid @RequestBody DeckRequestDto requestDto){

//        deckService.createDeck(user, requestDto);
        return ResponseUtils.createDeckOk();
    }

    /**
     * deck 생성
//     * @param user 로그인 유저
     * @param requestDto 수정할 deck 정보
     * @param deckId 수정할 deck id
     * @return 수정 성공 메세지 전송
     */
    @PutMapping("/{deckId}")
    public ResponseEntity<MessageResponseDto> updateDeck(
            @PathVariable Long deckId,
//            @AuthenticationPrincipal AuthenticationUser user,
            @Valid @RequestBody DeckRequestDto requestDto){

//        deckService.updateDeck(user, deckId, requestDto);
        return ResponseUtils.updateDeckOk();
    }

    /**
     * deck 삭제
//     * @param user 로그인 유저
     * @param deckId 삭제할 deck id
     * @return 삭제 성공 메세지 전송
     */
    @DeleteMapping("/{deckId}")
    public ResponseEntity<MessageResponseDto> deleteDeck(
            @PathVariable Long deckId
//            @AuthenticationPrincipal AuthenticationUser user
           ){

//        deckService.deleteDeck(user, deckId);
        return ResponseUtils.deleteOk();
    }

}
