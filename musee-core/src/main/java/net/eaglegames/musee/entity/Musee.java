package net.eaglegames.musee.entity;

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

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("_______________________________________________\n");
        out.append(upper).append("\n");
        out.append(middle).append("\n");
        out.append(lower);
        out.append("_______________________________________________\n");

        return out.toString();
    }

    public static void score(Musee musee) {
        int score = 0;

        score += Gallery.score(musee.getUpper());
        score += Gallery.score(musee.getMiddle(), musee.getUpper());
        score += Gallery.score(musee.getLower(), musee.getMiddle());

        musee.setScore(score);
    }
}
