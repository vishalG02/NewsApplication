package com.sarnava.newsapplication.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sarnava.newsapplication.BuildConfig;
import com.sarnava.newsapplication.data.local.DBNews;
import com.sarnava.newsapplication.data.local.NewsDatabase;
import com.sarnava.newsapplication.data.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private static Repository instance;
    private NewsDatabase newsDatabase;

    public static Repository getInstance(){

        if(instance == null){

            instance = new Repository();
        }
        return instance;
    }

    public void setRoomDB(Context context){

        newsDatabase = Room.databaseBuilder(context, NewsDatabase.class, "news_db").build();
    }

    public Retrofit getRetrofit() {

        final String BASE_URL = BuildConfig.ROOT_URL;
        Retrofit retrofit;
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        return retrofit;
    }

    @SuppressLint("CheckResult")
    public MutableLiveData<List<DBNews>> fetchNewsFromDB(){

        final MutableLiveData<List<DBNews>> data = new MutableLiveData<>();

        newsDatabase.newsDao().getAllNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<DBNews>>() {
                    @Override
                    public void accept(List<DBNews> dbNews) throws Exception {

                        Log.e("lala db", "accept "+dbNews.size());
                        data.setValue(dbNews);
                    }
                });
        return data;
    }

    public MutableLiveData<List<News>> fetchNewsFromServer(String country){

        final MutableLiveData<List<News>> data = new MutableLiveData<>();

        ApiInterface apiInterface = getRetrofit().create(ApiInterface.class);

        apiInterface.getNews(country,BuildConfig.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("lala api", "subscribe");
                    }

                    @Override
                    public void onNext(NewsResponse newsResponse) {
                        Log.e("lala api", "onnext");
                        data.setValue(newsResponse.getArticles());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("lala api", "error "+e);
                        data.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                        Log.e("lala api", "complete");
                    }
                });
        return data;
    }

    @SuppressLint("CheckResult")
    public void deleteNewsFromDbAndThenInsert(final List<DBNews> dbNews){

        newsDatabase.newsDao().deleteAll()
                .subscribeOn(Schedulers.io())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e("lala ", "deleted from db");
                        insertNewsInDb(dbNews);
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void insertNewsInDb(final List<DBNews> dbNews){

        newsDatabase.newsDao().insertNews(dbNews)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e("lala ", "inserted in db "+ dbNews.size());
                    }
                });
    }
}
