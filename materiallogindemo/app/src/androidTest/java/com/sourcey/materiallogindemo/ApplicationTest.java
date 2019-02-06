package com.sourcey.materiallogindemo;

import android.app.Application;
import android.support.design.widget.TextInputLayout;
import android.support.test.espresso.ViewAction;
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

import static android.app.PendingIntent.getActivity;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static com.sourcey.materiallogindemo.Support_Class.*;


import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;


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
    /*
    Check that text boxes display proper hints.
     */
    public void checkHints(){

        //Hints in the Edittext aren't showing. Need to grab from parent
        ViewInteraction PassInputBox = onView(withChild(withChild(withId(R.id.input_password))));//.check(matches(withHint("Password")));
        PassInputBox.check(matches(hasTextInputLayoutHintText("Password")));

        ViewInteraction emailInputBox = onView(withChild(withChild(withId(R.id.input_email))));
        emailInputBox.check(matches(hasTextInputLayoutHintText("Email")));

        //Check that displays after click
        ViewInteraction emailInputEditText = onView(withId(R.id.input_email));
        emailInputBox.perform(click());
        emailInputBox.check(matches(hasTextInputLayoutHintText("Email")));

        ViewInteraction passInputBox = onView(withId(R.id.input_password)).perform(click());
        PassInputBox.check(matches(hasTextInputLayoutHintText("Password")));



        //PassInput.perform(scrollTo(), replaceText("ifyug"),closeSoftKeyboard());
    }

    @Test
    public void noPassCheck() {

        //When pass input is empty a warning will show
        addInputText(R.id.input_email, "users@asdfa.com");
        clearInputText(R.id.input_password);
        login();
        checkPassWarning();

    }

    @Test
    public void shortPassCheck() {

        //When pass input is too short a warning will show
        addInputText(R.id.input_email, "users@asdfa.com");
        addInputText(R.id.input_password, "abc");
        login();
        checkPassWarning();

    }

    @Test
    public void longPassCheck() {

        //When pass input is too long warning will show
        addInputText(R.id.input_email, "users@asdfa.com");
        addInputText(R.id.input_password, "0123456789a");
        login();
        checkPassWarning();

    }

    @Test
    public void badEmailCheck(){
        addInputText(R.id.input_password, "abcd1234");
        addInputText(R.id.input_email, "users@asdfa");
        login();
        checkEmailWarning();
    }

    @Test
    public void noEmailCheck() {

        addInputText(R.id.input_password, "abcd1234");
        clearInputText(R.id.input_email);
        login();
        checkEmailWarning();

    }

    @Test
    public void nonAlphaNumericPass()
    {
        ///When pass input contains bad input will be warned
        //TODO implent a fix for this in the code. Known Failure
        addInputText(R.id.input_email, "users@asdfa.com");
        addInputText(R.id.input_password, "!01267!");
        login();
        checkPassWarning();
    }

    @Test
    //TODO implement a database to pull from
    //TODO Blocked because dev have not impleented database or auth methods
    public void loginWithRegUser() throws InterruptedException {
        addInputText(R.id.input_password, "1234abcd");
        addInputText(R.id.input_email, "Ringo.Evan@gmail.com");
        login();
        //onView(withText(("Authenticating..."))).check(matches(isDisplayed()));
        //waitId("Hello world!", 5000);

        //TODO Fix crappy code. When the activity finishes root is lost, so need to implement a different wait method.
        //TODO look into intended, Idilingresource, Or custom solution that refreshes the view being queried?
        Thread.sleep(10000);
        //onView(withText("Hello world!")).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitId("Hello world!", 10000));
    }

    @Test
    //TODO implement a database to pull from
    //TODO Blocked because dev have not impleented database or auth methods
    //TODO This will
    public void loginWithRegUser_withBadPass() {
        addInputText(R.id.input_password, "12345");
        addInputText(R.id.input_email, "Ringo.Evan@gmail.com");
        login();

        //should this be checking for a toast
        onView(withText("Login Failed")).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        //onView(withText(("Authenticating..."))).check(matches(isDisplayed()));
        //waitId("Hello world!", 5000);

        //onView(isRoot()).perform(waitId("Hello world!", 10000));
    }





}


