package net.eaglegames.musee.entity;

import net.eaglegames.musee.game.Game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents a player's gallery
 */
public class Gallery {
    public static final int GALLERY_SIZE = 6;

    private Type type;
    private List<Space> spaces = new ArrayList<>(GALLERY_SIZE);
    private boolean bonus = false;

    public Gallery(Type type) {
        this.type = type;
        // Generate empty spaces
        IntStream.range(0, GALLERY_SIZE).forEach(i -> spaces.add(new Space(i, this)));
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Space> getSpaces() {
        return spaces;
    }

    public void setSpaces(List<Space> spaces) {
        this.spaces = spaces;
    }

    public boolean isBonus() {
        return bonus;
    }

    public void setBonus(boolean bonus) {
        this.bonus = bonus;
    }

    /**
     * Used to score the upper gallery as there will be no gallery above it to score staircase connected themes
     *
     * @param gallery should be Upper gallery
     * @return the score for the upper gallery
     */
    public static int score(Game game, Gallery gallery) {
        return score(game, gallery, null);
    }

    /**
     * Scores the middle and lower galleries which have galleries above them to score staircase connected theme bonuses
     *
     * @param gallery the gallery to score
     * @param above   the gallery above to reference to check if same theme matches above space in gallery
     * @return score for the gallery
     */
    public static int score(Game game, Gallery gallery, Gallery above) {
        int score = 0;
        for (Space space : gallery.getSpaces()) {
            if (space.hasPainting()) {
                score += 1;

                // Gallery theme bonuses
                if (space.getPosition() > 0) {
                    Painting previous = gallery.getSpaces().get(space.getPosition() - 1).getPainting();
                    if (previous != null && previous.getTheme().equals(space.getPainting().getTheme())) {
                        score += 2;
                    }
                }

                // Connected gallery theme bonuses
                if (above != null) {
                    Space spaceAbove = above.getSpaces().get(space.getPosition());
                    if (spaceAbove.isLowerStaircase()) {
                        Painting paintingAbove = above.getSpaces().get(space.getPosition()).getPainting();
                        if (paintingAbove != null && paintingAbove.getTheme().equals(space.getPainting().getTheme())) {
                            score += 3;
                        }
                    }
                }
            }
        }

        if (!game.getClaimedBonuses().get(gallery.getType())) {
            if (gallery.getSpaces().stream().filter(Space::hasPainting).count() == 6) {
                game.getClaimedBonuses().put(gallery.getType(), true);
                gallery.setBonus(true);
            }
        }

        // Gallery bonus
        if (gallery.isBonus()) {
            score += 4;
        }

        return score;
    }

    public boolean validSpaceForPainting(final Space space, final Painting painting) {
        // can't place a painting on a space that already has a painting
        if (space.hasPainting()) {
            return false;
        }

        // make sure the paintings to the left of the requested space have a smaller value...
        long left = spaces.stream()
                .filter(Space::hasPainting)
                .filter(s -> s.getPosition() < space.getPosition())
                .filter(s -> s.getPainting().getValue() > painting.getValue())
                .count();

        // ...and painting to the right have a larger value
        long right = spaces.stream()
                .filter(Space::hasPainting)
                .filter(s -> s.getPosition() > space.getPosition())
                .filter(s -> s.getPainting().getValue() < painting.getValue())
                .count();

        return left + right == 0;
    }

    public List<Space> validSpacesForPainting(final Painting painting) {
        return spaces.stream()
                .filter(s -> validSpaceForPainting(s, painting))
                .collect(Collectors.toList());
    }

    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append(spaces.stream().map(Space::toString).collect(Collectors.joining(" ")))
                .append(bonus ? " GALLERY\n" : "\n")
                .append("|     | |     | |     | |     | |     | |     |")
                .append(bonus ? "  BONUS\n" : "\n")
                .append("|     | |     | |     | |     | |     | |     |\n");

        spaces.stream().forEach(space -> {
            if (type.equals(Type.UPPER)) {
                if (space.isLowerStaircase())
                    out.append(">     < ");
                else
                    out.append("======= ");
            }
            if (type.equals(Type.MIDDLE)) {
                if (space.isLowerStaircase())
                    out.append(">     < ");
                else
                    out.append("======= ");
            }
        });

        return out.toString();
    }

    public enum Type {
        UPPER, MIDDLE, LOWER
    }
}
