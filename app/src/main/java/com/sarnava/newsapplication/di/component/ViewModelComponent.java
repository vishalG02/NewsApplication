package com.sarnava.newsapplication.di.component;

import com.sarnava.newsapplication.di.ActivityScope;
import com.sarnava.newsapplication.di.module.ViewModelModule;
import com.sarnava.newsapplication.ui.main.MainViewModel;

import dagger.Component;

@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {ViewModelModule.class})
public interface ViewModelComponent {

    void inject(MainViewModel mainViewModel);
}
