package com.csatimes.dojma;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;

import io.realm.RealmList;

/**
 * Created by Vikramaditya Kukreja on 19-06-2016.
 */

public class ImageGalleryRV extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private Tracker mTracker;
    private RealmList<HeraldNewsItemFormat> resultsList;

    public ImageGalleryRV(Context context, RealmList<HeraldNewsItemFormat> resultsList) {
        this.context = context;
        this.resultsList = resultsList;

        Fresco.initialize(context);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = this.context;
        if (viewType == 0) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View herald_card_view_format = inflater.inflate(R.layout.images_rv_item_format, parent, false);

            return new ViewHolder1(herald_card_view_format);
        } else {
            LayoutInflater inflater = LayoutInflater.from(context);
            View herald_card_view_format = inflater.inflate(R.layout.images_rv_item_picasso,
                    parent, false);

            return new ViewHolder2(herald_card_view_format);

        }
    }

    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final HeraldNewsItemFormat foobar = resultsList.get(position);
        if (holder.getItemViewType() == 0) {
            ViewHolder1 vh1 = (ViewHolder1) holder;
            vh1.simpleDraweeView.setImageURI(Uri.parse(foobar.getImageURL()));

        } else {
            ViewHolder2 vh2 = (ViewHolder2) holder;
            Picasso.with(context).load(resultsList.get(position).getImageURL()).into
                    (vh2.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 4 <= 1) return 0;
        else return 1;
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        SimpleDraweeView simpleDraweeView;

        ViewHolder1(final View itemView) {
            super(itemView);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.images_rv_imageview);
        }

    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder2(final View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.images_rv_imageview_simple);
        }
    }
}
