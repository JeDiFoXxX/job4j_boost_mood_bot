package ru.job4j.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "mb_mood")
public class Mood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private boolean good;

    public Mood() { }

    public Mood(String text, boolean good) {
        this.text = text;
        this.good = good;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isGood() {
        return good;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Mood mood = (Mood) object;
        return Objects.equals(id, mood.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
