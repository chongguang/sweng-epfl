package ch.epfl.sweng.quizapp.test;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import ch.epfl.sweng.quizapp.QuizQuestion;
import junit.framework.TestCase;

/** Tests whether the app correctly handles proper JSON */
public class ProperJSONParsingTest extends TestCase {

    private static final String PROPER_JSON = "{\n"
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
    private static final int EXPECTED_QUESTION_ID = 17005;

    private List<String> properAnswers;
    private List<String> properTags;

    protected void setUp() throws Exception {
        super.setUp();

        properAnswers = new ArrayList<String>();
        properAnswers.add("Chisinau");
        properAnswers.add("Saipan");
        properAnswers.add("St. John's");
        properAnswers.add("Plymouth");

        properTags = new ArrayList<String>();
        properTags.add("capitals");
        properTags.add("geography");
        properTags.add("countries");
    }

    /** test that question ID is correctly parsed */
    public void testProperID() throws JSONException {
        QuizQuestion q = QuizQuestion.parseFromJSON(new JSONObject(PROPER_JSON));
        assertEquals("Question ID does not match",
                EXPECTED_QUESTION_ID, q.getID());
    }

    /** test that question body is correctly parsed */
    public void testProperBody() throws JSONException {
        QuizQuestion q = QuizQuestion.parseFromJSON(new JSONObject(PROPER_JSON));
        assertEquals("Question body does not match",
                "What is the capital of Antigua and Barbuda?", q.getBody());
    }

    /** test that question owner is correctly parsed */
    public void testProperOwner() throws JSONException {
        QuizQuestion q = QuizQuestion.parseFromJSON(new JSONObject(PROPER_JSON));
        assertEquals("Question owner does not match",
                "sweng", q.getOwner());
    }

    /** test that answer list is correctly parsed */
    public void testProperAnswers() throws JSONException {
        QuizQuestion q = QuizQuestion.parseFromJSON(new JSONObject(PROPER_JSON));
        assertEquals("Answers do not match",
                properAnswers, q.getAnswers());
    }

    /** test that solution index is correctly parsed */
    public void testProperSolutionIndex() throws JSONException {
        QuizQuestion q = QuizQuestion.parseFromJSON(new JSONObject(PROPER_JSON));
        assertEquals("Solution index does not match",
                2, q.getSolutionIndex());
    }

    /** test that tag list is correctly parsed */
    public void testProperTags() throws JSONException {
        QuizQuestion q = QuizQuestion.parseFromJSON(new JSONObject(PROPER_JSON));
        assertEquals("Tags do not match",
                properTags, q.getTags());
    }
}
