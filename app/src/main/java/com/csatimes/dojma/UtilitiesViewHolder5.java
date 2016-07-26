package com.csatimes.dojma;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Vikramaditya Kukreja on 22-07-2016.
 */

public class UtilitiesViewHolder5 extends RecyclerView.ViewHolder {

    public UtilitiesViewHolder5(final View itemView, final Context context) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/d/u/0/viewer?mid=1oWEtH59EbMs82Z49uj00UnxQD2o"));
                intent.setPackage("com.google.android.apps.maps");
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                } else {
                    Snackbar.make(itemView, "Google Maps is not installed!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

}
