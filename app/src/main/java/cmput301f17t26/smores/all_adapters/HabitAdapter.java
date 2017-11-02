package cmput301f17t26.smores.all_adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        holder.mHabitName.setText(mValues.get(position).getTitle());
        holder.mHabitStat.setText(mValues.get(position).getReason());

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
        Log.d("HABIT ADAPTER", Integer.toString(mValues.size()));
        return mValues.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mHabitName;
        public final TextView mHabitStat;
        public Habit mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mHabitName = (TextView) view.findViewById(R.id.habitTitle);
            mHabitStat = (TextView) view.findViewById(R.id.habitStat);
        }
    }
}
