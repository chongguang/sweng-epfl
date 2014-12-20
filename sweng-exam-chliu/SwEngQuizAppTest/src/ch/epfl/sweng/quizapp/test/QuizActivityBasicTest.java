/*
 * Copyright 2014 EPFL. All rights reserved.
 */

package ch.epfl.sweng.quizapp.test;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isEnabled;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import android.test.ActivityInstrumentationTestCase2;
import ch.epfl.sweng.quizapp.QuizActivity;
import ch.epfl.sweng.quizapp.QuizClient;
import ch.epfl.sweng.quizapp.QuizClientException;
import ch.epfl.sweng.quizapp.QuizQuestion;
import ch.epfl.sweng.quizapp.R;

/** Tests basic functionality of the QuizActivity */
public class QuizActivityBasicTest extends ActivityInstrumentationTestCase2<QuizActivity> {

    public QuizActivityBasicTest() {
        super(QuizActivity.class);
    }

    public void testQuizClientGetterSetter() throws QuizClientException {
        QuizActivity activity = getActivity();
        QuizClient quizClient = new QuizClient() {
            @Override
            public QuizQuestion fetchRandomQuestion() throws QuizClientException {
                return null;
            }
        };

        activity.setQuizClient(quizClient);
        assertSame(quizClient, activity.getQuizClient());
    }

    public void testNextQuestionButtonName() {
        getActivity();
        onView(withId(R.id.nextQuestionButton))
                .check(matches(withText("Next question")));
    }

    public void testFirstQuestionBodyVisible() {
        getActivity();
        onView(withId(R.id.questionBodyView))
                .check(matches(isDisplayed()));
    }

    public void testNextQuestionDisabled() {
        getActivity();
        onView(withId(R.id.nextQuestionButton)).check(matches(not(isEnabled())));
    }
}
