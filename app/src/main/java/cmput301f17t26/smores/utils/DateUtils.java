/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 */

package cmput301f17t26.smores.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Rohan on 11/16/2017.
 */

public class DateUtils {

    public static String getStringOfDate(Date date) {
        return  getDateFormat().format(date);
    }

    public static String getStringOfDate(Calendar calendar) {
        return getStringOfDate(calendar.getTime());
    }

    public static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("EEE MMM d, y", Locale.getDefault());
    }

}
