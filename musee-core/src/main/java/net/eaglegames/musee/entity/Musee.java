package net.eaglegames.musee.entity;

import net.eaglegames.musee.game.Game;

import java.util.ArrayList;
import java.util.List;

public class Musee {
    private Gallery upper;
    private Gallery middle;
    private Gallery lower;
    private int score = 0;

    public Musee() {
        upper = new Gallery(Gallery.Type.UPPER);
        middle = new Gallery(Gallery.Type.MIDDLE);
        lower = new Gallery(Gallery.Type.LOWER);
    }

    public Gallery getUpper() {
        return upper;
    }

    public void setUpper(Gallery upper) {
        this.upper = upper;
    }

    public Gallery getMiddle() {
        return middle;
    }

    public void setMiddle(Gallery middle) {
        this.middle = middle;
    }

    public Gallery getLower() {
        return lower;
    }

    public void setLower(Gallery lower) {
        this.lower = lower;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public static void score(final Game game) {
        score(game, game.getCurrentPlayer().getMusee());
    }

    public static void score(final Game game, final Musee musee) {
        int score = 0;

        score += Gallery.score(game, musee.getUpper());
        score += Gallery.score(game, musee.getMiddle(), musee.getUpper());
        score += Gallery.score(game, musee.getLower(), musee.getMiddle());

        musee.setScore(score);
    }

    public List<Space> validSpacesForPainting(final Painting painting) {
        List<Space> validSpaces = new ArrayList<>(upper.validSpacesForPainting(painting));
        validSpaces.addAll(middle.validSpacesForPainting(painting));
        validSpaces.addAll(lower.validSpacesForPainting(painting));

        return validSpaces;
    }

    @Override
    public String toString() {
        return "_______________________________________________\n" +
                upper + "\n" +
                middle + "\n" +
                lower +
                "_______________________________________________\n";
    }

    public Gallery getGallery(Gallery.Type galleryType) {
        switch (galleryType) {
            case UPPER:
                return getUpper();
            case MIDDLE:
                return getMiddle();
            case LOWER:
                return getLower();
        }
        return null;
    }
}
