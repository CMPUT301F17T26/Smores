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

        switch (progress) {
            case 0:
                holder.mProgress.setImageResource(R.drawable.bar000); break;
            case 1:
                holder.mProgress.setImageResource(R.drawable.bar001); break;
            case 2:
                holder.mProgress.setImageResource(R.drawable.bar002); break;
            case 3:
                holder.mProgress.setImageResource(R.drawable.bar003); break;
            case 4:
                holder.mProgress.setImageResource(R.drawable.bar004); break;
            case 5:
                holder.mProgress.setImageResource(R.drawable.bar005); break;
            case 6:
                holder.mProgress.setImageResource(R.drawable.bar006); break;
            case 7:
                holder.mProgress.setImageResource(R.drawable.bar007); break;
            case 8:
                holder.mProgress.setImageResource(R.drawable.bar008); break;
            case 9:
                holder.mProgress.setImageResource(R.drawable.bar009); break;
            case 10:
                holder.mProgress.setImageResource(R.drawable.bar010); break;
            case 11:
                holder.mProgress.setImageResource(R.drawable.bar011); break;
            case 12:
                holder.mProgress.setImageResource(R.drawable.bar012); break;
            case 13:
                holder.mProgress.setImageResource(R.drawable.bar013); break;
            case 14:
                holder.mProgress.setImageResource(R.drawable.bar014); break;
            case 15:
                holder.mProgress.setImageResource(R.drawable.bar015); break;
            case 16:
                holder.mProgress.setImageResource(R.drawable.bar016); break;
            case 17:
                holder.mProgress.setImageResource(R.drawable.bar017); break;
            case 18:
                holder.mProgress.setImageResource(R.drawable.bar018); break;
            case 19:
                holder.mProgress.setImageResource(R.drawable.bar019); break;
            case 20:
                holder.mProgress.setImageResource(R.drawable.bar020); break;
            case 21:
                holder.mProgress.setImageResource(R.drawable.bar021); break;
            case 22:
                holder.mProgress.setImageResource(R.drawable.bar022); break;
            case 23:
                holder.mProgress.setImageResource(R.drawable.bar023); break;
            case 24:
                holder.mProgress.setImageResource(R.drawable.bar024); break;
            case 25:
                holder.mProgress.setImageResource(R.drawable.bar025); break;
            case 26:
                holder.mProgress.setImageResource(R.drawable.bar026); break;
            case 27:
                holder.mProgress.setImageResource(R.drawable.bar027); break;
            case 28:
                holder.mProgress.setImageResource(R.drawable.bar028); break;
            case 29:
                holder.mProgress.setImageResource(R.drawable.bar029); break;
            case 30:
                holder.mProgress.setImageResource(R.drawable.bar030); break;
            case 31:
                holder.mProgress.setImageResource(R.drawable.bar031); break;
            case 32:
                holder.mProgress.setImageResource(R.drawable.bar032); break;
            case 33:
                holder.mProgress.setImageResource(R.drawable.bar033); break;
            case 34:
                holder.mProgress.setImageResource(R.drawable.bar034); break;
            case 35:
                holder.mProgress.setImageResource(R.drawable.bar035); break;
            case 36:
                holder.mProgress.setImageResource(R.drawable.bar036); break;
            case 37:
                holder.mProgress.setImageResource(R.drawable.bar037); break;
            case 38:
                holder.mProgress.setImageResource(R.drawable.bar038); break;
            case 39:
                holder.mProgress.setImageResource(R.drawable.bar039); break;
            case 40:
                holder.mProgress.setImageResource(R.drawable.bar040); break;
            case 41:
                holder.mProgress.setImageResource(R.drawable.bar041); break;
            case 42:
                holder.mProgress.setImageResource(R.drawable.bar042); break;
            case 43:
                holder.mProgress.setImageResource(R.drawable.bar043); break;
            case 44:
                holder.mProgress.setImageResource(R.drawable.bar044); break;
            case 45:
                holder.mProgress.setImageResource(R.drawable.bar045); break;
            case 46:
                holder.mProgress.setImageResource(R.drawable.bar046); break;
            case 47:
                holder.mProgress.setImageResource(R.drawable.bar047); break;
            case 48:
                holder.mProgress.setImageResource(R.drawable.bar048); break;
            case 49:
                holder.mProgress.setImageResource(R.drawable.bar049); break;
            case 50:
                holder.mProgress.setImageResource(R.drawable.bar050); break;
            case 51:
                holder.mProgress.setImageResource(R.drawable.bar051); break;
            case 52:
                holder.mProgress.setImageResource(R.drawable.bar052); break;
            case 53:
                holder.mProgress.setImageResource(R.drawable.bar053); break;
            case 54:
                holder.mProgress.setImageResource(R.drawable.bar054); break;
            case 55:
                holder.mProgress.setImageResource(R.drawable.bar055); break;
            case 56:
                holder.mProgress.setImageResource(R.drawable.bar056); break;
            case 57:
                holder.mProgress.setImageResource(R.drawable.bar057); break;
            case 58:
                holder.mProgress.setImageResource(R.drawable.bar058); break;
            case 59:
                holder.mProgress.setImageResource(R.drawable.bar059); break;
            case 60:
                holder.mProgress.setImageResource(R.drawable.bar060); break;
            case 61:
                holder.mProgress.setImageResource(R.drawable.bar061); break;
            case 62:
                holder.mProgress.setImageResource(R.drawable.bar062); break;
            case 63:
                holder.mProgress.setImageResource(R.drawable.bar063); break;
            case 64:
                holder.mProgress.setImageResource(R.drawable.bar064); break;
            case 65:
                holder.mProgress.setImageResource(R.drawable.bar065); break;
            case 66:
                holder.mProgress.setImageResource(R.drawable.bar066); break;
            case 67:
                holder.mProgress.setImageResource(R.drawable.bar067); break;
            case 68:
                holder.mProgress.setImageResource(R.drawable.bar068); break;
            case 69:
                holder.mProgress.setImageResource(R.drawable.bar069); break;
            case 70:
                holder.mProgress.setImageResource(R.drawable.bar070); break;
            case 71:
                holder.mProgress.setImageResource(R.drawable.bar071); break;
            case 72:
                holder.mProgress.setImageResource(R.drawable.bar072); break;
            case 73:
                holder.mProgress.setImageResource(R.drawable.bar073); break;
            case 74:
                holder.mProgress.setImageResource(R.drawable.bar074); break;
            case 75:
                holder.mProgress.setImageResource(R.drawable.bar075); break;
            case 76:
                holder.mProgress.setImageResource(R.drawable.bar076); break;
            case 77:
                holder.mProgress.setImageResource(R.drawable.bar077); break;
            case 78:
                holder.mProgress.setImageResource(R.drawable.bar078); break;
            case 79:
                holder.mProgress.setImageResource(R.drawable.bar079); break;
            case 80:
                holder.mProgress.setImageResource(R.drawable.bar080); break;
            case 81:
                holder.mProgress.setImageResource(R.drawable.bar081); break;
            case 82:
                holder.mProgress.setImageResource(R.drawable.bar082); break;
            case 83:
                holder.mProgress.setImageResource(R.drawable.bar083); break;
            case 84:
                holder.mProgress.setImageResource(R.drawable.bar084); break;
            case 85:
                holder.mProgress.setImageResource(R.drawable.bar085); break;
            case 86:
                holder.mProgress.setImageResource(R.drawable.bar086); break;
            case 87:
                holder.mProgress.setImageResource(R.drawable.bar087); break;
            case 88:
                holder.mProgress.setImageResource(R.drawable.bar088); break;
            case 89:
                holder.mProgress.setImageResource(R.drawable.bar089); break;
            case 90:
                holder.mProgress.setImageResource(R.drawable.bar090); break;
            case 91:
                holder.mProgress.setImageResource(R.drawable.bar091); break;
            case 92:
                holder.mProgress.setImageResource(R.drawable.bar092); break;
            case 93:
                holder.mProgress.setImageResource(R.drawable.bar093); break;
            case 94:
                holder.mProgress.setImageResource(R.drawable.bar094); break;
            case 95:
                holder.mProgress.setImageResource(R.drawable.bar095); break;
            case 96:
                holder.mProgress.setImageResource(R.drawable.bar096); break;
            case 97:
                holder.mProgress.setImageResource(R.drawable.bar097); break;
            case 98:
                holder.mProgress.setImageResource(R.drawable.bar098); break;
            case 99:
                holder.mProgress.setImageResource(R.drawable.bar099); break;
            case 100:
                holder.mProgress.setImageResource(R.drawable.bar100); break;
            default:
                holder.mProgress.setImageResource(R.drawable.errorbar); break;
        }

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
