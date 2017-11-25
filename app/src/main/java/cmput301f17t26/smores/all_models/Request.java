/*
 * Request
 *
 * Version 1.0
 *
 * November 25, 2017
 *
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: Represents the request object. Used for sending friend requests between users.
 */

package cmput301f17t26.smores.all_models;


/**
 * Represents a request between two users
 *
 * @author Chris
 * @version 1.0
 * @since 1.0
 */
public class Request {
    private String mToUser;
    private String mFromUser;
    private String mID;

    /**
     * Constructs a Request object.
     *
     * @param fromUser receiver username
     * @param toUser sender username
     */
    public Request(String fromUser, String toUser) {
        mToUser = toUser;
        mFromUser = fromUser;
        mID = mToUser + mFromUser;
    }

    /**
     * Returns request unique identifier.
     * @return mID String
     */
    public String getID() {
        return mID;
    }

    /**
     * Returns request receiver username.
     * @return mToUser
     */
    public String getToUser() {
        return mToUser;
    }

    /**
     * Returns request sender username.
     * @return mFromUser
     */
    public String getFromUser() {
        return mFromUser;
    }

}
