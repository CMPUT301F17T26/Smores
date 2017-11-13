/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 */

package cmput301f17t26.smores.all_storage_controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

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

    private boolean checkUsername(String username) {
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

}
