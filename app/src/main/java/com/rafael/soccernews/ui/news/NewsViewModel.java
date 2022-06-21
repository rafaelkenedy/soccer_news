package com.rafael.soccernews.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rafael.soccernews.domain.News;

import java.util.ArrayList;
import java.util.List;

public class NewsViewModel extends ViewModel {

    private final MutableLiveData<List<News>> news;
    private final String lorem = "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...";

    public NewsViewModel() {
        this.news = new MutableLiveData<>();

        List<News> news = new ArrayList<>();
        news.add(new News("Ferroviária Tem Desfalque Importante", lorem));
        news.add(new News("Ferroviária Ganha de Palmeiras", lorem));
        news.add(new News("Copa do Mundo Está Terminando", lorem));

        this.news.setValue(news);

    }

    public LiveData<List<News>>  getNews() {
        return news;
    }
}