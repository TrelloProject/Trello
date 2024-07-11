package com.sparta.trello.domain.card.service;

import com.sparta.trello.domain.card.adapter.CardAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class CardService {

    private final CardAdapter cardAdapter;
}