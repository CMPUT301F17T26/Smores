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

import org.joda.time.LocalDate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import cmput301f17t26.smores.all_exceptions.ImageNotSetException;
import cmput301f17t26.smores.all_models.Habit;
import cmput301f17t26.smores.all_models.HabitEvent;
import cmput301f17t26.smores.all_models.Pair;
import cmput301f17t26.smores.utils.DataListener;
import cmput301f17t26.smores.utils.DateUtils;
import cmput301f17t26.smores.utils.NetworkUtils;

/**
 * Created by Christian on 2017-10-31.
 */

public class HabitEventController {
    private static HabitEventController habitEventController = null;
    private ArrayList<HabitEvent> mHabitEvents, mFilteredHabitEvents;
    private static final String SAVED_DATA_KEY = "cmput301f17t26.smores.all_storage_controller.HabitEventController";
    private static final String FILENAME = "HabitEvents.sav";

    protected Set<DataListener> mDataListenerSet;

    private HabitEventController(Context context) {
        mFilteredHabitEvents = new ArrayList<>();
        mDataListenerSet = new HashSet<>();
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
        Collections.sort(mHabitEvents, new Comparator<HabitEvent>() {
            @Override
            public int compare(HabitEvent o1, HabitEvent o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        Collections.reverse(mHabitEvents);
        addHabitEventToServer(context, habitEvent);
        saveHabitEvents(context);
    }

    public void updateHabitEvent(Context context, HabitEvent habitEvent) {
        updateHabitEventOnServer(context, habitEvent);
        saveHabitEvents(context);
    }

    public void deleteHabitEvent(Context context, int index) {
        deleteHabitEventFromServer(context, mHabitEvents.get(index));
        mHabitEvents.remove(index);
        saveHabitEvents(context);
    }

    public void deleteHabitEvent(Context context, UUID uuid) {
        for (HabitEvent habitEvent: mHabitEvents) {
            if (habitEvent.getID().equals(uuid)) {
                deleteHabitEventFromServer(context, habitEvent);
                mHabitEvents.remove(habitEvent);
                mFilteredHabitEvents.remove(habitEvent);
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
                    habitEvent.getDate().getDate() == date.getDate()) {
                return false;
            }
        }
        return true;
    }

    public Boolean doesHabitEventExist (Habit habit, Date date) {
        ArrayList<HabitEvent> habitEvents = getHabitEventsByHabit(habit);
        for (HabitEvent habitEvent: habitEvents) {
            if (habitEvent.getDate().getYear() == date.getYear() &&
                    habitEvent.getDate().getMonth() == date.getMonth() &&
                    habitEvent.getDate().getDay() == date.getDay()) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Date> getMissedHabitEvents(Habit habit) {
        ArrayList<Date> missedHabitEvents = new ArrayList<>();
        HashMap<Integer, Boolean> mDays = habit.getDaysOfWeek();

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(habit.getStartDate());

        Calendar endCal = Calendar.getInstance();
        LocalDate endDate = LocalDate.fromCalendarFields(endCal);
        for (LocalDate date = LocalDate.fromCalendarFields(startCal); date.isBefore(endDate); date = date.plusDays(1)) {
            Calendar c = Calendar.getInstance();
            c.setTime(date.toDate());
            if (mDays.get(c.get(Calendar.DAY_OF_WEEK) - 1) && doesHabitEventExist(habit, date.toDate())) {
                missedHabitEvents.add(date.toDate());
                Log.d("Found: ", DateUtils.getStringOfDate(date.toDate()));
            }
        }

        return missedHabitEvents;
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

    private void retrieveHabitEvents(Context context) {
        try {
            FileInputStream fileInputStream = context.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fileInputStream));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<HabitEvent>>(){}.getType();
            mHabitEvents = gson.fromJson(in, listType);
            mFilteredHabitEvents.clear();
            mFilteredHabitEvents.addAll(mHabitEvents);
        } catch (FileNotFoundException e) {
            mFilteredHabitEvents = new ArrayList<>();
            mHabitEvents = new ArrayList<>();
        }
        notifyAllObservers();
    }

    private void saveHabitEvents(Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);
            Gson gson = new Gson();
            gson.toJson(mHabitEvents, writer);
            writer.flush();
            retrieveHabitEvents(context);
        } catch (Exception e) {

        }
    }

    private void deleteHabitEventFromServer(Context context, HabitEvent habitEvent) {
        ElasticSearchController.RemoveHabitEventTask removeHabitEventTask
                = new ElasticSearchController.RemoveHabitEventTask();

        if (NetworkUtils.isNetworkAvailable(context)) {
            removeHabitEventTask.execute(habitEvent.getID());

        } else {
            Pair pair = new Pair(habitEvent, Pair.REMOVE_HABIT_EVENT);
            OfflineController.getOfflineController(context).addPair(context, pair);
        }


    }

    private void addHabitEventToServer(Context context, HabitEvent habitEvent) {
        ElasticSearchController.AddHabitEventTask addHabitEventTask
                = new ElasticSearchController.AddHabitEventTask();
        if (NetworkUtils.isNetworkAvailable(context)) {
            addHabitEventTask.execute(habitEvent);
        } else {
            Pair pair = new Pair(habitEvent, Pair.ADD_HABIT_EVENT);
            OfflineController.getOfflineController(context).addPair(context, pair);
        }
    }

    private void updateHabitEventOnServer(Context context, HabitEvent habitEvent) {
        ElasticSearchController.UpdateHabitEventTask updateHabitEventTask
                = new ElasticSearchController.UpdateHabitEventTask();

        if (NetworkUtils.isNetworkAvailable(context)) {
            updateHabitEventTask.execute(habitEvent);
        } else {
            Pair pair = new Pair(habitEvent, Pair.UPDATE_HABIT_EVENT);
            OfflineController.getOfflineController(context).addPair(context, pair);
        }
    }

    public void deleteHabitEventsByHabit(Context context, UUID habitID) {
        for (HabitEvent event: mHabitEvents) {
            if (event.getHabitID().equals(habitID)) {
                deleteHabitEvent(context, event.getID());
            }
        }
    }

    public void addListener(DataListener dataListener) {
        mDataListenerSet.add(dataListener);
        notifyUpdate(dataListener);
    }
    public void removeListener(DataListener dataListener) {
        mDataListenerSet.remove(dataListener);
    }
    private void notifyUpdate(DataListener dataListener) {
        dataListener.onDataUpdated();
    }
    private void notifyAllObservers() {
        for (DataListener dataListener: mDataListenerSet) {
            notifyUpdate(dataListener);
        }
    }
}