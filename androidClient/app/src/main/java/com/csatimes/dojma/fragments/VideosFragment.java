package com.csatimes.dojma.fragments;

import android.app.Activity;
import android.os.Bundle;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import static com.csatimes.dojma.utilities.DHC.TAG_PREFIX;

public class VideosFragment extends Fragment {

    private static final String TAG = TAG_PREFIX + VideosFragment.class.getSimpleName();
    private RecyclerView videosRv;
    private List<VideosItem> videos = new ArrayList<>();
    private List<VideosItem> filteredVideos = new ArrayList<>();
    private List<VideosItem> allVideos = new ArrayList<>();
    private VideosAdapter mAdapter;
    private Spinner spinner;
    private List<String> list = new ArrayList<>();
    private ArrayAdapter<String> dataAdapter;

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
        videosRv.setNestedScrollingEnabled(false);
        spinner = view.findViewById(R.id.filter_spinner);
        mAdapter = new VideosAdapter(videos);
        videosRv.setAdapter(mAdapter);
        addListenerOnSpinnerItemSelection();
    }

    @Override
    public void onStart() {
        super.onStart();
        final Activity activity = getActivity();
        getData();
        if (activity != null) {
            list.add("All");
            dataAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
        } else {
            Log.e(TAG, "getActivity() returned null in onStart()");
        }
    }

    private void getData() {
        FirebaseValues.videosRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                allVideos.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    VideosItem videosItem = postSnapshot.getValue(VideosItem.class);
                    allVideos.add(videosItem);
                    Collections.reverse(allVideos);
                    if (!list.contains(videosItem.creator)) {
                        list.add(videosItem.getCreator());
                    }
                }
                dataAdapter.notifyDataSetChanged();
                setfilter(allVideos);
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
                if (parent.getItemAtPosition(position).toString().equals("All")) {
                    setfilter(allVideos);
                } else {
                    filteredVideos.clear();
                    for (int i = 0; i < allVideos.size(); i++) {
                        if (parent.getItemAtPosition(position).toString().equals(allVideos.get(i).getCreator())) {
                            filteredVideos.add(allVideos.get(i));
                        }
                    }
                    setfilter(filteredVideos);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setfilter(allVideos);
            }
        });
    }

    public void setfilter(List<VideosItem> filterList) {
        videos.clear();
        videos.addAll(filterList);
        class StringDateComparator implements Comparator<VideosItem> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            public int compare(VideosItem lhs, VideosItem rhs) {
                try {
                    return dateFormat.parse(lhs.getDateStamp()).compareTo(dateFormat.parse(rhs.getDateStamp()));
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        }
        Collections.sort(videos, new StringDateComparator());
        Collections.reverse(videos);
        mAdapter.notifyDataSetChanged();
    }
}
