package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.model.Mood;

@Repository
public interface MoodRepository extends MainRepository<Mood, Long> { }
