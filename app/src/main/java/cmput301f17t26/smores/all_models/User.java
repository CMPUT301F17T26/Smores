package cmput301f17t26.smores.all_models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;

/**
 * Created by Rohan on 10/19/2017.
 */

public class User {

    private static final String SAVED_ID_KEY = "useridkey";

    //JestID to be added
    private static String mID;

    private static ArrayList<String> requests;
    private static ArrayList<String> following;

    public static User mUser = null;

    public static User getUserSession(Context context) {
        if (mUser == null) {
            mUser = new User(context);
        } else {
            return mUser;
        }
    }

    private User(Context context) {
        SharedPreferences userIDData = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        mID = userIDData.getString(SAVED_ID_KEY, "");
        if (mID.equals("")) {
            mID = "TEST-ID";

            //assuming network call succeeds
            SharedPreferences.Editor prefsEditor = userIDData.edit();
            prefsEditor.putString(SAVED_ID_KEY, mID);
            prefsEditor.apply();
        }
    }



    public static String getUserID (Context context) {

    }

    public static ArrayList<String> getRequestsList () {
        return requests;
    }


}
