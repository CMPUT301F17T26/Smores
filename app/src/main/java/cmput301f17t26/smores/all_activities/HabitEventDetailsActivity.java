package cmput301f17t26.smores.all_activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import cmput301f17t26.smores.R;
import cmput301f17t26.smores.all_models.HabitEvent;

public class HabitEventDetailsActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 0;
    public static final int CAMERA_REQUEST = 1;
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

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    invokeCamera();
                } else {
                    String[] permissionRequested = {Manifest.permission.CAMERA};
                    requestPermissions(permissionRequested, REQUEST_CODE);
                }
            }
        });
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                invokeCamera();
            } else {
                Toast.makeText(this, "Unable to request camera", Toast.LENGTH_LONG).show();
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
           // mImageView.setImageBitmap(imageBitmap);
            Log.d("Habit Event Details;", "Bitmap size: " + Integer.toString(imageBitmap.getByteCount()));
            Bitmap scaledBitmap = HabitEvent.compressBitmap(imageBitmap);
            Log.d("Habit Event Details;", "Scaled Bitmap size: " + Integer.toString(scaledBitmap.getByteCount()));
            //mImageView.setImageBitmap(scaledBitmap);
            Bitmap newScaled = HabitEvent.decompressBitmap(scaledBitmap);
            mImageView.setImageBitmap(newScaled);
            Log.d("Habit Event Details;", "Scaled Bitmap size: " + Integer.toString(newScaled.getByteCount()));

        }
    }

}
