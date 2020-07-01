import java.time.LocalDateTime;

/**
 * java.time.LocalDateTimeを二つ持ち、期間を表現するクラス。
 *
 * @version 1.0
 * @since 1.0
 * @author oketa216
 */
public class Term {

    private LocalDateTime start;
    private LocalDateTime end;

    public Term(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return start.getMonthValue() + "/" + start.getDayOfMonth() + " "
                + start.getHour() + ":" + start.getMinute() + " - "
                + end.getHour() + ":" + end.getMinute();
    }
}
