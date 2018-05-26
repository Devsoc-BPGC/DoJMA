package com.csatimes.dojma.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.PosterItem;
import com.csatimes.dojma.utilities.CircleImageDrawable;
import com.facebook.drawee.view.SimpleDraweeView;

import io.realm.RealmList;

/**
 * Created by Vikramaditya Kukreja on 19-06-2016.
 */

public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.ViewHolder> {
    private final OnPhotoListener listener;
    private RealmList<PosterItem> posterItems;
    private Context context;

    public ImageGalleryAdapter(Context context, RealmList<PosterItem> posterItems, OnPhotoListener listener) {

        this.context = context;
        this.posterItems = posterItems;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(context,R.layout.item_format_images_rv,null));
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.imageView.setImageURI(posterItems.get(position).getUrl());
        holder.textView.setText(posterItems.get(position).getTitle());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onPhotoClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return posterItems.size();
    }

    public interface OnPhotoListener {
        void onPhotoClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView imageView;
        TextView textView;

        ViewHolder(final View itemView) {
            super(itemView);
            imageView = (SimpleDraweeView) itemView.findViewById(R.id.item_format_images_rv_image);
            textView = (TextView) itemView.findViewById(R.id.item_format_images_rv_title);
            imageView.getHierarchy().setProgressBarImage(new CircleImageDrawable());
        }
    }

}
