/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 */

package cmput301f17t26.smores;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import cmput301f17t26.smores.all_activities.HabitDetailsActivity;

/**
 * Created by farhadmakiabady on 2017-11-11.
 */

public class HabitDetailsActivityTest extends ActivityInstrumentationTestCase2<HabitDetailsActivity> {
    private Solo solo;

    public HabitDetailsActivityTest() {
        super(HabitDetailsActivity.class);
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

    /**
     * Runs at the end of the tests
     * @throws Exception
     */
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
