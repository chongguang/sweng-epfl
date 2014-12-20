/*
 * Copyright 2014 EPFL. All rights reserved.
 */

package ch.epfl.sweng.quizapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A {@link QuizClient} implementation that uses a {@link NetworkProvider} to
 * communicate with a SwEng quiz server.
 *
 */
public class NetworkQuizClient implements QuizClient {
    private final String mServerUrl;
    private final NetworkProvider mNetworkProvider;
    

    private final static int HTTP_SUCCESS_START = 200;
    private final static int HTTP_SUCCESS_END = 299;

    /**
     * Creates a new NetworkQuizClient instance that communicates with a SwEng
     * server at a given location, through a {@link NetworkProvider} object.
     * @param serverUrl the base URL of the SwEng server
     * (e.g., "https://sweng-quiz.appspot.com").
     * @param networkProvider a NetworkProvider object through which to channel
     * the server communication.
     */
    public NetworkQuizClient(String serverUrl, NetworkProvider networkProvider) {
        mServerUrl = serverUrl;
        mNetworkProvider = networkProvider;
    }

    public QuizQuestion fetchRandomQuestion() throws QuizClientException {
        try {
            URL url = new URL(mServerUrl + "/quizquestions/random");
            HttpURLConnection conn = mNetworkProvider.getConnection(url);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            int response = conn.getResponseCode();
            if (response < HTTP_SUCCESS_START || response > HTTP_SUCCESS_END) {
                throw new QuizClientException("Invalid HTTP response code");
            }
            
            //QuizQuestionParserFactory parserFactory = new QuizQuestionParserFactory();
            QuizQuestion q = QuizQuestionParserFactory.parserForContentType(conn.getContentType()).parse(fetchContent(conn));
            if (q.equals(null)){
                throw new QuizClientException("Invalid HTTP response code");            	
            } else {
            	return q;
            }
            /*JSONObject jsonObject = new JSONObject(fetchContent(conn));
            return QuizQuestion.parseFromJSON(jsonObject);*/
        } catch (IOException e) {
            throw new QuizClientException(e);
        } catch (NoSuchQuestionFormatException e) {
            throw new QuizClientException(e);
		} catch (QuizQuestionParseException e) {
            throw new QuizClientException(e);
		}
    }

    private String fetchContent(HttpURLConnection conn) throws IOException {
        // Credits go to http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
        InputStream is = conn.getInputStream();
        try {
            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        } finally {
            is.close();
        }
    }
}
