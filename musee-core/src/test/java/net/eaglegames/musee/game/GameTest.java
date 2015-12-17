package net.eaglegames.musee.game;

import net.eaglegames.musee.entity.Painting;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GameTest {
    @Test
    public void gameIsSetupCorrectly() {
        Game game = new Game(null);

        assertNotNull(game.getPlayer1());
        assertEquals(5, game.getPlayer1().getHand().size());

        assertNotNull(game.getPlayer2());
        assertEquals(5, game.getPlayer2().getHand().size());
    }

    @Test
    public void gameIsScoredCorrectly() {
        Game game = new Game(null);

        game.getPlayer1().getMusee().getUpper().getSpaces().get(0).setPainting(new Painting(Painting.Theme.PERSONS, 8));
        game.getPlayer1().getMusee().getUpper().getSpaces().get(1).setPainting(new Painting(Painting.Theme.PERSONS, 8));
        game.getPlayer1().getMusee().getUpper().getSpaces().get(2).setPainting(new Painting(Painting.Theme.PERSONS, 11));
        game.getPlayer1().getMusee().getUpper().getSpaces().get(3).setPainting(new Painting(Painting.Theme.PERSONS, 23));
        game.getPlayer1().getMusee().getUpper().getSpaces().get(4).setPainting(new Painting(Painting.Theme.ANIMAL, 28));
        game.getPlayer1().getMusee().getUpper().getSpaces().get(5).setPainting(new Painting(Painting.Theme.PERSONS, 38));

        game.getPlayer1().getMusee().getMiddle().getSpaces().get(0).setPainting(new Painting(Painting.Theme.ANIMAL, 4));
        game.getPlayer1().getMusee().getMiddle().getSpaces().get(1).setPainting(new Painting(Painting.Theme.ARCHITECTURE, 10));
        game.getPlayer1().getMusee().getMiddle().getSpaces().get(2).setPainting(new Painting(Painting.Theme.LANDSCAPE, 25));
        game.getPlayer1().getMusee().getMiddle().getSpaces().get(3).setPainting(new Painting(Painting.Theme.LANDSCAPE, 35));
        game.getPlayer1().getMusee().getMiddle().getSpaces().get(4).setPainting(new Painting(Painting.Theme.LANDSCAPE, 45));
        game.getPlayer1().getMusee().getMiddle().getSpaces().get(5).setPainting(new Painting(Painting.Theme.ANIMAL, 48));

        game.getPlayer1().getMusee().getLower().getSpaces().get(1).setPainting(new Painting(Painting.Theme.WATER, 19));
        game.getPlayer1().getMusee().getLower().getSpaces().get(2).setPainting(new Painting(Painting.Theme.WATER, 31));
        game.getPlayer1().getMusee().getLower().getSpaces().get(3).setPainting(new Painting(Painting.Theme.LANDSCAPE, 36));
        game.getPlayer1().getMusee().getLower().getSpaces().get(4).setPainting(new Painting(Painting.Theme.LANDSCAPE, 44));
        game.getPlayer1().getMusee().getLower().getSpaces().get(5).setPainting(new Painting(Painting.Theme.ANIMAL, 45));
    }
}
