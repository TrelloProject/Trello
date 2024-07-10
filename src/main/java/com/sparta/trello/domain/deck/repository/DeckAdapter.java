package com.sparta.trello.domain.deck.repository;

import com.sparta.trello.domain.deck.Entity.Deck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeckAdapter {
    private DeckRepository deckRepository;

    public Deck findById(Long deckId){
        return deckRepository.findById(deckId).orElseThrow(() ->
                new IllegalArgumentException("임시"));
    }

    public List<Deck> findByAll(){
        return deckRepository.findAll();
    }

    public Deck save(Deck deck){
        return deckRepository.save(deck);
    }

    public void delete(Deck deck){
        deckRepository.delete(deck);
    }

}
