/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 */

package cmput301f17t26.smores.all_models;

import cmput301f17t26.smores.all_storage_controller.ElasticSearchController;

/**
 * Created by rohan on 11/13/2017.
 */

public class Pair {

    public static final int ADD_HABIT_EVENT = 0;
    public static final int REMOVE_HABIT_EVENT = 1;
    public static final int UPDATE_HABIT_EVENT = 2;

    public static final int ADD_HABIT = 3;
    public static final int REMOVE_HABIT = 4;
    public static final int UPDATE_HABIT = 5;

    private HabitEvent mHabitEvent;
    private Habit mHabit;

    private int mCommand;

    public Pair(HabitEvent habitEvent, int command) {
        mHabitEvent = habitEvent;
        mCommand = command;
    }

    public Pair(Habit habit, int command) {
        mHabit = habit;
        mCommand = command;
    }



    public void executeTask() {
        switch (mCommand) {
            case Pair.ADD_HABIT_EVENT:
                ElasticSearchController.AddHabitEventTask addHabitEventTask
                        = new ElasticSearchController.AddHabitEventTask();
                addHabitEventTask.execute(mHabitEvent);
                break;
            case Pair.REMOVE_HABIT_EVENT:
                ElasticSearchController.RemoveHabitEventTask removeHabitEventTask
                        = new ElasticSearchController.RemoveHabitEventTask();
                removeHabitEventTask.execute(mHabitEvent.getID());
                break;
            case Pair.UPDATE_HABIT_EVENT:
                ElasticSearchController.UpdateHabitEventTask updateHabitEventTask
                        = new ElasticSearchController.UpdateHabitEventTask();
                updateHabitEventTask.execute(mHabitEvent);
                break;
            case Pair.ADD_HABIT:
                ElasticSearchController.AddHabitTask addHabitTask
                        = new ElasticSearchController.AddHabitTask();
                addHabitTask.execute(mHabit);
                break;
            case Pair.REMOVE_HABIT:
                ElasticSearchController.RemoveHabitTask removeHabitTask
                        = new ElasticSearchController.RemoveHabitTask();
                removeHabitTask.execute(mHabit.getID());
                break;
            case Pair.UPDATE_HABIT:
                ElasticSearchController.UpdateHabitTask updateHabitTask
                        = new ElasticSearchController.UpdateHabitTask();
                updateHabitTask.execute(mHabit);
        }
    }


}