package ru.job4j.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "mb_award")
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private int days;

    public Award() { }

    public Award(String title, String description, int days) {
        this.title = title;
        this.description = description;
        this.days = days;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDays() {
        return days;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Award award = (Award) object;
        return Objects.equals(id, award.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
