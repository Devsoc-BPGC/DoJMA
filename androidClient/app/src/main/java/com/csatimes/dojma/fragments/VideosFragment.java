package com.csatimes.dojma.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.VideosAdapter;
import com.csatimes.dojma.models.VideosItem;
import com.csatimes.dojma.utilities.FirebaseValues;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import static com.csatimes.dojma.utilities.DHC.TAG_PREFIX;

public class VideosFragment extends Fragment {

    private static final String TAG = TAG_PREFIX + VideosFragment.class.getSimpleName();
    private RecyclerView videosRv;
    private List<VideosItem> videos = new ArrayList<>();
    private VideosAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_videos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videosRv = view.findViewById(R.id.fragment_videos_rv);
        videosRv.setHasFixedSize(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        final Activity activity = getActivity();
        getData();
        if (activity != null) {
            mAdapter = new VideosAdapter(videos);
            videosRv.setAdapter(mAdapter);
        } else {
            Log.e(TAG, "getActivity() returned null in onStart()");
        }
    }

    private void getData() {
        FirebaseValues.videosRef().orderByChild("dateStamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                videos.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    VideosItem videosItem = postSnapshot.getValue(VideosItem.class);
                    videos.add(videosItem);
                    Collections.reverse(videos);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase Error", databaseError.getDetails());
            }
        });
    }
}
