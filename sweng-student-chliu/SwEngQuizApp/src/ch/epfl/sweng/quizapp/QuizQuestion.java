package ch.epfl.sweng.quizapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Encapsulates the data in a quiz question returned by the SwEng server.
 * @author lcg31439
 *
 */
public class QuizQuestion {
	
	private long quizId;
	private String quizOwner;
	private String quizBody;
	private List<String> quizAnswers;
	private int quizSolutionIndex;
	private List<String> quizTags;

    /**
     * Creates a new QuizQuestion instance from the question elements provided
     * as arguments.
     * @param id the numeric ID of the question
     * @param owner the name of the owner of the question
     * @param body the question body
     * @param answers a list of two or more possible question answers
     * @param solutionIndex the index in the answer list of the correct answer
     * @param tags a list of zero or more tags associated to the question
     */
    public QuizQuestion(long id, String owner, String body, List<String> answers,
            int solutionIndex, List<String> tags) throws IllegalArgumentException {
    	if (owner == null || body == null || solutionIndex > answers.size() || solutionIndex < 0 || 2 > answers.size()) {
    		throw new IllegalArgumentException("owner can't be null");
    	}
        this.quizId = id;
        this.quizOwner = owner;
        this.quizBody = body;
        this.quizAnswers = answers;
        this.quizSolutionIndex = solutionIndex;
        this.quizTags = tags;
    }

    /**
     * Returns the question ID.
     */
    public long getID() {
        return this.quizId;
    }

    /**
     * Returns the question owner.
     */
    public String getOwner() {
        return this.quizOwner;
    }

    /**
     * Returns the question body.
     */
    public String getBody() {
        return this.quizBody;
    }

    /**
     * Returns a list of the question answers.
     */
    public List<String> getAnswers() {
        return this.quizAnswers;
    }

    /**
     * Returns the index of the solution in the answer list.
     */
    public int getSolutionIndex() {
        return this.quizSolutionIndex;
    }

    /**
     * Returns a (possibly empty) list of question tags.
     */
    public List<String> getTags() {
        return this.quizTags;
    }

    /**
     * Creates a new QuizQuestion object by parsing a JSON object in the format
     * returned by the quiz server.
     * @param jsonObject a {@link JSONObject} encoding.
     * @return a new QuizQuestion object.
     * @throws JSONException in case of malformed JSON.
     */
    public static QuizQuestion parseFromJSON(JSONObject jsonObject) throws JSONException {
    	
    	long id;
    	String owner;
    	String body;    	
    	List<String> answers = new ArrayList<String>();
    	int solutionIndex;
    	List<String> tags = new ArrayList<String>();
    	
    	try {    	
	    	id = Long.valueOf(jsonObject.getString("id")).longValue();
	    	
	    	owner = jsonObject.getString("owner");
	    	
	    	body = jsonObject.getString("question");
	    	
	    	solutionIndex = Integer.valueOf(jsonObject.getString("solutionIndex"));
	    	
	    	JSONArray tagsArr = jsonObject.getJSONArray("answers");
	    	
	    	answers = new ArrayList<String>(); 
	    	if (tagsArr != null) {
	    		int len = tagsArr.length();
	    		for (int i=0; i<len; i++) {
	    			answers.add(tagsArr.get(i).toString());
	    		} 
	    	}
	    	
    	} catch (JSONException e) {
    		throw new JSONException("malformed JSON object: " + e);
    	}
    	
    	QuizQuestion quiz = new QuizQuestion(id, owner, body, answers, solutionIndex, tags);
		return quiz;
    }
}