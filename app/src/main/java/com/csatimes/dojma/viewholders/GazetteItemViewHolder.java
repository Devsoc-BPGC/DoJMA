package com.csatimes.dojma.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.R;

/**
 * Gazette viewholder having a single element title
 */

public class GazetteItemViewHolder extends RecyclerView.ViewHolder {
    public TextView title;

    public GazetteItemViewHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.item_format_gazette_title);
    }
}
