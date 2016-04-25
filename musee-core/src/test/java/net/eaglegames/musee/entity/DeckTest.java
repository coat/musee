package net.eaglegames.musee.entity;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DeckTest {
    @Test
    public void deckShouldBeCreatedCorrectly() {
        Deck deck = new Deck();

        assertEquals(Deck.DECK_SIZE, deck.getPaintings().size());

        assertEquals(Painting.Theme.ANIMAL, deck.getPaintings().get(15 - 1).getTheme());
        assertEquals(15, deck.getPaintings().get(15 - 1).getValue().intValue());
    }

    @Test
    public void cardShouldBeDrawn() {
        Deck deck = new Deck();

        Painting painting = deck.draw();

        assertEquals(59, deck.getPaintings().size());

        assertEquals(new Painting(Painting.Theme.LANDSCAPE, 1), painting);
    }

    @Test
    public void cardsShouldBeDrawn() {
        Deck deck = new Deck();

        List<Painting> paintings = deck.draw(5);

        assertEquals(55, deck.getPaintings().size());

        assertEquals(5, paintings.size());

        assertEquals(new Painting(Painting.Theme.LANDSCAPE, 1), paintings.get(0));
        assertEquals(new Painting(Painting.Theme.WATER, 2), paintings.get(1));
        assertEquals(new Painting(Painting.Theme.PERSONS, 3), paintings.get(2));
        assertEquals(new Painting(Painting.Theme.ARCHITECTURE, 4), paintings.get(3));
        assertEquals(new Painting(Painting.Theme.ANIMAL, 5), paintings.get(4));
    }
}
