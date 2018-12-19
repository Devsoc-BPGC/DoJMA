package com.csatimes.dojma.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.VideosAdapter;
import com.csatimes.dojma.models.VideosItem;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.csatimes.dojma.activities.AboutDojmaActivity.TAG;
import static com.csatimes.dojma.utilities.DHC.TAG_PREFIX;
import static com.csatimes.dojma.utilities.DHC.getGridSpan;

public class VideosFragment extends Fragment {

    //private static final String TAG = TAG_PREFIX + VideosFragment.class.getSimpleName();
    private RecyclerView videosRv;

    String videoURL[] = {"https://www.facebook.com/DoPy.BITSGoa/videos/622644644796899/","https://www.facebook.com/bitsquark/videos/309257229884592/","https://www.facebook.com/bitsquark/videos/2300637253556600/","https://www.youtube.com/watch?v=IM2czqavlWM", "https://www.youtube.com/watch?v=737dCFBqO4I", "https://www.youtube.com/watch?v=yuboWIBPDvk", "https://www.youtube.com/watch?v=pXCpc2Bcirs", "https://www.youtube.com/watch?v=4AQm22YQfYU", "https://www.youtube.com/watch?v=3PtbK_0j1sw", "https://www.youtube.com/watch?v=WDIeS7NHTpU", "https://www.youtube.com/watch?v=LvetJ9U_tVY", "https://www.youtube.com/watch?v=6JYIGclVQdw"};
    String videoTitle[] = {"Engineer's Day Video","Quark'19 Theme","Best Drone Pilots","AI Solves 100X100 Rubik's Cube","Apple Killed The Mac Mini","The Lord Of The Rings","How To Train Your Dragon","Does SomeoneElse Have Your Face?","Razer Blade Shealth","Animator vs Animation","SlipKnot - Vermilion","Skrillex5"};
    String date[] = {"2018-09-15","2018-09-21","2018-12-18","2018-07-20","2018-08-20","2018-09-29","2018-04-10","2018-11-07","2018-06-18","2018-01-09","2018-07-01","2018-12-25"};
    String creator[] = {"DoPy","Quark","Quark","MAC","Quark","Waves","DoPy","Celestia","MusOC","Spree","AeroD","DoSM"};
    String thumbnailSource[] = {"https://www.desktopbackground.org/download/1366x768/2014/08/21/812557_civil-engineering-wallpapers_1600x1200_h.jpg","facebook","facebook","youtube","youtube","youtube","youtube","youtube","youtube","youtube","youtube","youtube"};
    String description[] = {" "," "," ","This is a generic YouTube Description shown in the VideoPlayer Activity.","This is a generic YouTube Description shown in the VideoPlayer Activity.","This is a generic YouTube Description shown in the VideoPlayer Activity.","This is a generic YouTube Description shown in the VideoPlayer Activity.","This is a generic YouTube Description shown in the VideoPlayer Activity.","This is a generic YouTube Description shown in the VideoPlayer Activity.","This is a generic YouTube Description shown in the VideoPlayer Activity.","This is a generic YouTube Description shown in the VideoPlayer Activity.","This is a generic YouTube Description shown in the VideoPlayer Activity."};

    private List<VideosItem> dummyData() {
        List<VideosItem> data = new ArrayList<>(12);
        for (int i = 0; i < 9; i++) {
            data.add(new VideosItem(videoURL[i],videoTitle[i],date[i],creator[i],thumbnailSource[i],description[i]));
        }//data is the list of objects to be set in the list item
        return data;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_videos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    videosRv = view.findViewById(R.id.fragment_videos_rv);
    final Context context = getContext();
    videosRv.setHasFixedSize(true);
    videosRv.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onStart() {
        super.onStart();
        final Activity activity = getActivity();
            final Context context = getContext();
            Fresco.initialize(context);
            List<VideosItem> videos = dummyData();
        if (activity != null) {
            final VideosAdapter mAdapter = new VideosAdapter(videos);
            videosRv.setAdapter(mAdapter);
        } else {
            Log.e(TAG, "getActivity() returned null in onStart()");
        }


    }
























}
