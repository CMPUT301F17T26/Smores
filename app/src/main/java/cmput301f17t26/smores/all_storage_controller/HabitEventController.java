/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 */

package cmput301f17t26.smores.all_storage_controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.UUID;

import cmput301f17t26.smores.all_exceptions.ImageNotSetException;
import cmput301f17t26.smores.all_models.HabitEvent;

/**
 * Created by Christian on 2017-10-31.
 */

public class HabitEventController {
    private static HabitEventController habitEventController = null;
    private ArrayList<HabitEvent> mHabitEvents;
    private static final String SAVED_DATA_KEY = "cmput301f17t26.smores.all_storage_controller.HabitEventController";

    private HabitEventController(Context context) {
        initHabitEvents(context);
    }

    private void initHabitEvents(Context context) {
        retrieveHabitEvents(context);
    }

    public static HabitEventController getHabitEventController(Context context) {
        if (habitEventController == null)
            habitEventController = new HabitEventController(context);
        return habitEventController;
    }

    public void addHabitEvent(Context context, HabitEvent habitEvent) {
        mHabitEvents.add(habitEvent);
        saveHabitEvents(context);
    }

    public void updateHabitEvent(Context context, HabitEvent habitEvent) {
        saveHabitEvents(context);
    }

    public void deleteHabitEvent(Context context, int index) {
        mHabitEvents.remove(index);
        saveHabitEvents(context);
    }

    public void deleteHabitEvent(Context context, UUID uuid) {
        for (HabitEvent habitEvent: mHabitEvents) {
            if (habitEvent.getID().equals(uuid)) {
                mHabitEvents.remove(habitEvent);
                break;
            }
        }
        saveHabitEvents(context);
    }


    public ArrayList<HabitEvent> getHabitEvents() {
        return mHabitEvents;
    }

    public HabitEvent getHabitEvent(int index) {
        return mHabitEvents.get(index);
    }

    public HabitEvent getHabitEvent(UUID id) {
        for (HabitEvent event : mHabitEvents) {
            if (event.getID().equals(id))
                return event;
        }
        return null;
    }

    public void retrieveHabitEvents(Context context) {
        SharedPreferences habitEventData = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String JSONHabitEvent = habitEventData.getString(SAVED_DATA_KEY, "");

        if (!JSONHabitEvent.equals("")) {
            mHabitEvents = gson.fromJson(JSONHabitEvent, new TypeToken<ArrayList<HabitEvent>>(){}.getType());
        } else {
            mHabitEvents = new ArrayList<HabitEvent>();
        }
    }

    public void saveHabitEvents(Context context) {
        SharedPreferences habitEventData = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String JSONHabitEvents = gson.toJson(mHabitEvents);

        SharedPreferences.Editor prefsEditor = habitEventData.edit();
        prefsEditor.putString(SAVED_DATA_KEY, JSONHabitEvents);
        prefsEditor.apply();

        retrieveHabitEvents(context);
    }
}