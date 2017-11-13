/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 */

package cmput301f17t26.smores.all_adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_fragments.TodayFragment.OnListFragmentInteractionListener;
import cmput301f17t26.smores.all_models.Habit;
import cmput301f17t26.smores.all_models.HabitEvent;
import cmput301f17t26.smores.all_storage_controller.HabitController;
import cmput301f17t26.smores.all_storage_controller.HabitEventController;
import cmput301f17t26.smores.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.ViewHolder> {

    private ArrayList<Habit> mValues;
    private Context mContext;

    public TodayAdapter(Context context) {
        mContext = context;
        mValues = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_habit_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mHabit = mValues.get(position);
        holder.mTitle.setText(mValues.get(position).getTitle());
        holder.mStat.setText(mValues.get(position).getReason()); // TODO change to .getStat()
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitle;
        public final TextView mStat;
        public Habit mHabit;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = (TextView) view.findViewById(R.id.habitTitle);
            mStat = (TextView) view.findViewById(R.id.habitStat);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitle.getText() + "'";
        }
    }

    public void filterTodayHabits()
    {
        mValues.clear();
        Calendar today = Calendar.getInstance();
        for (Habit habit : HabitController.getHabitController(mContext).getHabitList())
            if (habit.getDaysOfWeek().get(today.get(Calendar.DAY_OF_WEEK)-1) == true)
                if (HabitEventController.getHabitEventController(mContext).doesHabitEventExist(habit))
                    mValues.add(habit);
        notifyDataSetChanged();
    }
}
