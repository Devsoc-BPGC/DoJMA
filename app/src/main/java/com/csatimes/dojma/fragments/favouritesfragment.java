package com.csatimes.dojma.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class favouritesfragment extends Fragment {

    public favouritesfragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }
}
