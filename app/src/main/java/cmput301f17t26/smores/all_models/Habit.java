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
 */

package cmput301f17t26.smores.all_models;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import cmput301f17t26.smores.all_exceptions.NotDayOfWeekException;
import cmput301f17t26.smores.all_exceptions.ReasonTooLongException;
import cmput301f17t26.smores.all_exceptions.TitleTooLongException;

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
    private Integer mDaysMissed;
    private Integer mDaysCompleted;
    private Double mPercentageFollowed;
    private String mMostFrequentDay;

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

        mDaysMissed = 0;
        mDaysCompleted = 0;
        mPercentageFollowed = 0.0;
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
     * Sets the number of days the habit is missed.
     *
     * @param daysMissed
     */
    public void setDaysMissed(Integer daysMissed) {
        mDaysMissed = daysMissed;
    }

    /**
     * Sets the number of days the habit is completed.
     *
     * @param daysCompleted
     */
    public void setDaysCompleted(Integer daysCompleted) {
        mDaysCompleted = daysCompleted;
    }

    /**
     * Sets the percentage the habit is followed for.
     *
     * @param percentageFollowed
     */
    public void setPercentageFollowed(Double percentageFollowed) {
        mPercentageFollowed = percentageFollowed;
    }

    /**
     * Sets the most frequent day of habit.
     *
     * @param dayOfWeek
     * @throws NotDayOfWeekException
     */
    public void setMostFrequentDay(Integer dayOfWeek) throws NotDayOfWeekException {
       switch (dayOfWeek) {
           case SUNDAY:
               mMostFrequentDay = "Sunday";
               break;
           case MONDAY:
               mMostFrequentDay = "Monday";
               break;
           case TUESDAY:
               mMostFrequentDay = "Tuesday";
               break;
           case WEDNESDAY:
               mMostFrequentDay = "Wednesday";
               break;
           case THURSDAY:
               mMostFrequentDay = "Thursday";
               break;
           case FRIDAY:
               mMostFrequentDay = "Friday";
               break;
           case SATURDAY:
               mMostFrequentDay = "Saturday";
               break;
           default:
               throw new NotDayOfWeekException();
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
        return mPercentageFollowed;
    }

    /**
     * Returns the most frequent day the habit is followed for
     *
     * @return String mMostFrequentDay
     */
    public String getMostFrequentDay() {
        return mMostFrequentDay;
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






}
