package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.model.MoodLog;

import java.util.List;

@Repository
public interface MoodLogRepository extends MainRepository<MoodLog, Long> {
    List<MoodLog> findAllByUserClientIdAndCreatedAtGreaterThan(Long clientId, long startTimestamp);
}
