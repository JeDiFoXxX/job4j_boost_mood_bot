package ru.job4j.repository.test;

import org.springframework.test.fake.CrudRepositoryFake;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ru.job4j.model.Mood;
import ru.job4j.model.User;
import ru.job4j.repository.MoodRepository;

public class MoodFakeRepository
        extends CrudRepositoryFake<Mood, Long>
        implements MoodRepository {

    public List<Mood> findAll() {
        return new ArrayList<>(memory.values());
    }
}
