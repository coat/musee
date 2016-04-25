package net.eaglegames.musee.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Deck {
    public static final int DECK_SIZE = 60;

    private List<Painting> paintings;

    public Deck() {
        int position = 1;
        paintings = new ArrayList<>(DECK_SIZE);

        while (position <= DECK_SIZE) {
            for (Painting.Theme theme : Painting.Theme.values()) {
                paintings.add(new Painting(theme, position++));
            }
        }
    }

    public List<Painting> getPaintings() {
        return paintings;
    }

    public void setPaintings(List<Painting> paintings) {
        this.paintings = paintings;
    }

    public void shuffle() {
        Collections.shuffle(paintings);
    }

    public Painting draw() {
        return paintings.size() > 0 ? paintings.remove(0) : null;
    }

    public List<Painting> draw(int amount) {
        List<Painting> drawed = new ArrayList<>(amount);

        IntStream.range(0, amount).forEach(i -> {
            if (paintings.size() >= amount) {
                drawed.add(draw());
            }
        });

        return drawed;
    }
}
