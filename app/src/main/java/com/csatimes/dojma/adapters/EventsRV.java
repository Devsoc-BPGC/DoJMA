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

import io.realm.RealmList;

/**
 * Adapter for Events section
 */

public class EventsRV extends RecyclerView.Adapter<EventItemViewHolder> {

    private RealmList<EventItem> mEventItems;
    private Date mCurrentDate;
    private Context mContext;

    public EventsRV(Context mContext, RealmList<EventItem> mEventItems, Date mCurrentDate) {
        this.mEventItems = mEventItems;
        this.mCurrentDate = mCurrentDate;
        this.mContext = mContext;
    }

    @Override
    public EventItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View event_item_format = inflater.inflate(R.layout.item_format_event, parent, false);
        // Return a new holder instance
        return new EventItemViewHolder(event_item_format, mContext);
    }

    @Override
    public void onBindViewHolder(EventItemViewHolder holder, int position) {

        holder.item = mEventItems.get(position);
        holder.title.setText(holder.item.getTitle());
        holder.desc.setText(holder.item.getDesc());
        holder.dateTime.setText(holder.item.getStartDateFormatted() + "\n" + holder.item.getStartTimeFormatted());
        holder.location.setText(holder.item.getLocation());


        if (holder.item.getStartDateObj() != null) {
            long diff = -mCurrentDate.getTime() + holder.item.getStartDateObj().getTime();
            int color;

            if (diff <= 0) {
                //Irrespective of whether alarm was set, switch is unchecked since it isn't required anymore
                color = ContextCompat.getColor(mContext, ColorList.NO_PRIORITY);
            } else {
                color = getColorFromDate(diff);
            }

            holder.status.setColorFilter(color);
            holder.dateTime.setTextColor(color);


        } else {
            holder.dateTime.setTextColor(ContextCompat.getColor(mContext, ColorList.LOWEST_PRIORITY));
            holder.status.setColorFilter(Color.GRAY);
        }


        //Hide bars if first or last view
        if (position == 0 || position == getItemCount() - 1) {
            if (position == 0) {
                holder.up.setVisibility(View.GONE);
            }
            if (position == getItemCount() - 1) {
                holder.down.setVisibility(View.GONE);
            }
        } else {
            holder.up.setVisibility(View.VISIBLE);
            holder.down.setVisibility(View.VISIBLE);
        }
    }

    private int getColorFromDate(long diff) {
        int color;
        long DAY = 24 * 60 * 60 * 1000;
        if (diff > 0 && diff <= DAY) {
            color = ContextCompat.getColor(mContext, ColorList.HIGHEST_PRIORITY);
        } else if (diff > DAY && diff <= 3 * DAY) {
            color = ContextCompat.getColor(mContext, ColorList.HIGHER_PRIORITY);
        } else if (diff > 3 * DAY && diff <= 7 * DAY) {
            color = ContextCompat.getColor(mContext, ColorList.HIGH_PRIORITY);
        } else if (diff > 7 * DAY && diff <= 14 * DAY) {
            color = ContextCompat.getColor(mContext, ColorList.NORMAL_PRIORITY);
        } else if (diff > 14 * DAY && diff <= 30 * DAY) {
            color = ContextCompat.getColor(mContext, ColorList.LOW_PRIORITY);
        } else if (diff > 30 * DAY && diff <= 365 * DAY) {
            color = ContextCompat.getColor(mContext, ColorList.LOWER_PRIORITY);
        } else {
            color = ContextCompat.getColor(mContext, ColorList.LOWEST_PRIORITY);
        }
        return color;
    }

    @Override
    public int getItemCount() {
        return mEventItems.size();
    }

}
