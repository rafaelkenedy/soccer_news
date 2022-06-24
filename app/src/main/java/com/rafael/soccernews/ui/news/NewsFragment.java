package com.rafael.soccernews.ui.news;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.rafael.soccernews.MainActivity;
import com.rafael.soccernews.data.local.AppDatabase;
import com.rafael.soccernews.databinding.FragmentNewsBinding;
import com.rafael.soccernews.ui.adapter.NewsAdapter;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadNews();
        return root;
    }

    private void loadNews() {
        NewsViewModel newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> {
            binding.rvNews.setAdapter(new NewsAdapter(news, updateNews -> {
                MainActivity activity = (MainActivity) getActivity();
                if(activity != null ){
                    //AsyncTask.execute(() -> activity.getDb().newsDao().insert(updateNews));
                    activity.getDb().newsDao().insert(updateNews);
                }
            }));
        });

        newsViewModel.getState().observe(getViewLifecycleOwner(), state -> {
            switch (state) {
                case DOING:
                    //TODO Iniciar SwipeRefreshLayout (loaindg)
                    break;
                case DONE:
                    //TODO Finalizar SwipeRefreshLayout (loaindg)
                    break;
                case ERROR:
                    //TODO
                    //TODO Finalizar SwipeRefreshLayout (loaindg)
                    //TODO Mostrar erro genérico
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}