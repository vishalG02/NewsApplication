package com.sarnava.newsapplication.di.module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.sarnava.newsapplication.di.ActivityScope;
import com.sarnava.newsapplication.ui.main.MainViewModel;
import com.sarnava.newsapplication.ui.main.NewsAdapter;

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

    @ActivityScope
    @Provides
    MainViewModel getMainViewModel(AppCompatActivity activity){
        return ViewModelProviders.of(activity).get(MainViewModel.class);
    }

    @ActivityScope
    @Provides
    NewsAdapter getNewsAdapter(){
        return new NewsAdapter();
    }
}
