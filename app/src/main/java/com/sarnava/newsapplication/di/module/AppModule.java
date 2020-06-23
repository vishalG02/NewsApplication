package com.sarnava.newsapplication.di.module;

import android.app.Application;

import androidx.room.Room;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sarnava.newsapplication.BuildConfig;
import com.sarnava.newsapplication.NewsApplication;
import com.sarnava.newsapplication.data.local.NewsDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    private NewsApplication application;

    public AppModule(NewsApplication application){
        this.application = application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    public Retrofit getRetrofit(){

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.ROOT_URL)
                .build();
    }

    @Provides
    @Singleton
    public NewsDatabase getNewsDatabase(){

        return Room.databaseBuilder(application.getApplicationContext(), NewsDatabase.class, BuildConfig.DB_NAME).build();
    }
}
