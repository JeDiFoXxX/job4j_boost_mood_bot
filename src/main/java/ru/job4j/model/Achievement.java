package ru.job4j.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "mb_achievement")
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long createAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "award_id")
    private Award award;

    public Achievement() { }

    public Achievement(long createAt, User user, Award award) {
        this.createAt = createAt;
        this.user = user;
        this.award = award;
    }

    public Long getId() {
        return id;
    }

    public long getCreateAt() {
        return createAt;
    }

    public User getUser() {
        return user;
    }

    public Award getAward() {
        return award;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Achievement that = (Achievement) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
