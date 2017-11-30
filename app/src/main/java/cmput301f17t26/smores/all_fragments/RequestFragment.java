/*
 * RequestFragment
 *
 * Version 1.0
 *
 * November 25, 2017
 *
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: Fragment to show a list of requests.
 * With the help of the request adapter, shows the list of all of user's friend requests.
 * Provides functionality for swipe to refresh.
 */

package cmput301f17t26.smores.all_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_activities.MainActivity;
import cmput301f17t26.smores.all_adapters.RequestAdapter;
import cmput301f17t26.smores.all_storage_controller.RequestController;
import cmput301f17t26.smores.all_storage_controller.UserController;
import cmput301f17t26.smores.dummy.DummyContent;
import cmput301f17t26.smores.dummy.DummyContent.DummyItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class RequestFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private TextView username;
    private RecyclerView mRecyclerView;
    private ImageView background;
    private RequestAdapter mRequestAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RequestFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RequestFragment newInstance(int columnCount) {
        RequestFragment fragment = new RequestFragment();
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
        View view = inflater.inflate(R.layout.fragment_request_list, container, false);

        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        background = (ImageView) view.findViewById(R.id.RequestBG);
        mRequestAdapter = new RequestAdapter(context, mRecyclerView, background);
        mRecyclerView.setAdapter(mRequestAdapter);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });

        username = (TextView) view.findViewById(R.id.request_username);


        return view;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mRequestAdapter != null) {
            username.setText(UserController.getUserController(getActivity()).getUser().getUsername());
            mRequestAdapter.loadList();
        }
    }

    private void refreshItems() {
        mRequestAdapter.loadList();
        mSwipeRefreshLayout.setRefreshing(false);

        if (mRequestAdapter.getItemCount() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            background.setVisibility(View.VISIBLE);
        } else {
            background.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
