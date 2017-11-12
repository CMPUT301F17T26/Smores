/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 */

package cmput301f17t26.smores;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import cmput301f17t26.smores.all_activities.HabitEventDetailsActivity;

/**
 * Created by farhadmakiabady on 2017-11-11.
 */

public class HabitEventDetailsActivityTest extends ActivityInstrumentationTestCase2<HabitEventDetailsActivity> {
    private Solo solo;

    public HabitEventDetailsActivityTest() {
        super(HabitEventDetailsActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testCase() throws Exception {

    }

    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
