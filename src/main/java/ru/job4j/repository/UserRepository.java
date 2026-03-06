package ru.job4j.repository;

import org.springframework.stereotype.Repository;

import ru.job4j.model.User;

@Repository
public interface UserRepository extends MainRepository<User, Long> {
    User findByClientId(Long clientId);
}
