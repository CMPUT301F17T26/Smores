/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: View class for adding, editing and deleting Habits.
 * Outstanding issues: Provide statistics about a habit.
 */

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_exceptions.ReasonTooLongException;
import cmput301f17t26.smores.all_exceptions.TitleTooLongException;
import cmput301f17t26.smores.all_models.Habit;
import cmput301f17t26.smores.all_models.HabitEvent;
import cmput301f17t26.smores.all_storage_controller.HabitController;
import cmput301f17t26.smores.all_storage_controller.HabitEventController;
import cmput301f17t26.smores.all_storage_controller.UserController;
import cmput301f17t26.smores.utils.DateUtils;

public class HabitDetailsActivity extends AppCompatActivity {
    public static final int HABIT_POSITION_NONE = -1;
    public static final int HABIT_SAVED = 1;
    public static final int HABIT_DELETED = 2;

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
    private TextView mDaysMissedText;
    private TextView mDaysCompletedText;
    private TextView mPercentageText;
    private ImageView mStats;
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
        mStats = (ImageView) findViewById(R.id.HabitEventStat);
        mDaysMissedText = (TextView) findViewById(R.id.Habit_daysMissed);
        mDaysCompletedText = (TextView) findViewById(R.id.Habit_daysCompleted);
        mDaysCompletedText = (TextView) findViewById(R.id.Habit_daysCompleted);
        mPercentageText = (TextView) findViewById(R.id.Habit_percentage);

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

            mYear = mHabit.getStartDate().getYear() + 1900;
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
            mHabit.calculateStats(this);
            mDaysMissedText.setText(mHabit.getDaysMissed().toString());
            mDaysCompletedText.setText(mHabit.getDaysCompleted().toString());
            mPercentageText.setText(String.format("%.2f%%", mHabit.getPercentageFollowed()));

            int progress = (int) mHabit.getPercentageFollowed();

            switch (progress) {
                case 0:
                    mStats.setImageResource(R.drawable.stat000); break;
                case 1:
                    mStats.setImageResource(R.drawable.stat001); break;
                case 2:
                    mStats.setImageResource(R.drawable.stat002); break;
                case 3:
                    mStats.setImageResource(R.drawable.stat003); break;
                case 4:
                    mStats.setImageResource(R.drawable.stat004); break;
                case 5:
                    mStats.setImageResource(R.drawable.stat005); break;
                case 6:
                    mStats.setImageResource(R.drawable.stat006); break;
                case 7:
                    mStats.setImageResource(R.drawable.stat007); break;
                case 8:
                    mStats.setImageResource(R.drawable.stat008); break;
                case 9:
                    mStats.setImageResource(R.drawable.stat009); break;
                case 10:
                    mStats.setImageResource(R.drawable.stat010); break;
                case 11:
                    mStats.setImageResource(R.drawable.stat011); break;
                case 12:
                    mStats.setImageResource(R.drawable.stat012); break;
                case 13:
                    mStats.setImageResource(R.drawable.stat013); break;
                case 14:
                    mStats.setImageResource(R.drawable.stat014); break;
                case 15:
                    mStats.setImageResource(R.drawable.stat015); break;
                case 16:
                    mStats.setImageResource(R.drawable.stat016); break;
                case 17:
                    mStats.setImageResource(R.drawable.stat017); break;
                case 18:
                    mStats.setImageResource(R.drawable.stat018); break;
                case 19:
                    mStats.setImageResource(R.drawable.stat019); break;
                case 20:
                    mStats.setImageResource(R.drawable.stat020); break;
                case 21:
                    mStats.setImageResource(R.drawable.stat021); break;
                case 22:
                    mStats.setImageResource(R.drawable.stat022); break;
                case 23:
                    mStats.setImageResource(R.drawable.stat023); break;
                case 24:
                    mStats.setImageResource(R.drawable.stat024); break;
                case 25:
                    mStats.setImageResource(R.drawable.stat025); break;
                case 26:
                    mStats.setImageResource(R.drawable.stat026); break;
                case 27:
                    mStats.setImageResource(R.drawable.stat027); break;
                case 28:
                    mStats.setImageResource(R.drawable.stat028); break;
                case 29:
                    mStats.setImageResource(R.drawable.stat029); break;
                case 30:
                    mStats.setImageResource(R.drawable.stat030); break;
                case 31:
                    mStats.setImageResource(R.drawable.stat031); break;
                case 32:
                    mStats.setImageResource(R.drawable.stat032); break;
                case 33:
                    mStats.setImageResource(R.drawable.stat033); break;
                case 34:
                    mStats.setImageResource(R.drawable.stat034); break;
                case 35:
                    mStats.setImageResource(R.drawable.stat035); break;
                case 36:
                    mStats.setImageResource(R.drawable.stat036); break;
                case 37:
                    mStats.setImageResource(R.drawable.stat037); break;
                case 38:
                    mStats.setImageResource(R.drawable.stat038); break;
                case 39:
                    mStats.setImageResource(R.drawable.stat039); break;
                case 40:
                    mStats.setImageResource(R.drawable.stat040); break;
                case 41:
                    mStats.setImageResource(R.drawable.stat041); break;
                case 42:
                    mStats.setImageResource(R.drawable.stat042); break;
                case 43:
                    mStats.setImageResource(R.drawable.stat043); break;
                case 44:
                    mStats.setImageResource(R.drawable.stat044); break;
                case 45:
                    mStats.setImageResource(R.drawable.stat045); break;
                case 46:
                    mStats.setImageResource(R.drawable.stat046); break;
                case 47:
                    mStats.setImageResource(R.drawable.stat047); break;
                case 48:
                    mStats.setImageResource(R.drawable.stat048); break;
                case 49:
                    mStats.setImageResource(R.drawable.stat049); break;
                case 50:
                    mStats.setImageResource(R.drawable.stat050); break;
                case 51:
                    mStats.setImageResource(R.drawable.stat051); break;
                case 52:
                    mStats.setImageResource(R.drawable.stat052); break;
                case 53:
                    mStats.setImageResource(R.drawable.stat053); break;
                case 54:
                    mStats.setImageResource(R.drawable.stat054); break;
                case 55:
                    mStats.setImageResource(R.drawable.stat055); break;
                case 56:
                    mStats.setImageResource(R.drawable.stat056); break;
                case 57:
                    mStats.setImageResource(R.drawable.stat057); break;
                case 58:
                    mStats.setImageResource(R.drawable.stat058); break;
                case 59:
                    mStats.setImageResource(R.drawable.stat059); break;
                case 60:
                    mStats.setImageResource(R.drawable.stat060); break;
                case 61:
                    mStats.setImageResource(R.drawable.stat061); break;
                case 62:
                    mStats.setImageResource(R.drawable.stat062); break;
                case 63:
                    mStats.setImageResource(R.drawable.stat063); break;
                case 64:
                    mStats.setImageResource(R.drawable.stat064); break;
                case 65:
                    mStats.setImageResource(R.drawable.stat065); break;
                case 66:
                    mStats.setImageResource(R.drawable.stat066); break;
                case 67:
                    mStats.setImageResource(R.drawable.stat067); break;
                case 68:
                    mStats.setImageResource(R.drawable.stat068); break;
                case 69:
                    mStats.setImageResource(R.drawable.stat069); break;
                case 70:
                    mStats.setImageResource(R.drawable.stat070); break;
                case 71:
                    mStats.setImageResource(R.drawable.stat071); break;
                case 72:
                    mStats.setImageResource(R.drawable.stat072); break;
                case 73:
                    mStats.setImageResource(R.drawable.stat073); break;
                case 74:
                    mStats.setImageResource(R.drawable.stat074); break;
                case 75:
                    mStats.setImageResource(R.drawable.stat075); break;
                case 76:
                    mStats.setImageResource(R.drawable.stat076); break;
                case 77:
                    mStats.setImageResource(R.drawable.stat077); break;
                case 78:
                    mStats.setImageResource(R.drawable.stat078); break;
                case 79:
                    mStats.setImageResource(R.drawable.stat079); break;
                case 80:
                    mStats.setImageResource(R.drawable.stat080); break;
                case 81:
                    mStats.setImageResource(R.drawable.stat081); break;
                case 82:
                    mStats.setImageResource(R.drawable.stat082); break;
                case 83:
                    mStats.setImageResource(R.drawable.stat083); break;
                case 84:
                    mStats.setImageResource(R.drawable.stat084); break;
                case 85:
                    mStats.setImageResource(R.drawable.stat085); break;
                case 86:
                    mStats.setImageResource(R.drawable.stat086); break;
                case 87:
                    mStats.setImageResource(R.drawable.stat087); break;
                case 88:
                    mStats.setImageResource(R.drawable.stat088); break;
                case 89:
                    mStats.setImageResource(R.drawable.stat089); break;
                case 90:
                    mStats.setImageResource(R.drawable.stat090); break;
                case 91:
                    mStats.setImageResource(R.drawable.stat091); break;
                case 92:
                    mStats.setImageResource(R.drawable.stat092); break;
                case 93:
                    mStats.setImageResource(R.drawable.stat093); break;
                case 94:
                    mStats.setImageResource(R.drawable.stat094); break;
                case 95:
                    mStats.setImageResource(R.drawable.stat095); break;
                case 96:
                    mStats.setImageResource(R.drawable.stat096); break;
                case 97:
                    mStats.setImageResource(R.drawable.stat097); break;
                case 98:
                    mStats.setImageResource(R.drawable.stat098); break;
                case 99:
                    mStats.setImageResource(R.drawable.stat099); break;
                case 100:
                    mStats.setImageResource(R.drawable.stat100); break;
                default:
                    mStats.setImageResource(R.drawable.stat000); break;
            }

            mDateSelect.setText(DateUtils.getStringOfDate(mHabit.getStartDate()));

        } else {
            mDateSelect.setText(DateUtils.getStringOfDate(today));
        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID) {
            DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    mYear = year;
                    mMonth = month;
                    mDay = day;
                    Date d = new Date();
                    d.setYear(mYear - 1900);
                    d.setMonth(mMonth);
                    d.setDate(mDay);
                    mDateSelect.setText(DateUtils.getStringOfDate(d));
                }
            }, mYear, mMonth, mDay);

            DatePicker dp = dpd.getDatePicker();

            Calendar minCal = Calendar.getInstance();
            minCal.set(mYear, mMonth, mDay);
            dp.setMinDate(minCal.getTimeInMillis());

            if (HABIT_POSITION_NONE != mHabitPosition) {
                ArrayList<HabitEvent> habitEvents = HabitEventController.getHabitEventController(this)
                        .getHabitEventsByHabit(mHabit);
                if ( 0 != habitEvents.size()) {
                    Calendar maxCal = Calendar.getInstance();
                    maxCal.setTime(habitEvents.get(habitEvents.size() - 1).getDate());
                    dp.setMaxDate(maxCal.getTimeInMillis());
                }
            }

            return dpd;
        }
        return null;
    }

    private void saveButtonHandler() {
        //Validate inputs
        if (mNameText.getText().toString().trim().equals("")) {
            Toast.makeText(HabitDetailsActivity.this, "Please enter a Habit name.", Toast.LENGTH_SHORT).show();
        }
        else if (!HabitController.getHabitController(this).isHabitTitleUnique(
                    mNameText.getText().toString(),
                    mHabitPosition)) {
            Toast.makeText(HabitDetailsActivity.this, "Habit name already in use.", Toast.LENGTH_SHORT).show();
        }
        else if (mReasonText.getText().toString().equals("")) {
            Toast.makeText(HabitDetailsActivity.this, "Please enter a reason", Toast.LENGTH_SHORT).show();
        }
        else if (!checkDaysValid()) {
            Toast.makeText(HabitDetailsActivity.this, "Please select at least 1 day.", Toast.LENGTH_SHORT).show();
        }
        //Save and return
        else {
            if (mHabitPosition != HABIT_POSITION_NONE) {
                saveEdited();
            }
            else {
                saveNew();
            }
        }
    }

    private void deleteButtonHandler() {
        if (mHabitPosition == HABIT_POSITION_NONE) {
            Toast.makeText(HabitDetailsActivity.this, "You cannot delete a habit before it has been created!",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            HabitEventController.getHabitEventController(this).deleteHabitEventsByHabit(this, mHabit.getID());
            HabitController.getHabitController(this).deleteHabit(this, mHabit);
            Toast.makeText(HabitDetailsActivity.this, "Habit deleted", Toast.LENGTH_SHORT).show();
            setResult(HABIT_DELETED);
            finish();
        }
    }
    private void saveEdited() {
        Date date = mHabit.getStartDate();
        date.setYear(mYear - 1900);
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
        HabitController.getHabitController(this).updateHabit(this, mHabit);
        Toast.makeText(HabitDetailsActivity.this, "Habit saved", Toast.LENGTH_SHORT).show();
        setResult(HABIT_SAVED);
        finish();
    }
    private void saveNew() {
        Date date = new Date();
        date.setYear(mYear - 1900);
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
