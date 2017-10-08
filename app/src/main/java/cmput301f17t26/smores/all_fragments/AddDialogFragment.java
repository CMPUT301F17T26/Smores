package cmput301f17t26.smores.all_fragments;

import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cmput301f17t26.smores.R;

/**
 * Created by rohan on 10/8/2017.
 */

public class AddDialogFragment extends DialogFragment {
    private Button mSendButton;
    private Button mCancelButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_dialog, null);
        mSendButton = (Button) view.findViewById(R.id.Request_sendbtn);
        mCancelButton = (Button) view.findViewById(R.id.Request_cancelbtn);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        setCancelable(false);
        return view;
    }


}
