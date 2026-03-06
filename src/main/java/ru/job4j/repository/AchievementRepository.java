package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.model.Achievement;

@Repository
public interface AchievementRepository extends MainRepository<Achievement, Long> { }