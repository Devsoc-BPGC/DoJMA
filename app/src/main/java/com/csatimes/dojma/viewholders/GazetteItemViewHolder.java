package com.csatimes.dojma.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Gazette viewholder having a title,image
 */

public class GazetteItemViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public SimpleDraweeView image;

    public GazetteItemViewHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.item_format_gazette_title);
        image = (SimpleDraweeView) itemView.findViewById(R.id.item_format_gazette_image);
    }
}
