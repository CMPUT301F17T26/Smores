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
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;

import cmput301f17t26.smores.R;
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
        if (intent.getIntExtra("NOTIFICATION_EXTRA", 0) == 1) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent mainActivityIntent = new Intent(context, MainActivity.class);
            mainActivityIntent.putExtra("START_NOTIFICATION", true);
            mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_REQUEST_CODE, mainActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Integer numOfHabitsToDo = new TodayAdapter(context).getSizeOfTodayHabits();

            if (numOfHabitsToDo > 0) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.notificationicon)
                        .setContentTitle("You have habits to do today!")
                        .setContentText(numOfHabitsToDo.toString() + " habit(s) to do")
                        .setAutoCancel(true);
                notificationManager.notify(NOTIFICATION_REQUEST_CODE, builder.build());
            }
        }

    }
    public static void setUpNotifcations(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("NOTIFICATION_EXTRA", 1);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        if (pref.getInt("hour", -1) == -1) {
            alarmManager.cancel(pendingIntent);
            return;
        }

        calendar.set(Calendar.HOUR_OF_DAY, pref.getInt("hour", 8));
        calendar.set(Calendar.MINUTE, pref.getInt("minute", 30));

        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DATE, 1);
        }




        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

}
