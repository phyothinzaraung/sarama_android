package com.koekoetech.sayarma.model;

import java.util.ArrayList;

public class LessonQuestionAnswersModel {

    private ArrayList<QuestionModel> QuizModels;
    private ArrayList<AnswerModel> AnswerModels;

    public ArrayList<QuestionModel> getQuizModels() {
        return QuizModels;
    }

    public void setQuizModels(ArrayList<QuestionModel> quizModels) {
        QuizModels = quizModels;
    }

    public ArrayList<AnswerModel> getAnswerModels() {
        return AnswerModels;
    }

    public void setAnswerModels(ArrayList<AnswerModel> answerModels) {
        AnswerModels = answerModels;
    }
}
