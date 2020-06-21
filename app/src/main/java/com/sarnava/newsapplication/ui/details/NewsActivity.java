package com.sarnava.newsapplication.ui.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.sarnava.newsapplication.R;
import com.sarnava.newsapplication.data.News;
import com.sarnava.newsapplication.databinding.ActivityNewsBinding;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActivityNewsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_news);

        if(getIntent() != null){

            binding.setNews((News) getIntent().getSerializableExtra("news"));
        }

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }
}