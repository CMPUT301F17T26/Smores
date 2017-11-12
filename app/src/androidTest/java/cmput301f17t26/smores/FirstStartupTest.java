/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 */

package cmput301f17t26.smores;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.support.v4.app.FragmentManager;
import android.widget.EditText;

import com.robotium.solo.Solo;

import java.util.UUID;

import cmput301f17t26.smores.all_activities.MainActivity;

/**
 * Created by farhadmakiabady on 2017-11-11.
 */

public class FirstStartupTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;

    public FirstStartupTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testAddUser() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        if (solo.searchText("Check and Add")) {
            solo.enterText((EditText) solo.getView(R.id.Request_username), UUID.randomUUID().toString());
            solo.clickOnButton("Check and Add");
            solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        } else {
            //do nothing...
        }
    }

    public void testCase() throws Exception {

    }

    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
