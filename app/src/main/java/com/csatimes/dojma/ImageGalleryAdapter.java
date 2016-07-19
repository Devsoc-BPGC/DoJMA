package com.csatimes.dojma;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import io.realm.RealmList;

/**
 * Created by Vikramaditya Kukreja on 19-06-2016.
 */

public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.ViewHolder> {
    private final OnPhotoListener listener;
    private Context context;
    private RealmList<HeraldNewsItemFormat> resultsList;

    public ImageGalleryAdapter(Context context, RealmList<HeraldNewsItemFormat> resultsList, OnPhotoListener listener) {

        this.context = context;
        this.resultsList = resultsList;
        this.listener = listener;
    }

    public static SimpleDraweeView getImage(RecyclerView.ViewHolder holder) {
        if (holder instanceof ViewHolder) {
            return ((ViewHolder) holder).simpleDraweeView;
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
        final HeraldNewsItemFormat foobar = resultsList.get(position);
        try {
            holder.simpleDraweeView.setImageURI(Uri.parse(foobar.getImageURL()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.simpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onPhotoClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
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
        SimpleDraweeView simpleDraweeView;

        ViewHolder(final View itemView) {
            super(itemView);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.images_rv_imageview);
            CircleImageDrawable cid = new CircleImageDrawable();
            cid.setRadius(75);
            simpleDraweeView.getHierarchy().setProgressBarImage(cid);
        }
    }

}
