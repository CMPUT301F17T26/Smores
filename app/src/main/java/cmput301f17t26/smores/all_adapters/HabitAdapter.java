/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: Adapter class to convert Habit (model objects) to List view items
 * Outstanding issues: Make UI prettier (show more info)
 */

package cmput301f17t26.smores.all_adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_fragments.HabitFragment;
import cmput301f17t26.smores.all_fragments.HabitFragment.HabitFragmentListener;
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

        switch (progress) {
            case 0:
                holder.mProgress.setImageResource(R.drawable.progress000); break;
            case 1:
                holder.mProgress.setImageResource(R.drawable.progress001); break;
            case 2:
                holder.mProgress.setImageResource(R.drawable.progress002); break;
            case 3:
                holder.mProgress.setImageResource(R.drawable.progress003); break;
            case 4:
                holder.mProgress.setImageResource(R.drawable.progress004); break;
            case 5:
                holder.mProgress.setImageResource(R.drawable.progress005); break;
            case 6:
                holder.mProgress.setImageResource(R.drawable.progress006); break;
            case 7:
                holder.mProgress.setImageResource(R.drawable.progress007); break;
            case 8:
                holder.mProgress.setImageResource(R.drawable.progress008); break;
            case 9:
                holder.mProgress.setImageResource(R.drawable.progress009); break;
            case 10:
                holder.mProgress.setImageResource(R.drawable.progress010); break;
            case 11:
                holder.mProgress.setImageResource(R.drawable.progress011); break;
            case 12:
                holder.mProgress.setImageResource(R.drawable.progress012); break;
            case 13:
                holder.mProgress.setImageResource(R.drawable.progress013); break;
            case 14:
                holder.mProgress.setImageResource(R.drawable.progress014); break;
            case 15:
                holder.mProgress.setImageResource(R.drawable.progress015); break;
            case 16:
                holder.mProgress.setImageResource(R.drawable.progress016); break;
            case 17:
                holder.mProgress.setImageResource(R.drawable.progress017); break;
            case 18:
                holder.mProgress.setImageResource(R.drawable.progress018); break;
            case 19:
                holder.mProgress.setImageResource(R.drawable.progress019); break;
            case 20:
                holder.mProgress.setImageResource(R.drawable.progress020); break;
            case 21:
                holder.mProgress.setImageResource(R.drawable.progress021); break;
            case 22:
                holder.mProgress.setImageResource(R.drawable.progress022); break;
            case 23:
                holder.mProgress.setImageResource(R.drawable.progress023); break;
            case 24:
                holder.mProgress.setImageResource(R.drawable.progress024); break;
            case 25:
                holder.mProgress.setImageResource(R.drawable.progress025); break;
            case 26:
                holder.mProgress.setImageResource(R.drawable.progress026); break;
            case 27:
                holder.mProgress.setImageResource(R.drawable.progress027); break;
            case 28:
                holder.mProgress.setImageResource(R.drawable.progress028); break;
            case 29:
                holder.mProgress.setImageResource(R.drawable.progress029); break;
            case 30:
                holder.mProgress.setImageResource(R.drawable.progress030); break;
            case 31:
                holder.mProgress.setImageResource(R.drawable.progress031); break;
            case 32:
                holder.mProgress.setImageResource(R.drawable.progress032); break;
            case 33:
                holder.mProgress.setImageResource(R.drawable.progress033); break;
            case 34:
                holder.mProgress.setImageResource(R.drawable.progress034); break;
            case 35:
                holder.mProgress.setImageResource(R.drawable.progress035); break;
            case 36:
                holder.mProgress.setImageResource(R.drawable.progress036); break;
            case 37:
                holder.mProgress.setImageResource(R.drawable.progress037); break;
            case 38:
                holder.mProgress.setImageResource(R.drawable.progress038); break;
            case 39:
                holder.mProgress.setImageResource(R.drawable.progress039); break;
            case 40:
                holder.mProgress.setImageResource(R.drawable.progress040); break;
            case 41:
                holder.mProgress.setImageResource(R.drawable.progress041); break;
            case 42:
                holder.mProgress.setImageResource(R.drawable.progress042); break;
            case 43:
                holder.mProgress.setImageResource(R.drawable.progress043); break;
            case 44:
                holder.mProgress.setImageResource(R.drawable.progress044); break;
            case 45:
                holder.mProgress.setImageResource(R.drawable.progress045); break;
            case 46:
                holder.mProgress.setImageResource(R.drawable.progress046); break;
            case 47:
                holder.mProgress.setImageResource(R.drawable.progress047); break;
            case 48:
                holder.mProgress.setImageResource(R.drawable.progress048); break;
            case 49:
                holder.mProgress.setImageResource(R.drawable.progress049); break;
            case 50:
                holder.mProgress.setImageResource(R.drawable.progress050); break;
            case 51:
                holder.mProgress.setImageResource(R.drawable.progress051); break;
            case 52:
                holder.mProgress.setImageResource(R.drawable.progress052); break;
            case 53:
                holder.mProgress.setImageResource(R.drawable.progress053); break;
            case 54:
                holder.mProgress.setImageResource(R.drawable.progress054); break;
            case 55:
                holder.mProgress.setImageResource(R.drawable.progress055); break;
            case 56:
                holder.mProgress.setImageResource(R.drawable.progress056); break;
            case 57:
                holder.mProgress.setImageResource(R.drawable.progress057); break;
            case 58:
                holder.mProgress.setImageResource(R.drawable.progress058); break;
            case 59:
                holder.mProgress.setImageResource(R.drawable.progress059); break;
            case 60:
                holder.mProgress.setImageResource(R.drawable.progress060); break;
            case 61:
                holder.mProgress.setImageResource(R.drawable.progress061); break;
            case 62:
                holder.mProgress.setImageResource(R.drawable.progress062); break;
            case 63:
                holder.mProgress.setImageResource(R.drawable.progress063); break;
            case 64:
                holder.mProgress.setImageResource(R.drawable.progress064); break;
            case 65:
                holder.mProgress.setImageResource(R.drawable.progress065); break;
            case 66:
                holder.mProgress.setImageResource(R.drawable.progress066); break;
            case 67:
                holder.mProgress.setImageResource(R.drawable.progress067); break;
            case 68:
                holder.mProgress.setImageResource(R.drawable.progress068); break;
            case 69:
                holder.mProgress.setImageResource(R.drawable.progress069); break;
            case 70:
                holder.mProgress.setImageResource(R.drawable.progress070); break;
            case 71:
                holder.mProgress.setImageResource(R.drawable.progress071); break;
            case 72:
                holder.mProgress.setImageResource(R.drawable.progress072); break;
            case 73:
                holder.mProgress.setImageResource(R.drawable.progress073); break;
            case 74:
                holder.mProgress.setImageResource(R.drawable.progress074); break;
            case 75:
                holder.mProgress.setImageResource(R.drawable.progress075); break;
            case 76:
                holder.mProgress.setImageResource(R.drawable.progress076); break;
            case 77:
                holder.mProgress.setImageResource(R.drawable.progress077); break;
            case 78:
                holder.mProgress.setImageResource(R.drawable.progress078); break;
            case 79:
                holder.mProgress.setImageResource(R.drawable.progress079); break;
            case 80:
                holder.mProgress.setImageResource(R.drawable.progress080); break;
            case 81:
                holder.mProgress.setImageResource(R.drawable.progress081); break;
            case 82:
                holder.mProgress.setImageResource(R.drawable.progress082); break;
            case 83:
                holder.mProgress.setImageResource(R.drawable.progress083); break;
            case 84:
                holder.mProgress.setImageResource(R.drawable.progress084); break;
            case 85:
                holder.mProgress.setImageResource(R.drawable.progress085); break;
            case 86:
                holder.mProgress.setImageResource(R.drawable.progress086); break;
            case 87:
                holder.mProgress.setImageResource(R.drawable.progress087); break;
            case 88:
                holder.mProgress.setImageResource(R.drawable.progress088); break;
            case 89:
                holder.mProgress.setImageResource(R.drawable.progress089); break;
            case 90:
                holder.mProgress.setImageResource(R.drawable.progress090); break;
            case 91:
                holder.mProgress.setImageResource(R.drawable.progress091); break;
            case 92:
                holder.mProgress.setImageResource(R.drawable.progress092); break;
            case 93:
                holder.mProgress.setImageResource(R.drawable.progress093); break;
            case 94:
                holder.mProgress.setImageResource(R.drawable.progress094); break;
            case 95:
                holder.mProgress.setImageResource(R.drawable.progress095); break;
            case 96:
                holder.mProgress.setImageResource(R.drawable.progress096); break;
            case 97:
                holder.mProgress.setImageResource(R.drawable.progress097); break;
            case 98:
                holder.mProgress.setImageResource(R.drawable.progress098); break;
            case 99:
                holder.mProgress.setImageResource(R.drawable.progress099); break;
            case 100:
                holder.mProgress.setImageResource(R.drawable.progress100); break;
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
            mSaturday = (TextView) view.findViewById(R.id.Saterday);
        }
    }
}
