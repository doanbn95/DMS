package com.omi.sakura.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static String convertDateToString(Date date) {
        return new SimpleDateFormat(Constants.YYYYMMDD).format(date);
    }

    public static Date convertToDate(String dateString) {
        Date date = null;
        try {
            date = new SimpleDateFormat(Constants.YYYYMMDD).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getCurrentDate() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 60);
        calendar.set(Calendar.MINUTE, 60);
        calendar.set(Calendar.HOUR, 24);
        return calendar.getTime();

    }

}
