import com.google.api.client.util.DateTime;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * com.google.api.client.util.DateTimeとjava.time.LocalDateTimeに対するサービスクラスです。
 *
 * @version 1.0
 * @since 1.0
 * @author oketa216
 */
public class DateTimeService {

    /**
     * com.google.api.client.DateTimeをjava.time.LocalDateTimeに変換します。
     *
     * <p>
     *     LocalDateTimeで日付や時間の計算をして、DateTimeに変換するなどして活用してください。
     * </p>
     *
     * @param localDateTime 日付・時間共に指定されたjava.time.LocalDateTime
     * @return 引数をcom.google.api.client.DateTimeに変換したもの
     */
    public static DateTime convertToGDateTime(LocalDateTime localDateTime) {
        var millisecond = localDateTime.toEpochSecond(ZoneOffset.UTC) * 1000;
        return new DateTime(millisecond);
    }

    /**
     * java.time.LocalDateTimeをcom.google.api.client.DateTimeに変換します。
     *
     * <p>
     *     ±9時間問題には対応していません。
     * </p>
     *
     * @param gDateTime 日付・時間共に指定されたcom.google.api.client.DateTime
     * @return 引数をjava.time.LocalDateTimeに変換したもの
     */
    public static LocalDateTime convertToLocalDateTime(DateTime gDateTime) {
        var millisecond = gDateTime.getValue();
        var second = millisecond / 1000;
        return LocalDateTime.ofEpochSecond(second, 1000, ZoneOffset.UTC);
    }
}
