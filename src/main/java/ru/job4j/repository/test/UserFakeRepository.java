package ru.job4j.repository.test;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;


import java.util.*;

import ru.job4j.model.User;

@Profile("test")
@Repository
public class UserFakeRepository implements UserRepository {
    private Map<Long, User> userMap = new HashMap<>();

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public User findByClientId(Long clientId) {
        return userMap.get(clientId);
    }

    public void save(User user) {
        userMap.put(user.clientId(), user);
    }
}

