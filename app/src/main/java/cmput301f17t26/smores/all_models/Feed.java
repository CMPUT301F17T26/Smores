/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 */

package cmput301f17t26.smores.all_models;


/**
 * Represents a social feed
 *
 * @author rohan
 * @version 1.0
 * @since 1.0
 */
public class Feed {

    private Habit mHabit;
    private HabitEvent mHabitEvent;
    private String mUsername;

    /**
     * Constructs a feed object
     *
     * @param username friend identifier
     * @param habit friend habit type
     * @param habitEvent friend's most recent habit event
     */
    public Feed(String username, Habit habit, HabitEvent habitEvent) {
        mUsername = username;
        mHabit = habit;
        mHabitEvent = habitEvent;
    }

    /**
     * Returns friend habit object
     *
     * @return mHabit Habit
     */
    public Habit getHabit() {
        return mHabit;
    }

    /**
     * Returns friend habit event
     *
     * @return mHabitEvent HabitEvent
     */
    public HabitEvent getHabitEvent() {
        return mHabitEvent;
    }

    /**
     * Returns friend username
     *
     * @return mUsername String
     */
    public String getUsername() {
        return mUsername;
    }

}
