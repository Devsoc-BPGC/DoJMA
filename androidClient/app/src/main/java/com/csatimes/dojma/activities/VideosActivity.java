package com.csatimes.dojma.activities;



import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerInitListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.youtubeplayer.player.*;

public class VideosActivity extends AppCompatActivity {
    private String videoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        //getActionBar().hide();
        videoID= getIntent().getStringExtra("id");
        String dateStamp = getIntent().getStringExtra("date");
        String videoName = getIntent().getStringExtra("title");
        String creator = getIntent().getStringExtra("creator");
        String description = getIntent().getStringExtra("description");

        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        youTubePlayerView.initialize(new YouTubePlayerInitListener() {
            @Override
            public void onInitSuccess(final YouTubePlayer initializedYouTubePlayer) {
                initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        //String videoId="6JYIGclVQdw";
                        initializedYouTubePlayer.loadVideo(videoID, 0);
                    }
                });
            }
        }, true);

        //youTubePlayerView.addFullScreenListener(YouTubePlayerFullScreenListener fullScreenListener);
        //youTubePlayerView.enterFullScreen();
        TextView nameTv = findViewById(R.id.tv_name_video);
        TextView date = findViewById(R.id.dateStampVideo);
        TextView creatorTv = findViewById(R.id.tv_creator_video);
        TextView descriptionTv = findViewById(R.id.tv_description);

        nameTv.setText(videoName);

        date.setText(dateStamp);

        creatorTv.setText(creator);

        descriptionTv.setText(description);

    }
}
