package com.sarnava.newsapplication.ui.details;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.sarnava.newsapplication.R;
import com.sarnava.newsapplication.data.News;
import com.sarnava.newsapplication.databinding.ActivityNewsBinding;
import com.sarnava.newsapplication.ui.base.BaseActivity;

public class NewsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActivityNewsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_news);

        getActivityComponent().inject(this);

        if(getIntent() != null){

            binding.setNews((News) getIntent().getSerializableExtra("news"));
        }

        binding.ivBack.setOnClickListener(v -> onBackPressed());
    }
}