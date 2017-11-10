package com.bobo.normalman.themuseandme.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xiaobozhang on 11/9/17.
 */

public class DateUtil {

    public static String toString(long milliseconds) {
        Date date = new Date(milliseconds);
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(date);
    }

    public static long toLong(String date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.parse(date).getTime();
    }
}
