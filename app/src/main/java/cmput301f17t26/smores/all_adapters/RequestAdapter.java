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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_storage_controller.RequestController;
import cmput301f17t26.smores.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    private List<Request> mValues;
    private Context mContext;

    public RequestAdapter(Context context) {
        mValues = RequestController.getRequestController(context).getRequests(context);
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
        holder.mItem = mValues.get(position);
        holder.mUserName.setText(mValues.get(position).getFromUser());

        holder.mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestController.getRequestController(mContext).acceptRequest(mContext, holder.mItem);
            }
        });

        holder.mReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestController.getRequestController(mContext).declineRequest(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mUserName;
        public final Button mAccept;
        public final Button mReject;
        public Request mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mUserName = (TextView) view.findViewById(R.id.request_from_username);
            mAccept = (Button) view.findViewById(R.id.btnAccept);
            mReject = (Button) view.findViewById(R.id.btnReject);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mUserName.getText() + "'";
        }
    }
}
