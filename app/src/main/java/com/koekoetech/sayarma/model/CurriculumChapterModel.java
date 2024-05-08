package com.koekoetech.sayarma.model;

import com.koekoetech.sayarma.helper.Pageable;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CurriculumChapterModel extends RealmObject implements Serializable, Pageable {

    @PrimaryKey
    private String ChapterID;
    private String levelID;
    private String Title;
    private String Content;
    private String Audio;
    private boolean isDeleted;
    private String CreatedDate;
    private String Createdby;
    private String UpdatedDate;
    private String Updatedby;
    private int OrderingID;

    public String getChapterID() {
        return ChapterID;
    }

    public void setChapterID(String chapterID) {
        ChapterID = chapterID;
    }

    public String getLevelID() {
        return levelID;
    }

    public void setLevelID(String levelID) {
        this.levelID = levelID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getAudio() {
        return Audio;
    }

    public void setAudio(String audio) {
        Audio = audio;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getCreatedby() {
        return Createdby;
    }

    public void setCreatedby(String createdby) {
        Createdby = createdby;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }

    public String getUpdatedby() {
        return Updatedby;
    }

    public void setUpdatedby(String updatedby) {
        Updatedby = updatedby;
    }

    public int getOrderingID() {
        return OrderingID;
    }

    public void setOrderingID(int orderingID) {
        OrderingID = orderingID;
    }
}
