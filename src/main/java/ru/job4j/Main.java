package ru.job4j;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.Bean;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import ru.job4j.infastructur.TelegramBotHandler;
import ru.job4j.model.Award;
import ru.job4j.model.MoodContent;
import ru.job4j.repository.*;

@EnableScheduling
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner initTelegramApi(ApplicationContext ctx) {
        return args -> {
            var bot = ctx.getBean(TelegramBotHandler.class);
            try {
                bot.init(new TelegramBotsApi(DefaultBotSession.class));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        };
    }

    @Bean
    public CommandLineRunner loadDatabase(MoodRepository moodRepository,
                                          MoodContentRepository moodContentRepository,
                                          AwardRepository awardRepository) {
        return args -> {
            try (FileReader moodReader = new FileReader("./data/mood_content.json");
                 FileReader awardReader = new FileReader("./data/award.json")) {
                Gson gson = new Gson();
                List<MoodContent> moodContents = gson.fromJson(
                        moodReader,
                        new TypeToken<List<MoodContent>>() {
                        }.getType()
                );
                List<Award> awards = gson.fromJson(
                        awardReader,
                        new TypeToken<List<Award>>() {
                        }.getType()
                );
                moodRepository.saveAll(moodContents.stream()
                        .map(MoodContent::getMood).toList());
                moodContentRepository.saveAll(moodContents);
                awardRepository.saveAll(awards);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
