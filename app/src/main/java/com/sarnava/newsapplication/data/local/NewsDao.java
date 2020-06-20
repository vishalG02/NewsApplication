package com.sarnava.newsapplication.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;


@Dao
public interface NewsDao {

    @Query("SELECT * FROM DBNews")
    Maybe<List<DBNews>> getAllNews();

    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    Completable insertNews(DBNews news);

    @Update
    Completable updateNews(DBNews news);
}
