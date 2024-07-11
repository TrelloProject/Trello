package com.sparta.trello.domain.card.adapter;

import com.sparta.trello.domain.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class CardAdapter {

    private final CardRepository cardRepository;
}