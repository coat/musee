package net.eaglegames.musee.entity;

public class Space {
    private int position;
    private boolean lowerStaircase;
    private Painting painting;

    public Space(int position) {
        this.position = position;
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

    @Override
    public String toString() {
        return painting == null ? "|     |" : "|" + painting + "|";
    }
}
