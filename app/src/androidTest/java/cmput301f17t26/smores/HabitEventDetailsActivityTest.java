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

import cmput301f17t26.smores.all_activities.HabitEventDetailsActivity;
import cmput301f17t26.smores.all_activities.MainActivity;

/**
 * Created by farhadmakiabady on 2017-11-11.
 */

public class HabitEventDetailsActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;

    public HabitEventDetailsActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testAddDeleteHabitEvent() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("HABIT HISTORY");
        View fab = getActivity().findViewById(R.id.addFab);
        solo.clickOnView(fab);
        solo.enterText((EditText) solo.getView(R.id.Event_hComment), "Test Comment!");
        ImageButton save = (ImageButton) solo.getView(R.id.Event_hSave);
        solo.clickOnView(save);
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        assertTrue(solo.waitForText("Test Comment!"));
        solo.clickOnText("Test Comment!");
        ImageButton delete = (ImageButton) solo.getView(R.id.Event_hDelete);
        solo.clickOnView(delete);
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        assertFalse(solo.waitForText("Test Comment!"));
    }


    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
