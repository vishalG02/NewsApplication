package com.sarnava.newsapplication.di.module;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.sarnava.newsapplication.di.ActivityContext;
import com.sarnava.newsapplication.di.ActivityScope;
import com.sarnava.newsapplication.ui.main.MainViewModel;
import com.sarnava.newsapplication.ui.main.NewsAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return this.activity;
    }

    @ActivityContext
    @Provides
    Context providesContext() {
        return this.activity;
    }

    @ActivityScope
    @Provides
    MainViewModel getMainViewModel(AppCompatActivity activity){
        return ViewModelProviders.of(activity).get(MainViewModel.class);
    }
}
