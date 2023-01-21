package com.miomio;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static com.miomio.TestHelper.isEditTextInLayout;

import static org.hamcrest.Matchers.not;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

public class NoteEditorTest {
    private NoteRepository noteRepository;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        noteRepository = new NoteRepository(context);
        noteRepository.deleteAllNotes();
    }

    @After
    public void closeDb() {
        noteRepository.close();
    }

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void updateNote() {
        createNewNote();

        onView(withId(R.id.notesRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(isEditTextInLayout(R.id.textFieldTitle)).perform(clearText(), typeText("Test Note Updated"));
        onView(isEditTextInLayout(R.id.textFieldContent)).perform(clearText(), typeText("This is a test note updated"));
        pressBack();
        onView(withId(R.id.notesRecyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.notesRecyclerView)).check(matches(hasDescendant(withText("Test Note Updated"))));
    }

    @Test
    public void createNewNote() {
        onView(withId(R.id.floating_action_button)).perform(click());
        onView(isEditTextInLayout(R.id.textFieldTitle)).perform(typeText("Test Note"));
        onView(isEditTextInLayout(R.id.textFieldContent)).perform(typeText("This is a test note"));
        pressBack();
        onView(withId(R.id.notesRecyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.notesRecyclerView)).check(matches(hasDescendant(withText("Test Note"))));
    }

    @Test
    public void deleteNote() {
        onView(withId(R.id.floating_action_button)).perform(click());
        onView(isEditTextInLayout(R.id.textFieldTitle)).perform(typeText("Deleted Note"));
        onView(isEditTextInLayout(R.id.textFieldContent)).perform(typeText("This is a test note"));
        pressBack();
        onView(withId(R.id.notesRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.note_menu_delete)).perform(click());
        onView(withId(R.id.notesRecyclerView)).check(matches(hasDescendant(not(withText("Deleted Note")))));
    }

}
