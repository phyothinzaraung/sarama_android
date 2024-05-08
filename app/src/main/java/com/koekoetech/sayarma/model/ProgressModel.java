package com.koekoetech.sayarma.model;

import com.koekoetech.sayarma.helper.Pageable;

import java.io.Serializable;

import io.realm.RealmObject;

public class ProgressModel extends RealmObject implements Pageable, Serializable {

    private String UserID;
    private String Chapter_ID;
    private String SubChapter_ID;
    private String levelID;
    private boolean isSync = false;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getLevelID() {
        return levelID;
    }

    public void setLevelID(String levelID) {
        this.levelID = levelID;
    }

    public String getChapter_ID() {
        return Chapter_ID;
    }

    public void setChapter_ID(String chapter_ID) {
        Chapter_ID = chapter_ID;
    }

    public String getSubChapter_ID() {
        return SubChapter_ID;
    }

    public void setSubChapter_ID(String subChapter_ID) {
        SubChapter_ID = subChapter_ID;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }
}
