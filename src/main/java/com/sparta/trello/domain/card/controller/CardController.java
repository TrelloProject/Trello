package com.sparta.trello.domain.card.controller;

import com.sparta.trello.domain.card.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/cards")
@RestController
public class CardController {

    private final CardService cardService;
}