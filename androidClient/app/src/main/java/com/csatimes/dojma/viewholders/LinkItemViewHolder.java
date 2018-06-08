package com.csatimes.dojma.viewholders;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.LinkItem;
import com.csatimes.dojma.utilities.Browser;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by vikramaditya on 14/12/16.
 */

public class LinkItemViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView url;
    public LinkItem linkItem;

    public LinkItemViewHolder(final View itemView, final Activity context) {
        super(itemView);
        title = itemView.findViewById(R.id.item_format_links_title);
        url = itemView.findViewById(R.id.item_format_links_url);
        itemView.setOnClickListener(v -> new Browser(context).launchUrl(linkItem.getUrl()));
    }
}
