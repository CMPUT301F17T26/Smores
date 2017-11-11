package cmput301f17t26.smores.all_activities;

import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_exceptions.ImageNotSetException;
import cmput301f17t26.smores.all_exceptions.LocationNotSetException;
import cmput301f17t26.smores.all_models.HabitEvent;
import cmput301f17t26.smores.all_storage_controller.HabitEventController;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<HabitEvent> userHabitEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        userHabitEvents = HabitEventController.getHabitEventController(this).getFilteredHabitEvents();
        for (HabitEvent habitEvent: userHabitEvents) {
            try {

                //mMap.addMarker(new MarkerOptions().position(habitEvent.getLatLng()).title(habitEvent.getDate().toString()));
                mMap.addMarker(new MarkerOptions().position(habitEvent.getLatLng()).title(habitEvent.getDate().toString()).icon(BitmapDescriptorFactory.fromBitmap((habitEvent.getImage()))));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(habitEvent.getLatLng()));
            } catch (LocationNotSetException e) {
                continue;
            } catch (ImageNotSetException e) {
                continue;
            }
        }

        // Add a marker in Sydney and move the camera
        // Added a few more markers to see how this works...
        /*LatLng sydney = new LatLng(-34, 151);
        //LatLng sydneyTwo = new LatLng(-34, 152);
        LatLng sydneyThree = new LatLng(-31, 155);
        LatLng sydneyFour = new LatLng(-32, 154);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.addMarker(new MarkerOptions().position(sydneyTwo).title("Marker in Sydney two"));
        mMap.addMarker(new MarkerOptions().position(sydneyThree).title("Marker in Sydney three"));
        mMap.addMarker(new MarkerOptions().position(sydneyFour).title("Marker in Sydney four"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }
}
