package ru.job4j.infastructur;

import java.time.LocalDate;
import java.time.ZoneId;

public interface TimeProvider {
    default long startOfDay() {
        return LocalDate.now()
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .getEpochSecond();
    }

    default long endOfDay() {
        return LocalDate.now()
                .plusDays(1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .getEpochSecond() - 1;
    }

    default long timeNow() {
        return System.currentTimeMillis() / 1000L;
    }
}
