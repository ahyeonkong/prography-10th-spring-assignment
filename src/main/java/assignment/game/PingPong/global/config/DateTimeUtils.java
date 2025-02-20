package assignment.game.PingPong.global.config;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateTimeUtils {

    private DateTimeUtils() {
        // 유틸리티 클래스는 인스턴스화 방지
    }

    /**
     * LocalDateTime에서 나노초를 제거합니다.
     *
     * @param dateTime LocalDateTime 객체
     * @return 나노초가 제거된 LocalDateTime 객체
     */
    public static LocalDateTime removeNano(LocalDateTime dateTime) {
        return (dateTime != null) ? dateTime.truncatedTo(ChronoUnit.SECONDS) : null;
    }
}
