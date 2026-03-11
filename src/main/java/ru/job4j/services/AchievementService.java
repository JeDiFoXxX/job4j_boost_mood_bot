package ru.job4j.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.context.ApplicationListener;

import java.util.Comparator;

import ru.job4j.content.SentContent;
import ru.job4j.infastructur.LifecycleComponent;
import ru.job4j.infastructur.TimeProvider;
import ru.job4j.model.Achievement;
import ru.job4j.model.Content;
import ru.job4j.model.Mood;
import ru.job4j.model.MoodLog;
import ru.job4j.model.event.UserEvent;
import ru.job4j.repository.AchievementRepository;
import ru.job4j.repository.AwardRepository;
import ru.job4j.repository.MoodLogRepository;

@Service
public class AchievementService
        extends LifecycleComponent
        implements ApplicationListener<UserEvent>, TimeProvider {
    private final AchievementRepository achievementRepository;
    private final AwardRepository awardRepository;
    private final MoodLogRepository moodLogRepository;
    private final SentContent sentContent;

    public AchievementService(AchievementRepository achievementRepository,
                              AwardRepository awardRepository,
                              MoodLogRepository moodLogRepository,
                              SentContent sentContent) {
        this.achievementRepository = achievementRepository;
        this.awardRepository = awardRepository;
        this.moodLogRepository = moodLogRepository;
        this.sentContent = sentContent;
    }

    @Transactional
    @Override
    public void onApplicationEvent(UserEvent event) {
        var user = event.getUser();
        var moods = moodLogRepository.findByUserId(user.getId())
                .stream()
                .sorted(Comparator.comparing(MoodLog::getCreatedAt).reversed())
                .map(MoodLog::getMood)
                .toList();
        int count = 0;
        int total = 0;
        for (Mood mood : moods) {
            if (mood.isGood()) {
                count++;
                if (count > total) {
                    total = count;
                }
            } else {
                count = 0;
            }
        }
        int finalTotal = total;
        awardRepository.findAll()
                .stream()
                .filter(awd -> awd.getDays() == finalTotal)
                .findFirst()
                .ifPresent(awd -> {
                    if (!achievementRepository.existsByUserAndAward(user, awd)) {
                        Achievement achievement = new Achievement(
                                timeNow(), user, awd);
                        achievementRepository.save(achievement);
                        Content content = new Content(user.getChatId());
                        content.setText("Вы получили достижение: \n" + awd.getTitle());
                        sentContent.sent(content);
                    }
                });
    }
}
