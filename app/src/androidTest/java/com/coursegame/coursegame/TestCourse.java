package com.coursegame.coursegame;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by disha on 25-11-2016.
 */
@RunWith(AndroidJUnit4.class)
public class TestCourse  {



    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Test
    public void TestButton()
    {
        onView(withId(R.id.email_sign_in_button)).perform(click());
    }


}
