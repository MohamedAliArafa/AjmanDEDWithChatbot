package com.ajman.ded.ae.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class News implements Parcelable {

    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
    @SerializedName("NewsID")
    @Expose
    private int newsID;
    @SerializedName("NewsTitle")
    @Expose
    private String newsTitle;
    @SerializedName("NewsTitleEn")
    @Expose
    private String newsTitleEn;
    @SerializedName("NewsDate")
    @Expose
    private String newsDate;
    @SerializedName("NewsDetails")
    @Expose
    private String newsDetails;
    @SerializedName("NewsDetailsEn")
    @Expose
    private String newsDetailsEn;
    @SerializedName("NewsImage")
    @Expose
    private String newsImage;

    public News() {
    }

    protected News(Parcel in) {
        this.newsID = in.readInt();
        this.newsTitle = in.readString();
        this.newsTitleEn = in.readString();
        this.newsDate = in.readString();
        this.newsDetails = in.readString();
        this.newsDetailsEn = in.readString();
        this.newsImage = in.readString();
    }

    public int getNewsID() {
        return newsID;
    }

    public void setNewsID(int newsID) {
        this.newsID = newsID;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsTitleEn() {
        return newsTitleEn;
    }

    public void setNewsTitleEn(String newsTitleEn) {
        this.newsTitleEn = newsTitleEn;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public String getNewsDetails() {
        return newsDetails;
    }

    public void setNewsDetails(String newsDetails) {
        this.newsDetails = newsDetails;
    }

    public String getNewsDetailsEn() {
        return newsDetailsEn;
    }

    public void setNewsDetailsEn(String newsDetailsEn) {
        this.newsDetailsEn = newsDetailsEn;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.newsID);
        dest.writeString(this.newsTitle);
        dest.writeString(this.newsTitleEn);
        dest.writeString(this.newsDate);
        dest.writeString(this.newsDetails);
        dest.writeString(this.newsDetailsEn);
        dest.writeString(this.newsImage);
    }
}
