package net.eaglegames.musee.game;

import net.eaglegames.musee.entity.*;
import net.eaglegames.musee.util.RandomUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Deck deck;
    private Map<Gallery.Type, Boolean> claimedBonuses = new HashMap<>();

    public void start() {
        deck = new Deck();
        deck.shuffle();

        player1 = new Player(1);
        player1.setHand(deck.draw(5));

        player2 = new Player(2);
        player2.setHand(deck.draw(5));

        setupRandomStaircases(player1, player2);

        claimedBonuses.put(Gallery.Type.UPPER, false);
        claimedBonuses.put(Gallery.Type.MIDDLE, false);
        claimedBonuses.put(Gallery.Type.LOWER, false);

        setCurrentPlayer(player1);
    }

    public boolean playTurn(final Space space, final Painting painting) {
        // can't place a painting on a space that already has a painting
        if (space.hasPainting()) {
            return false;
        }

        // paintings must be placed sequentially left to right according to their value in a gallery
        long biggerPaintings = space.getGallery().getSpaces().stream()
                // get all spaces to the left of the selected space that have a painting
                .filter(s -> s.getPosition() < space.getPosition())
                .filter(Space::hasPainting)
                .collect(Collectors.toList()).stream()
                // return any paintings that have a higher value then the selected painting
                .filter(s -> s.getPainting().getValue() > painting.getValue())
                .count();

        if (biggerPaintings > 0) {
            return false;
        }

        currentPlayer.getHand().remove(painting);
        space.setPainting(painting);
        currentPlayer.getHand().add(deck.draw());

        currentPlayer = currentPlayer == player1 ? player2 : player1;

        updateScores();

        return true;
    }

    public Player getOpponent() {
        return currentPlayer == player1 ? player2 : player1;
    }

    public void updateScores() {
        Musee.score(this, player1.getMusee());
        Musee.score(this, player2.getMusee());
    }

    /**
     * Setup staircases from 2 arrays of booleans.  True is a staircase.
     *
     * @param player1 first player
     * @param player2 second player
     * @param top     the staircase between upper and middle galleries
     * @param bottom  the staircase between the middle and lower galleries
     */
    public static void setupStaircases(Player player1, Player player2,
                                       List<Boolean> top, List<Boolean> bottom) {
        if (top.size() != Gallery.GALLERY_SIZE || bottom.size() != Gallery.GALLERY_SIZE) {
            throw new IllegalArgumentException("Staircases must contain 6 booleans");
        }

        IntStream.range(0, Gallery.GALLERY_SIZE).forEach(i -> {
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
     *
     * @param players must be 2 Players
     */
    public static void setupRandomStaircases(Player... players) {
        if (players.length != 2) {
            throw new IllegalArgumentException("There must be exactly 2 players");
        }

        final List<Integer> upperStaircases = RandomUtil.getRandomInts(Gallery.GALLERY_SIZE, 3);
        final List<Integer> lowerStaircases = RandomUtil.getRandomInts(Gallery.GALLERY_SIZE, 3);

        upperStaircases.stream().forEach(
                space -> Arrays.stream(players).forEach(
                        player -> player.getMusee().getUpper().getSpaces().get(space).setLowerStaircase(true)));

        lowerStaircases.stream().forEach(
                space -> Arrays.stream(players).forEach(
                        player -> player.getMusee().getMiddle().getSpaces().get(space).setLowerStaircase(true)));
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
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

    public Map<Gallery.Type, Boolean> getClaimedBonuses() {
        return claimedBonuses;
    }
}
