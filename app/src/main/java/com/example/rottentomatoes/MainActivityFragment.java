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





        moviesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        moviesList = (RecyclerView) retView.findViewById(R.id.movies_list);

        moviesRepository = MoviesRepository.getInstance();

        getGenres();

        /*//if (adapter == null) {
            adapter = new MoviesAdapter(movies, movieGenres);
            moviesList.setAdapter(adapter);
            moviesList.setItemAnimator(new DefaultItemAnimator());
        } else {
            adapter.appendMovies(movies);
        }*/


        //MoviesAdapter adapter = new MoviesAdapter();
        //moviesList.setAdapter(adapter);
        //moviesList.setItemAnimator(new DefaultItemAnimator());



        return retView;
    }

    private void setupOnScrollListener() {
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        moviesList.setLayoutManager(manager);
        moviesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int totalItemCount = manager.getItemCount();
                int visibleItemCount = manager.getChildCount();
                int firstVisibleItem = manager.findFirstVisibleItemPosition();

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    if (!isFetchingMovies) {
                        getMovies(currentPage + 1);
                    }
                }
            }
        });
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
            public void onSuccess(int page, List<Movie> movie) {
                Log.d("MoviesRepository", "Current Page = " + page);
                //movies = movie;
                if (adapter == null) {
                    adapter = new MoviesAdapter(movies, movieGenres);
                    moviesList.setAdapter(adapter);
                    moviesList.setItemAnimator(new DefaultItemAnimator());
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

    /*private void showError() {
        Toast.makeText(MainActivity., "Please check your internet connection.", Toast.LENGTH_SHORT).show();
    }*/


}
