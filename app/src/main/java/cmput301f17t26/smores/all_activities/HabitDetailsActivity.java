package cmput301f17t26.smores.all_activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;

import cmput301f17t26.smores.R;

public class HabitDetailsActivity extends AppCompatActivity {

    private Button mDateSelect;
    private EditText mNameText;
    private EditText mReasonText;
    private CheckBox mSunBox;
    private CheckBox mMonBox;
    private CheckBox mTueBox;
    private CheckBox mWedBox;
    private CheckBox mThuBox;
    private CheckBox mFriBox;
    private CheckBox mSatBox;
    private int mYear;
    private int mMonth;
    private int mDay;
    private static final int DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNameText = (EditText) findViewById(R.id.Habit_hName);
        mReasonText = (EditText) findViewById(R.id.Habit_hReason);
        mSunBox = (CheckBox) findViewById(R.id.Habit_sun);
        mMonBox = (CheckBox) findViewById(R.id.Habit_mon);
        mTueBox = (CheckBox) findViewById(R.id.Habit_tue);
        mWedBox = (CheckBox) findViewById(R.id.Habit_wed);
        mThuBox = (CheckBox) findViewById(R.id.Habit_thu);
        mFriBox = (CheckBox) findViewById(R.id.Habit_fri);
        mSatBox = (CheckBox) findViewById(R.id.Habit_sat);

        mDateSelect = (Button) findViewById(R.id.Habit_dateBtn);
        mDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_ID);
            }
        });

        ImageButton saveButton = (ImageButton) findViewById(R.id.Habit_saveBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveButtonHandler();
            }
        });


        //Get index
        //if index exists, get current date

        //else
        Calendar today = Calendar.getInstance();
        mYear = today.get(Calendar.YEAR);
        mMonth = today.get(Calendar.MONTH);
        mDay = today.get(Calendar.DATE);

        mDateSelect.setText(String.format("%d - %d - %d", mYear, mMonth, mDay));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID) {
            Calendar today = Calendar.getInstance();
            return new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    mYear = year;
                    mMonth = month;
                    mDay = day;
                    mDateSelect.setText(String.format("%d-%d-%d", mYear, mMonth, mDay));
                }
            }, mYear, mMonth, mDay);
        }
        return null;
    }

    private void saveButtonHandler() {
        boolean valid = true;
        //Validate inputs
        if (mNameText.getText().toString().equals("")) {
            Log.d("Habit", "No title");
            valid = false;
        }
        if (mReasonText.getText().toString().equals("")) {
            Log.d("Habit", "No reason");
            valid = false;
        }
        if (!checkDaysValid()) {
            Log.d("Habit", "No days");
            valid = false;
        }

        //Save and return
        if (valid) {
            Log.d("Habit", "Everything is awesome!");
        }
    }

    private boolean checkDaysValid() {
        if (   !mSunBox.isChecked()
            && !mMonBox.isChecked()
            && !mTueBox.isChecked()
            && !mWedBox.isChecked()
            && !mThuBox.isChecked()
            && !mFriBox.isChecked()
            && !mSatBox.isChecked()
        ) {
            return false;
        }
        return true;
    }

}
