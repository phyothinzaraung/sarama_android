package com.koekoetech.sayarma.model;

import com.koekoetech.sayarma.helper.Pageable;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class QuestionModel extends RealmObject implements Serializable, Pageable {

    @PrimaryKey
    private String QuestionId;
    private String levelID;
    private String SubChapterID;
    private String ChapterTitle;
    private String SubChapterTitle;
    private String QuestionContent;
    private String CreatedDate;
    private int Type;
    private int OrderingID;
    private int Point;
    private String Audio1;
    private String Audio2;
    private String Answers;

    public String getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(String questionId) {
        QuestionId = questionId;
    }

    public String getLevelID() {
        return levelID;
    }

    public void setLevelID(String levelID) {
        this.levelID = levelID;
    }

    public String getSubChapterID() {
        return SubChapterID;
    }

    public void setSubChapterID(String subChapterID) {
        SubChapterID = subChapterID;
    }

    public String getChapterTitle() {
        return ChapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        ChapterTitle = chapterTitle;
    }

    public String getSubChapterTitle() {
        return SubChapterTitle;
    }

    public void setSubChapterTitle(String subChapterTitle) {
        SubChapterTitle = subChapterTitle;
    }

    public String getQuestionContent() {
        return QuestionContent;
    }

    public void setQuestionContent(String questionContent) {
        QuestionContent = questionContent;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getOrderingID() {
        return OrderingID;
    }

    public void setOrderingID(int orderingID) {
        OrderingID = orderingID;
    }

    public int getPoint() {
        return Point;
    }

    public void setPoint(int point) {
        Point = point;
    }

    public String getAudio1() {
        return Audio1;
    }

    public void setAudio1(String audio1) {
        Audio1 = audio1;
    }

    public String getAudio2() {
        return Audio2;
    }

    public void setAudio2(String audio2) {
        Audio2 = audio2;
    }

    public String getAnswers() {
        return Answers;
    }

    public void setAnswers(String answers) {
        Answers = answers;
    }
}
