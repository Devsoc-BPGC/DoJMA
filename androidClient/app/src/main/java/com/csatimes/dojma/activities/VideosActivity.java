package com.csatimes.dojma.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.VideosItem;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Use {@link #launchVideo(Context, VideosItem)} to correctly launch this activity.
 */
public class VideosActivity extends AppCompatActivity {
    // parameter keys for intent launching this activity
    private static final String PARAM_ID = "video.id";
    private static final String PARAM_DATE = "video.date";
    private static final String PARAM_TITLE = "video.title";
    private static final String PARAM_CREATOR = "video.creator";
    private static final String PARAM_DESC = "video.description";

    public static void launchVideo(Context context, VideosItem params) {
        Intent videoIntent = new Intent(context, VideosActivity.class);

        videoIntent.putExtra(PARAM_ID, params.id);
        videoIntent.putExtra(PARAM_DATE, params.dateStamp);
        videoIntent.putExtra(PARAM_TITLE, params.videoName);
        videoIntent.putExtra(PARAM_CREATOR, params.creator);
        videoIntent.putExtra(PARAM_DESC, params.description);

        context.startActivity(videoIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        Intent launchIntent = getIntent();

        final VideosItem params = new VideosItem();

        params.id = launchIntent.getStringExtra(PARAM_ID);
        params.dateStamp = launchIntent.getStringExtra(PARAM_DATE);
        params.videoName = launchIntent.getStringExtra(PARAM_TITLE);
        params.creator = launchIntent.getStringExtra(PARAM_CREATOR);
        params.description = launchIntent.getStringExtra(PARAM_DESC);

        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);

        youTubePlayerView.initialize(initializedYouTubePlayer -> initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady() {
                initializedYouTubePlayer.loadVideo(params.id, 0);
            }
        }), true);

        TextView nameTv = findViewById(R.id.tv_name_video);
        TextView date = findViewById(R.id.dateStampVideo);
        TextView creatorTv = findViewById(R.id.tv_creator_video);
        TextView descriptionTv = findViewById(R.id.tv_description);

        findViewById(R.id.fab_back).setOnClickListener(view -> onBackPressed());

        nameTv.setText(params.videoName);
        date.setText(params.dateStamp);
        creatorTv.setText(params.creator);
        descriptionTv.setText(params.description);
    }
}
