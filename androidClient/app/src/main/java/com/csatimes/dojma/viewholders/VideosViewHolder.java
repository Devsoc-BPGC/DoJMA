package com.csatimes.dojma.viewholders;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.VideosItem;
import com.csatimes.dojma.utilities.DHC;
import com.facebook.drawee.view.SimpleDraweeView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;

import static com.csatimes.dojma.activities.VideosActivity.launchVideo;
import static com.csatimes.dojma.viewholders.UrlOperations.getFbVideoThumbUrl;
import static com.csatimes.dojma.viewholders.UrlOperations.getQueryMap;
import static com.csatimes.dojma.viewholders.UrlOperations.getYtThumbUrl;

public class VideosViewHolder extends RecyclerView.ViewHolder {

    public static final String TAG = VideosViewHolder.class.getSimpleName();
    private TextView nameTv;
    private TextView date;
    private TextView creatorTv;
    private SimpleDraweeView image;
    private ImageButton shareVideo;
    private ImageButton source;

    public VideosViewHolder(View itemView) {
        super(itemView);
        nameTv = itemView.findViewById(R.id.tv_name);
        date = itemView.findViewById(R.id.tv_date);
        creatorTv = itemView.findViewById(R.id.tv_creator);
        image = itemView.findViewById(R.id.sdv_thumb);
        shareVideo = itemView.findViewById(R.id.ib_share_video);
        source = itemView.findViewById(R.id.ib_video_source);
    }

    public void populate(VideosItem item) {
        nameTv.setText(item.videoName);
        date.setText(item.dateStamp);
        creatorTv.setText(item.creator);
        item.type = item.type.toLowerCase();
        switch (item.type) {
            case "youtube": {
                Map<String, String> getParams = getQueryMap(item.videoURL);
                if (getParams != null) {
                    item.id = getParams.get(DHC.Values.YT_VIDEO_KEY);
                    item.thumbUrl = getYtThumbUrl(item.id);
                }
                source.setImageResource(R.drawable.ic_youtube_24dp);
                break;
            }
            case "facebook": {
                URL url;
                try {
                    url = new URL(item.videoURL);
                } catch (MalformedURLException e) {
                    Log.e(TAG, e.getMessage(), e);
                    break;
                }
                List<String> path = Arrays.asList((url.getPath().split("/")));
                int i = path.lastIndexOf("videos");
                if (i >= 0 && i < path.size() - 1) {
                    item.id = path.get(i + 1);
                    item.thumbUrl = getFbVideoThumbUrl(item.id);
                }
                source.setImageResource(R.drawable.ic_facebook_social);
                break;
            }
        }

        if (item.thumbUrl == null) {
            item.thumbUrl = item.type;
        }

        if (item.thumbUrl != null) {
            Uri thumbnail = Uri.parse(item.thumbUrl);
            image.setImageURI(thumbnail);
        }

        View.OnClickListener clickListener = (v -> {
            if (item.type.equalsIgnoreCase("youtube")) {
                launchVideo(itemView.getContext(), item);
            } else {
                Intent redirectionIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.videoURL));
                itemView.getContext().startActivity(redirectionIntent);
            }
        });

        nameTv.setOnClickListener(clickListener);

        date.setOnClickListener(clickListener);

        creatorTv.setOnClickListener(clickListener);

        image.setOnClickListener(clickListener);

        shareVideo.setOnClickListener(v -> {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_SUBJECT, item.videoName);
            share.putExtra(Intent.EXTRA_TEXT, item.videoURL);
            itemView.getContext().startActivity(Intent.createChooser(share, "Share video"));
        });
    }
}

