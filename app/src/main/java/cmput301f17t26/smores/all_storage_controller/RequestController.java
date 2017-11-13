/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 */

package cmput301f17t26.smores.all_storage_controller;

import android.content.Context;

import java.util.ArrayList;

import cmput301f17t26.smores.all_models.Request;
import cmput301f17t26.smores.all_models.User;

/**
 * Created by Christian on 2017-11-13.
 */

public class RequestController {
    private static RequestController mRequestController;
    private ArrayList<Request> mRequests = new ArrayList<Request>();

    private RequestController(Context context) {
        retrieveRequests(context);
    }

    public static RequestController getRequestController(Context context) {
        if (mRequestController == null)
            mRequestController = new RequestController(context);
        return mRequestController;
    }

    public ArrayList<Request> getRequests(Context context) {
        retrieveRequests(context);
        return mRequests;
    }

    boolean VerifyRequest(Context context, Request request) {
        return !UserController.getUserController(context).checkUsername(request.getToUser());
    }

    public boolean AddRequest(Context context, Request request) {
        if (VerifyRequest(context, request)) {
            putRequest(request);
            return true;
        }
        return false;
    }

    private void retrieveRequests(Context context) {
        mRequests.clear();
        ElasticSearchController.GetRequestTask getRequestTask
                = new ElasticSearchController.GetRequestTask();
        getRequestTask.execute("mToUser", UserController.getUserController(context).getUser().getUsername());
        try {
            mRequests.addAll(getRequestTask.get());
        } catch (Exception e) {
        }
    }

    private void putRequest(Request request) {
        ElasticSearchController.AddRequestTask addRequestTask
                = new ElasticSearchController.AddRequestTask();
        addRequestTask.execute(request);
    }

    private void removeRequest(Request request) {
        ElasticSearchController.RemoveRequestTask removeRequestTask
                = new ElasticSearchController.RemoveRequestTask();
        removeRequestTask.execute(request.getID());
        mRequests.remove(request);
    }

    public void acceptRequest(Context context, Request request) {
        User sender;
        ElasticSearchController.CheckUserTask checkUserTask
                = new ElasticSearchController.CheckUserTask();
        checkUserTask.execute("username", request.getFromUser());
        try {
            sender = checkUserTask.get().get(0);
            if (!sender.getFollowingList().contains(UserController.getUserController(context).getUser().getUserID()))
                sender.getFollowingList().add(UserController.getUserController(context).getUser().getUserID());
            ElasticSearchController.UpdateUser updateUser
                    = new ElasticSearchController.UpdateUser();
            updateUser.execute(sender);
        } catch (Exception e) {
        }
        removeRequest(request);
    }

    public void declineRequest(Request request) {
        removeRequest(request);
    }

}
