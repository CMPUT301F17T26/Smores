/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: Controller class for storing and retrieving Habits
 * Outstanding Issues: None at this time
 */

package cmput301f17t26.smores.all_storage_controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import cmput301f17t26.smores.all_activities.HabitDetailsActivity;
import cmput301f17t26.smores.all_models.Habit;
import cmput301f17t26.smores.all_models.Pair;
import cmput301f17t26.smores.utils.DataListener;
import cmput301f17t26.smores.utils.NetworkUtils;

/**
 * Created by Luke on 2017-10-31.
 */

public class HabitController {
    private static HabitController mHabitController = null;
    private static String SAVED_DATA_KEY = "cmput301f17t26.smores.all_storage_controller.HabitController";

    private ArrayList<Habit> mHabitList;

    private HabitController(Context context) {
        loadHabits(context);
    }

    public static HabitController getHabitController(Context context) {
        if (mHabitController == null) {
            mHabitController = new HabitController(context);
        }
        return mHabitController;
    }

    public void loadHabits(Context context) {
        SharedPreferences habitData = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String JSONHabits = habitData.getString(SAVED_DATA_KEY, "");

        if (JSONHabits.equals("")) {
            mHabitList = new ArrayList<Habit>();
        }
        else {
            mHabitList = gson.fromJson(JSONHabits,new TypeToken<ArrayList<Habit>>(){}.getType());
        }
        Log.d("main", Integer.toString(mHabitList.size() ));
    }

    public void saveHabits(Context context) {
        SharedPreferences habitData = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String JSONHabits = gson.toJson(mHabitList);

        SharedPreferences.Editor spEditor = habitData.edit();
        spEditor.putString(SAVED_DATA_KEY, JSONHabits);
        spEditor.apply();

        loadHabits(context);
    }

    public void updateHabit(Context context, Habit habit) {
        updateHabitOnServer(context, habit);
        saveHabits(context);
    }

    public void addHabit(Context context, Habit habit) {
        mHabitList.add(habit);
        saveHabits(context);
        addHabitToServer(context, habit);
    }

    public Habit getHabit(int i) {
        return mHabitList.get(i);
    }

    public void deleteHabit(Context context, Habit habit) {
        Log.d("Habit controller", "Gets called!");
        mHabitList.remove(habit);
        saveHabits(context);
        deleteHabitFromServer(context, habit);
    }

    public ArrayList<Habit> getHabitList() {
        return mHabitList;
    }

    private void deleteHabitFromServer(Context context, Habit habit) {
        ElasticSearchController.RemoveHabitTask removeHabitTask
                = new ElasticSearchController.RemoveHabitTask();
        if (NetworkUtils.isNetworkAvailable(context)) {
            removeHabitTask.execute(habit.getID());
        } else {
            Pair pair = new Pair(habit, Pair.REMOVE_HABIT);
            OfflineController.getOfflineController(context).addPair(context, pair);
        }
    }



    private void addHabitToServer(Context context, Habit habit) {
        ElasticSearchController.AddHabitTask addHabitTask
                = new ElasticSearchController.AddHabitTask();
        if (NetworkUtils.isNetworkAvailable(context)) {
            addHabitTask.execute(habit);
        } else {
            Pair pair = new Pair(habit, Pair.ADD_HABIT);
            OfflineController.getOfflineController(context).addPair(context, pair);
        }
    }

    private void updateHabitOnServer(Context context, Habit habit) {
        ElasticSearchController.UpdateHabitTask updateHabitTask
                = new ElasticSearchController.UpdateHabitTask();
        if (NetworkUtils.isNetworkAvailable(context)) {
            updateHabitTask.execute(habit);
        } else {
            Pair pair = new Pair(habit, Pair.UPDATE_HABIT);
            OfflineController.getOfflineController(context).addPair(context, pair);
        }
    }

    public Habit getHabit(UUID habitID) {
        for (Habit habit : mHabitList) {
            if (habit.getID().equals(habitID))
                return habit;
        }
        return null;
    }

    public UUID getHabitIDByTitle(String title) {
        for (Habit habit : mHabitList) {
            if (habit.getTitle().equals(title)) {
                return habit.getID();
            }
        }
        return null;
    }

    public String getHabitTitleByHabitID(UUID uuid) {
        for (Habit habit : mHabitList) {
            if (habit.getID().equals(uuid)) {
                return habit.getTitle();
            }
        }
        return null;
    }

    public Habit getHabitByTitle(String title) {
        for (Habit habit : mHabitList) {
            if (habit.getTitle().equals(title)) {
                return habit;
            }
        }
        return null;
    }

    public boolean isHabitTitleUnique(String title, int habitPosition) {
        for (Habit habit : mHabitList) {
            if (title.equals(habit.getTitle())
                    && (HabitDetailsActivity.HABIT_POSITION_NONE == habitPosition
                    || habit != mHabitList.get(habitPosition))) {
                return false;
            }
        }
        return true;
    }
}
