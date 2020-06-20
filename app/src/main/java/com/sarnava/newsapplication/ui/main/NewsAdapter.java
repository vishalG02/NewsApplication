package com.sarnava.newsapplication.ui.main;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sarnava.newsapplication.R;
import com.sarnava.newsapplication.Util;
import com.sarnava.newsapplication.data.News;
import com.sarnava.newsapplication.databinding.NewsListItemBinding;
import com.sarnava.newsapplication.ui.details.NewsActivity;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> news;

    public NewsAdapter(List<News> news) {
        this.news = news;
    }

    public void setNews(List<News> news) {
        this.news = news;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        NewsListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.news_list_item,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {

        holder.binding.setNews(news.get(position));
    }

    @Override
    public int getItemCount() {
        return news == null?0:news.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        NewsListItemBinding binding;

        public ViewHolder(final NewsListItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

            binding.getRoot().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    int action = event.getAction();

                    if(action == MotionEvent.ACTION_DOWN){

                        binding.getRoot().setScaleX(0.9f);
                        binding.getRoot().setScaleY(0.9f);
                        binding.getRoot().setAlpha(.6f);
                    }
                    else if(action == MotionEvent.ACTION_UP){

                        binding.getRoot().setScaleX(1f);
                        binding.getRoot().setScaleY(1f);
                        binding.getRoot().setAlpha(1f);

                        if (!Util.block()) {

                            Intent intent = new Intent(v.getContext(), NewsActivity.class);
                            intent.putExtra("news", news.get(getAdapterPosition()));
                            v.getContext().startActivity(intent);
                        }
                    }
                    else {

                        binding.getRoot().setScaleX(1f);
                        binding.getRoot().setScaleY(1f);
                        binding.getRoot().setAlpha(1f);
                    }

                    return true;
                }
            });
        }
    }

}
