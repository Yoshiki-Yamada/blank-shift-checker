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
        var month = start.getMonthValue();
        var day = start.getDayOfMonth();
        var startHour = start.getHour();
        var startMinute = start.getMinute() == 0 ? "00" : start.getMinute();
        var endHour = end.getHour();
        var endMinute = end.getMinute() == 0 ? "00" : end.getMinute();
        return month + "/" + day + " " + startHour + ":" + startMinute + " ~ " + endHour + ":" + endMinute;
    }
}
