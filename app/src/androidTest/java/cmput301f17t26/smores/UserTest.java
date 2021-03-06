package cmput301f17t26.smores;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.UUID;

import cmput301f17t26.smores.all_exceptions.InvalidUUIDException;
import cmput301f17t26.smores.all_models.User;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UserTest extends ActivityInstrumentationTestCase2 {

    public UserTest() {
        super(cmput301f17t26.smores.all_models.User.class);
    }

    public void testUser() {
        //Includes coverage of getUsername()
        String username = "Steve";
        User user = new User(username);
        assertEquals(user.getUsername(), username);
    }

    public void testUserRandomUUID() {
        //Includes coverage of getUserID()
        User user1 = new User("myfirstuser");
        User user2 = new User("myseconduser");
        User user3 = new User("Steve");

        assertTrue(user1.getUserID() != user2.getUserID());
        assertTrue(user1.getUserID() != user3.getUserID());
        assertTrue(user2.getUserID() != user3.getUserID());
    }

    public void testAddRequest() {
        User user1 = new User("bob");
        User user2 = new User("Steve");

        try {
            user1.addRequest(user2.getUserID());
        } catch (InvalidUUIDException e) {
            e.printStackTrace();
        }
        assertEquals(user1.getRequest(0), user2.getUserID());
    }

    public void testAddFollowing() {
        User user1 = new User("bob");
        User user2 = new User("Steve");

        try {
            user1.addFollowing(user2.getUserID());
        } catch (InvalidUUIDException e) {
            e.printStackTrace();
        }
        assertEquals(user1.getFollowing(0), user2.getUserID());
    }


    public void testRemoveRequest() {
        User user1 = new User("bob");
        User user2 = new User("Steve");

        try {
            user1.addRequest(user2.getUserID());
        } catch (InvalidUUIDException e) {
            e.printStackTrace();
        }

        user1.removeRequest(user2.getUserID());
        ArrayList<UUID> user1RequestList = user1.getRequestsList();
        assertEquals(0, user1RequestList.size());
    }

    public void testRemoveRequestNotInList() {
        User user1 = new User("bob");
        User user2 = new User("Steve");
        User user3 = new User("Aristotle");

        try {
            user1.addRequest(user2.getUserID());
        } catch (InvalidUUIDException e) {
            e.printStackTrace();
        }
        user1.removeRequest(user3.getUserID());
        ArrayList<UUID> user1RequestList = user1.getRequestsList();
        assertEquals(1, user1RequestList.size());
    }

    public void testGetRequestList() {
        User user1 = new User("bob");
        User user2 = new User("Steve");

        try {
            user1.addRequest(user2.getUserID());
        } catch (InvalidUUIDException e) {
            e.printStackTrace();
        }

        ArrayList<UUID> user1RequestList = user1.getRequestsList();
        assertEquals(user2.getUserID(), user1RequestList.get(0));
    }

    public void testGetFollowingList() {
        User user1 = new User("bob");
        User user2 = new User("Steve");

        try {
            user1.addFollowing(user2.getUserID());
        } catch (InvalidUUIDException e) {
            e.printStackTrace();
        }

        ArrayList<UUID> user1FollowingList = user1.getFollowingList();
        assertEquals(user2.getUserID(), user1FollowingList.get(0));
    }

    public void testGetRequest() {
        User user1 = new User("bob");
        User user2 = new User("Steve");

        try {
            user1.addRequest(user2.getUserID());
        } catch (InvalidUUIDException e) {
            e.printStackTrace();
        }

        assertEquals(user2.getUserID(), user1.getRequest(0));
    }

    public void testGetRequestOutOfBounds() {
        User user1 = new User("bob");
        User user2 = new User("Steve");

        try {
            user1.addRequest(user2.getUserID());
        } catch (InvalidUUIDException e) {
            e.printStackTrace();
        }

        try {
            user1.getRequest(30);
            Assert.fail("Should have thrown IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e){
            //success
        }
    }

    public void testGetFollowing() {
        User user1 = new User("bob");
        User user2 = new User("Steve");

        try {
            user1.addFollowing(user2.getUserID());
        } catch (InvalidUUIDException e) {
            e.printStackTrace();
        }

        assertEquals(user2.getUserID(), user1.getFollowing(0));
    }

    public void testGetFollowingOutOfBounds() {
        User user1 = new User("bob");
        User user2 = new User("Steve");

        try {
            user1.addFollowing(user2.getUserID());
        } catch (InvalidUUIDException e) {
            e.printStackTrace();
        }

        try {
            user1.getFollowing(30);
            Assert.fail("Should have thrown IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException e){
            //success
        }
    }
}