package cmput301f17t26.smores.all_models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.UUID;

import cmput301f17t26.smores.all_exceptions.InvalidUUIDException;

/**
 * Created by Rohan on 10/19/2017.
 */

public class User {

    private UUID mID;
    private String username;
    private ArrayList<UUID> requests;
    private ArrayList<UUID> following;

    public User (String username) {
        mID = UUID.randomUUID();
        requests = new ArrayList<>();
        following = new ArrayList<>();
        this.username = username;
    }

    public UUID getUserID () {
        return mID;
    }

    public String getUsername () {
        return username;
    }

    public void addRequest (UUID friend) throws InvalidUUIDException {
        requests.add(friend);

    }

    public void addFollowing (UUID friend) throws InvalidUUIDException {
        following.add(friend);
    }

    public void removeRequest (UUID friend) {
        requests.remove(friend);
    }

    public ArrayList<UUID> getRequestsList () {
        return requests;
    }

    public ArrayList<UUID> getFollowingList() {
        return following;
    }

    public UUID getRequest(int index) {
        return requests.get(index);
    }

    public UUID getFollowing(int index) {
        return following.get(index);
    }


}
