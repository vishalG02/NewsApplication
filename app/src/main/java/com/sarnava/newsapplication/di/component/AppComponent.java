package com.sarnava.newsapplication.di.component;

import com.sarnava.newsapplication.NewsApplication;
import com.sarnava.newsapplication.data.Repository;
import com.sarnava.newsapplication.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(NewsApplication application);
    void inject(Repository repository);
}
