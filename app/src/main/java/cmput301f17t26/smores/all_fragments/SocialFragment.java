/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: Fragment to show the social feed
 * Outstanding issues: To be implemented
 */

package cmput301f17t26.smores.all_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_adapters.SocialAdapter;
import cmput301f17t26.smores.dummy.DummyContent;
import cmput301f17t26.smores.dummy.DummyContent.DummyItem;
import cmput301f17t26.smores.utils.NetworkUtils;

public class SocialFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private SocialAdapter mSocialAdapter;
    private RecyclerView recyclerView;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SocialFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SocialFragment newInstance(int columnCount) {
        SocialFragment fragment = new SocialFragment();
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
        View view = inflater.inflate(R.layout.fragment_social_list, container, false);

        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mSocialAdapter = new SocialAdapter(getActivity());
        recyclerView.setAdapter(mSocialAdapter);

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisbleToUser) {
        super.setUserVisibleHint(isVisbleToUser);
        if (isVisbleToUser && mSocialAdapter != null) {
            mSocialAdapter.loadList();
        }
    }

}
