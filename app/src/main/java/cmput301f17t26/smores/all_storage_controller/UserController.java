/*
 * UserController
 *
 * Version 1.0
 *
 * November 25, 2017
 *
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: Controller class for storing and retrieving the User
 */

package cmput301f17t26.smores.all_storage_controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

import cmput301f17t26.smores.all_models.Feed;
import cmput301f17t26.smores.all_models.Habit;
import cmput301f17t26.smores.all_models.HabitEvent;
import cmput301f17t26.smores.all_models.User;

/**
 * Created by apate on 2017-10-31.
 */

public class UserController {

    private User user;
    private static final String SAVED_DATA_KEY = "cmput301f17t26.smores.all_storage_controller.user_controller";
    private static UserController mUserController = null;

    public static UserController getUserController(Context context) {
        if (mUserController == null) {
            mUserController = new UserController(context);
            return mUserController;
        }
        return mUserController;
    }

    private UserController( Context context) {
        initController(context);
    }

    private void retrieveUser(Context context) {
        SharedPreferences userData = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String JSONUser = userData.getString(SAVED_DATA_KEY, "");

        if (!JSONUser.equals("")) {
            user = gson.fromJson(JSONUser, new TypeToken<User>(){}.getType());
        }
    }

    private void saveUser(Context context, User user) {
        SharedPreferences userData = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String jsonUser = gson.toJson(user);

        SharedPreferences.Editor prefsEditor = userData.edit();
        prefsEditor.putString(SAVED_DATA_KEY, jsonUser);
        prefsEditor.apply();

        retrieveUser(context);
    }

    public User getUser() {
        return user;
    }

    private void initController(Context context) {
        retrieveUser(context);
    }

    public boolean isUserSet() {
        return user != null;
    }

    boolean checkUsername(String username) {
        ArrayList<User> foundUsers = new ArrayList<User>();
        ElasticSearchController.CheckUserTask checkUserTask
                = new ElasticSearchController.CheckUserTask();
        checkUserTask.execute("username", username);
        try {
            foundUsers.addAll(checkUserTask.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return foundUsers.size() == 0;
    }

    public boolean addUser(Context context, User user) {
        if (!checkUsername(user.getUsername())){ //username not unique
            return false;
        } else { //username unique
            ElasticSearchController.AddUserTask addUserTask = new ElasticSearchController.AddUserTask();
            addUserTask.execute(user);
            saveUser(context, user);
            return true;
        }
    }

    public void updateFollowingList() {
        ElasticSearchController.CheckUserTask checkUserTask = new ElasticSearchController.CheckUserTask();
        checkUserTask.execute("username", user.getUsername());
        try {
            user.setFollowingList(checkUserTask.get().get(0).getFollowingList());

        } catch (Exception e) {
        }
    }

    public ArrayList<HabitEvent> getFriendsHabitEvents() {
        ArrayList<HabitEvent> friendHabitEvents = new ArrayList<>();
        for (UUID friendUUID: user.getFollowingList()) {
            ElasticSearchController.GetHabitEventTask getHabitEventTask
                    = new ElasticSearchController.GetHabitEventTask();
            getHabitEventTask.execute("mUserID", friendUUID.toString());
            try  {
                ArrayList<HabitEvent> friendI = getHabitEventTask.get();
                Log.d("User controller", friendI.get(0).getHabitID().toString());

                if (friendI.size() > 0) {
                    Collections.sort(friendI, new Comparator<HabitEvent>() {
                        @Override
                        public int compare(HabitEvent o1, HabitEvent o2) {
                            return o1.getDate().compareTo(o2.getDate());
                        }
                    });
                    friendHabitEvents.add(friendI.get(friendI.size() - 1));
                }

            } catch (Exception e) {

            }

        }
        return friendHabitEvents;
    }

    public ArrayList<Habit>  getFriendsHabits() {
        ArrayList<Habit> friendHabits = new ArrayList<>();

        for (UUID friendUUID: user.getFollowingList()) {
            ElasticSearchController.GetHabitTask getHabitTask
                    = new ElasticSearchController.GetHabitTask();
            getHabitTask.execute("mUserID", friendUUID.toString());
            try {
                ArrayList<Habit> friendI = getHabitTask.get();
                Log.d("User controller", friendI.get(0).getTitle());
                if (friendI.size() > 0) {
                    friendHabits.addAll(friendI);
                }
            } catch (Exception e) {

            }
        }
        return friendHabits;
    }

    public ArrayList<Feed> getFeed() {
        ArrayList<HabitEvent> friendsHabitEvents = getFriendsHabitEvents();
        ArrayList<Habit> friendsHabits = getFriendsHabits();
        ArrayList<Habit> unprocessedHabits = new ArrayList<Habit>();
        unprocessedHabits.addAll(friendsHabits);
        ArrayList<Feed> feed = new ArrayList<>();
        for (Habit habit: friendsHabits) {

            for (HabitEvent habitEvent: friendsHabitEvents) {
                if (habitEvent.getHabitID().equals(habit.getID())) {
                    Feed f = new Feed(getUsernameByID(habit.getUserID()), habit, habitEvent);
                    unprocessedHabits.remove(habit);
                    feed.add(f);
                }
            }
        }
        for (Habit habit: unprocessedHabits) {
            Feed f = new Feed(getUsernameByID(habit.getUserID()), habit, null);
            feed.add(f);
        }

        Collections.sort(feed, new Comparator<Feed>() {
            @Override
            public int compare(Feed o1, Feed o2) {
                int compareValue = o1.getUsername().compareTo(o2.getUsername());
                if (compareValue != 0) {
                    return compareValue;
                } else {
                    return o1.getHabit().getTitle().compareTo(o2.getHabit().getTitle());
                }
            }
        });

        return feed;
    }

    public String getUsernameByID(UUID uuid) {
        ElasticSearchController.CheckUserTask checkUserTask
                = new ElasticSearchController.CheckUserTask();
        checkUserTask.execute("mID", uuid.toString());
        try {
            ArrayList<User> users = checkUserTask.get();
            return users.get(0).getUsername();
        } catch (Exception e) {
            return null;
        }
    }
}
