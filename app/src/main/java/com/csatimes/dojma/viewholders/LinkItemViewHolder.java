package com.csatimes.dojma.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.R;

/**
 * Created by vikramaditya on 14/12/16.
 */

public class LinkItemViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView url;


    public LinkItemViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.item_format_links_title);
        url = (TextView) itemView.findViewById(R.id.item_format_links_url);
    }
}