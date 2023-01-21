package com.miomio;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static com.miomio.TestHelper.isEditTextInLayout;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SearchFieldUITest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);


    private NoteRepository noteRepository;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        noteRepository = new NoteRepository(context);
        Note note = new Note();
        note.setTitle("test1");
        note.setContent("test1");

        noteRepository.createNote(note);
    }


    @Test
    public void isSearchViewVisible() {


        onView(withId(R.id.layout_note_search)).check(matches(isDisplayed()));

    }

    @Test
    public void typeInSearchField() {
        onView(
                isEditTextInLayout(R.id.layout_note_search)


        ).perform(typeText("test1"));
        // check if the note is displayed
        onView(withId(R.id.notesRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
    }
}
