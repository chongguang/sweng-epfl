package ch.epfl.sweng.quizapp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author lcg31439
 *
 */
public class JsonQuizQuestionParser implements QuizQuestionParser {
	
	//public final static String URL = "https://sweng-quiz.appspot.com";

	@Override
	public QuizQuestion parse(String s) throws QuizQuestionParseException {


		//NetworkQuizClient client = new NetworkQuizClient(URL, new DefaultNetworkProvider());
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(s);
		} catch (JSONException e) {
			throw new QuizQuestionParseException();
		}
        try {
			return QuizQuestion.parseFromJSON(jsonObject);
		} catch (JSONException e) {
			throw new QuizQuestionParseException();
		}
	}

}
