package com.csatimes.dojma;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Vector;

/**
 * Created by Vikramaditya Kukreja on 19-06-2016.
 */

public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.ViewHolder> {
    private final OnPhotoListener listener;
    Vector<PosterItem> posterItems;
    private Context context;

    public ImageGalleryAdapter(Context context, Vector<PosterItem> posterItems, OnPhotoListener listener) {

        this.context = context;
        this.posterItems = posterItems;
        this.listener = listener;
    }

    public static ImageView getImage(RecyclerView.ViewHolder holder) {
        if (holder instanceof ViewHolder) {
            return ((ViewHolder) holder).imageView;
        } else {
            return null;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View herald_card_view_format = inflater.inflate(R.layout.images_rv_item_format, parent, false);

        return new ViewHolder(herald_card_view_format);


    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.imageView.setImageURI(posterItems.get(position).url);
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

    //This method is used when more than one kind of ViewHolders are required in RecyclerView
    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public interface OnPhotoListener {
        void onPhotoClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView imageView;

        ViewHolder(final View itemView) {
            super(itemView);
            imageView = (SimpleDraweeView) itemView.findViewById(R.id.images_rv_imageview);
            imageView.getHierarchy().setProgressBarImage(new CircleImageDrawable());
        }
    }

}
