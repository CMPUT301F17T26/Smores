/*
 * BootReceiver
 *
 * Version 1.0
 *
 * November 25, 2017
 *
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: Broadcast receiver for setting up notifications on boot of device
 */

package cmput301f17t26.smores.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by rohan on 11/23/2017.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationReceiver.setUpNotifcations(context);
    }
}
