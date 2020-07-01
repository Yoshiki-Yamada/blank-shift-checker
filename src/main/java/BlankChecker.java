import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlankChecker {

    private static final String APPLICATION_NAME = "BlankShift Checker";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    public String checkBlank(Term date, Term time) throws GeneralSecurityException, IOException {
        var HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        var service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        var fromDate = date.getStart();
        var toDate = date.getEnd();
        var fromTime = time.getStart();
        var toTime = time.getEnd();

        var message = "空いている時間をお知らせします。\n";

        for (var focus = fromDate; focus.isBefore(toDate); focus = focus.plusDays(1)) {
            var from = LocalDateTime.of(focus.getYear(), focus.getMonthValue(), focus.getDayOfMonth(),
                    fromTime.getHour(), fromTime.getMinute(), 0, 0);
            var to = LocalDateTime.of(focus.getYear(), focus.getMonthValue(), focus.getDayOfMonth(),
                    toTime.getHour(), toTime.getMinute(), 0, 0);
            var extent = new Term(from, to);
            var events = service.events().list("primary")
                    .setTimeMin(DateTimeService.convertToGDateTime(from.minusHours(9)))
                    .setTimeMax(DateTimeService.convertToGDateTime(to.minusHours(9)))
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            var items = events.getItems();
            var comparisons = new ArrayList<Term>();
            if (items.isEmpty()) {
            } else {
                for (var event : items) {
                    var start = DateTimeService.convertToLocalDateTime(event.getStart().getDateTime()).plusHours(9).withNano(0);
                    var end = DateTimeService.convertToLocalDateTime(event.getEnd().getDateTime()).plusHours(9).withNano(0);
                    var comparison = new Term(start, end);
                    comparisons.add(comparison);
                }
            }
            var blanks = TermService.findBlanks(extent, comparisons);
            for (var blank : blanks) {
                if (blank != null) {
                    message += " - " + blank.toString() + "\n";
                }
            }
        }
        return message;
    }

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = Main.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
}
