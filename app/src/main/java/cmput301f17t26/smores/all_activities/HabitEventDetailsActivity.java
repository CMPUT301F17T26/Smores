/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: View class for adding, editing and deleting Habit Events.
 * Outstanding issues: Being able to add an habit event for previous day.
 */

package cmput301f17t26.smores.all_activities;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.NetworkOnMainThreadException;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
import cmput301f17t26.smores.utils.DateUtils;
import cmput301f17t26.smores.utils.NetworkStateReceiver;
import cmput301f17t26.smores.utils.NetworkUtils;

public class HabitEventDetailsActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {
    public static final int CAMERA_REQUEST_CODE = 0;
    public static final int LOCATION_REQUEST_CODE = 2;
    public static final int GALLERY_REQUEST_CODE = 3;
    public static final int GALLERY_REQUEST = 4;
    public static final int CAMERA_REQUEST = 1;
    private RadioButton mTodayRad;
    private RadioButton mPreviousDayRad;
    private RadioGroup mRadioGroup;

    private Spinner mPreviousDaySpin;
    private TextView mPreviousDayText;

    private Spinner mHabitType;
    private TextView mHabitType_Fixed;
    private EditText mComment;
    private TextView mDateCompleted;

    private ToggleButton mToggleLocation;
    private Button mUpdateLocation;
    private TextView mLocationString;
    private ImageButton mImageButton;
    private ImageView mImageView;
    private Button mGalleryButton;
    private ImageButton mSave;
    private ImageButton mDelete;
    private FusedLocationProviderClient mFusedLocationClient;
    private UUID mHabitEventUUID;
    private HabitEvent mHabitEvent;
    private Location mLocation;
    private Bitmap mImage;
    private String mLocationText;

    private ArrayList<Habit> mHabitList;
    private ArrayAdapter<String> spinnerDataAdapter;
    private ArrayAdapter<String> previousSpinnerDataAdapter;
    private ArrayList<Date> mDaysMissed;

    private NetworkStateReceiver networkStateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getUIElements();
        mHabitList = HabitController.getHabitController(this).getHabitList();

        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        viewHiding();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null && bundle.get("habitEventPosition") != null) {
            mHabitEventUUID = (UUID) bundle.get("habitEventPosition");
        }
        setListeners();
        loadDetails();
        interentViews();
    }

    private void interentViews() {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            mToggleLocation.setEnabled(false);
            mUpdateLocation.setEnabled(false);
        } else {
            mToggleLocation.setEnabled(true);
            mUpdateLocation.setEnabled(true);
        }
        if (mHabitEvent != null) {
            try {
                mHabitEvent.getLocation();
                mToggleLocation.setEnabled(true);
            } catch (LocationNotSetException e) {
                if (!NetworkUtils.isNetworkAvailable(this)) {
                    mToggleLocation.setEnabled(false);
                }

            }
        }
    }

    private void viewHiding() {
        mHabitType.setVisibility(View.GONE);
        mHabitType_Fixed.setVisibility(View.GONE);
        mUpdateLocation.setVisibility(View.GONE);

        mPreviousDayText.setVisibility(View.GONE);
        mPreviousDaySpin.setVisibility(View.GONE);
        mRadioGroup.setVisibility(View.GONE);
        mTodayRad.setVisibility(View.GONE);
        mPreviousDayRad.setVisibility(View.GONE);

    }

    private void getUIElements() {
        mTodayRad = (RadioButton) findViewById(R.id.Event_hTodayRad);
        mPreviousDayRad = (RadioButton) findViewById(R.id.Event_hPreviousDayRad);

        mRadioGroup = (RadioGroup) findViewById(R.id.RadGroup);
        mPreviousDayText = (TextView) findViewById(R.id.Event_hPreviousText);
        mPreviousDaySpin = (Spinner) findViewById(R.id.Event_hPreviousSpinner);

        mHabitType = (Spinner) findViewById(R.id.Event_hType);
        mHabitType_Fixed = (TextView) findViewById(R.id.Event_hTypeFixed);
        mComment = (EditText) findViewById(R.id.Event_hComment);
        mDateCompleted = (TextView) findViewById(R.id.Event_hDate);
        mToggleLocation = (ToggleButton) findViewById(R.id.Event_hToggleButton);
        mUpdateLocation = (Button) findViewById(R.id.Event_hUpdateButton);
        mLocationString = (TextView) findViewById(R.id.Event_hLocationText);
        mImageButton = (ImageButton) findViewById(R.id.Event_hImagebtn);
        mGalleryButton = (Button) findViewById(R.id.Event_hGallerybtn);
        mImageView = (ImageView) findViewById((R.id.Event_hImage));
        mSave = (ImageButton) findViewById(R.id.Event_hSave);
        mDelete = (ImageButton) findViewById(R.id.Event_hDelete);
    }

    private void setListeners() {
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteButtonHandler();
            }
        });

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

        mImageButton.setOnTouchListener(new TextView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mImageButtonOnTouch(event);
            }
        });

        mGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    invokeGallery();
                } else {
                    String[] permissionRequested = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissionRequested, GALLERY_REQUEST_CODE);
                }
            }
        });



        mToggleLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {


                if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if (isChecked) {
                        mUpdateLocation.setEnabled(true);
                        if (NetworkUtils.isNetworkAvailable(HabitEventDetailsActivity.this)) {
                            getLocation();
                        } else {
                            Toast.makeText(HabitEventDetailsActivity.this, "Oops, you are offline, please try again later!", Toast.LENGTH_SHORT).show();
                            mUpdateLocation.setEnabled(false);
                            mToggleLocation.setEnabled(false);
                        }
                    } else {
                        if (!NetworkUtils.isNetworkAvailable(HabitEventDetailsActivity.this)) {
                            mToggleLocation.setEnabled(false);
                        }
                        mUpdateLocation.setEnabled(false);
                        mLocation = null;
                        mLocationString.setText("");
                        mLocationText = null;
                    }
                } else {
                    String[] permissionRequested = {Manifest.permission.ACCESS_COARSE_LOCATION};
                    requestPermissions(permissionRequested, LOCATION_REQUEST_CODE);
                }
            }
        });

        mUpdateLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (NetworkUtils.isNetworkAvailable(HabitEventDetailsActivity.this)) {
                            getLocation();
                        } else {
                            Toast.makeText(HabitEventDetailsActivity.this, "Oops, you are offline, please try again later!", Toast.LENGTH_SHORT).show();
                            mToggleLocation.setEnabled(false);
                        }

                } else {
                    String[] permissionRequested = {Manifest.permission.ACCESS_COARSE_LOCATION};
                    requestPermissions(permissionRequested, LOCATION_REQUEST_CODE);
                }

            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveButtonHandler();
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (((RadioButton) findViewById(i)).getText().equals("Previous Day")) {
                    mPreviousDayText.setVisibility(View.VISIBLE);
                    mPreviousDaySpin.setVisibility(View.VISIBLE);
                } else {
                    mPreviousDayText.setVisibility(View.GONE);
                    mPreviousDaySpin.setVisibility(View.GONE);
                }
                loadSpinner();
                loadPreviousSpinner();
            }
        });
    }



    private void loadDetails() {
        if (mHabitEventUUID != null) {
            mHabitEvent = HabitEventController.getHabitEventController(this).getHabitEvent(mHabitEventUUID);
            mUpdateLocation.setVisibility(View.VISIBLE);
            mHabitType_Fixed.setVisibility(View.VISIBLE);
            mHabitType_Fixed.setText(HabitController.getHabitController(this).getHabit(mHabitEvent.getHabitID()).getTitle());
            mDateCompleted.setText(DateUtils.getStringOfDate(mHabitEvent.getDate()));
            try {
                mComment.setText(mHabitEvent.getComment());
            } catch (CommentNotSetException e) {}
            try {
                mHabitEvent.getLocation();
                mToggleLocation.setChecked(true);
                mLocationString.setText(mHabitEvent.getLocationString());
            } catch (LocationNotSetException e) {}
            try {
                mImageView.setImageBitmap(HabitEvent.decompressBitmap(mHabitEvent.getImage()));
                mImage = mHabitEvent.getImage();
            } catch (ImageNotSetException e) {}

        } else {
            mHabitType.setVisibility(View.VISIBLE);
            mTodayRad.setVisibility(View.VISIBLE);
            mPreviousDayRad.setVisibility(View.VISIBLE);
            mRadioGroup.setVisibility(View.VISIBLE);
            loadSpinner();
        }
    }


    long time = 0;

    private boolean mImageButtonOnTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            time = (Long) System.currentTimeMillis();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (((Long) System.currentTimeMillis() - time) > 2000) {
                mImageView.setImageBitmap(null);
                mImage = null;
                return true;
            }
        }
        return false;
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
            saveNew();
        } else {
            saveExsisting();
        }

    }

    private void saveExsisting() {
        if (!mComment.getText().toString().equals("")) {
            try {
                mHabitEvent.setComment(mComment.getText().toString());
            } catch (CommentTooLongException e) {
            }
        }
        else {
            try {
                mHabitEvent.setComment(null);
            } catch (Exception e) {}
        }
        if (mToggleLocation.isChecked()) {
            if (mLocation != null)
                mHabitEvent.setLocation(mLocation, mLocationText);
        } else {
            mHabitEvent.setLocation(null);

        }
        try {
            mHabitEvent.setImage(mImage);
        } catch (ImageTooBigException e) {
        }
        HabitEventController.getHabitEventController(this).updateHabitEvent(this, mHabitEvent);
        finish();
    }

    private void saveNew() {
        String title = null;
        try {
            title = mHabitType.getSelectedItem().toString();
        } catch (NullPointerException e) {
            Toast.makeText(HabitEventDetailsActivity.this, "Please select a Habit Type!", Toast.LENGTH_SHORT).show();
            return;
        }
        HabitEvent habitEvent = new HabitEvent(UserController.getUserController(this).getUser().getUserID(),
                HabitController.getHabitController(this).getHabitIDByTitle(title));

        if (mPreviousDayRad.isChecked()) {
            SimpleDateFormat simpleDateFormat = DateUtils.getDateFormat();
            try {
                mPreviousDaySpin.getSelectedItem().toString();
                Date previousDate = simpleDateFormat.parse(mPreviousDaySpin.getSelectedItem().toString());
                Log.d("Date Read in as: ", mPreviousDaySpin.getSelectedItem().toString());
                Log.d("Date saved to: ", DateUtils.getStringOfDate(previousDate));
                habitEvent.setDate(previousDate);
            } catch (Exception e) {
                Toast.makeText(HabitEventDetailsActivity.this, "Please select a previous date!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        try { habitEvent.setComment(mComment.getText().toString()); } catch (CommentTooLongException e) {}

        if (mToggleLocation.isChecked())
            habitEvent.setLocation(mLocation, mLocationText);
        try { habitEvent.setImage(mImage); } catch (ImageTooBigException e) {}
        HabitEventController.getHabitEventController(this).addHabitEvent(this, habitEvent);
        finish();
    }

    public void loadSpinner() {
        ArrayList<Habit> availableHabits = new ArrayList<>();
        availableHabits.addAll(mHabitList);
        ArrayList<String> stringAvailableHabits = new ArrayList<>();


        if (mTodayRad.isChecked()) {
            for (Habit habit : availableHabits) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                if (simpleDateFormat.format(habit.getStartDate()).compareTo(simpleDateFormat.format(new Date())) > 0) {
                    continue;
                }
                if (HabitEventController.getHabitEventController(this).doesHabitEventExist(habit)) {
                    stringAvailableHabits.add(habit.getTitle());
                }
            }
        } else if (mPreviousDayRad.isChecked()) {
            for (Habit habit : availableHabits) {
                stringAvailableHabits.add(habit.getTitle());
            }
        }
        spinnerDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stringAvailableHabits);
        spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mHabitType.setAdapter(spinnerDataAdapter);

    }

    public void loadPreviousSpinner() {
        if (mPreviousDayRad.getVisibility() != View.GONE) {
            if (mPreviousDayRad.isChecked()) {
                String habitTitle;
                try {
                    habitTitle =  mHabitType.getSelectedItem().toString();
                } catch (NullPointerException e) { return; }
                Habit habit = HabitController.getHabitController(this).getHabitByTitle(habitTitle);
                mDaysMissed = HabitEventController.getHabitEventController(this).getMissedHabitEvents(habit);
                ArrayList<String> stringAvailableDays = new ArrayList<>();
                for (Date date : mDaysMissed) {
                    stringAvailableDays.add(DateUtils.getStringOfDate(date));
                }
                previousSpinnerDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stringAvailableDays);
                previousSpinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mPreviousDaySpin.setAdapter(previousSpinnerDataAdapter);
            }
        }

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Request for camera", Toast.LENGTH_LONG).show();
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
                mToggleLocation.setChecked(false);
            }
        } else if (requestCode == GALLERY_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Request for storage granted", Toast.LENGTH_LONG).show();
                invokeGallery();
            } else {
                Toast.makeText(this, "Unable to request storage services", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void invokeCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private void invokeGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);
        galleryIntent.setDataAndType(data, "image/*");
        startActivityForResult(galleryIntent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(HabitEvent.decompressBitmap(imageBitmap));
            mImage = HabitEvent.compressBitmap(imageBitmap);
            /*if (mHabitEventUUID != null) {
                try {
                    mHabitEvent.getLocation();
                    mToggleLocation.setChecked(true);
                } catch (LocationNotSetException e) {
                }
            }*/
        } else if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap imageBitmap =  BitmapFactory.decodeStream(inputStream);
                mImageView.setImageBitmap(HabitEvent.decompressBitmap(imageBitmap));
                mImage = HabitEvent.compressBitmap(imageBitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getLocation() {
        try {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(HabitEventDetailsActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    mLocation = location;
                    Geocoder myLocation = new Geocoder(HabitEventDetailsActivity.this, Locale.getDefault());
                    try {
                        List<Address> myList = myLocation.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        StringBuilder Baddress = new StringBuilder();
                        if (!(myList.get(0).getLocality() == null)) {
                            Baddress.append(" " + myList.get(0).getLocality());
                        }
                        if (!(myList.get(0).getPostalCode() == null)) {
                            Baddress.append(" " + myList.get(0).getPostalCode());
                        }
                        if (!(myList.get(0).getThoroughfare() == null)) {
                            Baddress.append(" " + myList.get(0).getThoroughfare());
                        }
                        String address = Baddress.toString();
                        mLocationString.setText(address);
                        mLocationText = address;
                    } catch (IOException e) {

                    }


                }
            });
        } catch (SecurityException e) {
            String[] permissionRequested = {Manifest.permission.ACCESS_COARSE_LOCATION};
            requestPermissions(permissionRequested, LOCATION_REQUEST_CODE);
        }
    }


    @Override
    public void networkAvailable() {
        interentViews();
        Toast.makeText(this, "You are online, location tracking is possible!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void networkUnavailable() {
        interentViews();
        Toast.makeText(this, "You are offline, disabling updating/setting location", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        this.unregisterReceiver(networkStateReceiver);
    }
}
