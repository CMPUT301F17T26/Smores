package cmput301f17t26.smores.all_models;

import android.graphics.Bitmap;
import android.location.Location;
import android.support.v4.app.LoaderManager;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.UUID;

import cmput301f17t26.smores.all_exceptions.CommentNotSetException;
import cmput301f17t26.smores.all_exceptions.CommentTooLongException;
import cmput301f17t26.smores.all_exceptions.ImageNotSetException;
import cmput301f17t26.smores.all_exceptions.ImageTooBigException;
import cmput301f17t26.smores.all_exceptions.LocationNotSetException;

/**
 * Created by Rohan on 10/19/2017.
 */

public class HabitEvent {

    private UUID mID;

    private Date mDateCompleted;

    private String mComment;
    private Bitmap mImage;
    private Location mLocation;

    private String mUserID;
    private String mHabitID;

    public HabitEvent(String userID, String habitID)  {
        mID = UUID.randomUUID();

        mUserID = userID;
        mHabitID = habitID;
        mDateCompleted = new Date();
    }

    public void setComment(String comment) throws CommentTooLongException {
        if (comment.length() > 20) {
            throw new CommentTooLongException();
        } else {
            mComment = comment;
        }
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public void setImage(Bitmap image) throws ImageTooBigException {
        if (image.getByteCount() >= 65536) {
            throw new ImageTooBigException();
        } else {
            mImage = image;
        }
    }

    public void removeLocation() {
        mLocation = null;
    }

    public void removeImage() {
        mImage = null;
    }

    public void removeComment() {
        mComment = null;
    }

    public Location getLocation() throws LocationNotSetException {
        if (mLocation == null) {
            throw new LocationNotSetException();
        } else {
            return mLocation;
        }
    }

    public LatLng getLatLng() throws LocationNotSetException {
        if (mLocation == null) {
            throw new LocationNotSetException();
        } else {
            return new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        }
    }

    public Bitmap getImage() throws ImageNotSetException {
        if (mImage == null) {
            throw new ImageNotSetException();
        } else {
            return mImage;
        }
    }

    public String getComment() throws CommentNotSetException {
        if (mComment == null) {
            throw new CommentNotSetException();
        } else {
            return mComment;
        }
    }

    public String getHabitID() {
        return mHabitID;
    }

    public String getUserID() {
        return mUserID;
    }

    public UUID getID() {
        return mID;
    }
}
