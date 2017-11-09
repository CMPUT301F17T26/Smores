package cmput301f17t26.smores.all_activities;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import cmput301f17t26.smores.R;

public class HabitEventDetailsActivity extends AppCompatActivity {
    private Spinner mHabitType;
    private EditText mComment;
    private TextView mDateCompleted;
    private ToggleButton mToggleLocation;
    private ImageButton mImageButton;
    private ImageView mImageView;
    private ImageButton mSave;
    private ImageButton mDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mHabitType = (Spinner) findViewById(R.id.Event_hType);
        mComment = (EditText) findViewById(R.id.Event_hComment);
        mDateCompleted = (TextView) findViewById(R.id.Event_hDate);
        mToggleLocation = (ToggleButton) findViewById(R.id.Event_hToggleButton);
        mImageButton = (ImageButton) findViewById(R.id.Event_hImagebtn);
        mImageView = (ImageView) findViewById((R.id.Event_hImage));
        mSave = (ImageButton) findViewById(R.id.Event_hSave);
        mDelete = (ImageButton) findViewById(R.id.Event_hDelete);
    }

    
}
