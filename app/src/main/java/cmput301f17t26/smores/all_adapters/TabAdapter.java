/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: Adapter class to return a fragment depending on which tab in main activity you are on
 * Outstanding issues: None at this time
 */

package cmput301f17t26.smores.all_adapters;

/**
 * Created by rohan on 10/5/2017.
 */

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import cmput301f17t26.smores.all_activities.MainActivity;
import cmput301f17t26.smores.all_fragments.HabitFragment;
import cmput301f17t26.smores.all_fragments.HabitHistoryFragment;
import cmput301f17t26.smores.all_fragments.RequestFragment;
import cmput301f17t26.smores.all_fragments.SocialFragment;
import cmput301f17t26.smores.all_fragments.TodayFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class TabAdapter extends FragmentPagerAdapter {

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        switch (position) {
            case 0:
                return new TodayFragment();
            case 1:
                return new HabitFragment();
            case 2:
                return new HabitHistoryFragment();
            case 3:
                return new SocialFragment();
            case 4:
                return new RequestFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 5 total pages.
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "TODAY";
            case 1:
                return "HABIT";
            case 2:
                return "HABIT HISTORY";
            case 3:
                return "SOCIAL";
            case 4:
                return "REQUESTS";
        }
        return null;
    }
}