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
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cmput301f17t26.smores.all_activities.HabitDetailsActivity;
import cmput301f17t26.smores.all_models.Habit;

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

    public void addHabit(Context context, Habit habit) {
        mHabitList.add(habit);
        saveHabits(context);
        addHabitToServer(habit);
    }

    public Habit getHabit(int i) {
        return mHabitList.get(i);
    }

    public void deleteHabit(Context context, int i) {
        deleteHabitFromServer(mHabitList.get(i));
        mHabitList.remove(i);
        saveHabits(context);
    }

    public void deleteHabitFromServer(Habit habit) {
        ElasticSearchController.RemoveHabitTask removeHabitTask
                = new ElasticSearchController.RemoveHabitTask();
        removeHabitTask.execute(habit.getID());
    }

    public ArrayList<Habit> getHabitList() {
        return mHabitList;
    }

    private void addHabitToServer(Habit habit) {
        ElasticSearchController.AddHabitTask addHabitTask
                = new ElasticSearchController.AddHabitTask();
        //if internet {
        addHabitTask.execute(habit);
        //}
        //else {
        //listOfCommandsToDo.add(new Pair(habit, addHabitTask));
        //}
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
