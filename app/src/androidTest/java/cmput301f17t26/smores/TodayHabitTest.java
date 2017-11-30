/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 */

package cmput301f17t26.smores;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.robotium.solo.Solo;

import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.UUID;

import cmput301f17t26.smores.all_activities.HabitDetailsActivity;
import cmput301f17t26.smores.all_activities.MainActivity;

/**
 * Created by Rohan on 11/13/2017.
 */

public class TodayHabitTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;


    public TodayHabitTest() {
        super(MainActivity.class);
    }

    public void testTodayTab() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("HABIT");
        View fab = getActivity().findViewById(R.id.addFab);
        solo.clickOnView(fab);
        solo.assertCurrentActivity("Wrong Activity", HabitDetailsActivity.class);
        UUID uuid = UUID.randomUUID();
        solo.enterText((EditText) solo.getView(R.id.Habit_hName), uuid.toString().substring(0,15));
        solo.enterText((EditText) solo.getView(R.id.Habit_hReason), "Test Reasonv3!");


        Calendar today = Calendar.getInstance();
        int day_of_week = today.get(Calendar.DAY_OF_WEEK) - 1;
        switch(day_of_week) {
            case 0:
                solo.clickOnView(solo.getView(R.id.Habit_sun));
                break;
            case 1:
                solo.clickOnView(solo.getView(R.id.Habit_mon));
                break;
            case 2:
                solo.clickOnView(solo.getView(R.id.Habit_tue));
                break;
            case 3:
                solo.clickOnView(solo.getView(R.id.Habit_wed));
                break;
            case 4:
                solo.clickOnView(solo.getView(R.id.Habit_thu));
                break;
            case 5:
                solo.clickOnView(solo.getView(R.id.Habit_fri));
                break;
            case 6:
                solo.clickOnView(solo.getView(R.id.Habit_sat));
                break;
        }
        ImageButton save = (ImageButton) solo.getView(R.id.Habit_saveBtn);
        solo.clickOnView(save);
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("TODAY");
        assertTrue(solo.waitForText(uuid.toString().substring(0,15)));
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {
        try {
            solo.finishOpenedActivities();
        } catch (ConcurrentModificationException e) {

        }
    }




}
