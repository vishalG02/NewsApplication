package com.sarnava.newsapplication.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NewsResponse implements Serializable {

    @SerializedName("status")
    private String status;
    @SerializedName("articles")
    private List<News> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<News> getArticles() {
        return articles;
    }

    public void setArticles(List<News> articles) {
        this.articles = articles;
    }
}
