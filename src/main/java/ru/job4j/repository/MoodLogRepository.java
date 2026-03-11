package ru.job4j.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.model.MoodLog;
import ru.job4j.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface MoodLogRepository extends MainRepository<MoodLog, Long> {
    List<MoodLog> findByUserId(Long userId);

    Stream<MoodLog> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<MoodLog> findAllByUserClientIdAndCreatedAtGreaterThan(Long clientId, long startTimestamp);

    @Query("SELECT DISTINCT ml.user FROM MoodLog ml WHERE ml.user NOT IN "
            + "(SELECT ml2.user FROM MoodLog ml2 WHERE ml2.createdAt BETWEEN :start AND :end)")
    List<User> findUsersWhoDidNotVoteToday(@Param("start") long start, @Param("end") long end);

    Optional<MoodLog> findByUserIdAndCreatedAtBetween(Long userId, long start, long end);
}
