/*
 * Habit
 *
 * Version 1.0
 *
 * October 28, 2017
 *
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: Represents a Habit Model
 */

package cmput301f17t26.smores.all_models;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import cmput301f17t26.smores.all_exceptions.ReasonTooLongException;
import cmput301f17t26.smores.all_exceptions.TitleTooLongException;
import cmput301f17t26.smores.all_storage_controller.HabitEventController;

/**
 * Represents a Habit
 *
 * @author rohan
 * @version 1.0
 * @since 1.0
 */
public class Habit {

    public static final int SUNDAY = 0;
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;

    private UUID mID;

    private String mTitle;
    private String mReason;
    private Date mStartDate;

    private HashMap<Integer, Boolean> mDaysOfWeek;

    private Date mLastCheckpoint;
    private int mCheckpointDaysMissed;
    private int mCheckpointDaysCompleted;
    private int mDaysMissed;
    private int mDaysCompleted;
    private double mCurrentPercentage;

    private UUID mUserID;

    /**
     * Constructs a habit object.
     *
     * @param userID associated user ID
     * @param title user specified
     * @param reason user specified
     * @param startDate user specified
     * @param daysOfWeek user specified
     * @throws TitleTooLongException if title is longer than 20 characters
     * @throws ReasonTooLongException if reason is longer than 30 characters
     */
    public Habit(UUID userID, String title, String reason, Date startDate, HashMap<Integer, Boolean> daysOfWeek) throws  TitleTooLongException, ReasonTooLongException {
        mID = UUID.randomUUID();

        mUserID = userID;

        setTitle(title);
        setReason(reason);

        mStartDate = startDate;
        mDaysOfWeek = daysOfWeek;

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        mLastCheckpoint = c.getTime();
        mDaysMissed = 0;
        mDaysCompleted = 0;
        mCheckpointDaysMissed = 0;
        mCheckpointDaysCompleted = 0;
        mCurrentPercentage = 0d;
    }

    /**
     * Sets the habit title.
     *
     * @param title string
     * @throws TitleTooLongException  if title length exceeds 20 characters
     */
    public void setTitle(String title) throws TitleTooLongException {
        if (title.length() > 20) {
            throw new TitleTooLongException();
        } else {
            mTitle = title;
        }
    }

    /**
     * Sets the habit reason.
     *
     * @param reason user specified string
     * @throws ReasonTooLongException if reason longer than 30 characters
     */
    public void setReason(String reason) throws ReasonTooLongException {
        if (reason.length() > 30) {
            throw new ReasonTooLongException();
        } else {
            mReason = reason;
        }
    }

    /**
     * Sets the habit start date.
     *
     * @param date user specified
     */
    public void setStartDate(Date date) {
        mStartDate = date;
    }

    /**
     * Sets the days of week the habit applies to.
     *
     * @param daysOfWeek specified by user
     */
    public void setDaysOfWeek(HashMap<Integer, Boolean> daysOfWeek, Context context) {
        if (!daysOfWeek.equals(mDaysOfWeek)) {
            boolean today = mDaysOfWeek.get(new Date().getDay());
            mDaysOfWeek = daysOfWeek;
            checkpointStats(context, today);
        }
    }

    /**
     * Returns habit title
     *
     * @return String mTitle
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Returns habit reason
     *
     * @return String mReason
     */
    public String getReason() {
        return mReason;
    }

    /**
     * Returns habit start date
     *
     * @return Date mStartDate
     */
    public Date getStartDate() {
        return mStartDate;
    }

    /**
     * Returns habit's last checkpoint date
     *
     * @return Date mLastCheckpoint
     */
    public Date getCheckpoint() {
        return mLastCheckpoint;
    }

    /**
     * Returns the days of the week a habit applies to
     *
     * @return Hashmap of Integer-Boolean pairs mDaysOfWeek
     */
    public HashMap<Integer, Boolean> getDaysOfWeek() {
        return mDaysOfWeek;
    }

    /**
     * Returns the number of days a habit was missed
     *
     * @return Integer mDaysMissed
     */
    public Integer getDaysMissed() {
        return mDaysMissed;
    }

    /**
     * Returns the number of days a habit was completed
     *
     * @return Integer mDaysCompleted
     */
    public Integer getDaysCompleted() {
        return mDaysCompleted;
    }

    /**
     * Returns the percentage a habit was followed for.
     *
     * @return Double mPercentageFollowed
     */
    public double getPercentageFollowed() {
        return mCurrentPercentage;
    }

    /**
     * Returns habit UUID
     *
     * @return UUID mId
     */
    public UUID getID() {
        return mID;
    }

    /**
     * Returns user UUID
     *
     * @return UUID mUserID
     */
    public UUID getUserID() {
        return mUserID;
    }

    public void calculateStats(ArrayList<HabitEvent> habitEvents) {
        int totalExpected = mCheckpointDaysCompleted + mCheckpointDaysMissed + findExpectedHabitEvents();
        int totalCompleted = mCheckpointDaysCompleted + findHabitEvents(habitEvents);
        int totalMissed = totalExpected - totalCompleted + mCheckpointDaysMissed;
        mDaysCompleted = totalCompleted;
        mDaysMissed = totalMissed;

        mCurrentPercentage = (mDaysCompleted * 100.0d ) / (mDaysMissed + mDaysCompleted);
        if (Double.isNaN(mCurrentPercentage)) {
            mCurrentPercentage = 0;
        }
    }

    public void calculateStats(Context context) {
        ArrayList<HabitEvent> habitEvents = HabitEventController.getHabitEventController(context).getHabitEventsByHabit(this);
        calculateStats(habitEvents);
    }

    public void checkpointStats(Context context, boolean today) {
        mCheckpointDaysMissed = mDaysMissed;
        mCheckpointDaysCompleted = mDaysCompleted;
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        mLastCheckpoint = c.getTime();

        ArrayList<HabitEvent> habitEvents = HabitEventController.getHabitEventController(context).getHabitEventsByHabit(this);
        if (today) {
            if (0 != habitEvents.size() && habitEvents.get(0).getDate().compareTo(mLastCheckpoint) > 0) {
                if (true == mDaysOfWeek.get(mLastCheckpoint.getDay())) {
                    mCheckpointDaysCompleted--;
                }
            } else {
                mCheckpointDaysMissed--;
            }
        }

        calculateStats(context);
    }

    /**
     * Calculates the number of habit events that should have been completed.
     *
     * @return expected number of habit events
     */
    private int findExpectedHabitEvents() {
        int expected = 0;

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(mLastCheckpoint);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(new Date());

        do {
            if (true == mDaysOfWeek.get(startCal.get(Calendar.DAY_OF_WEEK) - 1)) {
                expected++;
            }
            startCal.add(Calendar.DAY_OF_MONTH, 1);
        } while (startCal.getTimeInMillis() < endCal.getTimeInMillis()); //excluding end date

        return expected;
    }

    /**
     * Calculates the number of habit events that occurred on the habit's expected days
     *
     * @param habitEvents a list of the habitEvents that have been completed
     * @return number of habit events that were completed on expected days
     */

    private int findHabitEvents(ArrayList<HabitEvent> habitEvents) {
        Calendar c = Calendar.getInstance();
        int successes = 0;

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(mLastCheckpoint);

        for(HabitEvent habitEvent : habitEvents) {
            c.setTime(habitEvent.getDate());
            if (mID.equals(habitEvent.getHabitID())
                    && habitEvent.getDate().compareTo(startCal.getTime()) >= 0
                    && mDaysOfWeek.get(c.get(Calendar.DAY_OF_WEEK) - 1)) {
                successes++;
            }
        }
        return successes;
    }
}
