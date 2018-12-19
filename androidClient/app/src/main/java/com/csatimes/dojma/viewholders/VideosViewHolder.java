package com.csatimes.dojma.viewholders;

//import android.net.Uri;
import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.VideosItem;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerInitListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;

public class VideosViewHolder extends RecyclerView.ViewHolder{

    private TextView nameTv,date,creatorTv;
    //private Button video;
    private String videoName,videoURL,dateStamp,creator,type,URL,videoID,description;
    private Uri thumbnail;
    private SimpleDraweeView image;
    private Intent intent = new Intent(itemView.getContext(),com.csatimes.dojma.activities.VideosActivity.class);

    public VideosViewHolder(View itemView) {
        super(itemView);//itemView is view_v.xml
        nameTv = itemView.findViewById(R.id.tv_name);
        date = itemView.findViewById(R.id.dateStamp);
        //video = itemView.findViewById(R.id.go);
        creatorTv = itemView.findViewById(R.id.tv_creator);
        image = itemView.findViewById(R.id.my_image_view);

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

        if(type.equalsIgnoreCase("youtube")){
            videoID=videoURL.substring(videoURL.length()-11,videoURL.length());
            URL="https://img.youtube.com/vi/"+videoID+"/hqdefault.jpg";


        }
        else if(type.equalsIgnoreCase("facebook")){
            videoID=videoURL.substring(videoURL.lastIndexOf("videos/")+6,videoURL.length()-1);
            URL="https://graph.facebook.com/"+videoID+"/picture";

        }
        else{
            URL=type;
        }
        thumbnail=Uri.parse(URL);
        image.setImageURI(thumbnail);
        //video.setText(videoID);

        nameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


            }
        });

        creatorTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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



            }
        });

        image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

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


            }
        });


    }

}

