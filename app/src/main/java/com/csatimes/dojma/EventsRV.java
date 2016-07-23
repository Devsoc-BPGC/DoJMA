package com.csatimes.dojma;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
    private EventItem[] eventItems;
    private Date date;
    private Date end;
    private Date currentDate;

    public EventsRV(Context context, EventItem[] eventItems, Date currentDate) {
        this.context = context;
        this.eventItems = eventItems;
        this.currentDate = currentDate;
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

        String status;

        DateFormat originalFormat = new SimpleDateFormat("ddMMyyyyHHmm", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("EEE, dd MMM h:mm a", Locale.UK);


        date = null;
        end = null;
        String one = "";

        try {

            date = originalFormat.parse(eventItems[position].getDate() + eventItems[position].getTime());
            end = originalFormat.parse(eventItems[position].getDate() + eventItems[position].getEndTime());

            one = targetFormat.format(date);

            long datesDiff = date.getTime() - currentDate.getTime();
            long endDiff = end.getTime() - currentDate.getTime();
            long days = datesDiff / (24 * 60 * 60 * 1000);
            long hours = datesDiff / (60 * 60 * 1000) % 24;
            long minutes = datesDiff / (60 * 1000) % 60;

            Log.e("TAG", days + " days " + hours + " hours " + minutes + " min ");

            if (days == 0) {
                if (hours == 1) {
                    holder.status.setText("STARTING SOON");
                    holder.status.setTextColor(ContextCompat.getColor(context, R.color.teal500));
                } else if (hours > 1) {
                    holder.status.setText("IN " + hours + " HOURS");
                    holder.status.setTextColor(ContextCompat.getColor(context, R.color.green700));
                } else if (hours == 0 && endDiff > 0) {

                    if (minutes != 1)
                        holder.status.setText("IN " + minutes + " MINUTES ");
                    else holder.status.setText("STARTING");

                    holder.status.setTextColor(ContextCompat.getColor(context, R.color.lightblue500));

                    if (minutes < 0) {
                        holder.status.setText("ONGOING");
                        holder.status.setTextColor(ContextCompat.getColor(context, R.color.blue500));
                    }
                } else {
                    holder.status.setText("EVENT OVER");
                    holder.status.setTextColor(ContextCompat.getColor(context, R.color.red500));
                }


            } else if (days > 0) {
                if (days == 1) {
                    holder.status.setText("TOMORROW");
                    holder.status.setTextColor(ContextCompat.getColor(context, R.color.grey900));
                } else {
                    holder.status.setText("AFTER " + days + " DAYS");
                    holder.status.setTextColor(ContextCompat.getColor(context, R.color.grey700));
                }
            } else if (currentDate.getTime() - end.getTime() > 0) {
                holder.status.setText("EVENT IS OVER");
                holder.status.setTextColor(ContextCompat.getColor(context, R.color.red500));
            }

        } catch (ParseException e) {
            one = "format";
            holder.status.setText("");
        }
        holder.datetime.setText(one);
        holder.location.setText(eventItems[position].getLocation());
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime", date.getTime());
                intent.putExtra("endTIme", end.getTime());
                intent.putExtra("allDay", false);
                intent.putExtra("duration", end.getTime() - date.getTime());
                intent.putExtra("title", eventItems[position].getTitle());
                intent.putExtra("description", eventItems[position].getDesc());
                intent.putExtra("eventLocation", eventItems[position].getLocation());
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Snackbar.make(view, "No calendar found", Snackbar.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(context, "Sorry could not open calendar", Toast.LENGTH_SHORT).show();
                }
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
        TextView status;
        ImageButton add;

        public ViewHolder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.events_format_title);
            desc = (TextView) itemView.findViewById(R.id.events_format_description);
            datetime = (TextView) itemView.findViewById(R.id.events_format_time);
            location = (TextView) itemView.findViewById(R.id.events_format_location);
            add = (ImageButton) itemView.findViewById(R.id.events_format_add);
            status = (TextView) itemView.findViewById(R.id.events_format_status);

        }

    }
}
