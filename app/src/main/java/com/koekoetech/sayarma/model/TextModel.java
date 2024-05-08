package com.koekoetech.sayarma.model;

public class TextModel {

    private String key;
    private String english;
    private String myanmar;

    public TextModel(){}

    public TextModel(String key, String english, String myanmar){
        this.key = key;
        this.english = english;
        this.myanmar = myanmar;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getMyanmar() {
        return myanmar;
    }

    public void setMyanmar(String myanmar) {
        this.myanmar = myanmar;
    }
}
