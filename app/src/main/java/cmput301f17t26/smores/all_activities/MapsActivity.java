package cmput301f17t26.smores.all_activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.collect.Maps;

import java.util.ArrayList;

import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_exceptions.ImageNotSetException;
import cmput301f17t26.smores.all_exceptions.LocationNotSetException;
import cmput301f17t26.smores.all_models.HabitEvent;
import cmput301f17t26.smores.all_storage_controller.HabitController;
import cmput301f17t26.smores.all_storage_controller.HabitEventController;

import static cmput301f17t26.smores.all_activities.HabitEventDetailsActivity.LOCATION_REQUEST_CODE;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<HabitEvent> userHabitEvents;
    private CheckBox mMyself, mFriendsCheckbox;
    private EditText mRadiusField;
    private Button mSearch;
    private Location currentLocation;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        userHabitEvents = HabitEventController.getHabitEventController(this).getFilteredHabitEvents();
        mFriendsCheckbox = (CheckBox) findViewById(R.id.friendsCheckbox);
        mFriendsCheckbox.setChecked(false);

        mMyself = (CheckBox) findViewById(R.id.meCheckbox);
        mMyself.setChecked(true);

        mRadiusField = (EditText) findViewById(R.id.radiusField);
        mSearch = (Button) findViewById(R.id.searchButton);
        mSearch.setEnabled(false);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            String[] permissionRequested = {Manifest.permission.ACCESS_COARSE_LOCATION};
            requestPermissions(permissionRequested, LOCATION_REQUEST_CODE);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();

        if (mMyself.isChecked()) {
            for (HabitEvent habitEvent: userHabitEvents) {
                try {
                    String fullTitle = getMarkerString(habitEvent);
                    mMap.addMarker(new MarkerOptions().position(habitEvent.getLatLng()).title(fullTitle));
                } catch (LocationNotSetException e) {
                    continue;
                }
            }
        }

        mRadiusField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    mSearch.setEnabled(true);


                } else {
                    mSearch.setEnabled(false);
                    if (mMyself.isChecked()) {
                        loadMyMarkers();
                    }
                    if (mFriendsCheckbox.isChecked()) {
                        loadFriendMarkers();
                    }


                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();

                if (mMyself.isChecked()) {
                    loadMyMarkers();
                }

                if (mFriendsCheckbox.isChecked()) {
                    loadFriendMarkers();
                }
            }
        });

        mMyself.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mMap.clear();
                if (mMyself.isChecked()) {
                    loadMyMarkers();
                }
                if (mFriendsCheckbox.isChecked()) {
                    loadFriendMarkers();
                }
            }
        });

        mFriendsCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mMap.clear();
                if (mMyself.isChecked()) {
                    loadMyMarkers();
                }
                if (mFriendsCheckbox.isChecked()) {
                    loadFriendMarkers();
                }
            }
        });


        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    private void loadMyMarkers() {
        for (HabitEvent habitEvent: userHabitEvents) {
            try {
                if (mRadiusField.getText().toString().trim().equals("")) {
                    String fullTitle = getMarkerString(habitEvent);
                    mMap.addMarker(new MarkerOptions().position(habitEvent.getLatLng()).title(fullTitle));
                } else {
                    if (currentLocation.distanceTo(habitEvent.getLocation()) <= 1000 * Float.valueOf(mRadiusField.getText().toString())){
                        String fullTitle = getMarkerString(habitEvent);
                        mMap.addMarker(new MarkerOptions().position(habitEvent.getLatLng()).title(fullTitle));
                    }
                }
            } catch (LocationNotSetException e) {

            }
        }
    }

    private void loadFriendMarkers() {

    }

    @NonNull
    private String getMarkerString(HabitEvent habitEvent) {
        String Habit_title = HabitController.getHabitController(this).getHabitTitleByHabitID(habitEvent.getHabitID());
        String Habit_dateCompleted = habitEvent.getDate().toString();
        return Habit_title + Habit_dateCompleted;
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Request for location granted", Toast.LENGTH_LONG).show();
                getLocation();
            } else {
                Toast.makeText(this, "Unable to request location services", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getLocation() {
        try {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(MapsActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    currentLocation = location;
                }
            });
        } catch (SecurityException e) {
            String[] permissionRequested = {Manifest.permission.ACCESS_COARSE_LOCATION};
            requestPermissions(permissionRequested, LOCATION_REQUEST_CODE);
        }
    }

}
