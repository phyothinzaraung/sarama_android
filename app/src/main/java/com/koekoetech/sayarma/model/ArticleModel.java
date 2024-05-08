package com.koekoetech.sayarma.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.koekoetech.sayarma.helper.Pageable;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ArticleModel extends RealmObject implements Serializable, Pageable {

    @PrimaryKey
    @Expose
    @SerializedName("ContentID")
    private String ContentID;

    @Expose
    @SerializedName("CategoryID")
    private String CategoryID;

    @Expose
    @SerializedName("Title")
    private String Title;

    @Expose
    @SerializedName("TitleInEnglish")
    private String TitleInEnglish;

    @Expose
    @SerializedName("CategoryDescription")
    private String CategoryDescription;

    @Expose
    @SerializedName("ContentBody")
    private String ContentBody;

    @Expose
    @SerializedName("image")
    private String image;

    @Expose
    @SerializedName("ArticleType")
    private String ArticleType;

    @Expose
    @SerializedName("VideoURL")
    private String VideoURL;

    @Expose
    @SerializedName("CreatedDate")
    private String CreatedDate;

    @Expose
    @SerializedName("Createdby")
    private String Createdby;

    @Expose
    @SerializedName("UpdatedDate")
    private String UpdatedDate;

    @Expose
    @SerializedName("Updatedby")
    private String Updatedby;

    @Expose
    @SerializedName("OrganizationDescription")
    private String OrganizationDescription;

    @Expose
    @SerializedName("OrganizationTitle")
    private String OrganizationTitle;

    @Expose
    @SerializedName("photos")
    private RealmList<String> photos;

    @Expose
    @SerializedName("contentLikeModels")
    private RealmList<ContentLikeModel> contentLikeModels;

    @Expose
    @SerializedName("contentCommentModels")
    private RealmList<ContentCommentModel> contentCommentModels;

    @Expose
    @SerializedName("likeCount")
    private int likeCount;

    @Expose
    @SerializedName("isLike")
    private boolean isLike;

    @Expose
    @SerializedName("userId")
    private String userId;

    public String getContentID() {
        return ContentID;
    }

    public void setContentID(String contentID) {
        ContentID = contentID;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTitleInEnglish() {
        return TitleInEnglish;
    }

    public void setTitleInEnglish(String titleInEnglish) {
        TitleInEnglish = titleInEnglish;
    }

    public String getCategoryDescription() {
        return CategoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        CategoryDescription = categoryDescription;
    }

    public String getContentBody() {
        return ContentBody;
    }

    public void setContentBody(String contentBody) {
        ContentBody = contentBody;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getArticleType() {
        return ArticleType;
    }

    public void setArticleType(String articleType) {
        ArticleType = articleType;
    }

    public String getVideoURL() {
        return VideoURL;
    }

    public void setVideoURL(String videoURL) {
        VideoURL = videoURL;
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

    public String getOrganizationDescription() {
        return OrganizationDescription;
    }

    public void setOrganizationDescription(String organizationDescription) {
        OrganizationDescription = organizationDescription;
    }

    public String getOrganizationTitle() {
        return OrganizationTitle;
    }

    public void setOrganizationTitle(String organizationTitle) {
        OrganizationTitle = organizationTitle;
    }

    public RealmList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(RealmList<String> photos) {
        this.photos = photos;
    }

    public RealmList<ContentLikeModel> getContentLikeModels() {
        return contentLikeModels;
    }

    public void setContentLikeModels(RealmList<ContentLikeModel> contentLikeModels) {
        this.contentLikeModels = contentLikeModels;
    }

    public RealmList<ContentCommentModel> getContentCommentModels() {
        return contentCommentModels;
    }

    public void setContentCommentModels(RealmList<ContentCommentModel> contentCommentModels) {
        this.contentCommentModels = contentCommentModels;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
