package com.sarnava.newsapplication.ui.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sarnava.newsapplication.NewsApplication;
import com.sarnava.newsapplication.di.component.ActivityComponent;
import com.sarnava.newsapplication.di.component.DaggerActivityComponent;
import com.sarnava.newsapplication.di.module.ActivityModule;

public class BaseActivity extends AppCompatActivity {

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(NewsApplication.getComponent())
                .build();
    }

    public ActivityComponent getActivityComponent(){

        return activityComponent;
    }
}
