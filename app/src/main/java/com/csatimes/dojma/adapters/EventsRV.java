package com.csatimes.dojma.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.EventItem;
import com.csatimes.dojma.utilities.ColorList;
import com.csatimes.dojma.viewholders.EventItemViewHolder;

import java.util.Date;

import io.realm.RealmResults;

/**
 * Created by Vikramaditya Kukreja on 19-07-2016.
 */

public class EventsRV extends RecyclerView.Adapter<EventItemViewHolder> {

    private RealmResults<EventItem> eventItems;
    private Date currentDate;
    private Context context;
    private long DAY = 24 * 60 * 60 * 1000;

    public EventsRV(Context context, RealmResults<EventItem> eventItems, Date currentDate) {
        this.eventItems = eventItems;
        this.currentDate = currentDate;
        this.context = context;
    }

    @Override
    public EventItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View event_item_format = inflater.inflate(R.layout.item_format_event, parent, false);
        // Return a new holder instance
        return new EventItemViewHolder(event_item_format);
    }

    @Override
    public void onBindViewHolder(EventItemViewHolder holder, int position) {

        holder.title.setText(eventItems.get(position).getTitle());
        holder.desc.setText(eventItems.get(position).getDesc());
        holder.time.setText(eventItems.get(position).getStartTimeFormatted());
        holder.date.setText(eventItems.get(position).getStartDateFormatted());
        holder.location.setText(eventItems.get(position).getLocation());

        if (eventItems.get(position).getStartDateObj() != null) {
            long diff = -currentDate.getTime() + eventItems.get(position).getStartDateObj().getTime();
            int color;

            if (diff <= 0) {
                color = ContextCompat.getColor(context, ColorList.NO_PRIORITY);
                holder.aSwitch.setChecked(false);
                holder.aSwitch.setEnabled(false);
                holder.aSwitch.setClickable(false);
                holder.itemView.setClickable(false);

            } else if (diff > 0 && diff <= DAY) {
                color = ContextCompat.getColor(context, ColorList.HIGHEST_PRIORITY);
            } else if (diff > DAY && diff <= 3 * DAY) {
                color = ContextCompat.getColor(context, ColorList.HIGHER_PRIORITY);
            } else if (diff > 3 * DAY && diff <= 7 * DAY) {
                color = ContextCompat.getColor(context, ColorList.HIGH_PRIORITY);
            } else if (diff > 7 * DAY && diff <= 14 * DAY) {
                color = ContextCompat.getColor(context, ColorList.NORMAL_PRIORITY);
            } else if (diff > 14 * DAY && diff <= 30 * DAY) {
                color = ContextCompat.getColor(context, ColorList.LOW_PRIORITY);
            } else if (diff > 30 * DAY && diff <= 365 * DAY) {
                color = ContextCompat.getColor(context, ColorList.LOWER_PRIORITY);
            } else {
                color = ContextCompat.getColor(context, ColorList.LOWEST_PRIORITY);
            }

            holder.status.setColorFilter(color);
            holder.time.setTextColor(color);
            holder.date.setTextColor(color);

        } else holder.status.setColorFilter(Color.GRAY);


        //Hide bars if first or last view
        if (position == 0) {
            holder.up.setVisibility(View.GONE);
        }
        if (position == getItemCount() - 1) {
            holder.down.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (eventItems != null) {
            return eventItems.size();
        } else {
            return 0;
        }
    }

}
