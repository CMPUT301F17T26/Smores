/*
 * User
 *
 * Version 1.0
 *
 * October 28, 2016
 *
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 */

package cmput301f17t26.smores.all_models;

import java.util.ArrayList;
import java.util.UUID;

import cmput301f17t26.smores.all_exceptions.InvalidUUIDException;

/**
 * Represents a User
 *
 * @author akpatel
 * @author rohan
 * @version 1.0
 * @since 1.0
 */
public class User {

    private UUID mID;
    private String username;
    private ArrayList<UUID> requests;
    private ArrayList<UUID> following;

    /**
     * Creates a user object
     *
     * @param username
     */
    public User (String username) {
        mID = UUID.randomUUID();
        requests = new ArrayList<>();
        following = new ArrayList<>();
        this.username = username;
    }

    /**
     * Returns user's ID.
     *
     * @return UUID mID
     */
    public UUID getUserID () {
        return mID;
    }

    /**
     * Returns user's username.
     *
     * @return String username
     */
    public String getUsername () {
        return username;
    }

    /**
     * Adds a friend request to the user, by UUID.
     * I.e. A person who requested to follow the user.
     *
     * @param friend
     * @throws InvalidUUIDException
     */
    public void addRequest (UUID friend) throws InvalidUUIDException {
        requests.add(friend);
    }

    /**
     * Adds a follower to the user, by UUID.
     * I.e. Who the user is following.
     *
     * @param friend
     * @throws InvalidUUIDException
     */
    public void addFollowing (UUID friend) throws InvalidUUIDException {
        following.add(friend);
    }

    /**
     * Removes a friend request from the user.
     * I.e. Removes a person who requested to follow the user.
     *
     * @param friend
     */
    public void removeRequest (UUID friend) {
        requests.remove(friend);
    }

    /**
     * Returns a list of friend requests, by UUID.
     * I.e. A list of people who have requested to follow the user.
     *
     * @return ArrayList<UUID> requests
     */
    public ArrayList<UUID> getRequestsList () {
        return requests;
    }

    /**
     * Returns a list of followers, by UUID.
     * I.e. A list of people who the user is following.
     *
     * @return ArrayList<UUID> following
     */
    public ArrayList<UUID> getFollowingList() {
        return following;
    }

    /**
     * Returns a specified user on the request list, by UUID.
     * I.e. A person who requested to follow the user
     *
     * @param index
     * @return UUID request
     */
    public UUID getRequest(int index) {
        return requests.get(index);
    }

    /**
     * Returns a specified user on the following list, by UUID.
     * I.e. A person who the user is following.
     *
     * @param index
     * @return UUID following
     */
    public UUID getFollowing(int index) {
        return following.get(index);
    }


}
