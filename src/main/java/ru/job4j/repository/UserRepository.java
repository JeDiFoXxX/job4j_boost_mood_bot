package ru.job4j.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Component;
import ru.job4j.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
@Component
public class UserRepository implements Repository {
    private final ConcurrentHashMap<Long, User> userMap = new ConcurrentHashMap<>();

    @Override
    public void add(User user) {
        userMap.putIfAbsent(user.getClientId(), user);
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
