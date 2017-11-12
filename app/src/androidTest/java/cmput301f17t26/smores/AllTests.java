/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 */

package cmput301f17t26.smores;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestSuite;

import cmput301f17t26.smores.all_activities.MainActivity;

/**
 * Created by farhadmakiabady on 2017-11-11.
 */

public class AllTests extends ActivityInstrumentationTestCase2<Activity> {
    public AllTests(Class<Activity> activityClass) {
        super(activityClass);
    }

    public static TestSuite suite() {
        TestSuite t = new TestSuite();
        t.addTestSuite(FirstStartupTest.class);
        t.addTestSuite(MainActivityTest.class);
        t.addTestSuite(HabitDetailsActivityTest.class);
        t.addTestSuite(HabitEventDetailsActivityTest.class);

        return t;
    }

    @Override
    public void setUp() throws Exception {

    }

    @Override
    public void tearDown() throws Exception {

    }
}
