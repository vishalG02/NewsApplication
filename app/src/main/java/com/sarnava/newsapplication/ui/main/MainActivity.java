package com.sarnava.newsapplication.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sarnava.newsapplication.BuildConfig;
import com.sarnava.newsapplication.R;
import com.sarnava.newsapplication.data.News;
import com.sarnava.newsapplication.data.NewsResponse;
import com.sarnava.newsapplication.data.local.DBNews;
import com.sarnava.newsapplication.data.local.NewsDatabase;
import com.sarnava.newsapplication.databinding.ActivityMainBinding;
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

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private NewsAdapter adapter;
    private List<News> news = new ArrayList<>();
    NewsDatabase newsDatabase;
    private boolean shouldUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rv.setLayoutManager(linearLayoutManager);
        binding.rv.setItemAnimator(new DefaultItemAnimator());
        adapter = new NewsAdapter(news);
        binding.rv.setAdapter(adapter);

        newsDatabase = Room.databaseBuilder(getApplicationContext(), NewsDatabase.class, "news_db").build();

        fetchNewsFromDB();
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

    private void fetchNews(){

        ApiInterface apiInterface = getRetrofit().create(ApiInterface.class);

        apiInterface.getNews("in",BuildConfig.API_KEY)
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
                        news = newsResponse.getArticles();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("lala api", "error "+e);
                        Toast.makeText(MainActivity.this, "Unable to refresh feed", Toast.LENGTH_SHORT).show();
                        binding.pb.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {

                        Log.e("lala api", "complete");
                        binding.pb.setVisibility(View.GONE);
                        if(news.size()>0){

                            adapter.setNews(news);
                            deleteDataFromDb(news);
                        }
                        else {

                            Toast.makeText(MainActivity.this, "Unable to refresh feed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void fetchNewsFromDB(){

        newsDatabase.newsDao().getAllNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<DBNews>>() {
                    @Override
                    public void accept(List<DBNews> dbNews) throws Exception {

                        if(dbNews.size() > 0)
                            shouldUpdate = true;

                        Log.e("lala db", "accept "+dbNews.size());
                        for(int i=0;i<dbNews.size();i++){

                            News db_news = new News();
                            db_news.setTitle(dbNews.get(i).getTitle());
                            db_news.setDescription(dbNews.get(i).getDescription());
                            db_news.setUrlToImage(dbNews.get(i).getUrlToImage());
                            db_news.setPublishedAt(dbNews.get(i).getPublishedAt());

                            News.Source source = new News.Source();
                            source.setName(dbNews.get(i).getSource());
                            db_news.setSource(source);

                            news.add(db_news);
                        }
                        adapter.setNews(news);
                        fetchNews();
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void deleteDataFromDb(final List<News> news){

        if(shouldUpdate){

            newsDatabase.newsDao().deleteAll()
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Action() {
                        @Override
                        public void run() throws Exception {
                            Log.e("lala ", "delete from db");
                            insertNewsInDb(news);
                        }
                    });
        }
        else {

            insertNewsInDb(news);
        }

    }

    @SuppressLint("CheckResult")
    private void insertNewsInDb(List<News> news){

        final List<DBNews> dbNewsList = new ArrayList<>();
        for(int i=0;i<news.size();i++){

            DBNews dbNews = new DBNews();
            dbNews.setTitle(news.get(i).getTitle());
            dbNews.setDescription(news.get(i).getDescription());
            dbNews.setUrlToImage(news.get(i).getUrlToImage());
            dbNews.setPublishedAt(news.get(i).getPublishedAt());
            dbNews.setSource(news.get(i).getSource().getName());

            dbNewsList.add(dbNews);
        }

        newsDatabase.newsDao().insertNews(dbNewsList)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e("lala ", "insert in db "+dbNewsList.size());
                    }
                });
    }
}