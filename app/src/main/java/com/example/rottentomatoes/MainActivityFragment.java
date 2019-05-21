package com.example.rottentomatoes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

public class MainActivityFragment extends Fragment {

    private RecyclerView moviesList;
    private MoviesAdapter adapter;

    private MoviesRepository moviesRepository;

    private List<Genre> movieGenres;
    private List<Movie> movies;

    private boolean isFetchingMovies;
    private int currentPage = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View retView = inflater.inflate(R.layout.activity_main_fragment, container);
        moviesRepository = MoviesRepository.getInstance();

        RecyclerView moviesList = (RecyclerView) retView.findViewById(R.id.movies_list);

        moviesList.setLayoutManager(new LinearLayoutManager(getActivity()));



        //MoviesAdapter adapter = new MoviesAdapter();
        //moviesList.setAdapter(adapter);
        //moviesList.setItemAnimator(new DefaultItemAnimator());
        getGenres();


        return retView;
    }

    private void getGenres() {
        moviesRepository.getGenres(new OnGetGenresCallback() {
            @Override
            public void onSuccess(List<Genre> genres) {
                movieGenres = genres;
                getMovies(currentPage);
            }

            @Override
            public void onError() {
               // showError();
            }
        });
    }

    private void getMovies(int page) {
        isFetchingMovies = true;
        moviesRepository.getMovies(page, new OnGetMoviesCallback() {
            @Override
            public void onSuccess(int page, List<Movie> movies) {
                Log.d("MoviesRepository", "Current Page = " + page);
                if (adapter == null) {
                    adapter = new MoviesAdapter(movies, movieGenres);
                    moviesList.setAdapter(adapter);
                } else {
                    adapter.appendMovies(movies);
                }
                currentPage = page;
                isFetchingMovies = false;
            }

            @Override
            public void onError() {
               // showError();
            }
        });
    }


}
