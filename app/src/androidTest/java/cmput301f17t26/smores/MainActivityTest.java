/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 */

package cmput301f17t26.smores;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import cmput301f17t26.smores.all_activities.HabitDetailsActivity;
import cmput301f17t26.smores.all_activities.HabitEventDetailsActivity;
import cmput301f17t26.smores.all_activities.MainActivity;
import cmput301f17t26.smores.all_activities.MapsActivity;
import cmput301f17t26.smores.all_models.HabitEvent;

import com.robotium.solo.Solo;

/**
 * Created by farhadmakiabady on 2017-11-11.
 */

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    /**
     * Runs at the beginning of the tests
     * @throws Exception
     */
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testBeginHabitActivity() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("HABIT");
        View fab = getActivity().findViewById(R.id.addFab);
        solo.clickOnView(fab);
        solo.assertCurrentActivity("Wrong Acitity", HabitDetailsActivity.class);
    }

    public void testBeginHabitEventActivity() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("HABIT HISTORY");
        View fab = getActivity().findViewById(R.id.addFab);
        solo.clickOnView(fab);
        solo.assertCurrentActivity("Wrong Acitity", HabitEventDetailsActivity.class);
    }

    public void testMapHabitEventActivity() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("SOCIAL");
        View fab = getActivity().findViewById(R.id.mapsFab);
        solo.clickOnView(fab);
        solo.assertCurrentActivity("Wrong Acitity", MapsActivity.class);
    }

    public void testMapSocialActivity() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("SOCIAL");
        View fab = getActivity().findViewById(R.id.mapsFab);
        solo.clickOnView(fab);
        solo.assertCurrentActivity("Wrong Acitity", MapsActivity.class);
    }

    public void testAddRequestActivity() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("REQUESTS");
        View fab = getActivity().findViewById(R.id.addFab);
        solo.clickOnView(fab);
        solo.clickOnButton("Cancel");
        solo.assertCurrentActivity("Wrong Acitity", MainActivity.class);
    }
    /**
     * Runs at the end of the tests
     * @throws Exception
     */
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
