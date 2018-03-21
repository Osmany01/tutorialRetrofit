package com.example.osmany.comvogellajavaretrofitgerrit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by osmany on 21/3/18.
 */

public class FakeDataProvider {

    public static List<Question> getQuestions(){
        List<Question> questions =  new ArrayList<>();

        for (int i = 0; i <10 ; i++) {
            Question question = new Question();
            question.questionId = String.valueOf(i);
            question.setTitle(String.valueOf(i));
            question.setBody(String.valueOf(i)+" body");
            questions.add(question);
        }
        return questions;
    }

    public static List<Answer> getAnswers(){
        List<Answer> answers = new ArrayList<>();
        for (int i = 0; i<10; i++) {
            Answer answer = new Answer();
            answer.answerId = i;
            answer.accepted = false;
            answer.score = i;
            answers.add(answer);
        }
        return answers;
    }
}
