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
        HashMap<Integer, Boolean> testdays = new HashMap<Integer, Boolean>() {{
            put(Habit.SUNDAY, Boolean.TRUE);
            put(Habit.MONDAY, Boolean.FALSE);
            put(Habit.TUESDAY, Boolean.FALSE);
            put(Habit.WEDNESDAY, Boolean.FALSE);
            put(Habit.THURSDAY, Boolean.FALSE);
            put(Habit.FRIDAY, Boolean.FALSE);
            put(Habit.SATURDAY, Boolean.FALSE);
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

    public void testSetDaysMissed() {
        Integer daysmissed = 2;
        try {
            Habit habit = new Habit(userID, title, reason, date, days);
            habit.setDaysMissed(daysmissed);
            assertEquals(habit.getDaysMissed(), daysmissed);
        } catch (TitleTooLongException e) {
            e.printStackTrace();
        } catch (ReasonTooLongException e) {
            e.printStackTrace();
        }
    }

    public void testSetPercentageFollowed() {
        Double testpercentage = 42.0;
        try {
            Habit habit = new Habit(userID, title, reason, date, days);
            habit.setPercentageFollowed(testpercentage);
            assertEquals(habit.getPercentageFollowed(), testpercentage);
        } catch (TitleTooLongException e) {

        } catch (ReasonTooLongException e) {
            e.printStackTrace();
        }
    }

    public void testSetMostFrequentDay() {
        Integer testfrequentday = Habit.MONDAY;
        try {
            Habit habit = new Habit(userID, title, reason, date, days);
            habit.setMostFrequentDay(testfrequentday);
            assertEquals(habit.getMostFrequentDay(), "Monday");
        } catch (TitleTooLongException e) {
            e.printStackTrace();
        } catch (ReasonTooLongException e) {
            e.printStackTrace();
        } catch (NotDayOfWeekException e) {
            e.printStackTrace();
        }
    }

    public void testSetDaysCompleted() {
        Integer testdayscompleted = 4;
        try {
            Habit habit = new Habit(userID, title, reason, date, days);
            habit.setDaysCompleted(testdayscompleted);
            assertEquals(habit.getDaysCompleted(), testdayscompleted);
        } catch (TitleTooLongException e) {
            e.printStackTrace();
        } catch (ReasonTooLongException e) {
            e.printStackTrace();
        }
    }
}
