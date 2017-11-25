package cmput301f17t26.smores;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import cmput301f17t26.smores.all_exceptions.NotDayOfWeekException;
import cmput301f17t26.smores.all_exceptions.ReasonTooLongException;
import cmput301f17t26.smores.all_exceptions.TitleTooLongException;
import cmput301f17t26.smores.all_models.Habit;

/**
 * Created by farhadmakiabady on 2017-10-21.
 */

public class HabitTest extends ActivityInstrumentationTestCase2 {

    //Used for a default Habit constructor to make test cases neater
    private UUID userID = UUID.randomUUID();
    private String title = "testhabit";
    private String reason = "testreason";
    private Date date = new Date();
    private HashMap<Integer, Boolean> days = new HashMap<Integer, Boolean>() {{
        put(Habit.SUNDAY, Boolean.TRUE);
        put(Habit.MONDAY, Boolean.FALSE);
        put(Habit.TUESDAY, Boolean.FALSE);
        put(Habit.WEDNESDAY, Boolean.FALSE);
        put(Habit.THURSDAY, Boolean.FALSE);
        put(Habit.FRIDAY, Boolean.FALSE);
        put(Habit.SATURDAY, Boolean.FALSE);
    }};

    public HabitTest() {
        super(cmput301f17t26.smores.all_models.Habit.class);
    }

    public void testHabit() {

        try {
            Habit habit = new Habit(userID, title, reason, date, days);
            assertTrue(userID == habit.getUserID());
            assertTrue(title == habit.getTitle());
            assertTrue(reason == habit.getReason());
            assertTrue(date == habit.getStartDate());
            assertTrue(days == habit.getDaysOfWeek());
        } catch (TitleTooLongException e) {
            Assert.fail("Title too long.");
        } catch (ReasonTooLongException e) {
            Assert.fail("Reason too long.");
        }
    }

    public void testSetTitle() {
        //Includes coverage of getTitle
        String testtitle = "testtitle";
        try {
            Habit habit = new Habit(userID, title, reason, date, days);
            habit.setTitle(testtitle);
            assertEquals(habit.getTitle(), testtitle);
        } catch (TitleTooLongException e) {
            e.printStackTrace();
        } catch (ReasonTooLongException e) {
            e.printStackTrace();
        }
    }

    public void testSetTitleTooLong() {
        String badtitle = "this is a string with more than 20 characters";
        try {
            Habit habit = new Habit(userID, title, reason, date, days);
            habit.setTitle(badtitle);
            Assert.fail("Should have thrown TitleTooLongException");
        } catch (TitleTooLongException e) {
            //success
        } catch (ReasonTooLongException e) {
            e.printStackTrace();
        }
    }

    public void testSetReason() {
        //Includes coverage of getReason
        String testreason = "testtitle";
        try {
            Habit habit = new Habit(userID, title, reason, date, days);
            habit.setReason(testreason);
            assertEquals(habit.getReason(), testreason);
        } catch (TitleTooLongException e) {
            e.printStackTrace();
        } catch (ReasonTooLongException e) {
            e.printStackTrace();
        }
    }

    public void testSetReasonTooLong() {
        String badreason = "this is a string with more than 30 characters";
        try {
            Habit habit = new Habit(userID, title, reason, date, days);
            habit.setReason(badreason);
            Assert.fail("Should have thrown ReasonTooLongException");
        } catch (TitleTooLongException e) {
            e.printStackTrace();
        } catch (ReasonTooLongException e) {
            //success
        }
    }

    public void testSetStartDate() {
        //Includes coverage of getStartDate
        Date testdate = new Date();
        try {
            Habit habit = new Habit(userID, title, reason, date, days);
            habit.setStartDate(testdate);
            assertEquals(habit.getStartDate(), testdate);
        } catch (TitleTooLongException e) {
            e.printStackTrace();
        } catch (ReasonTooLongException e) {
            e.printStackTrace();
        }
    }

    public void testSetDaysOfWeek() {
        //Includes coverage of getDaysOfWeek
        HashMap<Integer, Boolean> testdays = new HashMap<Integer, Boolean>() {{
            put(Habit.SUNDAY, Boolean.FALSE);
            put(Habit.MONDAY, Boolean.TRUE);
            put(Habit.TUESDAY, Boolean.TRUE);
            put(Habit.WEDNESDAY, Boolean.TRUE);
            put(Habit.THURSDAY, Boolean.TRUE);
            put(Habit.FRIDAY, Boolean.TRUE);
            put(Habit.SATURDAY, Boolean.TRUE);
        }};
        try {
            Habit habit = new Habit(userID, title, reason, date, days);
            habit.setDaysOfWeek(testdays);
            assertEquals(habit.getDaysOfWeek(), testdays);
        } catch (TitleTooLongException e) {
            e.printStackTrace();
        } catch (ReasonTooLongException e) {
            e.printStackTrace();
        }
    }
}
