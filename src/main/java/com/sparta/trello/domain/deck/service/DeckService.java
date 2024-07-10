package com.sparta.trello.domain.deck.service;

import com.sparta.trello.domain.deck.Entity.Deck;
import com.sparta.trello.domain.deck.dto.DeckRequestDto;
import com.sparta.trello.domain.deck.repository.DeckAdapter;
import com.sparta.trello.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeckService {

    private DeckAdapter deckAdapter;

    /**
     * deck 생성
     * @param user 로그인 유저
     * @param requestDto 생성할 deck 정보
     */
    @Transactional(readOnly = true)
    public void createDeck(User user, DeckRequestDto requestDto) {
        //유저 검증 필요

        Deck deck = Deck.builder()
                .title(requestDto.getTitle())
                .build();

        deckAdapter.save(deck);
    }

    /**
     * deck 수정
     * @param user 로그인 유저
     * @param deckId 수정할 deck id
     * @param requestDto 수정할 deck 정보
     */
    @Transactional
    public void updateDeck(User user, Long deckId, DeckRequestDto requestDto){
        //유저 검증 필요

        Deck deck = deckAdapter.findById(deckId);

        deck.updateDeck(requestDto.getTitle());
    }

    /**
     * deck 삭제
     * @param user 로그인 유저
     * @param deckId 삭제할 deck id
     */
    @Transactional
    public void deleteDeck(User user, Long deckId){
        //유저 검증 필요

        Deck deck = deckAdapter.findById(deckId);

        deckAdapter.delete(deck);
    }
}
