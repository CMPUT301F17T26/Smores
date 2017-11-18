/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 */

package cmput301f17t26.smores.all_models;

/**
 * Created by Rohan on 11/14/2017.
 */

public class Feed {

    private Habit mHabit;
    private HabitEvent mHabitEvent;
    private String mUsername;

    public Feed(String username, Habit habit, HabitEvent habitEvent) {
        mUsername = username;
        mHabit = habit;
        mHabitEvent = habitEvent;
    }

    public Habit getHabit() {
        return mHabit;
    }

    public HabitEvent getHabitEvent() {
        return mHabitEvent;
    }

    public String getUsername() {
        return mUsername;
    }




}
