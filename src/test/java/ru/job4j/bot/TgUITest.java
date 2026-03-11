package ru.job4j.bot;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import ru.job4j.model.Mood;
import ru.job4j.repository.MoodRepository;
import ru.job4j.repository.test.MoodFakeRepository;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = {TgUI.class, MoodFakeRepository.class})
class TgUITest {
    @Autowired
    private TgUI tgUI;

    @Autowired
    @Qualifier("moodFakeRepository")
    private MoodRepository moodRepository;

    @Test
    public void whenBtnGood() {
        assertThat(moodRepository).isNotNull();
    }

    @Test
    public void whenBuildButtonsValid() {
        var mood = new Mood("Good", true);
        moodRepository.save(mood);
        var result = tgUI.buildButtons().getKeyboard();
        assertThat("Good").isEqualTo(result.get(0).get(0).getText());
    }
}