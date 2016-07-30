package com.csatimes.dojma;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Vikramaditya Kukreja on 21-07-2016.
 */

public class UtilitiesViewHolder4 extends RecyclerView.ViewHolder {
    TextView text;

    public UtilitiesViewHolder4(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.viewholder_misc_format_text);

    }
}
