/*
 * Copyright 2014 EPFL. All rights reserved.
 */

package ch.epfl.sweng.quizapp.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mockito.Mockito;

import ch.epfl.sweng.quizapp.NetworkProvider;
import ch.epfl.sweng.quizapp.NetworkQuizClient;
import ch.epfl.sweng.quizapp.QuizClientException;
import ch.epfl.sweng.quizapp.QuizQuestion;
import ch.epfl.sweng.testing.MockTestCase;

/** Tests the NetworkQuizClient */
public class NetworkQuizClientTest extends MockTestCase {
    private static final int ASCII_SPACE = 0x20;
    private static final int SAMPLE_QUESTION_ID = 17005;
    private static final String JSON_RESPONSE = "{\n"
            + "  \"id\": 17005,\n"
            + "  \"question\": \"What is the capital of Antigua and Barbuda?\",\n"
            + "  \"answers\": [\n"
            + "    \"Chisinau\",\n"
            + "    \"Saipan\",\n"
            + "    \"St. John's\",\n"
            + "    \"Plymouth\"\n"
            + "  ],\n"
            + "  \"solutionIndex\": 2,\n"
            + "  \"tags\": [\n"
            + "    \"capitals\",\n"
            + "    \"geography\",\n"
            + "    \"countries\"\n"
            + "  ],\n"
            + "  \"owner\": \"sweng\"\n"
            + "}\n";
    private static final String JSON_CONTENT_TYPE = "application/json; charset=utf-8";
    private static final String TEXT_CONTENT_TYPE = "text/plain; charset=utf-8";

    private NetworkQuizClient quizClient;
    private HttpURLConnection connection;
    private NetworkProvider networkProvider;

    protected void setUp() throws Exception {
        super.setUp();
        connection = Mockito.mock(HttpURLConnection.class);
        networkProvider = Mockito.mock(NetworkProvider.class);
        Mockito.doReturn(connection).when(networkProvider).getConnection(Mockito.any(URL.class));

        quizClient = new NetworkQuizClient("http://example.com", networkProvider);
    }

    private void configureResponse(int status, String content, String contentType)
        throws IOException {
        InputStream dataStream = new ByteArrayInputStream(content.getBytes());
        Mockito.doReturn(status).when(connection).getResponseCode();
        Mockito.doReturn(dataStream).when(connection).getInputStream();
        Mockito.doReturn(contentType).when(connection).getContentType();
    }

    private void configureCrash(int status) throws IOException {
        InputStream dataStream = Mockito.mock(InputStream.class);
        Mockito.when(dataStream.read())
                .thenReturn(ASCII_SPACE, ASCII_SPACE, ASCII_SPACE, ASCII_SPACE)
                .thenThrow(new IOException());

        Mockito.doReturn(status).when(connection).getResponseCode();
        Mockito.doReturn(dataStream).when(connection).getInputStream();
    }

    /** Test that client fails gracefully on invalid URL */
    public void testInvalidBaseURL() {
        try {
            new NetworkQuizClient("abcdefghijklmn", networkProvider).fetchRandomQuestion();
            fail("Client should fail on invalid URL");
        } catch (IllegalArgumentException e) {
            // good
        } catch (QuizClientException e) {
            // good
        }
    }

    /**
     * Test that the quiz client requests the correct URL.
     * @throws IOException
     * @throws MalformedURLException
     * @throws QuizClientException
     */
    public void testRandomQuestionURL() throws MalformedURLException, IOException, QuizClientException {
        configureResponse(HttpStatus.SC_OK, JSON_RESPONSE, JSON_CONTENT_TYPE);
        quizClient.fetchRandomQuestion();
        Mockito.verify(networkProvider).getConnection(
                new URL("http://example.com/quizquestions/random"));
    }

    /**
     * Test that the quiz client acts correctly when a question is successfully
     * retrieved.
     * @throws IOException
     * @throws QuizClientException
     */
    public void testResponse200OK() throws IOException, QuizClientException {
        configureResponse(HttpStatus.SC_OK, JSON_RESPONSE, JSON_CONTENT_TYPE);
        QuizQuestion question = quizClient.fetchRandomQuestion();
        assertNotNull(question);

        Mockito.verify(connection).getResponseCode();
        Mockito.verify(connection).getInputStream();
    }

    /**
     * Test that the constructed quiz question is correct.
     * @throws QuizClientException
     * @throws IOException
     */
    public void testQuestionDataIsCorrect() throws QuizClientException, IOException {
        // FIXME: This is more like a NetworkQuizClient + QuizQuestion
        // integration test. We should really inject the QuizQuestion factory
        // dependency in the NetworkQuizClient.

        configureResponse(HttpStatus.SC_OK, JSON_RESPONSE, JSON_CONTENT_TYPE);
        QuizQuestion question = quizClient.fetchRandomQuestion();

        List<String> answers = new ArrayList<String>();
        answers.add("Chisinau");
        answers.add("Saipan");
        answers.add("St. John's");
        answers.add("Plymouth");

        List<String> tags = new ArrayList<String>();
        tags.add("capitals");
        tags.add("geography");
        tags.add("countries");

        assertEquals(SAMPLE_QUESTION_ID, question.getID());
        assertEquals("What is the capital of Antigua and Barbuda?", question.getBody());
        assertEquals(answers, question.getAnswers());
        assertEquals(2, question.getSolutionIndex());
        assertEquals(tags, question.getTags());
        assertEquals("sweng", question.getOwner());
    }

    public void testResponse404NotFound() throws IOException {
        configureResponse(HttpStatus.SC_NOT_FOUND,
                "Could not find the quiz question.", TEXT_CONTENT_TYPE);
        try {
            quizClient.fetchRandomQuestion();
            fail("Did not raise QuizClientException");
        } catch (QuizClientException e) {
            // good
        }

        Mockito.verify(connection).getResponseCode();
        Mockito.verify(connection, Mockito.never()).getInputStream();
    }

    /** Test that client throws QuizClientException when connection is broken */
    public void testResponseConnectionBroken() throws IOException {
        configureCrash(HttpStatus.SC_OK);
        try {
            quizClient.fetchRandomQuestion();
            fail("Did not raise QuizClientException");
        } catch (QuizClientException e) {
            // good
        }
    }

    public void testInvalidJSON() throws IOException {
        configureResponse(HttpStatus.SC_OK, "This is some invalid JSON.", JSON_CONTENT_TYPE);
        try {
            quizClient.fetchRandomQuestion();
            fail("Did not raise QuizClientException");
        } catch (QuizClientException e) {
            // good
        }
    }

    /** Test that the JSON string can be arbitrarily long */
    public void testLongJSON() throws JSONException, IOException, QuizClientException {
        String longString = "test";
        final int logOfStringLength = 19;
        for (int i = 0; i < logOfStringLength; ++i) {
            longString = longString + longString;
        }

        JSONArray answers = new JSONArray();
        final int nAnswers = 20000;
        for (int i = 0; i < nAnswers; ++i) {
            answers.put("test" + i);
        }

        JSONArray tags = new JSONArray();
        tags.put("a");
        tags.put("b");

        JSONObject json = new JSONObject();
        json.put("id", SAMPLE_QUESTION_ID);
        json.put("question", longString);
        json.put("owner", "sweng");
        json.put("answers", answers);
        json.put("solutionIndex", 0);
        json.put("tags", tags);

        configureResponse(HttpStatus.SC_OK, json.toString(), JSON_CONTENT_TYPE);

        QuizQuestion question = quizClient.fetchRandomQuestion();
        assertEquals("Cannot handle long strings", longString, question.getBody());
        assertEquals(nAnswers, question.getAnswers().size());
    }

}
