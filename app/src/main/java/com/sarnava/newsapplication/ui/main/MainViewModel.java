package com.sarnava.newsapplication.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sarnava.newsapplication.data.News;
import com.sarnava.newsapplication.data.Repository;
import com.sarnava.newsapplication.data.local.DBNews;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<List<News>> news = new MutableLiveData<>();
    private MutableLiveData<List<DBNews>> dbNews = new MutableLiveData<>();
    private boolean shouldUpdate;
    private Repository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        repository = Repository.getInstance();
        repository.setRoomDB(application.getApplicationContext());
    }

    public LiveData<List<News>> getNews() {
        return news;
    }

    public LiveData<List<DBNews>> getDbNews() {
        return dbNews;
    }

    public void setShouldUpdate(boolean shouldUpdate) {
        this.shouldUpdate = shouldUpdate;
    }

    public void fetchNewsFromDB(){

        dbNews = repository.fetchNewsFromDB();
    }

    public void fetchNewsFromServer(){

        news = repository.fetchNewsFromServer("us");
    }

    public void saveServerDataInDB(List<News> news){

        final List<DBNews> dbNewsList = new ArrayList<>();
        for(int i=0; i<news.size(); i++){

            DBNews dbNews = new DBNews();
            dbNews.setTitle(news.get(i).getTitle());
            dbNews.setDescription(news.get(i).getDescription());
            dbNews.setUrlToImage(news.get(i).getUrlToImage());
            dbNews.setPublishedAt(news.get(i).getPublishedAt());
            dbNews.setSource(news.get(i).getSource().getName());

            dbNewsList.add(dbNews);
        }

        if(shouldUpdate){

            repository.deleteNewsFromDbAndThenInsert(dbNewsList);
        }
        else {

            repository.insertNewsInDb(dbNewsList);
        }
    }
}
