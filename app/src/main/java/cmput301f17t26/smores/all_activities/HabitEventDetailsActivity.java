package cmput301f17t26.smores.all_activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.UUID;

import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_exceptions.CommentNotSetException;
import cmput301f17t26.smores.all_exceptions.CommentTooLongException;
import cmput301f17t26.smores.all_exceptions.ImageNotSetException;
import cmput301f17t26.smores.all_exceptions.ImageTooBigException;
import cmput301f17t26.smores.all_exceptions.LocationNotSetException;
import cmput301f17t26.smores.all_models.Habit;
import cmput301f17t26.smores.all_models.HabitEvent;
import cmput301f17t26.smores.all_storage_controller.HabitController;
import cmput301f17t26.smores.all_storage_controller.HabitEventController;
import cmput301f17t26.smores.all_storage_controller.UserController;

public class HabitEventDetailsActivity extends AppCompatActivity {
    public static final int CAMERA_REQUEST_CODE = 0;
    public static final int LOCATION_REQUEST_CODE = 2;
    public static final int CAMERA_REQUEST = 1;
    private Spinner mHabitType;
    private TextView mHabitType_Fixed;
    private EditText mComment;
    private TextView mDateCompleted;
    private ToggleButton mToggleLocation;
    private ImageButton mImageButton;
    private ImageView mImageView;
    private ImageButton mSave;
    private ImageButton mDelete;
    private FusedLocationProviderClient mFusedLocationClient;
    private UUID mHabitEventUUID;
    private HabitEvent mHabitEvent;
    private Location mLocation;
    private Bitmap mImage;

    private ArrayList<Habit> mHabitList;
    private ArrayAdapter<String> spinnerDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mHabitType = (Spinner) findViewById(R.id.Event_hType);
        mHabitType_Fixed = (TextView) findViewById(R.id.Event_hTypeFixed);
        mComment = (EditText) findViewById(R.id.Event_hComment);
        mDateCompleted = (TextView) findViewById(R.id.Event_hDate);
        mToggleLocation = (ToggleButton) findViewById(R.id.Event_hToggleButton);
        mImageButton = (ImageButton) findViewById(R.id.Event_hImagebtn);
        mImageView = (ImageView) findViewById((R.id.Event_hImage));
        mSave = (ImageButton) findViewById(R.id.Event_hSave);
        mDelete = (ImageButton) findViewById(R.id.Event_hDelete);
        mHabitList = HabitController.getHabitController(this).getHabitList();

        mHabitType.setVisibility(View.GONE);
        mHabitType_Fixed.setVisibility(View.GONE);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveButtonHandler();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null && bundle.get("habitEventPosition") != null) {

            mHabitEventUUID = (UUID) bundle.get("habitEventPosition");
        }

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteButtonHandler();
            }
        });

        if (mHabitEventUUID != null) {
            mHabitEvent = HabitEventController.getHabitEventController(this).getHabitEvent(mHabitEventUUID);

            mHabitType_Fixed.setVisibility(View.VISIBLE);
            mHabitType_Fixed.setText(HabitController.getHabitController(this).getHabit(mHabitEvent.getHabitID()).getTitle());
            mDateCompleted.setText(String.format("%d - %d - %d", mHabitEvent.getDate().getYear() + 1900, mHabitEvent.getDate().getMonth() + 1, mHabitEvent.getDate().getDay()));
            try {
                mComment.setText(mHabitEvent.getComment());
            } catch (CommentNotSetException e) {
            }
            try {
                mHabitEvent.getLocation();
                Log.d("Details act", "Location is set!!");
                mToggleLocation.setChecked(true);
            } catch (LocationNotSetException e) {
                Log.d("Details act", "Location is not set!!");
            }
            try {
                mImageView.setImageBitmap(HabitEvent.decompressBitmap(mHabitEvent.getImage()));
                mImage = mHabitEvent.getImage();
            } catch (ImageNotSetException e) {
            }

        } else {
            mHabitType.setVisibility(View.VISIBLE);
            loadSpinner();
        }

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    invokeCamera();
                } else {
                    String[] permissionRequested = {Manifest.permission.CAMERA};
                    requestPermissions(permissionRequested, CAMERA_REQUEST_CODE);
                }
            }
        });
        mToggleLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if (isChecked) {
                        getLocation();
                    } else {
                        mLocation = null;
                    }
                } else {
                    String[] permissionRequested = {Manifest.permission.ACCESS_COARSE_LOCATION};
                    requestPermissions(permissionRequested, LOCATION_REQUEST_CODE);
                }
            }
        });
    }

    private void deleteButtonHandler() {
        if (mHabitEventUUID == null) {
            Toast.makeText(HabitEventDetailsActivity.this, "You cannot delete a habit event before it has been created!", Toast.LENGTH_SHORT).show();
        } else {
            HabitEventController.getHabitEventController(this).deleteHabitEvent(this, mHabitEventUUID);
            finish();
        }
    }

    private void saveButtonHandler() {
        if (mHabitEventUUID == null) {
            String title = null;
            try {
                title = mHabitType.getSelectedItem().toString();
            } catch (NullPointerException e) {
                Toast.makeText(HabitEventDetailsActivity.this, "Please select a Habit Type!", Toast.LENGTH_SHORT).show();
                return;
            }
            Log.d("Testing1", "Here I am!");
            HabitEvent habitEvent = new HabitEvent(UserController.getUserController(this).getUser().getUserID(),
                    HabitController.getHabitController(this).getHabitIDByTitle(title));

            Log.d("Testing3", "Here I am!");
            if (!mComment.getText().toString().equals("")) {
                try {
                    habitEvent.setComment(mComment.getText().toString());
                } catch (CommentTooLongException e) {
                }
            }
            if (mToggleLocation.isChecked())
                habitEvent.setLocation(mLocation);
            if (mImageView.getDrawable() != null) {
                try {
                    habitEvent.setImage(mImage);
                } catch (ImageTooBigException e) {
                }
            }

            HabitEventController.getHabitEventController(this).addHabitEvent(this, habitEvent);
            finish();
        } else {
            if (!mComment.getText().toString().equals("")) {
                try {
                    mHabitEvent.setComment(mComment.getText().toString());
                } catch (CommentTooLongException e) {
                }
            }
            if (mToggleLocation.isChecked()) {
                if (mLocation != null)
                    mHabitEvent.setLocation(mLocation);
            } else {
                mHabitEvent.setLocation(null);
            }
            if (mImageView.getDrawable() != null) {
                try {
                    mHabitEvent.setImage(mImage);
                } catch (ImageTooBigException e) {
                }
            }

            HabitEventController.getHabitEventController(this).updateHabitEvent(this, mHabitEvent);
            finish();
        }
    }

    public void loadSpinner() {
        ArrayList<Habit> availableHabits = new ArrayList<>();
        availableHabits.addAll(mHabitList);
        ArrayList<String> stringAvailableHabits = new ArrayList<>();
        for (Habit habit : availableHabits) {
            if (HabitEventController.getHabitEventController(this).doesHabitEventExist(habit)) {
                stringAvailableHabits.add(habit.getTitle());
            }
        }
        spinnerDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stringAvailableHabits);
        spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mHabitType.setAdapter(spinnerDataAdapter);

    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                invokeCamera();
            } else {
                Toast.makeText(this, "Unable to request camera", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Request for location granted", Toast.LENGTH_LONG).show();
                getLocation();
            } else {
                Toast.makeText(this, "Unable to request location services", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void invokeCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
            mImage = HabitEvent.compressBitmap(imageBitmap);

            if (mHabitEventUUID != null) {
                try {
                    mHabitEvent.getLocation();
                    mToggleLocation.setChecked(true);
                } catch (LocationNotSetException e) {
                }
            }
        }
    }

    private void getLocation() {
        try {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(HabitEventDetailsActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    mLocation = location;
                }
            });
        } catch (SecurityException e) {
            String[] permissionRequested = {Manifest.permission.ACCESS_COARSE_LOCATION};
            requestPermissions(permissionRequested, LOCATION_REQUEST_CODE);
        }
    }


}
