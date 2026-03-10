package ru.job4j.repository;

import org.springframework.stereotype.Repository;

import ru.job4j.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends MainRepository<User, Long> {
    Optional<User> findByClientId(Long clientId);
}
