package cmput301f17t26.smores.all_adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_exceptions.CommentNotSetException;
import cmput301f17t26.smores.all_fragments.HabitHistoryFragment;
import cmput301f17t26.smores.all_models.HabitEvent;
import cmput301f17t26.smores.all_storage_controller.HabitEventController;
import cmput301f17t26.smores.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;
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
            holder.mTitleView.setText(mFilterValues.get(position).getComment()); // HabitEvent.getTitle() not implemented
        } catch (CommentNotSetException e) {
            holder.mTitleView.setText("No comment"); // HabitEvent.getTitle() not implemented
        }

       // holder.mTitleView.setText("Dummy Title");
        holder.mDateView.setText(mFilterValues.get(position).getDate().toString());
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
        public final TextView mTitleView;
        public final TextView mDateView;
        public HabitEvent mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.Event_Element_hTitle);
            mDateView = (TextView) view.findViewById(R.id.Event_Element_hDate);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }

    public void filter(final String text) {
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
//                        if (item.content.toLowerCase().contains(text.toLowerCase())) { // not implemented getTitle()
//                            // Adding Matched items
                            mFilterValues.add(item);
                            mControllerFilterValue.add(item);
//                        }
                    }
                }

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
