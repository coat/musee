package net.eaglegames.musee.game;

import net.eaglegames.musee.entity.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class GameTest {
    @Test
    public void playersAreSetupCorrectly() {
        Game game = new Game();

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
    public void randomStaircasesAreSetup() {
        Player player1 = new Player(1);
        Player player2 = new Player(2);

        Game.setupRandomStaircases(player1, player2);
    }

    @Test
    public void validMove() {
        Game game = new Game();

        assertEquals(50, game.getDeck().getPaintings().size());

        Painting painting = game.getCurrentPlayer().getHand().get(0);
        Space space = game.getCurrentPlayer().getMusee().getUpper().getSpaces().get(0);

        assertTrue(space.getPainting() == null);
        boolean success = game.playTurn(space, painting);
        assertTrue(success);
        assertEquals(49, game.getDeck().getPaintings().size());

        success = game.playTurn(space, painting);
        assertFalse("Invalid move should return false", success);

        // Card should still be in the deck and current player remains the same
        assertEquals(49, game.getDeck().getPaintings().size());
        assertEquals(game.getPlayer2(), game.getCurrentPlayer());
    }

    @Test
    public void gameplay() {
        Game game = new Game();

        // reproducible deck order
        game.setDeck(new Deck());
        game.getPlayer1().setHand(game.getDeck().draw(5));
        game.getPlayer2().setHand(game.getDeck().draw(5));

        Game.setupStaircases(game.getPlayer1(), game.getPlayer2(),
                Arrays.asList(false, true, false, true, false, false),
                Arrays.asList(false, true, true, false, true, true));

        assertEquals(1, game.getCurrentPlayer().getHand().get(0).getValue().intValue());
        assertEquals(11, game.getDeck().getPaintings().get(0).getValue().intValue());

        assertEquals(50, game.getDeck().getPaintings().size());
        assertEquals(5, game.getCurrentPlayer().getHand().size());

        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getUpper().getSpaces().get(0), game.getCurrentPlayer().getHand().get(0)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getUpper().getSpaces().get(0), game.getCurrentPlayer().getHand().get(0)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getUpper().getSpaces().get(1), game.getCurrentPlayer().getHand().get(0)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getUpper().getSpaces().get(1), game.getCurrentPlayer().getHand().get(0)));

        assertEquals(2, game.getCurrentPlayer().getMusee().getScore());
        assertEquals(2, game.getOpponent().getMusee().getScore());

        assertEquals(46, game.getDeck().getPaintings().size());

        assertEquals(5, game.getCurrentPlayer().getHand().size());
        assertEquals(5, game.getOpponent().getHand().size());

        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getUpper().getSpaces().get(2), game.getCurrentPlayer().getHand().get(0)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getUpper().getSpaces().get(2), game.getCurrentPlayer().getHand().get(0)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getUpper().getSpaces().get(3), game.getCurrentPlayer().getHand().get(0)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getUpper().getSpaces().get(3), game.getCurrentPlayer().getHand().get(0)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getUpper().getSpaces().get(4), game.getCurrentPlayer().getHand().get(0)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getUpper().getSpaces().get(4), game.getCurrentPlayer().getHand().get(0)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getUpper().getSpaces().get(5), game.getCurrentPlayer().getHand().get(0)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getUpper().getSpaces().get(5), game.getCurrentPlayer().getHand().get(0)));

        // upper gallery bonus should be claimed by player 1
        assertEquals(10, game.getPlayer1().getMusee().getScore());
        assertEquals(6, game.getPlayer2().getMusee().getScore());
        assertTrue(game.getClaimedBonuses().get(Gallery.Type.UPPER));

        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getMiddle().getSpaces().get(0), game.getCurrentPlayer().getHand().get(0)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getMiddle().getSpaces().get(0), game.getCurrentPlayer().getHand().get(0)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getMiddle().getSpaces().get(1), game.getCurrentPlayer().getHand().get(0)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getMiddle().getSpaces().get(1), game.getCurrentPlayer().getHand().get(0)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getMiddle().getSpaces().get(2), game.getCurrentPlayer().getHand().get(0)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getMiddle().getSpaces().get(2), game.getCurrentPlayer().getHand().get(0)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getMiddle().getSpaces().get(3), game.getCurrentPlayer().getHand().get(0)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getMiddle().getSpaces().get(3), game.getCurrentPlayer().getHand().get(0)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getMiddle().getSpaces().get(4), game.getCurrentPlayer().getHand().get(0)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getMiddle().getSpaces().get(4), game.getCurrentPlayer().getHand().get(0)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getMiddle().getSpaces().get(5), game.getCurrentPlayer().getHand().get(0)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getMiddle().getSpaces().get(5), game.getCurrentPlayer().getHand().get(0)));

        assertEquals(30, game.getPlayer1().validMoves().size());
        assertEquals(30, game.getPlayer2().validMoves().size());

        assertTrue(game.getClaimedBonuses().get(Gallery.Type.MIDDLE));
        assertEquals(23, game.getPlayer1().getMusee().getScore());
        assertEquals(12, game.getPlayer2().getMusee().getScore());

        assertFalse(game.playTurn(game.getCurrentPlayer().getMusee().getMiddle().getSpaces().get(0), game.getCurrentPlayer().getHand().get(0)));

        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getLower().getSpaces().get(0), game.getCurrentPlayer().getHand().get(2)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getLower().getSpaces().get(0), game.getCurrentPlayer().getHand().get(2)));

        assertFalse(game.playTurn(game.getCurrentPlayer().getMusee().getLower().getSpaces().get(1), game.getCurrentPlayer().getHand().get(0)));

        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getLower().getSpaces().get(2), game.getCurrentPlayer().getHand().get(3)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getLower().getSpaces().get(1), game.getCurrentPlayer().getHand().get(3)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getLower().getSpaces().get(3), game.getCurrentPlayer().getHand().get(3)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getLower().getSpaces().get(2), game.getCurrentPlayer().getHand().get(3)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getLower().getSpaces().get(4), game.getCurrentPlayer().getHand().get(3)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getLower().getSpaces().get(3), game.getCurrentPlayer().getHand().get(4)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getLower().getSpaces().get(5), game.getCurrentPlayer().getHand().get(4)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getLower().getSpaces().get(4), game.getCurrentPlayer().getHand().get(4)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getLower().getSpaces().get(1), game.getCurrentPlayer().getHand().get(2)));
        assertTrue(game.playTurn(game.getCurrentPlayer().getMusee().getLower().getSpaces().get(5), game.getCurrentPlayer().getHand().get(4)));

        printGame(game);
    }

    private void printGame(Game game) {
        System.out.println("Deck size: " + game.getDeck().getPaintings().size());
        System.out.println("Current player: " + game.getCurrentPlayer());

        printPlayer(game.getPlayer1());
        printPlayer(game.getPlayer2());
    }

    private void printPlayer(final Player player) {
        System.out.println(player + "\n  Score: " + player.getMusee().getScore());
        System.out.println("  Hand: " + player.getHand().stream().map(Painting::toString).collect(Collectors.joining(", ")));
        System.out.println("  Valid moves: " + player.validMoves().size());
        System.out.println("  Musee:");
        System.out.println(player.getMusee());
    }
}
