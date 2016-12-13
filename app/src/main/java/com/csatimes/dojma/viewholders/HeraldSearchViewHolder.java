package com.csatimes.dojma.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Herald item in Searchable activity
 */

public class HeraldSearchViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView date;
    public SimpleDraweeView simpleDraweeView;

    public HeraldSearchViewHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.search_herald_title);
        date = (TextView) itemView.findViewById(R.id.search_herald_date);
        simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.search_herald_image);

    }
}
