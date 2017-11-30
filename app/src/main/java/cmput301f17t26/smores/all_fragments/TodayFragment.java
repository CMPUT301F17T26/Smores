/*
 * TodayFragment
 *
 * Version 1.0
 *
 * November 25, 2017
 *
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: With the help of the today adapter, shows a list of all habits to do today
 * Provides a clean icon to show if you have completed the habit for the day, or if its still pending.
 */

package cmput301f17t26.smores.all_fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_adapters.TodayAdapter;
import cmput301f17t26.smores.dummy.DummyContent.DummyItem;
import cmput301f17t26.smores.utils.NotificationReceiver;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TodayFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private TodayAdapter mTodayAdapter;
    private RecyclerView recyclerView;
    private TextView mNotificationTimeLabel;
    private ImageButton mNotificationButton;
    private CheckBox mNotificationEnable;
    private TextView mNotificationLabel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TodayFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TodayFragment newInstance(int columnCount) {
        TodayFragment fragment = new TodayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today_list, container, false);

        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mTodayAdapter = new TodayAdapter(getActivity());
        recyclerView.setAdapter(mTodayAdapter);
        mTodayAdapter.filterTodayHabits();
        mNotificationTimeLabel = (TextView) view.findViewById(R.id.notification_time_label);
        mNotificationButton = (ImageButton) view.findViewById(R.id.notification_button);
        mNotificationEnable = (CheckBox) view.findViewById(R.id.enable_notifications);
        mNotificationLabel = (TextView)  view.findViewById(R.id.notification_label);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = pref.edit();

        if (pref.getInt("hour", -1) != -1) {
            mNotificationEnable.setChecked(true);
            mNotificationLabel.setVisibility(View.VISIBLE);
            mNotificationButton.setVisibility(View.VISIBLE);
            String notificationTime = Integer.toString(pref.getInt("hour", 8)) + ":" + Integer.toString(pref.getInt("minute", 30));
            mNotificationTimeLabel.setText(notificationTime);

        }

        mNotificationEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    mNotificationTimeLabel.setText("8:30");
                    mNotificationLabel.setVisibility(View.VISIBLE);
                    mNotificationButton.setVisibility(View.VISIBLE);
                    editor.putInt("hour", 8);
                    editor.putInt("minute", 30);
                }
                else {
                    mNotificationTimeLabel.setText("");
                    mNotificationLabel.setVisibility(View.GONE);
                    mNotificationButton.setVisibility(View.GONE);
                    editor.putInt("hour", -1);
                }
                editor.commit();
                NotificationReceiver.setUpNotifcations(getActivity());
            }
        });

        mNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            /*throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");*/
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mTodayAdapter != null) {
            mTodayAdapter = new TodayAdapter(getActivity());
            recyclerView.setAdapter(mTodayAdapter);
            mTodayAdapter.filterTodayHabits();
        }
        mTodayAdapter.filterTodayHabits();
    }

    public void setTimeLabel(int hourOfDay, int minute) {
        String text = Integer.toString(hourOfDay) + ":" + Integer.toString(minute);
        mNotificationTimeLabel.setText(text);
    }

}
