package com.csatimes.dojma;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

import io.realm.RealmResults;

/**
 * Created by Vikramaditya Kukreja on 19-06-2016.
 */

public class HeraldRV extends RecyclerView.Adapter<HeraldRV.ViewHolder> {
    private Context context;
    private RealmResults<HeraldNewsItemFormat> results;
    private int pixels = DHC.dpToPx(40);

    public HeraldRV(Context context, RealmResults<HeraldNewsItemFormat> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View herald_card_view_format = inflater.inflate(R.layout.herald_card_view_format, parent, false);
        // Return a new holder instance
        return new ViewHolder(herald_card_view_format);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        HeraldNewsItemFormat foo = results.get(position);
        holder.date.setText(results.get(position).getOriginalDate());
        holder.author.setText(results.get(position).getAuthor());
        holder.title.setText(results.get(position).getTitle());
        holder.imageView.setVisibility(View.INVISIBLE);
        holder.progressBar.setVisibility(View.VISIBLE);
        if (DHC.doesImageExists(results.get(position).getPostID())) {
            Picasso.with(context).load(new File(DHC.directory, results.get(position).getPostID() + "" +
                    ".jpeg")).resize(pixels, pixels).error(R.drawable.no_image_info)
                    .into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.imageView.setVisibility(View.VISIBLE);
                            holder.progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError() {
                            holder.progressBar.setVisibility(View.INVISIBLE);
                        }
                    });

        } else {
            final int pos = position;
            Log.e("TAG", "Image Not Found! Downloading");
            Picasso.with(context).load(results.get(position).getImageURL())
                    .into
                            (new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    holder.imageView.setImageBitmap(bitmap);
                                    DHC.saveImage(bitmap, results.get(pos).getPostID());
                                    holder.imageView.setVisibility(View.VISIBLE);
                                    holder.progressBar.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void onBitmapFailed(Drawable errorDrawable) {
                                    holder.imageView.setImageResource(R.drawable.no_image_info);
                                    Log.e("TAG", "Failed Download");
                                    holder.imageView.setVisibility(View.VISIBLE);
                                    holder.progressBar.setVisibility(View.INVISIBLE);

                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {

                                }
                            });

        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView title;
        public TextView author;
        public TextView date;
        public ProgressBar progressBar;

        public ViewHolder(final View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.herald_rv_item_image);
            author = (TextView) itemView.findViewById(R.id.herald_rv_item_author);
            date = (TextView) itemView.findViewById(R.id.herald_rv_item_date);
            title = (TextView) itemView.findViewById(R.id.herald_rv_item_title);
            progressBar = (ProgressBar) itemView.findViewById(R.id.loadingImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            {
                if (view.getId() == itemView.getId()) {
                    if (isOnline()) {
                        Intent openWebpage = new Intent(context, OpenWebpage.class);
                        openWebpage.putExtra("URL", results.get(getAdapterPosition()).getLink());
                        openWebpage.putExtra("TITLE", results.get(getAdapterPosition()).getTitle());
                        context.startActivity(openWebpage);
                    } else {
                        Snackbar.make(view, "Unable to connect to internet", Snackbar.LENGTH_LONG).show();
                    }
                }
            }

        }
    }
}
