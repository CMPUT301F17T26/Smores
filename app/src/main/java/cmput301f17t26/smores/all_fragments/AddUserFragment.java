package cmput301f17t26.smores.all_fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_models.User;
import cmput301f17t26.smores.all_storage_controller.UserController;

/**
 * Created by apate on 2017-10-31.
 */

public class AddUserFragment extends DialogFragment {
    private Button mCheckButton;
    private EditText mUserName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adduser, null);
        mCheckButton = (Button) view.findViewById(R.id.Request_checkbtn);
        mUserName = (EditText) view.findViewById(R.id.Request_username);

        mCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserController userController = UserController.getUserController(getActivity());

                if (userController.addUser(getActivity(), new User(mUserName.getText().toString()))) { //we were able to add a user
                    Toast.makeText(getActivity(), "Added!", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "Another user already has your username! Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setCancelable(false);
        return view;
    }
}
