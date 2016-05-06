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

    /**
     * Initialize the game.  Create and shuffle the deck; create the players and draw 5 from the deck; set claimed
     * bonuses to false; set currentPlayer to player1
     */
    public Game() {
        deck = new Deck();
        deck.shuffle();

        player1 = new Player(1);
        player1.setHand(deck.draw(5));

        player2 = new Player(2);
        player2.setHand(deck.draw(5));

        claimedBonuses.put(Gallery.Type.UPPER, false);
        claimedBonuses.put(Gallery.Type.MIDDLE, false);
        claimedBonuses.put(Gallery.Type.LOWER, false);

        setCurrentPlayer(player1);
    }

    public boolean playTurn(final Space space, final Painting painting) {
        if (!space.getGallery().validSpaceForPainting(space, painting)) {
            return false;
        }

        // space is valid, place the painting in the space
        currentPlayer.getHand().remove(painting);
        space.setPainting(painting);

        // draw a new card
        // TODO: the deck could be empty?
        currentPlayer.getHand().add(deck.draw());

        Musee.score(this, currentPlayer.getMusee());

        if (!player1.hasValidMoves() && !player2.hasValidMoves()) {
            System.out.println("Game over!");
        }

        // switch player if the next player has moves left
        if (getOpponent().hasValidMoves()) {
            currentPlayer = currentPlayer == player1 ? player2 : player1;
        }

        return true;
    }

    public Player getOpponent() {
        return currentPlayer == player1 ? player2 : player1;
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

        if (top.stream().filter(s -> s).count() + bottom.stream().filter(s -> s).count() != 6) {
            throw new IllegalArgumentException("Must have exactly 6 staircases set");
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

        final List<Integer> staircases = RandomUtil.getRandomInts(Gallery.GALLERY_SIZE * 2, 6);

        List<Integer> upperStaircases = staircases.subList(0, Gallery.GALLERY_SIZE - 1);
        List<Integer> lowerStaircases = staircases.subList(Gallery.GALLERY_SIZE, 11);

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
