/*
 * HabitEvent
 *
 * Version 1.0
 *
 * October 28, 2016
 *
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 */

package cmput301f17t26.smores.all_models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v4.app.LoaderManager;
import android.util.Base64;

import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.UUID;

import cmput301f17t26.smores.all_exceptions.CommentNotSetException;
import cmput301f17t26.smores.all_exceptions.CommentTooLongException;
import cmput301f17t26.smores.all_exceptions.ImageNotSetException;
import cmput301f17t26.smores.all_exceptions.ImageTooBigException;
import cmput301f17t26.smores.all_exceptions.LocationNotSetException;

/**
 * Represents a Habit Event
 *
 * @author rohan
 * @version 1.0
 * @since 1.0
 */
public class HabitEvent {

    private UUID mID;

    private Date mDateCompleted;

    private String mComment;
    private transient Bitmap mImage;
    private String thumbnailBase64;
    private Location mLocation;

    private UUID mUserID;
    private UUID mHabitID;

    /**
     * Constructs a habit event object.
     *
     * @param userID
     * @param habitID
     */
    public HabitEvent(UUID userID, UUID habitID)  {
        mID = UUID.randomUUID();

        mUserID = userID;
        mHabitID = habitID;
        mDateCompleted = new Date();
    }

    /**
     * Sets the habit comment.
     *
     * @param comment
     * @throws CommentTooLongException
     */
    public void setComment(String comment) throws CommentTooLongException {
        if (comment.length() > 20) {
            throw new CommentTooLongException();
        } else {
            mComment = comment;
        }
    }

    /**
     * Sets the habit event location.
     *
     * @param location
     */
    public void setLocation(Location location) {
        mLocation = location;
    }

    /**
     * Sets the habit event image.
     *
     * @param image
     * @throws ImageTooBigException
     */
    public void setImage(Bitmap image) throws ImageTooBigException {
        if (image == null) {
            mImage = null;
            thumbnailBase64 = null;
            return;
        }
        if (image.getByteCount() >= 65536) {
            throw new ImageTooBigException();
        } else {
            mImage = image;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

            byte[] b = byteArrayOutputStream.toByteArray();
            thumbnailBase64 = Base64.encodeToString(b, Base64.DEFAULT);
        }
    }

    /**
     * Removes habit event location
     */
    public void removeLocation() {
        mLocation = null;
    }

    /**
     * Removes habit event image
     */
    public void removeImage() {
        mImage = null;
    }

    /**
     * Removes habit event comment
     */
    public void removeComment() {
        mComment = null;
    }

    /**
     * Returns habit event location
     *
     * @return Location mLocation
     * @throws LocationNotSetException
     */
    public Location getLocation() throws LocationNotSetException {
        if (mLocation == null) {
            throw new LocationNotSetException();
        } else {
            return mLocation;
        }
    }

    /**
     * Returns habit event location has a lat/long
     *
     * @return LatLng new LatLng(...)
     * @throws LocationNotSetException
     */
    public LatLng getLatLng() throws LocationNotSetException {
        if (mLocation == null) {
            throw new LocationNotSetException();
        } else {
            return new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        }
    }

    /**
     * Returns habit event bitmap
     *
     * @return Bitmap mImage
     * @throws ImageNotSetException
     */
    public Bitmap getImage() throws ImageNotSetException {
        if (mImage == null && thumbnailBase64 == null) {
            throw new ImageNotSetException();
        } else {
            if (thumbnailBase64 != null) {
                byte[] decodeString = Base64.decode(thumbnailBase64, Base64.DEFAULT);
                mImage = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
            }
            return mImage;
        }
    }

    /**
     * Returns habit event comment
     *
     * @return String mComment
     * @throws CommentNotSetException
     */
    public String getComment() throws CommentNotSetException {
        if (mComment == null) {
            throw new CommentNotSetException();
        } else {
            return mComment;
        }
    }

    /**
     * Returns habit ID associated with habit event
     *
     * @return UUID mHabitID
     */
    public UUID getHabitID() {
        return mHabitID;
    }

    /**
     * Returns user ID associated with habit event
     *
     * @return UUID mUserID
     */
    public UUID getUserID() {
        return mUserID;
    }

    /**
     * Returns habit event ID
     *
     * @return UUID mID
     */
    public UUID getID() {
        return mID;
    }

    public Date getDate() {
        return mDateCompleted;
    }

    public static Bitmap compressBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, 127, 127, true);

    }

    public static Bitmap decompressBitmap(Bitmap scaledBitmap) {
        return Bitmap.createScaledBitmap(scaledBitmap, 256, 256, true);
    }
}
