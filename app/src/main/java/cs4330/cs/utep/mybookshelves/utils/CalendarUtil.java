package cs4330.cs.utep.mybookshelves.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarUtil {

    public static String dateFormat = "MM-dd-yyyy";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

    public String convertMillisecondsToDate(long milliSeconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds * 1000);
        return simpleDateFormat.format(calendar.getTime());
    }
}
