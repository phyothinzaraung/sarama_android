package com.koekoetech.sayarma.model;

import com.koekoetech.sayarma.helper.Pageable;

import java.io.Serializable;

import io.realm.RealmObject;

public class ScoreModel extends RealmObject implements Pageable, Serializable {

    private String ScoreID;
    private String LessonID;
    private String Type;
    private int ScorePoint;
    private String UserID;
    private boolean isSync = false;

    public String getScoreID() {
        return ScoreID;
    }

    public void setScoreID(String scoreID) {
        ScoreID = scoreID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getScorePoint() {
        return ScorePoint;
    }

    public void setScorePoint(int scorePoint) {
        ScorePoint = scorePoint;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getLessonID() {
        return LessonID;
    }

    public void setLessonID(String lessonID) {
        LessonID = lessonID;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }
}
