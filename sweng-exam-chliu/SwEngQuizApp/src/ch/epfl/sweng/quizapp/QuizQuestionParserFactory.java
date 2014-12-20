package ch.epfl.sweng.quizapp;

/**
 * This factory class knows about supported question formats.
 */
public class QuizQuestionParserFactory {
    /**
     * Obtains a parser for the given MIME type.
     *
     * @param contentType
     *            The MIME type that the parser should understand, e.g.,
     *            "application/json"
     * @return A parser for the given contentType
     * @throws NoSuchQuestionFormatException
     *             If no known parser supports this content type
     */
	

    private static final String JSON_CONTENT_TYPE = "application/json; charset=utf-8";
    private static final String MARK_DOWN_CONTENT_TYPE = "text/markdown; charset=utf-8";
    
    public static QuizQuestionParser parserForContentType(String contentType)
            throws NoSuchQuestionFormatException {
        // TODO: Implement formats
    	if (contentType.equals(JSON_CONTENT_TYPE)) {
    		return new JsonQuizQuestionParser();
    	}
    	
    	if (contentType.equals(MARK_DOWN_CONTENT_TYPE)) {
    		return new MarkDownQuizQuestionParser();
    	}
        throw new NoSuchQuestionFormatException();
    }
}
