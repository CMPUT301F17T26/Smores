package cmput301f17t26.smores.all_models;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by Rohan on 10/19/2017.
 */

public class Habit {

    private String mTitle;
    private String mReason;
    private Date mStart;

    private HashMap<String, Boolean> mDaysOfWeek;
    private Integer mDaysMissed;
    private Integer mDaysCompleted;
    private Double mPercentageFollowed;
    private String mMostFrequentDay;

    //JestID to be added
    private String mID;

    private String mUserID;

    public Habit(String title, String reason, Date start, HashMap<String, Boolean> daysOfWeek) {
        this.mTitle = title;
        this.mStart = start;
        this.mDaysOfWeek = daysOfWeek;
    }





}
