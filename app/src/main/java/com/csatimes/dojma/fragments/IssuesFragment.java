package com.csatimes.dojma.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class IssuesFragment extends Fragment {

    public static Fragment newInstance(){
        IssuesFragment issuesFragment = new IssuesFragment();
        return issuesFragment;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_issues, container, false);
    }

}
