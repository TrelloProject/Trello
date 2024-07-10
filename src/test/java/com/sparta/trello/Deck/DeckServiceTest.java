package com.sparta.trello.Deck;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.sparta.trello.domain.board.entity.Board;
import com.sparta.trello.domain.board.repository.BoardRepository;
import com.sparta.trello.domain.deck.Entity.Deck;
import com.sparta.trello.domain.deck.repository.DeckAdapter;
import com.sparta.trello.domain.deck.service.DeckService;
import com.sparta.trello.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DeckServiceTest {

    @InjectMocks
    private DeckService deckService;

    @Mock
    private DeckAdapter deckAdapter;

    @Mock
    private BoardRepository boardRepository;

    private User mockUser;
    private Board mockBoard;
    private Deck mockDeck1;
    private Deck mockDeck2;
    private Deck mockDeck3;

    @BeforeEach
    public void setUp(){
        mockUser = new User();
        mockBoard = new Board();
        mockBoard.setId(1L);
        mockBoard.setHeadDeckId(1L);

        mockDeck1 = new Deck();
        mockDeck1.setId(1L);
        mockDeck1.setBoard(mockBoard);
        mockDeck1.setNextId(2L);

        mockDeck2 = new Deck();
        mockDeck2.setId(2L);
        mockDeck2.setBoard(mockBoard);
        mockDeck2.setNextId(3L);

        mockDeck3 = new Deck();
        mockDeck3.setId(3L);
        mockDeck3.setBoard(mockBoard);
        mockDeck3.setNextId(0L);

        List<Deck> mockDeckList = new ArrayList<>();
        mockDeckList.add(mockDeck1);
        mockDeckList.add(mockDeck2);
        mockDeckList.add(mockDeck3);

        when(deckAdapter.findById(1L)).thenReturn(mockDeck1);
        when(deckAdapter.findById(2L)).thenReturn(mockDeck2);
        when(deckAdapter.findById(3L)).thenReturn(mockDeck3);
        when(deckAdapter.findByBoard(mockBoard)).thenReturn(mockDeckList);
        when(deckAdapter.findByNextId(0L)).thenReturn(mockDeck3);
        when(boardRepository.findById(1L)).thenReturn(Optional.ofNullable(mockBoard));
    }

    @Test
    public void testMoveDeckToFirstPosition() {
        // when
        deckService.moveDeck(mockUser, 2L, 0L);
        // Assertions
        //assertEquals(mockDeck2.getNextId(), mockBoard.getHeadDeckId());
        assertEquals(mockBoard.getHeadDeckId(), mockDeck2.getId());
    }

}