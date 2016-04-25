package net.eaglegames.musee.entity;

public class Space {
    private int position;
    private boolean lowerStaircase;
    private Painting painting;
    private Gallery gallery;

    public Space(int position, Gallery gallery) {
        this.position = position;
        this.gallery = gallery;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isLowerStaircase() {
        return lowerStaircase;
    }

    public void setLowerStaircase(boolean lowerStaircase) {
        this.lowerStaircase = lowerStaircase;
    }

    public Painting getPainting() {
        return painting;
    }

    public void setPainting(Painting painting) {
        this.painting = painting;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public boolean hasPainting() {
        return painting != null;
    }
}
