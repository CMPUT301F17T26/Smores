/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 */

package cmput301f17t26.smores;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.robotium.solo.Solo;

import junit.framework.Assert;

import java.util.ConcurrentModificationException;
import java.util.UUID;
import java.util.regex.Pattern;

import cmput301f17t26.smores.all_activities.HabitDetailsActivity;
import cmput301f17t26.smores.all_activities.HabitEventDetailsActivity;
import cmput301f17t26.smores.all_activities.MainActivity;

/**
 * Created by farhadmakiabady on 2017-11-11.
 */

public class HabitDetailsActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;

    public HabitDetailsActivityTest() {
        super(MainActivity.class);
    }

    /**
     * Runs at the beginning of the tests
     * @throws Exception
     */
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testAddHabit() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("HABIT");
        View fab = getActivity().findViewById(R.id.addFab);
        solo.clickOnView(fab);
        UUID uuid = UUID.randomUUID();
        solo.enterText((EditText) solo.getView(R.id.Habit_hName), uuid.toString().substring(0,15));
        solo.enterText((EditText) solo.getView(R.id.Habit_hReason), "Test Reasonv2!");
        solo.clickOnCheckBox(0);
        ImageButton save = (ImageButton) solo.getView(R.id.Habit_saveBtn);
        solo.clickOnView(save);
        assertTrue(solo.waitForText(uuid.toString().substring(0,15)));
    }
    public void testAddDeleteHabit() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("HABIT");
        View fab = getActivity().findViewById(R.id.addFab);
        solo.clickOnView(fab);
        UUID uuid = UUID.randomUUID();
        solo.enterText((EditText) solo.getView(R.id.Habit_hName), uuid.toString().substring(0,15));
        solo.enterText((EditText) solo.getView(R.id.Habit_hReason), "Test Reason!");
        solo.clickOnCheckBox(0);
        ImageButton save = (ImageButton) solo.getView(R.id.Habit_saveBtn);
        solo.clickOnView(save);
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        assertTrue(solo.waitForText(uuid.toString().substring(0,15)));
        solo.clickOnText(uuid.toString().substring(0,15));
        ImageButton delete = (ImageButton) solo.getView(R.id.Habit_deleteBtn);
        solo.clickOnView(delete);
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        assertFalse(solo.waitForText(uuid.toString().substring(0,15)));
    }

    /**
     * Runs at the end of the tests
     * @throws Exception
     */
    public void tearDown() throws Exception{
        try {
            solo.finishOpenedActivities();
        } catch (ConcurrentModificationException e) {

        }

    }
}
