package com.rafael.soccernews.ui.favorites;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rafael.soccernews.MainActivity;
import com.rafael.soccernews.databinding.FragmentFavoritesBinding;
import com.rafael.soccernews.domain.News;
import com.rafael.soccernews.ui.adapter.NewsAdapter;
import com.rafael.soccernews.ui.news.NewsFragment;

import java.util.List;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoritesViewModel favoritesViewModel =
                new ViewModelProvider(this).get(FavoritesViewModel.class);

        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        loadFavoriteNews();

        return root;
    }

    private void loadFavoriteNews() {
        MainActivity activity = (MainActivity) getActivity();
        List<News> favoriteNews = activity.getDb().newsDao().loadFavoriteNews();
        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvNews.setAdapter(new NewsAdapter(favoriteNews, updateNews -> {
            if(activity != null ){
                //AsyncTask.execute(() -> activity.getDb().newsDao().insert(updateNews));
                activity.getDb().newsDao().insert(updateNews);
            }
            loadFavoriteNews();
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}