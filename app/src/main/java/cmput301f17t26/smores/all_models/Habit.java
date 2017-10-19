package cmput301f17t26.smores.all_models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import cmput301f17t26.smores.all_exceptions.NotDayOfWeekException;
import cmput301f17t26.smores.all_exceptions.ReasonTooLongException;
import cmput301f17t26.smores.all_exceptions.TitleTooLongException;

/**
 * Created by Rohan on 10/19/2017.
 */

public class Habit {

    public static final Integer SUNDAY = 0;
    public static final Integer MONDAY = 1;
    public static final Integer TUESDAY = 2;
    public static final Integer WEDNESDAY = 3;
    public static final Integer THURSDAY = 4;
    public static final Integer FRIDAY = 5;
    public static final Integer SATURDAY = 6;

    public static final Integer[] DAYS_OF_WEEK = {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY};

    private String mTitle;
    private String mReason;
    private Date mStartDate;

    private HashMap<Integer, Boolean> mDaysOfWeek;
    private Integer mDaysMissed;
    private Integer mDaysCompleted;
    private Double mPercentageFollowed;
    private String mMostFrequentDay;

    //JestID to be added
    private String mID;

    private String mUserID;

    public Habit(String userID, String title, String reason, Date startDate, HashMap<Integer, Boolean> daysOfWeek) {
        this.mUserID = userID;
        this.mTitle = title;
        this.mStartDate = startDate;
        this.mDaysOfWeek = daysOfWeek;

        this.mDaysMissed = 0;
        this.mDaysCompleted = 0;
        this.mPercentageFollowed = 0.0;
    }

    public void setTitle(String title) throws TitleTooLongException {
        if (title.length() > 20) {
            throw new TitleTooLongException();
        } else {
            this.mTitle = title;
        }
    }

    public void setReason(String reason) throws ReasonTooLongException {
        if (reason.length() > 30) {
            throw new ReasonTooLongException();
        } else {
            this.mReason = reason;
        }
    }
    public void setStartDate(Date date) {
        this.mStartDate = date;
    }

    public void setDaysOfWeek(HashMap<Integer, Boolean> daysOfWeek) {
        this.mDaysOfWeek = daysOfWeek;
    }

    public void setDaysMissed(Integer daysMissed) {
        this.mDaysMissed = daysMissed;
    }

    public void setPercentageFollowed(Double percentageFollowed) {
        this.mPercentageFollowed = percentageFollowed;
    }

    public void setMostFrequentDay(Integer dayOfWeek) throws NotDayOfWeekException {
       

    }





}
