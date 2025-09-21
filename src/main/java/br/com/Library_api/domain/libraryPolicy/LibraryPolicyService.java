package br.com.Library_api.domain.libraryPolicy;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class LibraryPolicyService {

    private static final LocalTime OPENING_HOUR = LocalTime.of(8, 0);
    private static final LocalTime CLOSING_HOUR = LocalTime.of(22, 0);

    public boolean isLibraryOpen(LocalDateTime dateTime) {
        DayOfWeek day = dateTime.getDayOfWeek();
        LocalTime time = dateTime.toLocalTime();

        //open Monday to Saturday, closed Sunday
        if (day == DayOfWeek.SUNDAY) {
            return false;
        }

        return !time.isBefore(OPENING_HOUR) && !time.isAfter(CLOSING_HOUR);
    }

    public LocalDateTime nextOpeningTime(LocalDateTime date) {
        LocalDateTime candidate = date.withHour(OPENING_HOUR.getHour()).withMinute(0).withSecond(0).withNano(0);

        if (isLibraryOpen(candidate)) {
            return candidate;
        }

        //next business day
        do {
            candidate = candidate.plusDays(1).withHour(OPENING_HOUR.getHour()).withMinute(0);
        } while (!isLibraryOpen(candidate));

        return candidate;
    }

    public LocalDateTime nextClosingTime(LocalDateTime date) {
        LocalDateTime candidate = date.withHour(OPENING_HOUR.getHour()).withMinute(0).withSecond(0).withNano(0);

        if (isLibraryOpen(candidate)) {
            return candidate.withHour(CLOSING_HOUR.getHour());
        }

        //next business day
        do {
            candidate = candidate.plusDays(1).withHour(CLOSING_HOUR.getHour()).withMinute(0);
        } while (!isLibraryOpen(candidate));

        return candidate;
    }
}
