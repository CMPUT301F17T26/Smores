package cmput301f17t26.smores.all_models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import cmput301f17t26.smores.all_exceptions.NotDayOfWeekException;
import cmput301f17t26.smores.all_exceptions.ReasonTooLongException;
import cmput301f17t26.smores.all_exceptions.TitleTooLongException;

/**
 * Created by Rohan on 10/19/2017.
 */

public class Habit {

    public static final int SUNDAY = 0;
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;

    //JestID to be added
    private String mID;

    private String mTitle;
    private String mReason;
    private Date mStartDate;

    private HashMap<Integer, Boolean> mDaysOfWeek;
    private Integer mDaysMissed;
    private Integer mDaysCompleted;
    private Double mPercentageFollowed;
    private String mMostFrequentDay;

    private String mUserID;

    public Habit(String userID, String title, String reason, Date startDate, HashMap<Integer, Boolean> daysOfWeek) {
        mUserID = userID;
        mTitle = title;
        mStartDate = startDate;
        mDaysOfWeek = daysOfWeek;

        mDaysMissed = 0;
        mDaysCompleted = 0;
        mPercentageFollowed = 0.0;
    }

    public void setTitle(String title) throws TitleTooLongException {
        if (title.length() > 20) {
            throw new TitleTooLongException();
        } else {
            mTitle = title;
        }
    }

    public void setReason(String reason) throws ReasonTooLongException {
        if (reason.length() > 30) {
            throw new ReasonTooLongException();
        } else {
            mReason = reason;
        }
    }
    public void setStartDate(Date date) {
        mStartDate = date;
    }

    public void setDaysOfWeek(HashMap<Integer, Boolean> daysOfWeek) {
        mDaysOfWeek = daysOfWeek;
    }

    public void setDaysMissed(Integer daysMissed) {
        mDaysMissed = daysMissed;
    }

    public void setPercentageFollowed(Double percentageFollowed) {
        mPercentageFollowed = percentageFollowed;
    }

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

    public String getTitle() {
        return mTitle;
    }
    public String getReason() {
        return mReason;
    }
    public Date getStartDate() {
        return mStartDate;
    }

    public HashMap<Integer, Boolean> getDaysofWeek() {
        return mDaysOfWeek;
    }

    public int getDaysMissed() {
        return mDaysMissed;
    }

    public int getDaysCompleted() {
        return mDaysCompleted;
    }

    public double getPercentageFollowed() {
        return mPercentageFollowed;
    }

    public String getMostFrequenyDay() {
        return mMostFrequentDay;
    }







}
