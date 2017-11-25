/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 */

package cmput301f17t26.smores;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

import cmput301f17t26.smores.all_models.Request;

/**
 * Created by Rohan on 11/25/2017.
 */

public class RequestTest extends ActivityInstrumentationTestCase2 {
    public RequestTest() {
        super(Request.class);
    }

    public void testGetID() {
        String toUser = "toUser";
        String fromUser = "fromUser";
        Request request = new Request(fromUser, toUser);
        Assert.assertEquals("ID wasn't as expected!", toUser + fromUser, request.getID());
    }

    public void testGetToUser() {
        String toUser = "toUser";
        String fromUser = "fromUser";
        Request request = new Request(fromUser, toUser);
        Assert.assertEquals("To user wasn't as expected!", toUser, request.getToUser());

    }

    public void testGetFromUser() {
        String toUser = "toUser";
        String fromUser = "fromUser";
        Request request = new Request(fromUser, toUser);
        Assert.assertEquals("From user wasn't as expected!", fromUser, request.getFromUser());
    }
}
