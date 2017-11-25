/*
 * HabitFragment
 *
 * Version 1.0
 *
 * November 25, 2017
 *
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: With the help of the habit adapter, shows the list of all of the user's habits.
 */

package cmput301f17t26.smores.all_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cmput301f17t26.smores.all_activities.MainActivity;
import cmput301f17t26.smores.all_adapters.HabitAdapter;
import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_storage_controller.HabitController;
import cmput301f17t26.smores.all_storage_controller.UserController;
import cmput301f17t26.smores.dummy.DummyContent;
import cmput301f17t26.smores.dummy.DummyContent.DummyItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link HabitFragmentListener}
 * interface.
 */
public class HabitFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private HabitFragmentListener mListener;
    private HabitAdapter mHabitAdapter;
    private RecyclerView recyclerView;
    private ImageView background;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HabitFragment() {
    }

    // TODO: Customize parameter initialization
    public static HabitFragment newInstance(int columnCount) {
        HabitFragment fragment = new HabitFragment();
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
        View view = inflater.inflate(R.layout.fragment_habit_list, container, false);

        Context context = view.getContext();
        mHabitAdapter = new HabitAdapter(mListener);
        background = (ImageView) view.findViewById(R.id.todayBG);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mHabitAdapter);

        if (mHabitAdapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            background.setVisibility(View.VISIBLE);
        } else {
            background.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HabitFragmentListener) {
            mListener = (HabitFragmentListener) context;
        } else {
            /*throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");*/
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface HabitFragmentListener {
        // TODO: Update argument type and name
        void onHabitListInteraction(int position);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mHabitAdapter != null) {
            mHabitAdapter = new HabitAdapter(mListener);
            recyclerView.setAdapter(mHabitAdapter);
            if (mHabitAdapter.getItemCount() == 0) {
                recyclerView.setVisibility(View.GONE);
                background.setVisibility(View.VISIBLE);
            } else {
                background.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }
    }
}
