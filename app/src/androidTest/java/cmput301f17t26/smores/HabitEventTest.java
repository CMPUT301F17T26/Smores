package cmput301f17t26.smores;

import android.graphics.Bitmap;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;

import com.google.android.gms.maps.model.LatLng;

import junit.framework.Assert;

import java.util.UUID;

import cmput301f17t26.smores.all_exceptions.CommentNotSetException;
import cmput301f17t26.smores.all_exceptions.CommentTooLongException;
import cmput301f17t26.smores.all_exceptions.ImageNotSetException;
import cmput301f17t26.smores.all_exceptions.ImageTooBigException;
import cmput301f17t26.smores.all_exceptions.LocationNotSetException;
import cmput301f17t26.smores.all_models.HabitEvent;

/**
 * Created by Luke on 2017-10-21.
 */

public class HabitEventTest extends ActivityInstrumentationTestCase2 {

    public HabitEventTest() {
        super(cmput301f17t26.smores.all_models.HabitEvent.class);
    }

    public void testHabitEvent() {
        //Includes coverage of getUserID() and getHabitID()
        UUID userID = UUID.randomUUID();
        UUID habitID = UUID.randomUUID();
        HabitEvent habitEvent = new HabitEvent(userID, habitID);

        assertEquals(userID, habitEvent.getUserID());
        assertEquals(habitID, habitEvent.getHabitID());
    }

    public void testHabitEventRandomUUID() {
        //Includes coverage of getID()
        UUID userID = UUID.randomUUID();
        UUID habitID = UUID.randomUUID();

        HabitEvent habitEvent1 = new HabitEvent(userID, habitID);
        HabitEvent habitEvent2 = new HabitEvent(userID, habitID);
        HabitEvent habitEvent3 = new HabitEvent(userID, habitID);

        assertTrue(habitEvent1.getID() != habitEvent2.getID());
        assertTrue(habitEvent1.getID() != habitEvent3.getID());
        assertTrue(habitEvent2.getID() != habitEvent3.getID());
    }

    public void testSetComment() {
        //Includes coverage of getComment()
        UUID userID = UUID.randomUUID();
        UUID habitID = UUID.randomUUID();
        HabitEvent habitEvent = new HabitEvent(userID, habitID);
        String comment = "short comment";

        try {
            habitEvent.setComment(comment);
            assertEquals(comment, habitEvent.getComment());
        } catch (CommentTooLongException e) {
            Assert.fail("Comment too long");
        } catch (CommentNotSetException e) {
            Assert.fail("Comment not set");
        }
    }

    public void testSetCommentLongComment() {
        UUID userID = UUID.randomUUID();
        UUID habitID = UUID.randomUUID();
        HabitEvent habitEvent = new HabitEvent(userID, habitID);
        String comment = "It is a long comment.";

        try {
            habitEvent.setComment(comment);
            Assert.fail("Should have thrown CommentTooLongException");
        } catch (CommentTooLongException e) {
            //success
        }
    }

    public void testSetLocation() {
        //Includes coverage of getLocation()
        UUID userID = UUID.randomUUID();
        UUID habitID = UUID.randomUUID();
        HabitEvent habitEvent = new HabitEvent(userID, habitID);

        Location location = new Location("");
        location.setLatitude(14.852071d);
        location.setLongitude(-91.530547d);

        habitEvent.setLocation(location);
        try {
            assertEquals(location, habitEvent.getLocation());
        } catch (LocationNotSetException e) {
            Assert.fail("Location not set");
        }
    }

    public void testSetImage() {
        //Includes coverage of getImage()
        UUID userID = UUID.randomUUID();
        UUID habitID = UUID.randomUUID();
        HabitEvent habitEvent = new HabitEvent(userID, habitID);

        Bitmap bitmap = Bitmap.createBitmap(5, 5, Bitmap.Config.ARGB_8888);

        try {
            habitEvent.setImage(bitmap);
            assertEquals(bitmap, habitEvent.getImage());
        } catch (ImageTooBigException e) {
            Assert.fail("small image was considered too big.");
        } catch (ImageNotSetException e) {
            Assert.fail("Image not set");
        }
    }

    public void testSetImageTooBig() {
        UUID userID = UUID.randomUUID();
        UUID habitID = UUID.randomUUID();
        HabitEvent habitEvent = new HabitEvent(userID, habitID);

        Bitmap bitmap = Bitmap.createBitmap(129, 129, Bitmap.Config.ARGB_8888);

        try {
            habitEvent.setImage(bitmap);
            Assert.fail("Should have thrown ImageTooBigException");
        } catch (ImageTooBigException e) {
            //success
        }
    }

    public void testRemoveLocation() {
        //Includes coverage of getLocation
        UUID userID = UUID.randomUUID();
        UUID habitID = UUID.randomUUID();
        HabitEvent habitEvent = new HabitEvent(userID, habitID);

        Location location = new Location("");
        location.setLatitude(14.852071d);
        location.setLongitude(-91.530547d);
        habitEvent.setLocation(location);

        habitEvent.removeLocation();
        try {
            habitEvent.getLocation();
            Assert.fail("Should have thrown LocationNotSetException");
        } catch (LocationNotSetException e) {
            //success
        }
    }

    public void testRemoveImage() {
        //Includes coverage of getImage
        UUID userID = UUID.randomUUID();
        UUID habitID = UUID.randomUUID();
        HabitEvent habitEvent = new HabitEvent(userID, habitID);

        Bitmap bitmap = Bitmap.createBitmap(5, 5, Bitmap.Config.ARGB_8888);
        try {
            habitEvent.setImage(bitmap);
        } catch (ImageTooBigException e) {
            Assert.fail("Could not set image");
        }

        habitEvent.removeImage();
        try {
            habitEvent.getImage();
            Assert.fail("Should have thrown ImageNotSetException");
        } catch (ImageNotSetException e) {
            //success
        }
    }

    public void testRemoveComment() {
        //Includes coverage of getComment
        UUID userID = UUID.randomUUID();
        UUID habitID = UUID.randomUUID();
        HabitEvent habitEvent = new HabitEvent(userID, habitID);

        String comment = "Cool comment.";
        try {
            habitEvent.setComment(comment);
        } catch (CommentTooLongException e) {
            Assert.fail("Could not set comment");
        }

        habitEvent.removeComment();
        try {
            habitEvent.getComment();
            Assert.fail("Should have thrown CommentNotSetException");
        } catch (CommentNotSetException e) {
            //success
        }
    }

    public void testGetLatLng() {
        UUID userID = UUID.randomUUID();
        UUID habitID = UUID.randomUUID();
        HabitEvent habitEvent = new HabitEvent(userID, habitID);

        LatLng latLng = new LatLng(14.852071d, -91.530547d);
        Location location = new Location("");
        location.setLatitude(latLng.latitude);
        location.setLongitude(latLng.longitude);

        habitEvent.setLocation(location);
        try {
            assertEquals(latLng, habitEvent.getLatLng());
        } catch (LocationNotSetException e) {
            Assert.fail("Location not set");
        }
    }
}
