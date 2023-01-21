package com.miomio;

import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


import org.hamcrest.Matcher;


import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;

import android.view.View;

public class TestHelper {
    static Matcher<View> isEditTextInLayout(int layoutId) {
        return allOf(
                isDescendantOfA(withId(layoutId)),
                withClassName(endsWith("EditText"))
        );
    }
}
