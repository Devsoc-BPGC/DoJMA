package com.csatimes.dojma.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.R;

/**
 * A simple text viewholder used in Searchable activity
 */

public class SimpleTextViewholder extends RecyclerView.ViewHolder {

    public TextView text;
    public SimpleTextViewholder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.viewholder_simple_text_textview);
    }
}
