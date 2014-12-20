package ch.epfl.sweng.quizapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A {@link QuizClient} implementation that uses a {@link NetworkProvider} to
 * communicate with a SwEng quiz server.
 * @author lcg31439
 *
 */
public class NetworkQuizClient implements QuizClient {
	
	private String serverUrlString;
	private NetworkProvider networkProviderInstance;
	public static final int READ_TIME_OUT = 10000;
	public static final int CONNECT_TIME_OUT = 15000;

    /**
     * Creates a new NetworkQuizClient instance that communicates with a SwEng
     * server at a given location, through a {@link NetworkProvider} object.
     * @param serverUrl the base URL of the SwEng server
     * (e.g., "https://sweng-quiz.appspot.com").
     * @param networkProvider a NetworkProvider object through which to channel
     * the server communication.
     */
    public NetworkQuizClient(String serverUrl, NetworkProvider networkProvider) {

    	this.networkProviderInstance = networkProvider;
    	this.serverUrlString = serverUrl;
    }

	@Override
	public QuizQuestion fetchRandomQuestion() throws QuizClientException {
		
		QuizQuestion quiz = null;
		try {
			HttpURLConnection conn = networkProviderInstance.getConnection(new URL(this.serverUrlString));
	        conn.setReadTimeout(READ_TIME_OUT);
	        conn.setConnectTimeout(CONNECT_TIME_OUT);
	        conn.setRequestMethod("GET");
	        conn.setDoInput(true);
	        // Starts the query
	        conn.connect();
//	        int response = conn.getResponseCode();
	        InputStream is = conn.getInputStream();
	        BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	        String jsonText = readAll(rd);
	        
	        JSONObject j = new JSONObject(jsonText);
	        
	        quiz = QuizQuestion.parseFromJSON(j);
		} catch (IOException  e) {
			throw new QuizClientException(e);
		} catch (JSONException e) {
			throw new QuizClientException(e);			
		}
		
		return quiz;
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
 
 
	public String getServerUrl() {
		return serverUrlString;
	}

	public NetworkProvider getNetworkProvider() {
		return networkProviderInstance;
	}

}