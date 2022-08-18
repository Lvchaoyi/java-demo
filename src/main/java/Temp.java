import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Temp {

  private boolean isWeekday(long millis) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(millis);
    return cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
        && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY;
  }

  public static void main(String[] args) {
    System.out.println(new Temp().isWeekday(1647509289567L));
  }


}
