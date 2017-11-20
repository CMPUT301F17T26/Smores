/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: View class for hosting fragments for habits to do today, habits, habit events,
 * social & requests. Also can launch activities for viewing details of habits & habit events & maps.
 * Outstanding issues: None known at this time
 */

package cmput301f17t26.smores.all_activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.UUID;

import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_adapters.TabAdapter;
import cmput301f17t26.smores.all_fragments.AddFriendFragment;
import cmput301f17t26.smores.all_fragments.AddUserFragment;
import cmput301f17t26.smores.all_fragments.HabitFragment;
import cmput301f17t26.smores.all_fragments.HabitHistoryFragment;
import cmput301f17t26.smores.all_storage_controller.OfflineController;
import cmput301f17t26.smores.all_storage_controller.UserController;
import cmput301f17t26.smores.utils.NetworkStateReceiver;

public class MainActivity extends AppCompatActivity implements HabitFragment.HabitFragmentListener, HabitHistoryFragment.HabitHistoryFragmentListener, NetworkStateReceiver.NetworkStateReceiverListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private TabAdapter mTabAdapter;

    private ViewPager mViewPager;
    private FloatingActionButton mAddFloatingActionButton;
    private FloatingActionButton mMapsFloatingActionButton;
    private TabLayout mTabLayout;
    private UserController mUserController;
    public static final int NEW_HABIT = 0;
    public static final int EDIT_HABIT = 1;

    private NetworkStateReceiver networkStateReceiver; //https://stackoverflow.com/questions/6169059/android-event-for-internet-connectivity-state-change
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserController = UserController.getUserController(this);
        getUser();

        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);


        mAddFloatingActionButton = (FloatingActionButton) findViewById(R.id.addFab);
        mMapsFloatingActionButton = (FloatingActionButton) findViewById(R.id.mapsFab);

        mMapsFloatingActionButton.hide();
        mAddFloatingActionButton.hide();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mTabAdapter = new TabAdapter(getSupportFragmentManager());



        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mTabAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        setupTabLayoutListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        this.unregisterReceiver(networkStateReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupTabLayoutListener() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0: //TODAY
                        mAddFloatingActionButton.setVisibility(View.INVISIBLE);
                        mAddFloatingActionButton.hide();

                        mMapsFloatingActionButton.setVisibility(View.INVISIBLE);
                        mMapsFloatingActionButton.hide();
                        return;
                    case 1: //HABIT
                        mAddFloatingActionButton.show();
                        mAddFloatingActionButton.setVisibility(View.VISIBLE);
                        mAddFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainActivity.this, HabitDetailsActivity.class);
                                MainActivity.this.startActivityForResult(intent, NEW_HABIT);
                            }
                        });

                        mMapsFloatingActionButton.setVisibility(View.INVISIBLE);
                        mMapsFloatingActionButton.hide();
                        return;
                    case 2: //HABIT HISTORY
                        mAddFloatingActionButton.show();
                        mAddFloatingActionButton.setVisibility(View.VISIBLE);
                        mAddFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainActivity.this, HabitEventDetailsActivity.class);
                                MainActivity.this.startActivity(intent);
                            }
                        });

                        mMapsFloatingActionButton.show();
                        mMapsFloatingActionButton.setVisibility(View.VISIBLE);
                        mMapsFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                                MainActivity.this.startActivity(intent);
                            }
                        });

                        return;
                    case 3: //SOCIAL
                        mAddFloatingActionButton.hide();
                        mAddFloatingActionButton.setVisibility(View.INVISIBLE);

                        mMapsFloatingActionButton.show();
                        mMapsFloatingActionButton.setVisibility(View.VISIBLE);
                        mMapsFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                                MainActivity.this.startActivity(intent);
                            }
                        });
                        return;
                    case 4: //REQUESTS
                        mAddFloatingActionButton.show();
                        mAddFloatingActionButton.setVisibility(View.VISIBLE);


                        mAddFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FragmentManager manager = MainActivity.this.getSupportFragmentManager();
                                AddFriendFragment addFriendFragment = new AddFriendFragment();
                                addFriendFragment.show(manager, "AddDialog");
                            }
                        });

                        mMapsFloatingActionButton.setVisibility(View.INVISIBLE);
                        mMapsFloatingActionButton.hide();
                        return;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

    public void getUser () {
        if (!mUserController.isUserSet()) {
            FragmentManager manager = MainActivity.this.getSupportFragmentManager();
            AddUserFragment addUserFragment = new AddUserFragment();
            addUserFragment.show(manager, "AddUser");
        } else {
            if (mUserController.getUser() == null) {
                Log.d("Tag", "Null!");
            } else {
                Log.d("Tag", mUserController.getUser().getUsername());
            }
        }
    }

    @Override
    public void onHabitListInteraction(int position) { //open new activity
        Intent intent = new Intent(MainActivity.this, HabitDetailsActivity.class);
        intent.putExtra("habitPosition", position);
        MainActivity.this.startActivityForResult(intent, EDIT_HABIT);
    }

    @Override
    public void onHabitEventListInteraction(UUID position) {
        Intent intent = new Intent (MainActivity.this, HabitEventDetailsActivity.class);
        intent.putExtra("habitEventPosition", position);
        MainActivity.this.startActivityForResult(intent, EDIT_HABIT);
    }

    @Override
    public void networkAvailable() {
        if (OfflineController.getOfflineController(this).executeOnServer(MainActivity.this)) {
            Snackbar.make(mViewPager, "You are online! Synchronized Habit and Habit Events with server!", Snackbar.LENGTH_INDEFINITE).show();
        } else {
            Snackbar.make(mViewPager, "You are online!", Snackbar.LENGTH_INDEFINITE).show();
        }
    }

    @Override
    public void networkUnavailable() {
        Snackbar.make(mViewPager, "You are offline! All Habit & Habit Events will be synchronized when you get online!", Snackbar.LENGTH_INDEFINITE).show();
    }
}
