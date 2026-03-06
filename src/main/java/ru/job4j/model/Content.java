package ru.job4j.model;

import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Objects;

public class Content {
    private final Long chatId;
    private String text;
    private InputFile photo;
    private InlineKeyboardMarkup markup;
    private InputFile audio;
    private InputFile video;

    public Content(Long chatId) {
        this.chatId = chatId;
    }

    public Long getChatId() {
        return chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public InputFile getPhoto() {
        return photo;
    }

    public void setPhoto(InputFile photo) {
        this.photo = photo;
    }

    public InlineKeyboardMarkup getMarkup() {
        return markup;
    }

    public void setMarkup(InlineKeyboardMarkup markup) {
        this.markup = markup;
    }

    public InputFile getAudio() {
        return audio;
    }

    public void setAudio(InputFile audio) {
        this.audio = audio;
    }

    public InputFile getVideo() {
        return video;
    }

    public void setVideo(InputFile video) {
        this.video = video;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Content content = (Content) object;
        return Objects.equals(chatId, content.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId);
    }
}
