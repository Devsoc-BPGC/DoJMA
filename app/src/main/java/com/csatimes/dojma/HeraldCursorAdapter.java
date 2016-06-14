package com.csatimes.dojma;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.CursorAdapter;
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

/**
 * Created by Vikramaditya Kukreja on 06-06-2016.
 *
 * @author Vikramaditya Kukreja
 * @see android.support.v4.widget.CursorAdapter
 */
public class HeraldCursorAdapter extends CursorAdapter {
    TextView title, author, date;
    ImageView image;
    ProgressBar progressBar;

    public HeraldCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {

        title = (TextView) view.findViewById(R.id.herald_lv_item_title);
        author = (TextView) view.findViewById(R.id.herald_lv_item_author);
        date = (TextView) view.findViewById(R.id.herald_lv_item_date);
        image = (ImageView) view.findViewById(R.id.herald_lv_item_image);
        progressBar = (ProgressBar) view.findViewById(R.id.loadingImage);
        //_ID , AUTHOR , TITLE , IMAGE URL , UPDATE , POST DATE , POST_URL
        title.setText(cursor.getString(2));
        author.setText(cursor.getString(1));
        date.setText(cursor.getString(5));
        image.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        if (DHC.doesImageExists(cursor.getString(0))) {
            Picasso.with(context).load(new File(DHC.directory, cursor.getString(0) + "" +
                    ".jpeg")).resize(DHC.dpToPx(40), DHC.dpToPx(40)).error(R.drawable.ic_menu_gallery)
                    .into(image, new Callback() {
                        @Override
                        public void onSuccess() {
                            image
                                    .setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError() {

                        }
                    });

            Log.e("TAG", "Image Found! " + cursor.getString(0));
        } else {

            Log.e("TAG", "Image Not Found! Downloading");
            Picasso.with(context).load(cursor.getString(3))
                    .into
                            (new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    image.setImageBitmap(bitmap);
                                    DHC.saveImage(bitmap, cursor.getString(0));
                                    Log.e("TAG", "Saved! + " + cursor.getString(0));
                                    image.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void onBitmapFailed(Drawable errorDrawable) {
                                    image.setImageResource(R.drawable.no_image_info);
                                    Log.e("TAG", "Failed Download");
                                    image.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);

                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {

                                }
                            });

        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.herald_lv_item_format, parent, false);
    }


}
