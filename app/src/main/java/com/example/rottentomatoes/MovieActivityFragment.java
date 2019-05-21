package com.example.rottentomatoes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MovieActivityFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View retView = inflater.inflate(R.layout.activity_movie_fragment, container);
        //Final FragmentActivity fragmentBelongActivity = getActivity();
        return retView;
    }
}