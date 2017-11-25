/*
 * AddFriendFragment
 *
 * Version 1.0
 *
 * November 25, 2017
 *
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: Fragment to send a friend request.
 * Prompts for the username to send a request to.
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

import java.util.UUID;

import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_models.Request;
import cmput301f17t26.smores.all_models.User;
import cmput301f17t26.smores.all_storage_controller.RequestController;
import cmput301f17t26.smores.all_storage_controller.UserController;
import cmput301f17t26.smores.utils.NetworkUtils;

/**
 * Created by rohan on 10/8/2017.
 */

public class AddFriendFragment extends DialogFragment {
    private Button mSendButton;
    private Button mCancelButton;
    private EditText mToUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_dialog, null);
        mSendButton = (Button) view.findViewById(R.id.Request_sendbtn);
        mCancelButton = (Button) view.findViewById(R.id.Request_cancelbtn);
        mToUser = (EditText) view.findViewById(R.id.Request_rID);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSendButton.setEnabled(false);

                if (!NetworkUtils.isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "No Internet connection! Please try again later.", Toast.LENGTH_SHORT).show();
                    return;
                }
                final Context mContext = getActivity();
                final String username = mToUser.getText().toString().trim();

                if (username.equals("")) {
                    Toast.makeText(getActivity(), "Please enter a username!", Toast.LENGTH_SHORT).show();
                    return;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (username.equals(UserController.getUserController(getActivity()).getUser().getUsername())) {

                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "Nice try, but you cannot add yourself!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            return;
                        }

                        UserController.getUserController(getActivity()).updateFollowingList();
                        for (UUID friendUUID: UserController.getUserController(getActivity()).getUser().getFollowingList()) {
                            String friendUsername =  UserController.getUserController(getActivity()).getUsernameByID(friendUUID);
                            if (friendUsername.equals(username)) {

                                ((Activity) mContext).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "You have already added this person!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return;
                            }
                        }

                        if (RequestController.getRequestController(getActivity()).checkIfRequestPending( new Request(UserController.getUserController(getActivity()).getUser().getUsername(),
                                mToUser.getText().toString()))) {
                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "The person has not yet accepted your request!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            return;
                        }

                        if (RequestController.getRequestController(getActivity()).AddRequest(getActivity(),
                                new Request(UserController.getUserController(getActivity()).getUser().getUsername(),
                                        mToUser.getText().toString()))) {

                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "Request sent!", Toast.LENGTH_SHORT).show();
                                    dismiss();
                                }
                            });

                        } else {

                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "User not found!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
                mSendButton.setEnabled(true);
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        setCancelable(false);
        return view;
    }


}
