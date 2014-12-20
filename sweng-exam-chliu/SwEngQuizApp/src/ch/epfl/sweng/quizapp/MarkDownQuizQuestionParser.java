package ch.epfl.sweng.quizapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Parses questions in MarkDown format */
public class MarkDownQuizQuestionParser implements QuizQuestionParser {

    @Override
    public QuizQuestion parse(String s) throws QuizQuestionParseException {
        // TODO: complete this method to parse the quiz question.
        // You may use whatever method you like, and can freely
        // add/delete/change code inside this method. Do not change the method
        // signature, though. The following Java API methods might be helpful:
        // - java.util.regex.Pattern#matcher
        // - java.lang.String#split

        // Example code follows...

        // First, split the string into questions and answers. Throw a
        // QuizQuestionParseException if there are extra paragraphs, or not
        // enough.
        String[] parts = s.split("\\n\\n");
        if (parts.length != 2) {
            throw new QuizQuestionParseException();
        }
        String bodyAndAttrs = parts[0];
        String answerList = parts[1];
        String[] answers =  answerList.split("");
        List<String> alist = new ArrayList();
        for(int i = 0; i< answers.length; i++){
        	alist.add(answers[i]);
        }

        return new QuizQuestion(
                0,                                         // ID
                "nobody",                                  // Owner
                "q",                                       // Question text
                alist,  // Answers
                0,                                         // Solution index
                Arrays.asList(new String[] {})             // Tags
                );
    }

}
