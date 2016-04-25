package net.eaglegames.musee.game;

import net.eaglegames.musee.entity.Painting;
import net.eaglegames.musee.entity.Player;
import net.eaglegames.musee.entity.Space;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class GameTest {
    @Test
    public void playersAreSetupCorrectly() {
        Game game = new Game();
        game.start();

        assertNotNull(game.getPlayer1());
        assertEquals(5, game.getPlayer1().getHand().size());

        assertNotNull(game.getPlayer2());
        assertEquals(5, game.getPlayer2().getHand().size());

        assertEquals(game.getPlayer1(), game.getCurrentPlayer());
        assertEquals(game.getPlayer2(), game.getOpponent());
    }

    @Test
    public void staircasesAreSetup() {
        Player player1 = new Player(1);
        Player player2 = new Player(2);

        List<Boolean> top = Arrays.asList(true, false, false, false, false, true);
        List<Boolean> bottom = Arrays.asList(false, true, true, true, true, false);

        Game.setupStaircases(player1, player2, top, bottom);

        assertTrue(player1.getMusee().getUpper().getSpaces().get(0).isLowerStaircase());
        assertTrue(player2.getMusee().getUpper().getSpaces().get(0).isLowerStaircase());

        assertTrue(player1.getMusee().getUpper().getSpaces().get(5).isLowerStaircase());
        assertTrue(player2.getMusee().getUpper().getSpaces().get(5).isLowerStaircase());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setupStaircasesHasRightAmountOfBooleans() {
        Player player1 = new Player(1);
        Player player2 = new Player(2);

        List<Boolean> top = Arrays.asList(true, false, false, false, false, true);
        List<Boolean> bottom = Arrays.asList(false, true, true, true, true);

        Game.setupStaircases(player1, player2, top, bottom);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setupRandomStaircasesForOnlyTwoPlayers() {
        Game.setupRandomStaircases(new Player(1), new Player(2), new Player(3));
    }

    @Test
    public void validMove() {
        Game game = new Game();
        game.start();

        assertEquals(50, game.getDeck().getPaintings().size());

        Painting painting = game.getCurrentPlayer().getHand().get(0);
        Space space = game.getCurrentPlayer().getMusee().getUpper().getSpaces().get(0);

        assertTrue(space.getPainting() == null);
        boolean success = game.playTurn(space, painting);
        assertEquals(49, game.getDeck().getPaintings().size());
        assertTrue(success);
        assertEquals(game.getPlayer2(), game.getCurrentPlayer());

        success = game.playTurn(space, painting);
        assertFalse("Invalid move should return false", success);

        // Card should still be in the deck and current player remains the same
        assertEquals(49, game.getDeck().getPaintings().size());
        assertEquals(game.getPlayer2(), game.getCurrentPlayer());
    }
}
