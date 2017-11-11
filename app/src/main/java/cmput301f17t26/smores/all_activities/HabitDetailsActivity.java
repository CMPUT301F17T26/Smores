package cmput301f17t26.smores.all_activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
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
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_exceptions.ReasonTooLongException;
import cmput301f17t26.smores.all_exceptions.TitleTooLongException;
import cmput301f17t26.smores.all_models.Habit;
import cmput301f17t26.smores.all_storage_controller.HabitController;
import cmput301f17t26.smores.all_storage_controller.UserController;

public class HabitDetailsActivity extends AppCompatActivity {
    public static final int HABIT_SAVED = 1;
    public static final int HABIT_DELETED = 2;

    private static final int HABIT_POSITION_NONE = -1;
    private static final int DIALOG_ID = 0;

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
    private int mHabitPosition = HABIT_POSITION_NONE;
    private Habit mHabit;

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

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null && bundle.get("habitPosition") != null) {
            mHabitPosition = (int) bundle.get("habitPosition");
        }

        ImageButton deleteButton = (ImageButton) findViewById(R.id.Habit_deleteBtn);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View view) {
                deleteButtonHandler();
            }
        });

        Calendar today = Calendar.getInstance();
        mYear = today.get(Calendar.YEAR);
        mMonth = today.get(Calendar.MONTH);
        mDay = today.get(Calendar.DATE);

        if (mHabitPosition != HABIT_POSITION_NONE) {
            // Fills in fields if user is editing a habit.
            mHabit = HabitController.getHabitController(this).getHabit(mHabitPosition);
            mNameText.setText(mHabit.getTitle());
            mReasonText.setText(mHabit.getReason());

            mYear = mHabit.getStartDate().getYear();
            mMonth = mHabit.getStartDate().getMonth();
            mDay = mHabit.getStartDate().getDate();

            HashMap<Integer, Boolean> days = mHabit.getDaysOfWeek();
            mSunBox.setChecked(days.get(Habit.SUNDAY));
            mMonBox.setChecked(days.get(Habit.MONDAY));
            mTueBox.setChecked(days.get(Habit.TUESDAY));
            mWedBox.setChecked(days.get(Habit.WEDNESDAY));
            mThuBox.setChecked(days.get(Habit.THURSDAY));
            mFriBox.setChecked(days.get(Habit.FRIDAY));
            mSatBox.setChecked(days.get(Habit.SATURDAY));
        }

        mDateSelect.setText(String.format("%d - %d - %d", mYear, mMonth + 1, mDay));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID) {
            return new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    mYear = year;
                    mMonth = month;
                    mDay = day;
                    mDateSelect.setText(String.format("%d-%d-%d", mYear, mMonth + 1, mDay));
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
            if (mHabitPosition != HABIT_POSITION_NONE) {
                saveEdited();
            }
            else {
                Log.d("Habit", "Everything is awesome!");
                saveNew();
            }
        }
        else {
            Toast.makeText(HabitDetailsActivity.this, "Please fill all fields and select at least one day.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void deleteButtonHandler() {
        boolean valid = true;
        if (mHabitPosition == HABIT_POSITION_NONE) {
            Toast.makeText(HabitDetailsActivity.this, "You cannot delete a habit before it has been created!",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            HabitController.getHabitController(this).deleteHabit(this, mHabitPosition);
            Toast.makeText(HabitDetailsActivity.this, "Habit deleted", Toast.LENGTH_SHORT).show();
            setResult(HABIT_DELETED);
            finish();
        }
    }
    private void saveEdited() {
        Date date = mHabit.getStartDate();
        date.setYear(mYear);
        date.setMonth(mMonth);
        date.setDate(mDay);

        HashMap<Integer, Boolean> days = new HashMap<Integer, Boolean>() {{
            put(Habit.SUNDAY, mSunBox.isChecked());
            put(Habit.MONDAY, mMonBox.isChecked());
            put(Habit.TUESDAY, mTueBox.isChecked());
            put(Habit.WEDNESDAY, mWedBox.isChecked());
            put(Habit.THURSDAY, mThuBox.isChecked());
            put(Habit.FRIDAY, mFriBox.isChecked());
            put(Habit.SATURDAY, mSatBox.isChecked());
        }};

        try {
            mHabit.setReason(mReasonText.getText().toString());
            mHabit.setTitle(mNameText.getText().toString());
            mHabit.setStartDate(date);
            mHabit.setDaysOfWeek(days);

        } catch (Exception e) {
            e.printStackTrace();
        }
        HabitController.getHabitController(this).saveHabits(this);
        Toast.makeText(HabitDetailsActivity.this, "Habit saved", Toast.LENGTH_SHORT).show();
        setResult(HABIT_SAVED);
        finish();
    }
    private void saveNew() {
        Date date = new Date();
        date.setYear(mYear);
        date.setMonth(mMonth);
        date.setDate(mDay);

        HashMap<Integer, Boolean> days = new HashMap<Integer, Boolean>() {{
            put(Habit.SUNDAY, mSunBox.isChecked());
            put(Habit.MONDAY, mMonBox.isChecked());
            put(Habit.TUESDAY, mTueBox.isChecked());
            put(Habit.WEDNESDAY, mWedBox.isChecked());
            put(Habit.THURSDAY, mThuBox.isChecked());
            put(Habit.FRIDAY, mFriBox.isChecked());
            put(Habit.SATURDAY, mSatBox.isChecked());
        }};

        try {
            Habit habit = new Habit(
                    UserController.getUserController(this).getUser().getUserID(),
                    mNameText.getText().toString(),
                    mReasonText.getText().toString(),
                    date,
                    days
                    );
            HabitController.getHabitController(this).addHabit(this, habit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(HabitDetailsActivity.this, "Habit saved", Toast.LENGTH_SHORT).show();
        setResult(HABIT_SAVED);
        finish();
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
