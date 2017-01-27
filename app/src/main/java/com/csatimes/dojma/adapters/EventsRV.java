package com.csatimes.dojma.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.EventItem;
import com.csatimes.dojma.utilities.ColorList;

import java.util.Date;

import io.realm.RealmList;

/**
 * Adapter for Events section
 */

public class EventsRV extends RecyclerView.Adapter<EventsRV.EventItemViewHolder> {

    private RealmList<EventItem> mEventItems;
    private Date mCurrentDate;
    private Context mContext;

    public EventsRV(Context mContext, RealmList<EventItem> mEventItems, Date mCurrentDate) {
        this.mEventItems = mEventItems;
        this.mCurrentDate = mCurrentDate;
        this.mContext = mContext;
    }

    @Override
    public EventsRV.EventItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View event_item_format = inflater.inflate(R.layout.item_format_event, parent, false);
        // Return a new holder instance
        return new EventsRV.EventItemViewHolder(event_item_format);
    }

    @Override
    public void onBindViewHolder(final EventsRV.EventItemViewHolder holder, int position) {

        holder.title.setText(mEventItems.get(position).getTitle());
        holder.desc.setText(mEventItems.get(position).getDesc());
        holder.dateTime.setText(mEventItems.get(position).getStartDateFormatted() + "\n" + mEventItems.get(position).getStartTimeFormatted());
        holder.location.setText(mEventItems.get(position).getLocation());


        if (mEventItems.get(position).getStartDateObj() != null) {
            long diff = -mCurrentDate.getTime() + mEventItems.get(position).getStartDateObj().getTime();
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

    class EventItemViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView desc;
        TextView dateTime;
        TextView location;
        ImageView status;
        View up;
        View down;


        EventItemViewHolder(final View itemView) {
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
                public void onClick(View view) {
                    if (mEventItems.get(getAdapterPosition()).getStartDateObj() != null && mEventItems.get(getAdapterPosition()).getStartDateObj().getTime() - mCurrentDate.getTime() >= 0) {
                       //TODO set alarm
                    } else {
                        Toast.makeText(mContext, "Cannot set reminder for old event", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }
}
