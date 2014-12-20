/*
 * Copyright 2014 EPFL. All rights reserved.
 */

package ch.epfl.sweng.quizapp.test;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.quizapp.QuizQuestion;
import junit.framework.TestCase;

/** Tests the QuizQuestion class. */
public class QuizQuestionTest extends TestCase {
    private static final int SAMPLE_QUESTION_ID = 12;
    private List<String> answerList;
    private List<String> tagList;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        answerList = new ArrayList<String>();
        answerList.add("answer1");
        answerList.add("answer2");
        answerList.add("answer3");

        tagList = new ArrayList<String>();
        tagList.add("tag1");
        tagList.add("tag2");
    }

    public void testIDAccess() {
        QuizQuestion question = new QuizQuestion(SAMPLE_QUESTION_ID, "test", "qbody",
                answerList, 1, tagList);

        assertEquals("Mismatched question ID", SAMPLE_QUESTION_ID, question.getID());
    }

    public void testOwnerAccess() {
        QuizQuestion question = new QuizQuestion(SAMPLE_QUESTION_ID, "test", "qbody",
                answerList, 1, tagList);

        assertEquals("Mismatched owner", "test", question.getOwner());
    }

    public void testAnswerListAccess() {
        QuizQuestion question = new QuizQuestion(SAMPLE_QUESTION_ID, "test", "qbody",
                answerList, 1, tagList);
        assertEquals("Mismatched answer list", answerList, question.getAnswers());
    }

    public void testSolutionIndexAccess() {
        QuizQuestion question = new QuizQuestion(SAMPLE_QUESTION_ID, "test", "qbody",
                answerList, 1, tagList);
        assertEquals("Mismatched solution index", 1, question.getSolutionIndex());
    }

    public void testTagListAccess() {
        QuizQuestion question = new QuizQuestion(SAMPLE_QUESTION_ID, "test", "qbody",
                answerList, 1, tagList);
        assertEquals("Mismatched tag list", tagList, question.getTags());
    }

    public void testSolutionIndexOutOfRange() {
        try {
            final int outOfRangeSolutionIndex = 100;
            new QuizQuestion(SAMPLE_QUESTION_ID, "test", "qbody", answerList, outOfRangeSolutionIndex, tagList);
            fail("Solution index larger than answer list size");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    public void testSolutionIndexNegative() {
        try {
            new QuizQuestion(SAMPLE_QUESTION_ID, "test", "qbody", answerList, -1, tagList);
            fail("Negative solution index");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    public void testSolutionIndexArraySize() {
        try {
            final int solutionIndex = 3;
            new QuizQuestion(SAMPLE_QUESTION_ID, "test", "qbody", answerList, solutionIndex, tagList);
            fail("Solution index should be less than answer list size");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    public void testArgumentImmutable() {
        QuizQuestion question = new QuizQuestion(SAMPLE_QUESTION_ID, "test", "qbody", answerList, 1, tagList);
        answerList.add("bogus");
        tagList.add("bogus");

        assertFalse("Answers should be copied", answerList.equals(question.getAnswers()));
        assertFalse("Tags should be copied", tagList.equals(question.getTags()));
    }

    public void testMemberImmutable() {
        QuizQuestion question = new QuizQuestion(SAMPLE_QUESTION_ID, "test", "qbody", answerList, 1, tagList);
        try {
            question.getAnswers().add("bogus");
        } catch (UnsupportedOperationException e) {
            // good
        }

        try {
            question.getTags().add("bogus");
        } catch (UnsupportedOperationException e) {
            // good
        }

        assertEquals("Answers should be copied", answerList, question.getAnswers());
        assertEquals("Tags should be copied", tagList, question.getTags());
    }

    public void testTooFewAnswers() {
        List<String> shortAnswerList = new ArrayList<String>();
        shortAnswerList.add("answer");

        try {
            new QuizQuestion(SAMPLE_QUESTION_ID, "test", "qbody", shortAnswerList, 0, tagList);
            fail("Question should have at least two answers");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    public void testNullBody() {
        try {
            new QuizQuestion(SAMPLE_QUESTION_ID, "test", null, answerList, 1, tagList);
            fail("Question must have non-null body");
        } catch (IllegalArgumentException e) {
            // success
        } catch (NullPointerException e) {
            // success
        }
    }

    public void testNullOwner() {
        try {
            new QuizQuestion(SAMPLE_QUESTION_ID, null, "qbody", answerList, 1, tagList);
            fail("Question must have non-null owner");
        } catch (IllegalArgumentException e) {
            // success
        } catch (NullPointerException e) {
            // success
        }
    }
}
