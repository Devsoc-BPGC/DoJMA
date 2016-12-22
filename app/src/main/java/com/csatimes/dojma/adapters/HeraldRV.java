package com.csatimes.dojma.adapters;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.HeraldNewsItemFormat;
import com.csatimes.dojma.utilities.CircleImageDrawable;
import com.facebook.drawee.view.SimpleDraweeView;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmList;

/**
 * Created by Vikramaditya Kukreja on 19-06-2016.
 */

public class HeraldRV extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RealmList<HeraldNewsItemFormat> resultsList;
    //Listeners
    private OnLikeClickedListener onLikeClickedListener;
    private OnShareClickedListener onShareClickedListener;
    private OnItemClickedListener onItemClickedListener;

    public HeraldRV(RealmList<HeraldNewsItemFormat> resultsList) {
        this.resultsList = resultsList;
        this.onLikeClickedListener = null;
        this.onShareClickedListener = null;
        this.onItemClickedListener = null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View herald_card_view_format = inflater.inflate(R.layout.item_format_herald, parent, false);
        return new HeraldPotraitViewHolder(herald_card_view_format);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        HeraldPotraitViewHolder viewHolder = (HeraldPotraitViewHolder) holder;
        HeraldNewsItemFormat foobar = resultsList.get(position);


        try {
            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
            Date of = simpleDate.parse(foobar.getOriginalDate());
            SimpleDateFormat tf = new SimpleDateFormat("dd MMM , ''yy", Locale.UK);
            viewHolder.date.setText(tf.format(of));
        } catch (Exception e) {
            viewHolder.date.setText(foobar.getOriginalDate());
        }

        if (foobar.isFav())
            viewHolder.fav.setLiked(true);
        else viewHolder.fav.setLiked(false);

        //since Html.fromHtml is deprecated from N onwards we add the special flag
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            viewHolder.desc.setText(Html.fromHtml(foobar.getExcerpt(), Html.FROM_HTML_MODE_LEGACY));
            viewHolder.title.setText(Html.fromHtml(foobar.getTitle(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            viewHolder.desc.setText(Html.fromHtml(foobar.getExcerpt()));
            viewHolder.title.setText(Html.fromHtml(foobar.getTitle()));
        }
        viewHolder.imageView.setImageURI(Uri.parse(foobar.getImageURL()));

    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    public void setOnLikeClickedListener(OnLikeClickedListener onLikeClickedListener) {
        this.onLikeClickedListener = onLikeClickedListener;
    }

    public void setOnShareClickedListener(OnShareClickedListener onShareClickedListener) {
        this.onShareClickedListener = onShareClickedListener;
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    public interface OnLikeClickedListener {
        void onLiked(String postID);

        void onDisLiked(String postID);
    }

    public interface OnShareClickedListener {
        void onShare(String postID);
    }

    public interface OnItemClickedListener {
        void onClick(String postID);
    }

    private class HeraldPotraitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView date;
        SimpleDraweeView imageView;
        TextView desc;
        LikeButton fav;
        ImageButton share;

        HeraldPotraitViewHolder(View itemView) {
            super(itemView);

            imageView = (SimpleDraweeView) itemView.findViewById(R.id.item_format_herald_image);
            date = (TextView) itemView.findViewById(R.id.item_format_herald_date);
            title = (TextView) itemView.findViewById(R.id.item_format_herald_title);
            desc = (TextView) itemView.findViewById(R.id.item_format_herald_desc);
            fav = (LikeButton) itemView.findViewById(R.id.item_format_herald_heart);
            share = (ImageButton) itemView.findViewById(R.id.item_format_herald_share);
            imageView.getHierarchy().setProgressBarImage(new CircleImageDrawable());

            fav.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    onLikeClickedListener.onLiked(resultsList.get(getAdapterPosition()).getPostID());
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    onLikeClickedListener.onDisLiked(resultsList.get(getAdapterPosition()).getPostID());
                }
            });

            itemView.setOnClickListener(this);
            share.setOnClickListener(this);

        }


        @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
        @Override
        public void onClick(View view) {

            String postID = resultsList.get(getAdapterPosition()).getPostID();

            if (view.getId() == itemView.getId()) {
                onItemClickedListener.onClick(postID);
            } else if (view.getId() == share.getId()) {
                onShareClickedListener.onShare(postID);
            }

        }


    }
}
