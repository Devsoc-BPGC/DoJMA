package com.csatimes.dojma.viewholders;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.LinkItem;
import com.csatimes.dojma.utilities.DHC;

import static android.content.Intent.ACTION_VIEW;

/**
 * Created by vikramaditya on 14/12/16.
 */

public class LinkItemViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView url;
    public LinkItem linkItem;

    public LinkItemViewHolder(final View itemView,final Context context) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.item_format_links_title);
        url = (TextView) itemView.findViewById(R.id.item_format_links_url);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ACTION_VIEW);
                intent.setData(Uri.parse(linkItem.getUrl()));
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Snackbar snackbar = DHC.makeCustomSnackbar(itemView,"No app to view link!", ContextCompat.getColor(context,R.color.colorAccent), Color.WHITE);
                    snackbar.show();
                }
            }
        });
    }
}