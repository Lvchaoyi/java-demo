import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Temp {

  public static void main(String[] args) {
    DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    Date now = new Date(System.currentTimeMillis() + 3600 * 24 * 30 * 1000L);
    String date = dateFormat.format(now);
    System.out.println(date);
  }

}
