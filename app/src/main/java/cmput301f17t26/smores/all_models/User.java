package cmput301f17t26.smores.all_models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Rohan on 10/19/2017.
 */

public class User {

    private UUID mID;
    private ArrayList<UUID> requests;
    private ArrayList<UUID> following;

    public User () {
        mID = UUID.randomUUID();
        requests = new ArrayList<>();
        following = new ArrayList<>();
    }

    public UUID getUserID () {
        return mID;
    }

    public void addRequests (UUID friend) {
        requests.add(friend);
    }

    public void addFollowing (UUID friend) {
        following.add(friend);
    }

    public void removeRequests (UUID friend) {
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

    public UUID getFollower(int index) {
        return following.get(index);
    }


}
