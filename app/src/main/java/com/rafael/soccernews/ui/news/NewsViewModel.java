package com.rafael.soccernews.ui.news;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rafael.soccernews.data.SoccerNewsRepository;
import com.rafael.soccernews.domain.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {

    public enum State {
        DOING, DONE, ERROR;
    }


    private final MutableLiveData<List<News>> news = new MutableLiveData<>();
    private final MutableLiveData<State> state = new MutableLiveData<>();


    public NewsViewModel() {
        this.findNews();
    }

    private void findNews() {
        state.setValue(State.DOING);
        SoccerNewsRepository.getInstance().getRemoteApi().getNews().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if(response.isSuccessful()){
                    news.setValue(response.body());
                    state.setValue(State.DONE);
                } else {
                    // TODO Tratamento
                    state.setValue(State.ERROR);
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                t.printStackTrace();
                state.setValue(State.ERROR);
            }
        });
    }

    public void saveNews(News news){
        AsyncTask.execute(() -> SoccerNewsRepository.getInstance().getLocalDb().newsDao().insert(news));
    }

    public LiveData<List<News>>  getNews() {
        return this.news;
    }
    public LiveData<State>  getState() {
        return this.state;
    }
}