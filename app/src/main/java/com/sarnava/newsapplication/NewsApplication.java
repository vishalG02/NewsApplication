package com.sarnava.newsapplication;

import android.app.Application;

import com.sarnava.newsapplication.di.component.AppComponent;
import com.sarnava.newsapplication.di.component.DaggerAppComponent;
import com.sarnava.newsapplication.di.module.AppModule;

public class NewsApplication extends Application {

    public static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getComponent() {
        return appComponent;
    }
}
