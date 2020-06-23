package com.sarnava.newsapplication;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.sarnava.newsapplication.ui.details.NewsActivity;
import com.sarnava.newsapplication.ui.main.MainActivity;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void test1_initialUI(){

        onView(withText("HEADLINES")).check(matches(isDisplayed()));
        onView(withId(R.id.rv)).check(matches(isDisplayed()));
    }

    @Test
    public void test2_scrollList(){

        onView(withId(R.id.rv)).check(matches(isDisplayed()));
        onView(withId(R.id.rv)).perform(swipeUp());
    }

    @Test
    public void test3_listItemClick(){

        Intents.init();

        onView(withId(R.id.rv)).check(matches(isDisplayed()));
        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        Intents.intended(IntentMatchers.hasComponent(NewsActivity.class.getName()));

        onView(withId(R.id.title)).check(matches(isDisplayed()));
        Intents.release();
    }
}
