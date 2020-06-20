package com.sarnava.newsapplication.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DBNews.class}, version = 1, exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
}
