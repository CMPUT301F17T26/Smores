/*
 * SocialAdapter
 *
 * Version 1.0
 *
 * November 25, 2017
 *
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: Adapter class to convert Feed (model objects) to List view items.
 * Displays for each of your friends their habits and most recent habit event.
 */

package cmput301f17t26.smores.all_adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private RecyclerView mRecyclerView;
    private ImageView mImageView;

    public SocialAdapter(Context context, RecyclerView recyclerView, ImageView imageView) {
        mContext = context;
        mRecyclerView = recyclerView;
        mImageView = imageView;

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
        int progress = (int) mFeed.get(position).getHabit().getPercentageFollowed();

        holder.mProgressNum.setText(progress + " %");
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
            holder.mHabitEventImage.setImageResource(R.mipmap.app_icon_round);
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
        public final ImageView mProgress;
        public final TextView mProgressNum;
        public Feed mFeed;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mUsername = (TextView) view.findViewById(R.id.Username);
            mHabitType = (TextView) view.findViewById(R.id.HabitType);
            mHabitEventComment = (TextView) view.findViewById(R.id.HabitEventComment);
            mHabitEventImage = (ImageView) view.findViewById(R.id.HabitEventImage);
            mProgress = (ImageView) view.findViewById(R.id.HabitEventProgess);
            mProgressNum = (TextView) view.findViewById(R.id.HabitEventProgessNum);
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

                        if (getItemCount() == 0) {
                            mRecyclerView.setVisibility(View.GONE);
                            mImageView.setVisibility(View.VISIBLE);
                        } else {
                            mImageView.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }).start();
    }

}
