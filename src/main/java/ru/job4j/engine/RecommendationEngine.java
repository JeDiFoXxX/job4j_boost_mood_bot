package ru.job4j.engine;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

import ru.job4j.content.ContentProvider;
import ru.job4j.infastructur.LifecycleComponent;
import ru.job4j.model.Content;

@Service
public class RecommendationEngine extends LifecycleComponent {
    private final List<ContentProvider> contents;
    private static final Random RND = new Random(System.currentTimeMillis());

    public RecommendationEngine(List<ContentProvider> contents) {
        this.contents = contents;
    }

    public Content recommendFor(Long chatId, Long moodId) {
        var index = RND.nextInt(0, contents.size());
        return contents.get(index).byMood(chatId, moodId);
    }
}
