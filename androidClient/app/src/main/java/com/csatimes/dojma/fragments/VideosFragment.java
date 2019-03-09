package com.csatimes.dojma.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
import java.util.TreeSet;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import static com.csatimes.dojma.utilities.DHC.TAG_PREFIX;

public class VideosFragment extends Fragment {

    private static final String TAG = TAG_PREFIX + VideosFragment.class.getSimpleName();
    private List<VideosItem> videos = new ArrayList<>();
    private List<VideosItem> allVideos = new ArrayList<>();
    private VideosAdapter mAdapter;
    private Spinner spinner;
    private List<String> tagList = new ArrayList<>();
    private TreeSet<String> tags = new TreeSet<>();
    private ArrayAdapter<String> dataAdapter;
    private Context parent;

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        parent = context;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
        spinner = view.findViewById(R.id.spinner_video_filter);
        tagList.add("All");
        dataAdapter = new ArrayAdapter<>(parent,
                android.R.layout.simple_spinner_item, tagList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        final RecyclerView videosRv = view.findViewById(R.id.fragment_videos_rv);
        mAdapter = new VideosAdapter(videos);
        videosRv.setAdapter(mAdapter);
        addListenerOnSpinnerItemSelection();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }

    private void getData() {
        FirebaseValues.videosRef().orderByChild("dateStamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                allVideos.clear();
                tags.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    VideosItem videosItem = postSnapshot.getValue(VideosItem.class);
                    if (videosItem == null) {
                        Log.e(TAG, String.format("onDataChange: null video item, snapshot: %s", postSnapshot));
                        continue;
                    }
                    allVideos.add(videosItem);
                    Collections.reverse(allVideos);
                    tags.add(videosItem.creator);
                }
                tagList.clear();
                tagList.add("All");
                tagList.addAll(tags);
                dataAdapter.notifyDataSetChanged();
                setFilter("All");
                spinner.setSelection(0);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase Error", databaseError.getDetails());
            }
        });
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String filter = parent.getItemAtPosition(position).toString();
                if (TextUtils.isEmpty(filter)) {
                    Log.e(TAG, "onItemSelected: null filter");
                    return;
                }
                setFilter(filter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setFilter("All");
            }
        });
    }

    private void setFilter(final String filter) {
        videos.clear();
        if ("All".equals(filter)) {
            videos.addAll(allVideos);
        } else {
            for (VideosItem video : allVideos) {
                if (filter.equals(video.creator)) {
                    videos.add(video);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}
