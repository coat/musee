package net.eaglegames.musee.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Deck {
    private List<Painting> paintings;

    public Deck() {
        int position = 1;
        paintings = new ArrayList<>(60);

        while (position <= 60) {
            paintings.add(new Painting(Painting.Theme.LANDSCAPE, position++));
            paintings.add(new Painting(Painting.Theme.WATER, position++));
            paintings.add(new Painting(Painting.Theme.PERSONS, position++));
            paintings.add(new Painting(Painting.Theme.ARCHITECTURE, position++));
            paintings.add(new Painting(Painting.Theme.ANIMAL, position++));
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
