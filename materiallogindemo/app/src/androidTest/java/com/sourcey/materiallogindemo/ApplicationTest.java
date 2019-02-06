package com.sourcey.materiallogindemo;

import android.app.Application;
import android.support.design.widget.TextInputLayout;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import android.view.View;
import android.widget.EditText;


import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;


/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
public class ApplicationTest extends ApplicationTestCase<Application> {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

   public ApplicationTest() {
        super(Application.class);

    }

    @Test
    public void listGoesOverTheFold() {
        ViewInteraction PassInput = onView(withChild(withChild(withId(R.id.input_password))));//.check(matches(isDisplayed()));
        PassInput.check(matches(hasTextInputLayoutHintText("Password")));

        //PassInput.perform(scrollTo(), replaceText("ifyug"),closeSoftKeyboard());
    }



    @Test
    public void emailCheck() {
        ViewInteraction emailInput = onView(withId(R.id.input_email));
        emailInput.perform(replaceText("Delete Me"),closeSoftKeyboard());
        emailInput.perform(clearText());

        ViewInteraction loginButton = onView(withId(R.id.btn_login));
        loginButton.perform(click());
    }
    //Code modified from
    //https://stackoverflow.com/a/38874162/621621
    public static Matcher<View> hasTextInputLayoutHintText(final String expectedErrorText) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof TextInputLayout)) {
                    return false;
                }

                CharSequence error = ((TextInputLayout) view).getHint();


                if (error == null) {
                    return false;
                }

                String hint = error.toString();

                return expectedErrorText.equals(hint);
            }

            @Override
            public void describeTo(Description description) {
            }
        };
    }

}


