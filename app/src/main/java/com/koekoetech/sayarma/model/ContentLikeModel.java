package com.koekoetech.sayarma.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.koekoetech.sayarma.helper.Pageable;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ContentLikeModel extends RealmObject implements Serializable, Pageable {

    @PrimaryKey
    @Expose
    @SerializedName("likeID")
    private String likeID;

    @Expose
    @SerializedName("ContentID")
    private String ContentID;

    @Expose
    @SerializedName("UserID")
    private String UserID;

    @Expose
    @SerializedName("isLike")
    private boolean isLike;

    @Expose
    @SerializedName("AccessTime")
    private String AccessTime;

    public String getLikeID() {
        return likeID;
    }

    public void setLikeID(String likeID) {
        this.likeID = likeID;
    }

    public String getContentID() {
        return ContentID;
    }

    public void setContentID(String contentID) {
        ContentID = contentID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public String getAccessTime() {
        return AccessTime;
    }

    public void setAccessTime(String accessTime) {
        AccessTime = accessTime;
    }
}
