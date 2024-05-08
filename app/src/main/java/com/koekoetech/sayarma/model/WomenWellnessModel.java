package com.koekoetech.sayarma.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.koekoetech.sayarma.helper.Pageable;

import java.io.Serializable;
import java.util.List;

public class WomenWellnessModel implements Pageable, Serializable {

    @Expose
    @SerializedName("WomensAwarenessID")
    private String WomensAwarenessID;

    @Expose
    @SerializedName("Title")
    private String Title;

    @Expose
    @SerializedName("photos")
    private List<String> photos;

    public String getWomensAwarenessID() {
        return WomensAwarenessID;
    }

    public void setWomensAwarenessID(String womensAwarenessID) {
        WomensAwarenessID = womensAwarenessID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
