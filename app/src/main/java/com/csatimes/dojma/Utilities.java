package com.csatimes.dojma;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.adapters.UtilitiesRV;

public class Utilities extends Fragment {
    private static final int REQUEST_CALL_PHONE = 112;
    private static final int REQUEST_WRITE_STORAGE = 113;
    RecyclerView utilitiesRecyclerView;
    boolean writingPermission = false;
    UtilitiesRV adapter;

    public Utilities() {
        // Required empty public constructor
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    writingPermission = true;
                    adapter.setHasWritePermission(writingPermission);
                } else {
                    Snackbar.make(utilitiesRecyclerView, "The app was not allowed to write to disk. It cannot function properly Please consider granting it this permission",
                            Snackbar.LENGTH_LONG)
                            .show();
                }
                break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.setActivity(getActivity());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (!writingPermission) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
            }
        } else writingPermission = true;
        {
            adapter.setHasWritePermission(true);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_utilities, container, false);


        utilitiesRecyclerView = (RecyclerView) view.findViewById(R.id.utilities_rv);

        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        sglm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        utilitiesRecyclerView.setLayoutManager(sglm);

        adapter = new UtilitiesRV(getContext());
        utilitiesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        utilitiesRecyclerView.setAdapter(adapter);


        writingPermission = (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!writingPermission) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
        }

        return view;
    }


}
