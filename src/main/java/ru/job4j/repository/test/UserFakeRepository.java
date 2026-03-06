package ru.job4j.repository.test;

import org.springframework.context.annotation.Profile;


import java.util.*;

import ru.job4j.model.User;
import ru.job4j.repository.Repository;

@Profile("test")
@org.springframework.stereotype.Repository
public class UserFakeRepository implements Repository {
    private final Map<Long, User> userMap = new HashMap<>();

    @Override
    public void add(User user) {
        userMap.put(user.getId(), user);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public User findByClientId(Long clientId) {
        return userMap.get(clientId);
    }
}

