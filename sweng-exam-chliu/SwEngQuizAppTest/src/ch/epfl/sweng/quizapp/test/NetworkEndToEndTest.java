/*
 * Copyright 2014 EPFL. All rights reserved.
 */

package ch.epfl.sweng.quizapp.test;

import junit.framework.TestCase;
import ch.epfl.sweng.quizapp.DefaultNetworkProvider;
import ch.epfl.sweng.quizapp.NetworkProvider;
import ch.epfl.sweng.quizapp.NetworkQuizClient;
import ch.epfl.sweng.quizapp.QuizClient;
import ch.epfl.sweng.quizapp.QuizClientException;
import ch.epfl.sweng.quizapp.QuizQuestion;

/** Tests whether we can interact with the real quiz server. */
public class NetworkEndToEndTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testGetRandomQuestion() throws QuizClientException {
        NetworkProvider networkProvider = new DefaultNetworkProvider();
        QuizClient quizClient = new NetworkQuizClient("https://sweng-quiz.appspot.com", networkProvider);
        QuizQuestion quizQuestion = quizClient.fetchRandomQuestion();

        assertTrue("Unexpected ID", quizQuestion.getID() > 0);
        assertTrue("Unexpected owner", quizQuestion.getOwner().length() > 0);
        assertTrue("Unexpected body", quizQuestion.getBody().length() > 0);
        assertTrue("Unexpected answer length", quizQuestion.getAnswers().size() >= 2);
        assertTrue("Unexpected solution index",
                quizQuestion.getSolutionIndex() >= 0);
        assertTrue("Unexpected solution index",
                quizQuestion.getSolutionIndex() < quizQuestion.getAnswers().size());
    }
}
