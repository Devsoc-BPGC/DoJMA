package com.csatimes.dojma.activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import com.csatimes.dojma.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatButton = onView(
allOf(withId(R.id.content_post_downloader_start_btn), withText("Start"), isDisplayed()));
        appCompatButton.perform(click());
        
        ViewInteraction appCompatCheckedTextView = onView(
allOf(withId(R.id.design_menu_item_text), withText("Favourites"), isDisplayed()));
        appCompatCheckedTextView.perform(click());
        
        ViewInteraction appCompatImageButton = onView(
allOf(withContentDescription("Navigate up"),
withParent(withId(R.id.activity_favourites_toolbar)),
isDisplayed()));
        appCompatImageButton.perform(click());
        
        ViewInteraction appCompatTextView = onView(
allOf(withText("Gazettes"), isDisplayed()));
        appCompatTextView.perform(click());
        
        ViewInteraction appCompatTextView2 = onView(
allOf(withText("Herald"), isDisplayed()));
        appCompatTextView2.perform(click());
        
        ViewInteraction appCompatTextView3 = onView(
allOf(withText("Utilities"), isDisplayed()));
        appCompatTextView3.perform(click());
        
        ViewInteraction recyclerView = onView(
allOf(withId(R.id.utilities_rv), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        
        }

    }
