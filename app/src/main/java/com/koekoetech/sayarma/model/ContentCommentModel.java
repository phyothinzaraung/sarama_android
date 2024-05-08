package com.koekoetech.sayarma.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.koekoetech.sayarma.helper.Pageable;

import java.io.Serializable;

import io.realm.RealmObject;

public class ContentCommentModel  extends RealmObject implements Serializable, Pageable {
    @Expose
    @SerializedName("CommentID")
    private String CommentID;

    @Expose
    @SerializedName("ContentID")
    private String ContentID;

    @Expose
    @SerializedName("UserID")
    private String UserID;

    @Expose
    @SerializedName("NameinEnglish")
    private String NameinEnglish;

    @Expose
    @SerializedName("Username")
    private String Username;

    @Expose
    @SerializedName("CommentText")
    private String CommentText;

    @Expose
    @SerializedName("AccessTime")
    private String AccessTime;

    public String getCommentID() {
        return CommentID;
    }

    public void setCommentID(String commentID) {
        CommentID = commentID;
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

    public String getNameinEnglish() {
        return NameinEnglish;
    }

    public void setNameinEnglish(String nameinEnglish) {
        NameinEnglish = nameinEnglish;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getCommentText() {
        return CommentText;
    }

    public void setCommentText(String commentText) {
        CommentText = commentText;
    }

    public String getAccessTime() {
        return AccessTime;
    }

    public void setAccessTime(String accessTime) {
        AccessTime = accessTime;
    }

}
