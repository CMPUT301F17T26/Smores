/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 */

package cmput301f17t26.smores.all_models;

import cmput301f17t26.smores.all_storage_controller.ElasticSearchController;


/**
 * Represents a Habit-command or HabitEvent-command Pair
 *
 * @author rohan
 * @version 1.0
 * @since 1.0
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

    /**
     * Constructs a HabitEvent-command Pair object.
     *
     * @param habitEvent data
     * @param command ADD_HABIT_EVENT = 0, REMOVE_HABIT_EVENT = 1, UPDATE_HABIT_EVENT = 2
     */
    public Pair(HabitEvent habitEvent, int command) {
        mHabitEvent = habitEvent;
        mCommand = command;
    }

    /**
     * Constructs a Habit-command Pair object.
     *
     * @param habit data
     * @param command ADD_HABIT = 3, REMOVE_HABIT = 4, UPDATE_HABIT = 5
     */
    public Pair(Habit habit, int command) {
        mHabit = habit;
        mCommand = command;
    }


    /**
     * Runs paired command code on associated Habit or HabitEvent data.
     */
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
