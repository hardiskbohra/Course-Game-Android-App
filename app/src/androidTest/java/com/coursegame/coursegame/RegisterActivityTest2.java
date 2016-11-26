package com.coursegame.coursegame;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest2 {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void registerActivityTest2() {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.new_account), withText("Create New Account"),
                        withParent(allOf(withId(R.id.email_login_form),
                                withParent(withId(R.id.login_form)))),
                        isDisplayed()));
        appCompatTextView.perform(click());




        ViewInteraction appCompatEditText = onView(
                withId(R.id.r_first_name));
        appCompatEditText.perform(scrollTo(), replaceText("fat"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                withId(R.id.r_last_name));
        appCompatEditText2.perform(scrollTo(), replaceText("kap"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                withId(R.id.r_email));
        appCompatEditText3.perform(scrollTo(), replaceText("fa@ka.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                withId(R.id.r_student_id));
        appCompatEditText4.perform(scrollTo(), replaceText("201512018"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                withId(R.id.r_password));
        appCompatEditText5.perform(scrollTo(), replaceText("123456"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                withId(R.id.r_contact));
        appCompatEditText6.perform(scrollTo(), replaceText("123"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.r_submit), withText("Submit")));
        appCompatButton.perform(scrollTo(), click());

    }


    }

