package cyclone.lunchvoting.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeUtils {
    private DateTimeUtils(){}

    public static LocalTime now() {
        return LocalTime.now();
    }
    public static LocalDate today() {
        return LocalDate.now();
    }
    public static LocalDateTime dateTime() {
        return LocalDateTime.now();
    }

    public static boolean isPast(LocalDateTime dateTime) {
        return dateTime.toLocalDate().isBefore(today());
    }

    public static LocalTime hhDashMmToLocalTime(String ddDashMm) {
        String[] votingEndsParts = ddDashMm.split("-");
        return LocalTime.of(Integer.valueOf(votingEndsParts[0]), Integer.valueOf(votingEndsParts[1]), 0);
    }
}
