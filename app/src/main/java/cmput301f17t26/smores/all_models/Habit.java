/*
 * Habit
 *
 * Version 1.0
 *
 * October 28, 2016
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
    private int mDaysMissed;
    private int mDaysCompleted;
    private double mCurrentPercentage;

    private UUID mUserID;

    /**
     * Constructs a habit object.
     *
     * @param userID
     * @param title
     * @param reason
     * @param startDate
     * @param daysOfWeek
     * @throws TitleTooLongException
     * @throws ReasonTooLongException
     */
    public Habit(UUID userID, String title, String reason, Date startDate, HashMap<Integer, Boolean> daysOfWeek) throws  TitleTooLongException, ReasonTooLongException {
        mID = UUID.randomUUID();

        mUserID = userID;

        setTitle(title);
        setReason(reason);

        mStartDate = startDate;
        mDaysOfWeek = daysOfWeek;

        mLastCheckpoint = startDate;
        mDaysMissed = 0;
        mDaysCompleted = 0;
        mCurrentPercentage = 0d;
    }

    /**
     * Sets the habit title.
     *
     * @param title
     * @throws TitleTooLongException
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
     * @param reason
     * @throws ReasonTooLongException
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
     * @param date
     */
    public void setStartDate(Date date) {
        mStartDate = date;
    }

    /**
     * Sets the days of week the habit applies to.
     *
     * @param daysOfWeek
     */
    public void setDaysOfWeek(HashMap<Integer, Boolean> daysOfWeek) {
        mDaysOfWeek = daysOfWeek;
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
     * Returns the days of the week a habit applies to
     *
     * @return Hashmap<Integer, Boolean> mDaysOfWeek
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

    public void calculateStats(Context context) {
        int expected = findExpectedHabitEvents();
        int newCompleted = findHabitEvents(context);
        int newMissed = expected - newCompleted;
        mDaysCompleted = newCompleted;
        mDaysMissed = newMissed;

        mCurrentPercentage = (mDaysCompleted * 100.0d ) / (mDaysMissed + mDaysCompleted);
    }

    private int findExpectedHabitEvents() {
        int expected = 0;

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(mLastCheckpoint);
        startCal.set(Calendar.MILLISECOND, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.HOUR_OF_DAY, 0);

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

    private int findHabitEvents(Context context) {
        ArrayList<HabitEvent> habitEvents = HabitEventController.getHabitEventController(context).getHabitEvents();
        Calendar c = Calendar.getInstance();
        int successes = 0;

        for(HabitEvent habitEvent : habitEvents) {
            c.setTime(habitEvent.getDate());
            if (mID.equals(habitEvent.getHabitID())
                    && habitEvent.getDate().compareTo(mLastCheckpoint) > 0
                    && mDaysOfWeek.get(c.get(Calendar.DAY_OF_WEEK) - 1)) {
                successes++;
            }
        }
        return successes;
    }
}
