package com.sarnava.newsapplication.ui.main;

import android.content.Intent;
import android.view.LayoutInflater;
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
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> news;

    public NewsAdapter(List<News> news) {
        this.news = news;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        NewsListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.news_list_item,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {

        News currentNews = news.get(position);
        holder.binding.title.setText(currentNews.getTitle());
        holder.binding.source.setText(currentNews.getSource().getName());

        currentNews.setPublishedAt(Util.getDate(currentNews.getPublishedAt()));

        holder.binding.date.setText(currentNews.getPublishedAt());

        Picasso.get().load(currentNews.getUrlToImage()).into(holder.binding.iv);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        NewsListItemBinding binding;

        public ViewHolder(NewsListItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), NewsActivity.class);
                    intent.putExtra("news", news.get(getAdapterPosition()));
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

}
