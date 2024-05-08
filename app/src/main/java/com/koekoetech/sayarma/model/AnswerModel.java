package com.koekoetech.sayarma.model;

import com.koekoetech.sayarma.helper.Pageable;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AnswerModel extends RealmObject implements Serializable, Pageable {

    @PrimaryKey
    private String AnswerId;
    private String QuestionId;
    private String Answer;
    private boolean IsRight;
    private int Point;
    private int OrderingID;
    private String Tips;

    public String getAnswerId() {
        return AnswerId;
    }

    public void setAnswerId(String answerId) {
        AnswerId = answerId;
    }

    public String getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(String questionId) {
        QuestionId = questionId;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public boolean isRight() {
        return IsRight;
    }

    public void setRight(boolean right) {
        IsRight = right;
    }

    public int getPoint() {
        return Point;
    }

    public void setPoint(int point) {
        Point = point;
    }

    public int getOrderingID() {
        return OrderingID;
    }

    public void setOrderingID(int orderingID) {
        OrderingID = orderingID;
    }

    public String getTips() {
        return Tips;
    }

    public void setTips(String tips) {
        Tips = tips;
    }
}
