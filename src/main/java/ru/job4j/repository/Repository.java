package ru.job4j.repository;

import java.util.List;

import ru.job4j.model.User;

public interface Repository {
    void add(User user);

    List<User> findAll();

    User findByClientId(Long clientId);
}
