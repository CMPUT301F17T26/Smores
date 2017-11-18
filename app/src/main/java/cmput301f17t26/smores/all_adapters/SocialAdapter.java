/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: Adapter class to show a social feed
 * Outstanding issues: To be implemented
 */

package cmput301f17t26.smores.all_adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_exceptions.CommentNotSetException;
import cmput301f17t26.smores.all_exceptions.ImageNotSetException;
import cmput301f17t26.smores.all_models.Feed;
import cmput301f17t26.smores.all_models.HabitEvent;
import cmput301f17t26.smores.all_storage_controller.UserController;

import java.util.List;

public class SocialAdapter extends RecyclerView.Adapter<SocialAdapter.ViewHolder> {

    private List<Feed> mFeed;
    private Context mContext;

    public SocialAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_social_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mFeed = mFeed.get(position);
        holder.mUsername.setText(mFeed.get(position).getUsername());
        holder.mHabitType.setText(mFeed.get(position).getHabit().getTitle());

        HabitEvent habitEvent = mFeed.get(position).getHabitEvent();
        if (habitEvent != null) {
            try {
                holder.mHabitEventComment.setText(habitEvent.getComment());
                holder.mHabitEventImage.setImageBitmap(habitEvent.getImage());
            } catch (CommentNotSetException e) {
                holder.mHabitEventComment.setText("No comment avaliable.");
            } catch (ImageNotSetException e) {
                holder.mHabitEventImage.setImageResource(R.mipmap.app_icon_round);
            }
        } else {
            holder.mHabitEventComment.setText(mFeed.get(position).getHabit().getReason());
        }

    }

    @Override
    public int getItemCount() {
        if (mFeed == null) {
            return 0;
        }
        return mFeed.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mUsername;
        public final TextView mHabitType;
        public final TextView mHabitEventComment;
        public final ImageView mHabitEventImage;
        public Feed mFeed;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mUsername = (TextView) view.findViewById(R.id.Username);
            mHabitType = (TextView) view.findViewById(R.id.HabitType);
            mHabitEventComment = (TextView) view.findViewById(R.id.HabitEventComment);
            mHabitEventImage = (ImageView) view.findViewById(R.id.HabitEventImage);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mHabitType.getText() + "'";
        }
    }


    public void loadList() {
        // Searching could be complex..so we will dispatch it to a different thread...

        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Now loading feed...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        new Thread(new Runnable() {
            @Override
            public void run() {

                UserController.getUserController(mContext).updateFollowingList();
                mFeed = UserController.getUserController(mContext).getFeed();
                // Set on UI Thread
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                });
            }
        }).start();
    }

}
