package net.eaglegames.musee.entity;

import java.util.Objects;

public class Painting implements Comparable<Painting> {
    private Theme theme;
    private Integer value;

    public Painting(Theme theme, Integer value) {
        this.theme = theme;
        this.value = value;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public int compareTo(Painting painting) {
        return painting.getValue().compareTo(this.getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Painting painting = (Painting) o;
        return Objects.equals(theme, painting.theme) && Objects.equals(value, painting.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(theme, value);
    }

    public enum Theme {
        LANDSCAPE, WATER, PERSONS, ARCHITECTURE, ANIMAL
    }
}
