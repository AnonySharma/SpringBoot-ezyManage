package com.ankit.ezymanage.utils;

import java.sql.Timestamp;
import java.util.Date;

public class MyCalander {
    public static Timestamp now() {
        Date date = new Date();
        // SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // isoFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        // date = isoFormat.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new
        // Date()));
        Timestamp currentTime = new java.sql.Timestamp(date.getTime());
        return currentTime;
    }
}
