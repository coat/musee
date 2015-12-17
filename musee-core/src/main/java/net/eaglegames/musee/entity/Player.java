package net.eaglegames.musee.entity;

import net.eaglegames.musee.graphics.EntityRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    private Musee musee;
    private List<Painting> hand;
    private EntityRenderer entityRenderer;

    public Player() {
        this.musee = new Musee();
        this.hand = new ArrayList<>();
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
    public String toString() {
        return hand.stream().map(Painting::toString).collect(Collectors.joining(","));
    }
}
