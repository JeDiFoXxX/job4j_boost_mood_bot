package ru.job4j.services;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import ru.job4j.bot.TgUI;
import ru.job4j.content.SentContent;
import ru.job4j.model.Content;
import ru.job4j.model.Mood;
import ru.job4j.model.MoodLog;
import ru.job4j.model.User;
import ru.job4j.repository.test.MoodFakeRepository;
import ru.job4j.repository.test.MoodLogFakeRepository;

class ReminderServiceTest {
    @Test
    public void whenMoodGood() {
        var result = new ArrayList<Content>();
        var sentContent = new SentContent() {
            @Override
            public void sent(Content content) {
                result.add(content);
            }
        };
        var moodRepository = new MoodFakeRepository();
        moodRepository.save(new Mood("Good", true));
        var moodLogRepository = new MoodLogFakeRepository();
        var user = new User(100, 100);
        var moodLog = new MoodLog();
        moodLog.setUser(user);
        var yesterday = LocalDate.now()
                .minusDays(10)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli() - 1;
        moodLog.setCreatedAt(yesterday);
        moodLogRepository.save(moodLog);
        var tgUI = new TgUI(moodRepository);
        new ReminderService(sentContent, moodLogRepository, tgUI)
                .remindUsers();
        assertThat(result.iterator()
                .next()
                .getMarkup()
                .getKeyboard()
                .iterator()
                .next()
                .iterator()
                .next().
                getText())
                .isEqualTo("Good");
    }
}