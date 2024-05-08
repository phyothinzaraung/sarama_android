package com.koekoetech.sayarma.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.koekoetech.sayarma.helper.Pageable;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginViewModel implements Pageable, Serializable {

    @Expose
    @SerializedName("ProgressModels")
    private ArrayList<ProgressModel> progressModel;

    @Expose
    @SerializedName("ScoreModels")
    private ArrayList<ScoreModel> scoreModel;

    @Expose
    @SerializedName("userModel")
    private MemberModel memberModel;

    public ArrayList<ProgressModel> getProgressModel() {
        return progressModel;
    }

    public void setProgressModel(ArrayList<ProgressModel> progressModel) {
        this.progressModel = progressModel;
    }

    public ArrayList<ScoreModel> getScoreModel() {
        return scoreModel;
    }

    public void setScoreModel(ArrayList<ScoreModel> scoreModel) {
        this.scoreModel = scoreModel;
    }

    public MemberModel getMemberModel() {
        return memberModel;
    }

    public void setMemberModel(MemberModel memberModel) {
        this.memberModel = memberModel;
    }
}
