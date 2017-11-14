/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: Controller class for storing and retrieving Habit Events
 * Outstanding Issues: None at this time
 */

package cmput301f17t26.smores.all_storage_controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import cmput301f17t26.smores.all_exceptions.ImageNotSetException;
import cmput301f17t26.smores.all_models.Habit;
import cmput301f17t26.smores.all_models.HabitEvent;

/**
 * Created by Christian on 2017-10-31.
 */

public class HabitEventController {
    private static HabitEventController habitEventController = null;
    private ArrayList<HabitEvent> mHabitEvents, mFilteredHabitEvents;
    private static final String SAVED_DATA_KEY = "cmput301f17t26.smores.all_storage_controller.HabitEventController";

    private HabitEventController(Context context) {
        mFilteredHabitEvents = new ArrayList<>();
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
        addHabitEventToServer(habitEvent);
        saveHabitEvents(context);
    }

    public void updateHabitEvent(Context context, HabitEvent habitEvent) {
        updateHabitEventOnServer(habitEvent);
        saveHabitEvents(context);
    }

    public void deleteHabitEvent(Context context, int index) {
        deleteHabitEventFromServer(mHabitEvents.get(index));
        mHabitEvents.remove(index);
        saveHabitEvents(context);
    }

    public void deleteHabitEvent(Context context, UUID uuid) {
        for (HabitEvent habitEvent: mHabitEvents) {
            if (habitEvent.getID().equals(uuid)) {
                deleteHabitEventFromServer(habitEvent);
                mHabitEvents.remove(habitEvent);
                break;
            }
        }
        saveHabitEvents(context);
    }


    public ArrayList<HabitEvent> getHabitEvents() {
        return mHabitEvents;
    }

    public ArrayList<HabitEvent> getFilteredHabitEvents() {
        return mFilteredHabitEvents;
    }

    public HabitEvent getHabitEvent(int index) {
        return mHabitEvents.get(index);
    }

    public Boolean doesHabitEventExist (Habit habit) {
        ArrayList<HabitEvent> habitEvents = getHabitEventsByHabit(habit);
        Date date = new Date();
        for (HabitEvent habitEvent: habitEvents) {
            if (habitEvent.getDate().getYear() == date.getYear() &&
                    habitEvent.getDate().getMonth() == date.getMonth() &&
                    habitEvent.getDate().getDay() == date.getDay()) {
                return false;
            }
        }
        return true;
    }

    @NonNull
    private ArrayList<HabitEvent> getHabitEventsByHabit(Habit habit) {
        ArrayList<HabitEvent> habitEvents = new ArrayList<>();
        for (HabitEvent habitEvent: mHabitEvents) {
            if (habitEvent.getHabitID().equals(habit.getID())) {
                habitEvents.add(habitEvent);
            }
        }
        return habitEvents;
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
            mFilteredHabitEvents.addAll(mHabitEvents);
        } else {
            mFilteredHabitEvents = new ArrayList<HabitEvent>();
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

    public void deleteHabitEventFromServer(HabitEvent habitEvent) {
        ElasticSearchController.RemoveHabitEventTask removeHabitEventTask
                = new ElasticSearchController.RemoveHabitEventTask();
        removeHabitEventTask.execute(habitEvent.getID());
    }

    public void addHabitEventToServer(HabitEvent habitEvent) {
        ElasticSearchController.AddHabitEventTask addHabitEventTask
                = new ElasticSearchController.AddHabitEventTask();
        //if internet {
        addHabitEventTask.execute(habitEvent);
        //}
        //else {
        //listOfCommandsToDo.add(new Pair(habit, addHabitTask));
        //}
    }

    public void updateHabitEventOnServer(HabitEvent habitEvent) {
        ElasticSearchController.UpdateHabitEventTask updateHabitEventTask
                = new ElasticSearchController.UpdateHabitEventTask();
        updateHabitEventTask.execute(habitEvent);
    }

    public void deleteHabitEventsByHabit(Context context, UUID habitID) {
        for (HabitEvent event: mHabitEvents) {
            if (event.getHabitID().equals(habitID)) {
                deleteHabitEvent(context, event.getID());
            }
        }
    }
}