package net.eaglegames.musee.game;

import net.eaglegames.musee.entity.Deck;
import net.eaglegames.musee.entity.Player;
import net.eaglegames.musee.graphics.GraphicsRenderer;
import net.eaglegames.musee.util.RandomUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Game {
    private GraphicsRenderer graphicsRenderer;

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Deck deck;

    private boolean upperBonusClaimed = false;

    public Game(GraphicsRenderer graphicsRenderer) {
        this.graphicsRenderer = graphicsRenderer;
        setup();
    }

    public void setup() {
        player1 = new Player();
        player2 = new Player();

        deck = new Deck();
        deck.shuffle();

        player1.setHand(deck.draw(5));
        player2.setHand(deck.draw(5));

        setupRandomStaircases(player1, player2);

        setCurrentPlayer(player1);
    }

    /**
     * Setup staircases from 2 arrays of booleans.  True is a staircase.
     *
     * @param player1 first player
     * @param player2 second player
     * @param top the staircase between upper and middle galleries
     * @param bottom the staircase between the middle and lower galleries
     */
    private static void setupStaircases(Player player1, Player player2,
                                        List<Boolean> top, List<Boolean> bottom) {
        IntStream.range(0, 6).forEach(i -> {
            if (top.get(i)) {
                player1.getMusee().getUpper().getSpaces().get(i).setLowerStaircase(true);
                player2.getMusee().getUpper().getSpaces().get(i).setLowerStaircase(true);
            }
            if (bottom.get(i)) {
                player1.getMusee().getMiddle().getSpaces().get(i).setLowerStaircase(true);
                player2.getMusee().getMiddle().getSpaces().get(i).setLowerStaircase(true);
            }
        });
    }

    /**
     * Setup staircases randomly
     * @param player1 first player
     * @param player2 second player
     */
    private static void setupRandomStaircases(Player player1, Player player2) {
        final List<Integer> upperStaircases = RandomUtil.getRandomInts(6, 3);
        final List<Integer> lowerStaircases = RandomUtil.getRandomInts(6, 3);

        upperStaircases.stream().forEach(s -> {
            player1.getMusee().getUpper().getSpaces().get(s).setLowerStaircase(true);
            player2.getMusee().getUpper().getSpaces().get(s).setLowerStaircase(true);
        });

        lowerStaircases.stream().forEach(s -> {
            player1.getMusee().getMiddle().getSpaces().get(s).setLowerStaircase(true);
            player2.getMusee().getMiddle().getSpaces().get(s).setLowerStaircase(true);
        });
    }

    public void draw() {
        graphicsRenderer.render(this);
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public GraphicsRenderer getGraphicsRenderer() {
        return graphicsRenderer;
    }

    public void setGraphicsRenderer(GraphicsRenderer graphicsRenderer) {
        this.graphicsRenderer = graphicsRenderer;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
