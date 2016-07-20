package com.csatimes.dojma;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Vikramaditya Kukreja on 19-07-2016.
 */

class EventsRV extends RecyclerView.Adapter<EventsRV.ViewHolder> {

    Context context;
    EventItem[] eventItems;
    Date date, time;

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
    public void onBindViewHolder(EventsRV.ViewHolder holder, final int position) {
        holder.title.setText(eventItems[position].getTitle());
        holder.desc.setText(eventItems[position].getDesc());
        DateFormat originalFormat = new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("EEE, dd MMM", Locale.UK);
        DateFormat originalTimeFormat = new SimpleDateFormat("HHmm", Locale.ENGLISH);
        DateFormat targetTimeFormat = new SimpleDateFormat("h:mm a", Locale.UK);

        date = null;
        time = null;
        String one = "";
        String two = "";
        try {
            date = originalFormat.parse(eventItems[position].getDate());
            String formattedDate = targetFormat.format(date);
            one = formattedDate;
        } catch (ParseException e) {
            one = "date_error";
        }
        try {
            time = originalTimeFormat.parse(eventItems[position].getTime());
            String formattedTime = targetTimeFormat.format(time);
            two = formattedTime;
        } catch (ParseException e) {
            two = "time_error";
        }
        holder.datetime.setText(one + " " + two);
        holder.location.setText(eventItems[position].getLocation());
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime", date.getTime());
                intent.putExtra("allDay", true);
                intent.putExtra("title", eventItems[position].getTitle());
                intent.putExtra("description", eventItems[position].getDesc());
                intent.putExtra("eventLocation", eventItems[position].getLocation());
                context.startActivity(intent);
            }
        });
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
        ImageButton add;

        public ViewHolder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.events_format_title);
            desc = (TextView) itemView.findViewById(R.id.events_format_description);
            datetime = (TextView) itemView.findViewById(R.id.events_format_time);
            location = (TextView) itemView.findViewById(R.id.events_format_location);
            add = (ImageButton) itemView.findViewById(R.id.events_format_add);
        }

    }
}
