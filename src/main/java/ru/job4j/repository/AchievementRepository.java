package ru.job4j.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.job4j.model.Achievement;
import ru.job4j.model.Award;

import java.util.List;

@Repository
public interface AchievementRepository extends MainRepository<Achievement, Long> {
    @Query("SELECT ach.award FROM Achievement ach WHERE ach.user.clientId = :clientId")
    List<Award> findAllByUserClientId(@Param("clientId") Long clientId);
}