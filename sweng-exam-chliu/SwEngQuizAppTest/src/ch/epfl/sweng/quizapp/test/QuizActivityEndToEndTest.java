/*
 * Copyright 2014 EPFL. All rights reserved.
 */

package ch.epfl.sweng.quizapp.test;

import static ch.epfl.sweng.testing.ViewMatchers.withPaintFlag;
import static ch.epfl.sweng.testing.ViewMatchers.withTextColor;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isEnabled;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withTagValue;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import android.graphics.Color;
import android.graphics.Paint;
import android.test.ActivityInstrumentationTestCase2;
import ch.epfl.sweng.quizapp.QuizActivity;
import ch.epfl.sweng.quizapp.R;

import com.google.android.apps.common.testing.ui.espresso.NoMatchingViewException;

/** Tests the GUI against the real server */
public class QuizActivityEndToEndTest extends
        ActivityInstrumentationTestCase2<QuizActivity> {

    private static final int N_QUESTIONS_TO_TEST = 5;

    public QuizActivityEndToEndTest() {
        super(QuizActivity.class);
    }

    @SuppressWarnings("unchecked")
    public void testFlipThroughQuestions() {
        getActivity();

        for (int count = 0; count < N_QUESTIONS_TO_TEST; ++count) {
            onView(withId(R.id.questionBodyView))
                    .check(matches(isDisplayed()));
            onView(withId(R.id.nextQuestionButton))
                    .check(matches(allOf(withText("Next question"), not(isEnabled()))));

            for (int i = 0; true; ++i) {
                try {
                    onView(withTagValue(is((Object) i)))
                            .perform(click())
                            .check(matches(anyOf(
                                    allOf(withTextColor(Color.RED), withPaintFlag(Paint.STRIKE_THRU_TEXT_FLAG)),
                                    withTextColor(Color.GREEN))));
                } catch (NoMatchingViewException e) {
                    // Stop clicking, no more radio buttons to check
                    break;
                }
            }

            onView(withId(R.id.nextQuestionButton))
                    .check(matches(isEnabled()))
                    .perform(click())
                    .check(matches(not(isEnabled())));
        }
    }

}
