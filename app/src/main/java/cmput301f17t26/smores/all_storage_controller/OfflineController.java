/*
 * OfflineController
 *
 * Version 1.0
 *
 * November 25, 2017
 *
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: Controller class for storing offline modifications to habit/habit events.
 * executeOnServer() gets called by main activity when connection is available
 */

package cmput301f17t26.smores.all_storage_controller;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import cmput301f17t26.smores.all_models.Pair;

/**
 * Created by rohan on 11/13/2017.
 */

public class OfflineController {

    private static final String FILENAME = "OfflineEvents.sav";
    private static OfflineController mOfflineController = null;
    private ArrayList<Pair> mCommandList;


    public static OfflineController getOfflineController(Context context) {
        if (mOfflineController == null) {
            mOfflineController = new OfflineController(context);
        }
        return mOfflineController;
    }

    private OfflineController(Context context) {
        initOfflineEvents(context);
    }

    private void initOfflineEvents(Context context) {
        retrieveOfflineEvents(context);
    }

    private void retrieveOfflineEvents(Context context) {
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Pair>>(){}.getType();
            mCommandList = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            mCommandList = new ArrayList<Pair>();
        }
    }

    private void saveOfflineEvents(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME,0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(mCommandList, writer);
            writer.flush();
            retrieveOfflineEvents(context);
        } catch (Exception e) {

        }
    }

    public void addPair(Context context, Pair pair) {
        mCommandList.add(pair);
        saveOfflineEvents(context);
    }

    public boolean executeOnServer(Context context) {
        if (mCommandList.size() == 0) {
            return false;
        }
        for (Pair pair: mCommandList) {
            pair.executeTask();
        }
        mCommandList.clear();
        saveOfflineEvents(context);
        return true;
    }
}
