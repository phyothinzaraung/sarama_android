package com.koekoetech.sayarma.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.koekoetech.sayarma.helper.Pageable;

import java.io.Serializable;

public class NotificationModel implements Pageable, Serializable {

    @Expose
    @SerializedName("NotificationID")
    private String NotificationID;

    @Expose
    @SerializedName("ContentID")
    private String ContentID;

    @Expose
    @SerializedName("Title")
    private String Title;

    @Expose
    @SerializedName("CreatedDate")
    private String CreatedDate;

    @Expose
    @SerializedName("UpdatedDate")
    private String UpdatedDate;

    @Expose
    @SerializedName("IsNew")
    private boolean IsNew;

    @Expose
    @SerializedName("isDeleted")
    private boolean isDeleted;

    public String getNotificationID() {
        return NotificationID;
    }

    public void setNotificationID(String notificationID) {
        NotificationID = notificationID;
    }

    public String getContentID() {
        return ContentID;
    }

    public void setContentID(String contentID) {
        ContentID = contentID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }

    public boolean isNew() {
        return IsNew;
    }

    public void setNew(boolean aNew) {
        IsNew = aNew;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
