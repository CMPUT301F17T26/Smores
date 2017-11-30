/*
 * HabitAdapter
 *
 * Version 1.0
 *
 * November 25, 2017
 *
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: Adapter class to convert Habit (model objects) to List view items
 * Shows the list of the users habits.
 */

package cmput301f17t26.smores.all_adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_fragments.HabitFragment;
import cmput301f17t26.smores.all_models.Habit;
import cmput301f17t26.smores.all_storage_controller.HabitController;
import cmput301f17t26.smores.dummy.DummyContent.DummyItem;
import cmput301f17t26.smores.utils.ProgressBarUtil;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link cmput301f17t26.smores.all_fragments.HabitFragment.HabitFragmentListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.ViewHolder> {

    private final List<Habit> mValues;
    private final HabitFragment.HabitFragmentListener mListener;

    public HabitAdapter(HabitFragment.HabitFragmentListener listener) {
        mValues = HabitController.getHabitController((Activity) listener).getHabitList();
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_habit_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mItem.calculateStats((Context) mListener);
        HabitController.getHabitController((Context) mListener).saveHabits((Context) mListener);
        holder.mHabitName.setText(mValues.get(position).getTitle());
        holder.mHabitStat.setText(mValues.get(position).getReason());
        int progress = (int) mValues.get(position).getPercentageFollowed();
        holder.mStatNum.setText(Integer.toString(progress) + "%");

        if (holder.mItem.getDaysOfWeek().get(0)) {
            holder.mSunday.setTypeface(null, Typeface.BOLD);
            holder.mSunday.setTextColor(Color.BLACK);
        }
        if (holder.mItem.getDaysOfWeek().get(1)) {
            holder.mMonday.setTypeface(null, Typeface.BOLD);
            holder.mMonday.setTextColor(Color.BLACK);
        }
        if (holder.mItem.getDaysOfWeek().get(2)) {
            holder.mTuesday.setTypeface(null, Typeface.BOLD);
            holder.mTuesday.setTextColor(Color.BLACK);
        }
        if (holder.mItem.getDaysOfWeek().get(3)) {
            holder.mWednesday.setTypeface(null, Typeface.BOLD);
            holder.mWednesday.setTextColor(Color.BLACK);
        }
        if (holder.mItem.getDaysOfWeek().get(4)) {
            holder.mThursday.setTypeface(null, Typeface.BOLD);
            holder.mThursday.setTextColor(Color.BLACK);
        }
        if (holder.mItem.getDaysOfWeek().get(5)) {
            holder.mFriday.setTypeface(null, Typeface.BOLD);
            holder.mFriday.setTextColor(Color.BLACK);
        }
        if (holder.mItem.getDaysOfWeek().get(6)) {
            holder.mSaturday.setTypeface(null, Typeface.BOLD);
            holder.mSaturday.setTextColor(Color.BLACK);
        }

        ProgressBarUtil.DrawBar(progress, holder, holder.mProgress);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onHabitListInteraction(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mHabitName;
        public final TextView mHabitStat;
        public final ImageView mProgress;
        public final TextView mSunday;
        public final TextView mMonday;
        public final TextView mTuesday;
        public final TextView mWednesday;
        public final TextView mThursday;
        public final TextView mFriday;
        public final TextView mSaturday;
        public final TextView mStatNum;
        public Habit mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mHabitName = (TextView) view.findViewById(R.id.habitTitle);
            mHabitStat = (TextView) view.findViewById(R.id.habitStat);
            mProgress = (ImageView) view.findViewById(R.id.HabitProgess);
            mSunday = (TextView) view.findViewById(R.id.Sunday);
            mMonday = (TextView) view.findViewById(R.id.Monday);
            mTuesday = (TextView) view.findViewById(R.id.Tuesday);
            mWednesday = (TextView) view.findViewById(R.id.Wednesday);
            mThursday = (TextView) view.findViewById(R.id.Thursday);
            mFriday = (TextView) view.findViewById(R.id.Friday);
            mSaturday = (TextView) view.findViewById(R.id.Saturday);
            mStatNum = (TextView) view.findViewById(R.id.HabitProgessNum);
        }
    }
}
