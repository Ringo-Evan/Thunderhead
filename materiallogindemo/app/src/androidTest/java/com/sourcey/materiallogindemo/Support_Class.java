package com.sourcey.materiallogindemo;

import android.support.design.widget.TextInputLayout;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.espresso.util.TreeIterables;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.security.cert.CertPathChecker;
import java.util.concurrent.TimeoutException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class Support_Class {
    public Support_Class()
    {}

    //TODO Change to passing ViewInteration as parameter?
    public static void checkPassWarning(){
        ViewInteraction passInputEdit = onView(withId(R.id.input_password));
        passInputEdit.check(matches(hasErrorText("between 4 and 10 alphanumeric characters")));
    }

    public static void clearInputText(int textView){
        //Todo Make this a method?
        ViewInteraction viewInputEdit = onView(withId(textView));
        viewInputEdit.perform(replaceText("Delete Me"),closeSoftKeyboard());
        viewInputEdit.perform(clearText());
    }

    public static void login(){
        ViewInteraction loginButton = onView(withId(R.id.btn_login));
        loginButton.perform(click());
    }

    public static void addInputText(int textView, String userString) {
        ViewInteraction viewTextEdit = onView(withId(textView));
        viewTextEdit.perform(replaceText(userString));
    }

    public static void checkEmailWarning() {
        ViewInteraction emailInputEdit = onView(withId(R.id.input_email));
        emailInputEdit.check(matches(hasErrorText("enter a valid email address")));

    }
    //For Some reason with hint seems not to work with textinputlayouts?
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

    public static ViewAction waitId(final String viewText, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for a specific view with id <" + viewText + "> during " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;
                final Matcher<View> viewMatcher = withText(viewText);

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        // found view with required ID
                        if (viewMatcher.matches(child)) {
                            return;
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50);
                }
                while (System.currentTimeMillis() < endTime);

                // timeout happens
                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException())
                        .build();
            }
        };
    }
}
