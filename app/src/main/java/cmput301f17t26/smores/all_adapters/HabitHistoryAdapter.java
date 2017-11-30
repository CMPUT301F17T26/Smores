/*
 * HabitHistoryAdapter
 *
 * Version 1.0
 *
 * November 25, 2017
 *
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: Adapter class to convert Habit Events (model objects) to List view items.
 * Shows the list of the users habit events. Also has a method to filter habit events by filter criteria.
 */

package cmput301f17t26.smores.all_adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_exceptions.CommentNotSetException;
import cmput301f17t26.smores.all_exceptions.ImageNotSetException;
import cmput301f17t26.smores.all_exceptions.LocationNotSetException;
import cmput301f17t26.smores.all_fragments.HabitHistoryFragment;
import cmput301f17t26.smores.all_models.HabitEvent;
import cmput301f17t26.smores.all_storage_controller.HabitController;
import cmput301f17t26.smores.all_storage_controller.HabitEventController;
import cmput301f17t26.smores.dummy.DummyContent.DummyItem;
import cmput301f17t26.smores.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link HabitHistoryFragment.HabitHistoryFragmentListener }.
 * TODO: Replace the implementation with code for your data type.
 */
public class HabitHistoryAdapter extends RecyclerView.Adapter<HabitHistoryAdapter.ViewHolder> {

    private List<HabitEvent> mValues, mFilterValues, mControllerFilterValue;
    private final HabitHistoryFragment.HabitHistoryFragmentListener mListener;
    private Context mContext;

    public HabitHistoryAdapter(HabitHistoryFragment.HabitHistoryFragmentListener listener) {
        mValues = HabitEventController.getHabitEventController((Context) listener).getHabitEvents();
        mControllerFilterValue = HabitEventController.getHabitEventController((Context) listener).getFilteredHabitEvents();
        mListener = listener;
        mFilterValues = new ArrayList<HabitEvent>();
        mFilterValues.addAll(mValues);
        mControllerFilterValue.addAll(mValues);
        mContext = (Context) listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_habithistory_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.mItem = mFilterValues.get(position);

        try {
            holder.mDate.setText(DateUtils.getStringOfDate(mFilterValues.get(position).getDate()));
            if (mFilterValues.get(position).getLocation() != null) {
                holder.mLocation.setImageResource(R.drawable.location);
            }
        } catch (LocationNotSetException e) {
            holder.mLocation.setImageResource(R.color.white);
        }

        try {
            holder.mComment.setText(mFilterValues.get(position).getComment()); // HabitEvent.getTitle() not implemented
        } catch (CommentNotSetException e) {
            holder.mComment.setText("No comment"); // HabitEvent.getTitle() not implemented
        }

        try {
            holder.mImage.setImageBitmap(mFilterValues.get(position).getImage());
        } catch (ImageNotSetException e) {
            holder.mImage.setImageResource(R.mipmap.app_icon);
        }

        holder.mHabitType.setText(HabitController.getHabitController(mContext).getHabitTitleByHabitID(holder.mItem.getHabitID()));

        final UUID uuid = mFilterValues.get(position).getID();
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onHabitEventListInteraction(uuid);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != mFilterValues ? mFilterValues.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mHabitType;
        public final TextView mComment;
        public final TextView mDate;
        public final ImageView mImage;
        public final ImageView mLocation;
        public HabitEvent mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mHabitType = (TextView) view.findViewById(R.id.Event_Element_hTitle);
            mComment = (TextView) view.findViewById(R.id.Event_Element_hComment);
            mDate = (TextView) view.findViewById(R.id.Event_Element_hDate);
            mLocation = (ImageView) view.findViewById(R.id.locationPin);
            mImage = (ImageView) view.findViewById(R.id.HabitEventImage2);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mHabitType.getText() + "'";
        }
    }

    public void filter(final String text, final boolean byHabitType) {
        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {
            @Override
            public void run() {

                // Clear the filter list
                mFilterValues.clear();
                mControllerFilterValue.clear();

                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {

                    mFilterValues.addAll(mValues);
                    mControllerFilterValue.addAll(mValues);


                } else {
                    // Iterate in the original List and add it to filter list...
                    for (HabitEvent item : mValues) {
                        if (byHabitType) {
                            if (HabitController.getHabitController(mContext).getHabitTitleByHabitID(item.getHabitID()).toLowerCase().contains(text.toLowerCase())) {
                                // Adding Matched items
                                mFilterValues.add(item);
                                mControllerFilterValue.add(item);
                            }
                        } else { // by comment
                            try {
                                if (item.getComment().toLowerCase().contains(text.toLowerCase())) {
                                    // Adding Matched items
                                    mFilterValues.add(item);
                                    mControllerFilterValue.add(item);
                                }
                            } catch (CommentNotSetException e) {}
                        }
                    }
                }

                Collections.sort(mFilterValues, new Comparator<HabitEvent>() {
                    @Override
                    public int compare(HabitEvent habitEvent, HabitEvent t1) {
                        return habitEvent.getDate().compareTo(t1.getDate());
                    }
                });
                Collections.reverse(mFilterValues);

                Collections.sort(mControllerFilterValue, new Comparator<HabitEvent>() {
                    @Override
                    public int compare(HabitEvent habitEvent, HabitEvent t1) {
                        return habitEvent.getDate().compareTo(t1.getDate());
                    }
                });
                Collections.reverse(mControllerFilterValue);

                // Set on UI Thread
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
