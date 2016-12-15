package com.csatimes.dojma.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.csatimes.dojma.R;

/**
 * Created by vikramaditya on 11/12/16.
 */

public class EventItemViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView desc;
    public TextView date;
    public TextView time;
    public TextView location;
    public ImageView status;
    public Switch aSwitch;
    public View up;
    public View down;


    public EventItemViewHolder(final View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.item_format_event_title);
        desc = (TextView) itemView.findViewById(R.id.item_format_event_desc);
        date = (TextView) itemView.findViewById(R.id.item_format_event_date);
        time = (TextView) itemView.findViewById(R.id.item_format_event_time);
        location = (TextView) itemView.findViewById(R.id.item_format_event_location);
        aSwitch = (Switch) itemView.findViewById(R.id.item_format_event_add);
        status = (ImageView) itemView.findViewById(R.id.item_format_event_dot);
        up = itemView.findViewById(R.id.item_format_event_dot_upper);
        down = itemView.findViewById(R.id.item_format_event_dot_lower);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aSwitch.toggle();
            }
        });

    }

}