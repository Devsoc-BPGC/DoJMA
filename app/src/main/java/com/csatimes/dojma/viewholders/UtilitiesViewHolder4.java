package com.csatimes.dojma.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.R;

/**
 * Created by Vikramaditya Kukreja on 21-07-2016.
 */

public class UtilitiesViewHolder4 extends RecyclerView.ViewHolder {
    public TextView text;

    public UtilitiesViewHolder4(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.viewholder_misc_format_text);

    }
}
