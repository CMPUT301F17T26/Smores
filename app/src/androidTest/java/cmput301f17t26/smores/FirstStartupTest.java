/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 */

package cmput301f17t26.smores;

import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.robotium.solo.Solo;

import junit.framework.Assert;

import java.util.UUID;

import cmput301f17t26.smores.all_activities.HabitDetailsActivity;
import cmput301f17t26.smores.all_activities.HabitEventDetailsActivity;
import cmput301f17t26.smores.all_activities.MainActivity;
import cmput301f17t26.smores.all_models.HabitEvent;

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
            solo.clickOnText("REQUESTS");
            if (!solo.searchText("Your username: ")) {
                Assert.fail();
            }

        }
    }

    public void testAllowLocation() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("HABIT HISTORY");
        View fab = getActivity().findViewById(R.id.addFab);
        solo.clickOnView(fab);
        solo.assertCurrentActivity("Wrong Activity", HabitEventDetailsActivity.class);
        solo.clickOnToggleButton("OFF");
        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        UiObject allowPermissions = mDevice.findObject(new UiSelector().text("ALLOW"));
        if (allowPermissions.exists()) {
            try {
                allowPermissions.click();
            } catch (UiObjectNotFoundException e) {
                //fails
            }
        } else{
            solo.goBack();
            solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
            solo.clickOnText("REQUESTS");
            if (!solo.searchText("Your username: ")) {
                Assert.fail();
            }
        }
    }

//    public void testAllowCamera() {
//        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
//        solo.clickOnText("HABIT HISTORY");
//        View fab = getActivity().findViewById(R.id.addFab);
//        solo.clickOnView(fab);
//        solo.assertCurrentActivity("Wrong Activity", HabitEventDetailsActivity.class);
//        ImageButton camera = (ImageButton) solo.getView(R.id.Event_hImagebtn);
//        solo.clickOnView(camera);
//        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
//        UiObject allowPermissions = mDevice.findObject(new UiSelector().text("ALLOW"));
//        if (allowPermissions.exists()) {
//            try {
//                allowPermissions.click();
//            } catch (UiObjectNotFoundException e) {
//                //fails
//            }
//        } else {
//            if (solo.getCurrentActivity().getLocalClassName().equals("HabitEventDetailsActivity")) {
//                Assert.fail();
//            } else{
//                solo.goBack();
//            }
//        }
//    }

    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
