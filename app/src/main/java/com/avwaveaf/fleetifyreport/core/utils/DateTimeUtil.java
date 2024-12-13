package com.avwaveaf.fleetifyreport.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {
    public static String getCurrentTimeFormatted(){
        Date currDate = new Date();

        Locale localData = new Locale("id", "ID");

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", localData);
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM", localData);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH : mm", localData);

        String dayName = dayFormat.format(currDate);
        String formattedDate = dateFormat.format(currDate);
        String formattedTime = timeFormat.format(currDate);
        return String.format("%s, %s - %s", dayName, formattedDate, formattedTime);
    }

    public static String formatDateTime(String inputDateTime) {
        if (inputDateTime.isEmpty() || inputDateTime.isBlank()) {
            return "N/A";
        }
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

            Date date = inputFormat.parse(inputDateTime);

            Locale localData = new Locale("id", "ID");
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", localData);
            SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM", localData);
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH : mm", localData);

            assert date != null;
            String dayName = dayFormat.format(date);
            String formattedDate = dateFormat.format(date);
            String formattedTime = timeFormat.format(date);

            return String.format("%s, %s - %s", dayName, formattedDate, formattedTime);
        } catch (Exception e) {
            return "N/A";
        }
    }
}
