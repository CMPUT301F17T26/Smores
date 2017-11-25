/*
 * AddUserFragment
 *
 * Version 1.0
 *
 * November 25, 2017
 *
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: Fragment to add a new user.
 * Prompts the user for a username.
 */

package cmput301f17t26.smores.all_fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_models.User;
import cmput301f17t26.smores.all_storage_controller.UserController;
import cmput301f17t26.smores.utils.NotificationReceiver;

/**
 * Created by apate on 2017-10-31.
 */

public class AddUserFragment extends DialogFragment {
    private Button mCheckButton;
    private EditText mUserName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adduser, null);
        mCheckButton = (Button) view.findViewById(R.id.Request_checkbtn);
        mUserName = (EditText) view.findViewById(R.id.Request_username);

        mCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context mContext = getActivity();
                final UserController userController = UserController.getUserController(getActivity());
                final String username = mUserName.getText().toString().trim();
                if (username.equals("")) {
                    Toast.makeText(getActivity(), "Please enter a username!", Toast.LENGTH_SHORT).show();
                    return;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (userController.addUser(mContext, new User(username))) {
                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(mContext, "Added!", Toast.LENGTH_SHORT).show();
                                    NotificationReceiver.setUpNotifcations(mContext);

                                    dismiss();
                                }
                            });
                        } else {
                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(mContext, "Another user already has your username! Please try again", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                }).start();
            }
        });
        setCancelable(false);
        return view;
    }
}
