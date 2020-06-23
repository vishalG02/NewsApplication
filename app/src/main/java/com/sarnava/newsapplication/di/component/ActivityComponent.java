package com.sarnava.newsapplication.di.component;

import com.sarnava.newsapplication.di.ActivityScope;
import com.sarnava.newsapplication.di.module.ActivityModule;
import com.sarnava.newsapplication.ui.details.NewsActivity;
import com.sarnava.newsapplication.ui.main.MainActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
    void inject(NewsActivity newsActivity);
}
