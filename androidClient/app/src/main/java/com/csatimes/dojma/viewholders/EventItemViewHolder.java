package com.csatimes.dojma.viewholders;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.EventItem;

public class EventItemViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView desc;
    public TextView dateTime;
    public TextView location;
    public ImageView status;
    public View up;
    public View down;
    public EventItem item;


    public EventItemViewHolder(final View itemView, final Context context) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.item_format_event_title);
        desc = (TextView) itemView.findViewById(R.id.item_format_event_desc);
        dateTime = (TextView) itemView.findViewById(R.id.item_format_event_date_time);
        location = (TextView) itemView.findViewById(R.id.item_format_event_location);
        status = (ImageView) itemView.findViewById(R.id.item_format_event_dot);
        up = itemView.findViewById(R.id.item_format_event_dot_upper);
        down = itemView.findViewById(R.id.item_format_event_dot_lower);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setType("vnd.android.cursor.item/event");
                    intent.putExtra("beginTime", item.getStartDateObj().getTime());
                    intent.putExtra("title", item.getTitle());
                    intent.putExtra("description", item.getDesc());
                    intent.putExtra("eventLocation", item.getLocation());
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "Sorry could not open calendar. ", Toast
                            .LENGTH_SHORT).show();
                }

            }
        });
    }

}