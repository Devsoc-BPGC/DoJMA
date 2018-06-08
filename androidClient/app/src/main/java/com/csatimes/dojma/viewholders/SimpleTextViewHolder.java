package com.csatimes.dojma.viewholders;

import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple text viewholder used in Searchable activity
 */

public class SimpleTextViewHolder extends RecyclerView.ViewHolder {

    public TextView text;

    public SimpleTextViewHolder(View itemView) {
        super(itemView);
        text = itemView.findViewById(R.id.viewholder_simple_text_textview);
    }
}
