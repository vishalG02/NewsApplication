package com.sarnava.newsapplication.di.module;

import com.sarnava.newsapplication.data.Repository;
import com.sarnava.newsapplication.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelModule {

    @ActivityScope
    @Provides
    Repository getRepository(){
        return new Repository();
    }
}
