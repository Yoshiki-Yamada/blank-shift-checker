import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        var session = SlackSessionFactory.createWebSocketSlackSession(Config.SLACK_API_TOKEN);
        session.connect();

        SlackChannel channel = session.findChannelByName(Config.CHANNEL_NAME);

        var fromDate = LocalDateTime.now();
        var toDate = fromDate.plusDays(Config.TO_DATE);
        var date = new Term(fromDate, toDate);
        var fromTime = LocalDateTime.now().withHour(Config.FROM_HOUR).withMinute(Config.FROM_MINUTE).withSecond(0).withNano(0);
        var toTime = LocalDateTime.now().withHour(Config.TO_HOUR).withMinute(Config.TO_MINUTE).withSecond(0).withNano(0);
        var time = new Term(fromTime, toTime);

        var blankChecker = new BlankChecker();
        var message = blankChecker.checkBlank(date, time);

        session.sendMessage(channel, message);

        session.disconnect();
    }
}
