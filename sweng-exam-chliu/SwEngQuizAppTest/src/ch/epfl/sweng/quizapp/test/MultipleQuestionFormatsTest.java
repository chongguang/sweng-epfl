package ch.epfl.sweng.quizapp.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpStatus;
import org.mockito.Mockito;

import ch.epfl.sweng.quizapp.NetworkProvider;
import ch.epfl.sweng.quizapp.NetworkQuizClient;
import ch.epfl.sweng.quizapp.NoSuchQuestionFormatException;
import ch.epfl.sweng.quizapp.QuizClientException;
import ch.epfl.sweng.quizapp.QuizQuestion;
import ch.epfl.sweng.quizapp.QuizQuestionParseException;
import ch.epfl.sweng.quizapp.QuizQuestionParserFactory;
import ch.epfl.sweng.testing.MockTestCase;

public class MultipleQuestionFormatsTest extends MockTestCase {
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
    private static final String MARK_DOWN_CONTENT_TYPE = "text/markdown; charset=utf-8";

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
    


    /** Test that the JSON Parser can deal with the correct case */ 
    public void testJSONParserCorrectCase() throws IOException, NoSuchQuestionFormatException, QuizQuestionParseException {

        configureResponse(HttpStatus.SC_OK, JSON_RESPONSE, JSON_CONTENT_TYPE);
        QuizQuestion q = QuizQuestionParserFactory.parserForContentType(connection.getContentType()).parse(JSON_RESPONSE);
        assertNotNull(q);
        assertEquals(q.getBody(),"What is the capital of Antigua and Barbuda?");
        assertEquals(q.getAnswers().size(), 4);
        
    }
   


    /** Test that the JSON parser get execptions for wrong content type */
    public void testWrongContentTypeOfJSONParser() throws IOException {

      configureResponse(HttpStatus.SC_OK, JSON_RESPONSE, "wrong content type");
      try {
    	  quizClient.fetchRandomQuestion();
          fail("Did not raise QuizClientException");
      } catch (QuizClientException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
       
    }

    /** Test that the MARKDOWN Parser deal with the correct case */
    public void testMarkDownCorrectCase() throws IOException, QuizQuestionParseException, NoSuchQuestionFormatException {
	  
	  String s = "{\n"
	            + "  \"id\": 17005,\n"
	            + "  \"question\": \"What is the answer to life, the universe and everything?\",\n"
	            + "  \"answers\": [\n"
	            + "    \"We don't know\",\n"
	            + "    \"42\",\n"
	            + "    \"six times nine (if you compute in base 13)\",\n"
	            + "    \"The mice know, but they won't tell.\"\n"
	            + "  ],\n"
	            + "  \"solutionIndex\": 2,\n"
	            + "  \"tags\": [],\n"
	            + "  \"owner\": \"sweng\"\n"
	            + "}\n";

      configureResponse(HttpStatus.SC_OK, s, MARK_DOWN_CONTENT_TYPE);
      QuizQuestion q = QuizQuestionParserFactory.parserForContentType(connection.getContentType()).parse(s);
      
      assertNotNull(q);
      assertEquals(q.getBody(),"What is the answer to life, the universe and everything?");
      assertEquals(q.getAnswers().size(), 4);
	  
  }
    
}