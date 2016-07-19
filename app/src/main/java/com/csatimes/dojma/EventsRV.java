package com.csatimes.dojma;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Vikramaditya Kukreja on 19-07-2016.
 */

class EventsRV extends RecyclerView.Adapter<EventsRV.ViewHolder> {

    Context context;
    EventItem[] eventItems;

    public EventsRV(Context context, EventItem[] eventItems) {
        this.context = context;
        this.eventItems = eventItems;
    }

    @Override
    public EventsRV.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View event_item_format = inflater.inflate(R.layout.event_item_format, parent, false);
        // Return a new holder instance
        return new ViewHolder(event_item_format);
    }

    @Override
    public void onBindViewHolder(EventsRV.ViewHolder holder, int position) {
        holder.title.setText(eventItems[position].getTitle());
        holder.desc.setText(eventItems[position].getDesc());
        holder.datetime.setText(eventItems[position].getDate() + " " + eventItems[position]
                .getTime());
        holder.location.setText(eventItems[position].getLocation());

    }

    @Override
    public int getItemCount() {
        return eventItems.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView desc;
        TextView datetime;
        TextView location;

        public ViewHolder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.events_format_title);
            desc = (TextView) itemView.findViewById(R.id.events_format_description);
            datetime = (TextView) itemView.findViewById(R.id.events_format_time);
            location = (TextView) itemView.findViewById(R.id.events_format_location);

        }

    }
}
