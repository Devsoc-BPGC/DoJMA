package com.csatimes.dojma.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.R;

/**
 * Created by vikramaditya on 24/2/17.
 */

public class ContributorsViewHolder extends RecyclerView.ViewHolder {
    public TextView nameTextView;

    public ContributorsViewHolder(View itemView) {
        super(itemView);
        nameTextView = (TextView) itemView.findViewById(R.id.item_format_contributors_name_tv);
    }
}
