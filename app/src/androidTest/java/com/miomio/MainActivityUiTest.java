package com.miomio;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Ordering;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityUiTest {
    private NoteRepository noteRepository;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        noteRepository = new NoteRepository(context);
        noteRepository.deleteAllNotes();
    }

    @After
    public void closeDb() throws IOException {
        noteRepository.close();
    }

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void isNoteListVisible() {
        onView(withId(R.id.notesRecyclerView)).check(matches(isDisplayed()));

    }

    @Test
    public void isAddNoteButtonVisible() {
        onView(withId(R.id.floating_action_button)).check(matches(isDisplayed()));
        onView(withId(R.id.floating_action_button)).perform(click());
    }

    @Test
    public void isEmptyViewVisible() throws InterruptedException {

        onView(withId(R.id.no_notes_text)).check(matches(isDisplayed()));
    }




}
