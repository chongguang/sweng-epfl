package ch.epfl.sweng.quizapp;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * @author lcg31439
 *
 */
public class QuizActivity extends Activity {
	
	private QuizQuestion currentQuiz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		
        // Gets the URL from the UI's text field.
        ConnectivityManager connMgr = (ConnectivityManager) 
            getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
        	try {
				currentQuiz = new DownloadWebpageTask().
						execute("https://sweng-quiz.appspot.com/quizquestions/random").get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();				
			}
        } else {
        	try {
				throw new QuizClientException("Connection problem.");
			} catch (QuizClientException e) {
				e.printStackTrace();
			}
        }
        
	}
	
	public void nestQuestionButtonClickHandler(View view) throws QuizClientException {
		
        // Gets the URL from the UI's text field.
        ConnectivityManager connMgr = (ConnectivityManager) 
            getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
        	try {
				currentQuiz = new DownloadWebpageTask().
				execute("https://sweng-quiz.appspot.com/quizquestions/random").get();
			} catch (InterruptedException e) {
				throw new QuizClientException("Fail to retrive quiz.");
			} catch (ExecutionException e) {
				throw new QuizClientException("Fail to retrive quiz.");				
			}
        } else {
        	throw new QuizClientException("Connection problem.");
        }
		
	}

    // Uses AsyncTask to create a task away from the main UI thread. This task takes a 
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    /**
     * @author lcg31439
     *
     */
    private class DownloadWebpageTask extends AsyncTask<String, Void, QuizQuestion> {
    	@Override
    	protected QuizQuestion doInBackground(String... urls) {
    		QuizQuestion quiz = null;
    		try {
    			quiz = downloadUrl(urls[0]);
    		} catch (IOException e) {
    			try {
    				throw new IOException("Unable to retrieve web page. URL may be invalid.");
    			} catch (IOException e1) {
    				e1.printStackTrace();
    			}
    		}
    		return quiz;
    	}
    	
    	// onPostExecute displays the results of the AsyncTask.
    	@Override
    	protected void onPostExecute(QuizQuestion quiz) {
    		
    		TextView textView = (TextView) findViewById(R.id.questionBodyView);    		
    		textView.setText(quiz.getBody());
    		
    		Button nextQuestionButton = (Button) findViewById(R.id.nextQuestionButton);
    		nextQuestionButton.setEnabled(false);
    		
    		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioButtonGroup);
    		radioGroup.clearCheck();
    		radioGroup.removeAllViews();
    		
    		List<String> answers = quiz.getAnswers();
    		
    		for (int i=0; i < answers.size(); i++) {
    			addButton(radioGroup, i, answers.get(i));
    		}
    	}
    }
    
    private void addButton(RadioGroup group, int id, String text) {
        final RadioButton button = new RadioButton(this);
        button.setTag(currentQuiz);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
        		int id = v.getId();
        	   	RadioButton buttonView = (RadioButton) findViewById(id);
        	   	Button nextQuestionButton = (Button) findViewById(R.id.nextQuestionButton);
        	   	
        	   	int solo = (int) ((QuizQuestion) button.getTag()).getSolutionIndex();
           	    
           	    if (id == solo) {
           	   	    buttonView.setTextColor(Color.GREEN);
           		   	nextQuestionButton.setEnabled(true);
           	    } else {
           	   	    buttonView.setTextColor(Color.RED);  
           	   	    buttonView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
           	    }
            }
        });
        
		button.setId(id);
		button.setText(text);
		button.setTextColor(Color.BLACK);
		button.setChecked(false);
		button.setPaintFlags(button.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
		group.addView(button);
    }
    
    
    private QuizQuestion downloadUrl(String myurl) throws IOException {
    	
    	NetworkQuizClient networkQuizClient = new NetworkQuizClient(myurl, new DefaultNetworkProvider());
    	QuizQuestion quiz = null;
    	try {
    		quiz = networkQuizClient.fetchRandomQuestion();
    	} catch (QuizClientException e) {
    		e.printStackTrace();
    	}    	
    	return quiz;
    }
    
    public QuizQuestion getCurrentQuiz() {
    	return currentQuiz;
    }
	
}
