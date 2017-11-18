/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 */

package cmput301f17t26.smores.all_models;

import java.util.UUID;

/**
 * Created by Christian on 2017-11-13.
 */

public class Request {
    private String mToUser;
    private String mFromUser;
    private String mID;

    public Request(String fromUser, String toUser) {
        mToUser = toUser;
        mFromUser = fromUser;
        mID = mToUser + mFromUser;
    }

    public String getID() {
        return mID;
    }

    public String getToUser() {
        return mToUser;
    }


    public String getFromUser() {
        return mFromUser;
    }

}
