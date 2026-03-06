package ru.job4j.model;

import java.util.concurrent.atomic.AtomicLong;
import java.util.Objects;

public class User {
    private static final AtomicLong COUNT = new AtomicLong(1);

    private final Long id;
    private final long clientId;
    private final long chatId;

    public User(long clientId, long chatId) {
        this.id = COUNT.getAndIncrement();
        this.clientId = clientId;
        this.chatId = chatId;
    }

    public Long getId() {
        return id;
    }

    public long getClientId() {
        return clientId;
    }

    public long getChatId() {
        return chatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", clientId="
                + clientId
                + ", chatId="
                + chatId
                + '}';
    }
}
