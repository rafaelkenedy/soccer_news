package com.rafael.soccernews.data.local;

import androidx.room.Dao;
import androidx.room.Query;

import com.rafael.soccernews.domain.News;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news where favorite = :favorite")
    List<News> loadFavoriteNews(boolean favorite);
}
