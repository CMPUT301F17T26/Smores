package cmput301f17t26.smores.all_storage_controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cmput301f17t26.smores.all_models.User;

/**
 * Created by apate on 2017-10-31.
 */

public class UserController {

    private User user;
    private static final String SAVED_DATA_KEY = "cmput301f17t26.smores.all_storage_controller";
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

    public void retrieveUser(Context context) {
        SharedPreferences userData = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String JSONUser = userData.getString(SAVED_DATA_KEY, "");

        if (!JSONUser.equals("")) {
            user = gson.fromJson(JSONUser, new TypeToken<User>(){}.getType());
        }
    }

    public void saveUser(Context context, User user) {
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

}
