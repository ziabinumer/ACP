package logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppLogger {
    public static void log(String type, String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.printf("[%s LOG %s] %s\n", type, timestamp, message);
    }
    public static void info(String message) {
        log("INFO", message);
    }

    public static void error(String message) {
        log("ERROR", message);
    }

    public static void success(String message) {
        log("SUCCESS", message);
    }
}
