/*
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 */

package cmput301f17t26.smores;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import cmput301f17t26.smores.all_exceptions.ReasonTooLongException;
import cmput301f17t26.smores.all_exceptions.TitleTooLongException;
import cmput301f17t26.smores.all_models.Feed;
import cmput301f17t26.smores.all_models.Habit;
import cmput301f17t26.smores.all_models.HabitEvent;

/**
 * Created by Rohan on 11/25/2017.
 */

public class FeedTest extends ActivityInstrumentationTestCase2 {
    public FeedTest() {
        super(Feed.class);
    }

    public void testGetHabit() {
        try {
            Habit habit = new Habit(UUID.randomUUID(), "Walk the dog", "Be fit", new Date(),new HashMap<Integer, Boolean>());
            HabitEvent habitEvent = new HabitEvent(habit.getUserID(), habit.getID());
            String username = "A username!";
            Feed feed = new Feed(username, habit, habitEvent);
            Assert.assertEquals("Habit was not equal!", habit, feed.getHabit());
        } catch (TitleTooLongException e) {
            Assert.fail("Failed to create habit. Title too long.");
        } catch (ReasonTooLongException e) {
            Assert.fail("Failled to create habit. Reason was too long");
        }
    }
    public void testGetHabitEvent() {
        try {
            Habit habit = new Habit(UUID.randomUUID(), "Walk the dog", "Be fit", new Date(),new HashMap<Integer, Boolean>());
            HabitEvent habitEvent = new HabitEvent(habit.getUserID(), habit.getID());
            String username = "A username!";
            Feed feed = new Feed(username, habit, habitEvent);
            Assert.assertEquals("Habit event was not equal!", habitEvent, feed.getHabitEvent());
        } catch (TitleTooLongException e) {
            Assert.fail("Failed to create habit. Title too long.");
        } catch (ReasonTooLongException e) {
            Assert.fail("Failled to create habit. Reason was too long");
        }

    }
    public void testGetUsername() {
        try {
            Habit habit = new Habit(UUID.randomUUID(), "Walk the dog", "Be fit", new Date(),new HashMap<Integer, Boolean>());
            HabitEvent habitEvent = new HabitEvent(habit.getUserID(), habit.getID());
            String username = "A username!";
            Feed feed = new Feed(username, habit, habitEvent);
            Assert.assertEquals("Username was not equal!", username, feed.getUsername());
        } catch (TitleTooLongException e) {
            Assert.fail("Failed to create habit. Title too long.");
        } catch (ReasonTooLongException e) {
            Assert.fail("Failled to create habit. Reason was too long");
        }
    }
}
