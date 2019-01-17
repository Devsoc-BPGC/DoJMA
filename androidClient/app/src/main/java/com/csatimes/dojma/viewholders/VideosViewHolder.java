package com.csatimes.dojma.viewholders;

import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.VideosItem;
import com.facebook.drawee.view.SimpleDraweeView;

public class VideosViewHolder extends RecyclerView.ViewHolder{

    private TextView nameTv,date,creatorTv;
    //private Button video;
    private String videoName;
    private String videoURL;
    private String dateStamp;
    private String creator;
    private String type;
    private String videoID;
    private String description;
    private SimpleDraweeView image;
    private ImageButton shareVideo, source;
    private Intent intent = new Intent(itemView.getContext(),com.csatimes.dojma.activities.VideosActivity.class);

    public VideosViewHolder(View itemView) {
        super(itemView);//itemView is view_v.xml
        nameTv = itemView.findViewById(R.id.tv_name);
        date = itemView.findViewById(R.id.dateStamp);
        //video = itemView.findViewById(R.id.go);
        creatorTv = itemView.findViewById(R.id.tv_creator);
        image = itemView.findViewById(R.id.my_image_view);
        shareVideo = itemView.findViewById(R.id.item_format_video_share);
        source = itemView.findViewById(R.id.video_source);

    }

    public void populate(VideosItem parts)
    {
        videoName=parts.getVideoName();
        nameTv.setText(videoName);

        dateStamp=parts.getDateStamp();
        date.setText(dateStamp);

        creator=parts.getCreator();
        creatorTv.setText(creator);

        videoURL=parts.getVideoURL();

        type=parts.getType();

        description=parts.getDescription();

        String URL;

        if(type.equalsIgnoreCase("youtube")){
            videoID=videoURL.substring(videoURL.length()-11,videoURL.length());
            URL ="https://img.youtube.com/vi/"+videoID+"/hqdefault.jpg";
            source.setImageResource(R.drawable.ic_youtube_24dp);

        }
        else if(type.equalsIgnoreCase("facebook")){
            videoID=videoURL.substring(videoURL.lastIndexOf("videos/")+6,videoURL.length()-1);
            URL ="https://graph.facebook.com/"+videoID+"/picture";
            source.setImageResource(R.drawable.ic_facebook_social);
        }
        else{
            URL =type;
        }
        Uri thumbnail = Uri.parse(URL);
        image.setImageURI(thumbnail);
        //video.setText(videoID);

        nameTv.setOnClickListener(v -> {
            if(type.equalsIgnoreCase("youtube"))
            {
                intent.putExtra("id", videoID);
                intent.putExtra("title",videoName);
                intent.putExtra("date",dateStamp);
                intent.putExtra("creator",creator);
                intent.putExtra("description",description);
                itemView.getContext().startActivity(intent);
            }
            else{
                Intent redirecter = new Intent(Intent.ACTION_VIEW, Uri.parse(videoURL));
                itemView.getContext().startActivity(redirecter);
            }

        });

        date.setOnClickListener(v -> {

            if(type.equalsIgnoreCase("youtube"))
            {
                intent.putExtra("id", videoID);
                intent.putExtra("title",videoName);
                intent.putExtra("date",dateStamp);
                intent.putExtra("creator",creator);
                intent.putExtra("description",description);
                itemView.getContext().startActivity(intent);
            }
            else{
                Intent redirecter = new Intent(Intent.ACTION_VIEW, Uri.parse(videoURL));
                itemView.getContext().startActivity(redirecter);
            }
        });

        creatorTv.setOnClickListener(v -> {

            if(type.equalsIgnoreCase("youtube"))
            {
                intent.putExtra("id", videoID);
                intent.putExtra("title",videoName);
                intent.putExtra("date",dateStamp);
                intent.putExtra("creator",creator);
                intent.putExtra("description",description);
                itemView.getContext().startActivity(intent);
            }
            else{
                Intent redirecter = new Intent(Intent.ACTION_VIEW, Uri.parse(videoURL));
                itemView.getContext().startActivity(redirecter);
            }
        });

        image.setOnClickListener(v -> {

            if(type.equalsIgnoreCase("youtube"))
            {
                intent.putExtra("id", videoID);
                intent.putExtra("title",videoName);
                intent.putExtra("date",dateStamp);
                intent.putExtra("creator",creator);
                intent.putExtra("description",description);
                itemView.getContext().startActivity(intent);
            }
            else{
                Intent redirecter = new Intent(Intent.ACTION_VIEW, Uri.parse(videoURL));
                itemView.getContext().startActivity(redirecter);
            }
        });

        shareVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, videoName);
                share.putExtra(Intent.EXTRA_TEXT, videoURL);

                itemView.getContext().startActivity(Intent.createChooser(share, "Share link!"));
            }
        });


    }

}

