package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.model.Award;

@Repository
public interface AwardRepository extends MainRepository<Award, Long> { }
