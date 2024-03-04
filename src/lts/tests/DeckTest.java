package lts.tests;

import lts.Cards.Card;
import lts.CommunityCards.Deck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void drawTest() {
        Deck deck = new Deck();
        assertEquals(14 + 11 + 4 + 25, deck.size());
        assertSame(deck.Draw().getClass(), Card.class);
        assertEquals(14 + 11 + 4 + 25 - 1, deck.size());
    }
}