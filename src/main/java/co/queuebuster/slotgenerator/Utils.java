package co.queuebuster.slotgenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static String changeDateFormat(String date, String currentFormat, String newFormat) {
        try {
            SimpleDateFormat oldDateFormat = new SimpleDateFormat(currentFormat);
            SimpleDateFormat newDateFormat = new SimpleDateFormat(newFormat);

            Date myDate = new Date();
            myDate = oldDateFormat.parse(date);
            date = newDateFormat.format(myDate);


        } catch (Exception e) {

        }

        return date;
    }


    public static Calendar getCalender(String time) {
        Calendar calendar = Calendar.getInstance();

        String mint = getMinute(time, "kk:mm");
        String hrs = getHours(time, "kk:mm");

        if (hrs != null)
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hrs));
        if (mint != null)
            calendar.set(Calendar.MINUTE, Integer.parseInt(mint));

        return calendar;
    }

    public static String getMinute(String time, String format) {

        try {
            SimpleDateFormat inFormat = new SimpleDateFormat(format, Locale.getDefault());
            SimpleDateFormat outFormat = new SimpleDateFormat("mm", Locale.getDefault());
            Date date = inFormat.parse(time);
            return outFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getHours(String time, String format) {
        try {
            SimpleDateFormat inFormat = new SimpleDateFormat(format, Locale.getDefault());
            SimpleDateFormat outFormat = new SimpleDateFormat("kk", Locale.getDefault());
            Date date = inFormat.parse(time);
            return outFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
