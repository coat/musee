package net.eaglegames.musee.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    private Integer id;
    private Musee musee;
    private List<Painting> hand;

    public Player(Integer id) {
        this.id = id;
        this.musee = new Musee();
        this.hand = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public Musee getMusee() {
        return musee;
    }

    public void setMusee(Musee musee) {
        this.musee = musee;
    }

    public List<Painting> getHand() {
        return hand;
    }

    public void setHand(List<Painting> hand) {
        this.hand = hand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
