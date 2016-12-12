package com.csatimes.dojma.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.csatimes.dojma.R;

/**
 * Created by vikramaditya on 11/12/16.
 */

public class EventItemViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView desc;
    public TextView datetime;
    public TextView location;
    public TextView status;
    public ImageButton add;

    public EventItemViewHolder(final View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.events_format_title);
        desc = (TextView) itemView.findViewById(R.id.events_format_description);
        datetime = (TextView) itemView.findViewById(R.id.events_format_time);
        location = (TextView) itemView.findViewById(R.id.events_format_location);
        add = (ImageButton) itemView.findViewById(R.id.events_format_add);
        status = (TextView) itemView.findViewById(R.id.events_format_status);

    }

}