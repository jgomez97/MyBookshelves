package cs4330.cs.utep.mybookshelves.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Jesus Gomez
 *
 * Util that handles the conversion of
 * milliseconds into a date.
 *
 * Used by:
 * @see ListAdapterBookshelves
 * @see CustomDialog
 *
 * Class: CS4330
 * Instructor: Dr. Cheon
 * Assignment: Final project
 * Date of last modification: 04/17/2019
 **/

public class CalendarUtil {

    /** Format */
    public static String dateFormat = "MM-dd-yyyy";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

    /**
     * Converts the given milliseconds into a date in a form of a string.
     *
     * @param  milliSeconds milliseconds that we want to convert.
     * @return date in a form of a string.
     * */
    public String convertMillisecondsToDate(long milliSeconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds * 1000);
        return simpleDateFormat.format(calendar.getTime());
    }
}
