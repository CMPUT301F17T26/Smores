/*
 * HabitHistoryFragment
 *
 * Version 1.0
 *
 * November 25, 2017
 *
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: Fragment to host a list of habit events
 * With the help of the habit event adapter, shows the list of all of user's habit events.
 * Provides UI to dynamically filter on habit type or event comment.
 */

package cmput301f17t26.smores.all_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.util.UUID;

import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_activities.MainActivity;
import cmput301f17t26.smores.all_adapters.HabitHistoryAdapter;
import cmput301f17t26.smores.all_storage_controller.HabitEventController;
import cmput301f17t26.smores.dummy.DummyContent;
import cmput301f17t26.smores.dummy.DummyContent.DummyItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link HabitHistoryFragmentListener}
 * interface.
 */
public class HabitHistoryFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private HabitHistoryFragmentListener mListener;
    private EditText mEditText;
    private RadioButton mFilterHabit, mFilterComment;
    private ImageView background;

    private HabitHistoryAdapter mHabitHistoryAdapter;
    private RecyclerView recyclerView;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HabitHistoryFragment() {}

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HabitHistoryFragment newInstance(int columnCount) {
        HabitHistoryFragment fragment = new HabitHistoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_habithistory_list, container, false);

        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.list);

        background = (ImageView) view.findViewById(R.id.EventBG);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mHabitHistoryAdapter = new HabitHistoryAdapter(mListener);
        recyclerView.setAdapter(mHabitHistoryAdapter);
        mEditText = (EditText) view.findViewById(R.id.search);
        mFilterHabit= (RadioButton)  view.findViewById(R.id.FilterHabitButton);
        mFilterComment = (RadioButton)  view.findViewById(R.id.FilterCommentButton);

        if (mHabitHistoryAdapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            background.setVisibility(View.VISIBLE);
        } else {
            background.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mHabitHistoryAdapter.filter(charSequence.toString(), mFilterHabit.isChecked());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mEditText.clearFocus();

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HabitHistoryFragmentListener) {
            mListener = (HabitHistoryFragmentListener) context;
        } else {
            /*throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");*/
        }

;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mHabitHistoryAdapter != null) {
            mHabitHistoryAdapter = new HabitHistoryAdapter(mListener);
            recyclerView.setAdapter(mHabitHistoryAdapter);
            mEditText.setText("");
            mEditText.clearFocus();

            if (mHabitHistoryAdapter.getItemCount() == 0) {
                recyclerView.setVisibility(View.GONE);
                background.setVisibility(View.VISIBLE);
            } else {
                background.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }
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
    public interface HabitHistoryFragmentListener {
        // TODO: Update argument type and name
        void onHabitEventListInteraction(UUID id);
    }

}
