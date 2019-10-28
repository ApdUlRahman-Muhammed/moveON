package com.example.android.popularmovie.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewListModel {

    @SerializedName("id")
    @Expose
    private  int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @SerializedName("results")
    @Expose
    private List<ReviewModel>  reviewModelList;

    public List<ReviewModel> getReviewModelList() {
        return reviewModelList;
    }

    public void setReviewModelList(List<ReviewModel> reviewModelList) {
        this.reviewModelList = reviewModelList;
    }

}
