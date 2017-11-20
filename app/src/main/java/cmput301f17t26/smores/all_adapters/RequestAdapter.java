/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: Adapter class to show a list of requests
 * Outstanding issues: To be implemented
 */

package cmput301f17t26.smores.all_adapters;

import cmput301f17t26.smores.all_models.*;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_storage_controller.RequestController;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    private List<Request> mRequests;
    private Context mContext;

    public RequestAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_request_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mRequest = mRequests.get(position);
        holder.mUserName.setText(mRequests.get(position).getFromUser());

        holder.mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestController.getRequestController(mContext).acceptRequest(mContext, holder.mRequest);
                notifyDataSetChanged();
            }
        });

        holder.mReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestController.getRequestController(mContext).declineRequest(holder.mRequest);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mRequests == null) {
            return 0;
        }
        return mRequests.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mUserName;
        public final ImageButton mAccept;
        public final ImageButton mReject;
        public Request mRequest;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mUserName = (TextView) view.findViewById(R.id.request_from_username);
            mAccept = (ImageButton) view.findViewById(R.id.btnAccept);
            mReject = (ImageButton) view.findViewById(R.id.btnReject);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mUserName.getText() + "'";
        }
    }

    public void loadList() {
        // Searching could be complex..so we will dispatch it to a different thread...

        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Now loading requests...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {

                mRequests = RequestController.getRequestController(mContext).getRequests(mContext);
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
