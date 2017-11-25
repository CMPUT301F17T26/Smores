/*
 * NotificationReceiver
 *
 * Version 1.0
 *
 * November 25, 2017
 *
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: Display a notification at a specified time once a day.
 * The notification shows the number of habits to do today.
 */

package cmput301f17t26.smores.utils;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;

import cmput301f17t26.smores.all_activities.MainActivity;
import cmput301f17t26.smores.all_adapters.TodayAdapter;

import static android.content.Context.ALARM_SERVICE;
import static cmput301f17t26.smores.all_activities.MainActivity.NOTIFICATION_REQUEST_CODE;

/**
 * Created by rohan on 11/22/2017.
 */

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar c = Calendar.getInstance();
        Log.d("Main r", DateUtils.getStringOfDate(c) + " " + Integer.toString(c.get(Calendar.HOUR_OF_DAY)) + " " + Integer.toString(c.get(Calendar.MINUTE)) + " " +  Integer.toString(c.get(Calendar.SECOND)));
        if (intent.getIntExtra("NOTIFICATION_EXTRA", 0) == 1) {
            Log.d("Main r2", DateUtils.getStringOfDate(c) + " " + Integer.toString(c.get(Calendar.HOUR_OF_DAY)) + " " + Integer.toString(c.get(Calendar.MINUTE)) + " " +  Integer.toString(c.get(Calendar.SECOND)));
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Intent repeating = new Intent(context, MainActivity.class);
            repeating.putExtra("START_NOTIFICATION", true);
            repeating.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_REQUEST_CODE, repeating, PendingIntent.FLAG_UPDATE_CURRENT);
            Integer numOfHabitsToDo = new TodayAdapter(context).getSizeOfTodayHabits();

            if (numOfHabitsToDo > 0) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(android.support.design.R.drawable.notification_icon_background)
                        .setContentTitle("You have habits to do today!")
                        .setContentText(numOfHabitsToDo.toString() + " habit(s) to do")
                        .setAutoCancel(true);
                notificationManager.notify(NOTIFICATION_REQUEST_CODE, builder.build());
                Log.d("Main r", "Some habits");
            }
        }

    }
    public static void setUpNotifcations(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 30);

        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DATE, 1);
        }

        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("NOTIFICATION_EXTRA", 1);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Log.d("datechange", "Setup notification for next day at 8:30am");
    }

}
