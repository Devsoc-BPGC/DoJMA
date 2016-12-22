package com.csatimes.dojma.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
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

import io.realm.RealmResults;

/**
 * Adapter for Events section
 */

public class EventsRV extends RecyclerView.Adapter<EventsRV.EventItemViewHolder> {

    private RealmResults<EventItem> eventItems;
    private Date currentDate;
    private Context context;
    private OnAlarmSetListener onAlarmSetListener;

    public EventsRV(Context context, RealmResults<EventItem> eventItems, Date currentDate) {
        this.eventItems = eventItems;
        this.currentDate = currentDate;
        this.context = context;
        this.onAlarmSetListener = null;
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

        holder.title.setText(eventItems.get(position).getTitle());
        holder.desc.setText(eventItems.get(position).getDesc());
        holder.dateTime.setText(eventItems.get(position).getStartDateFormatted() + "\n" + eventItems.get(position).getStartTimeFormatted());
        holder.location.setText(eventItems.get(position).getLocation());
        holder.up.setVisibility(View.VISIBLE);
        holder.down.setVisibility(View.VISIBLE);


        if (eventItems.get(position).getStartDateObj() != null) {
            long diff = -currentDate.getTime() + eventItems.get(position).getStartDateObj().getTime();
            int color;

            if (diff <= 0) {
                //Irrespective of whether alarm was set, switch is unchecked since it isn't required anymore
                color = ContextCompat.getColor(context, ColorList.NO_PRIORITY);
                holder.aSwitch.setChecked(false);

            } else {

                holder.aSwitch.setChecked(eventItems.get(position).isAlarmSet());

                long DAY = 24 * 60 * 60 * 1000;
                if (diff > 0 && diff <= DAY) {
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

            }

            holder.status.setColorFilter(color);
            holder.dateTime.setTextColor(color);


        } else {
            holder.dateTime.setTextColor(ContextCompat.getColor(context, ColorList.LOWEST_PRIORITY));
            holder.status.setColorFilter(Color.GRAY);
            holder.aSwitch.setChecked(false);
        }


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
        return eventItems.size();
    }

    public void setOnAlarmSetListener(OnAlarmSetListener onAlarmSetListener) {
        this.onAlarmSetListener = onAlarmSetListener;
    }


    public interface OnAlarmSetListener {
        void onItemClicked(String key);
    }

    class EventItemViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView desc;
        TextView dateTime;
        TextView location;
        ImageView status;
        SwitchCompat aSwitch;
        View up;
        View down;


        EventItemViewHolder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_format_event_title);
            desc = (TextView) itemView.findViewById(R.id.item_format_event_desc);
            dateTime = (TextView) itemView.findViewById(R.id.item_format_event_date_time);
            location = (TextView) itemView.findViewById(R.id.item_format_event_location);
            aSwitch = (SwitchCompat) itemView.findViewById(R.id.item_format_event_add);
            status = (ImageView) itemView.findViewById(R.id.item_format_event_dot);
            up = itemView.findViewById(R.id.item_format_event_dot_upper);
            down = itemView.findViewById(R.id.item_format_event_dot_lower);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (eventItems.get(getAdapterPosition()).getStartDateObj() != null && eventItems.get(getAdapterPosition()).getStartDateObj().getTime() - currentDate.getTime() >= 0) {
                        aSwitch.toggle();
                        if (onAlarmSetListener != null) {
                            onAlarmSetListener.onItemClicked(eventItems.get(getAdapterPosition()).getKey());
                        }
                    } else {
                        Toast.makeText(context, "Cannot set reminder for old event", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }
}
