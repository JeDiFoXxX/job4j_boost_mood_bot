package ru.job4j.repository.test;

import java.util.List;

import ru.job4j.model.User;

public interface UserRepository {
    List<User> findAll();

    User findByClientId(Long clientId);
}
