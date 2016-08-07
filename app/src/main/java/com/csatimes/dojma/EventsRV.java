package com.csatimes.dojma;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import java.util.Vector;

/**
 * Created by Vikramaditya Kukreja on 19-07-2016.
 */

class EventsRV extends RecyclerView.Adapter<EventsRV.ViewHolder> {

    Context context;
    private Vector<EventItem> eventItems;
    private Date date;
    private Date end;
    private Date currentDate;

    public EventsRV(Context context, Vector<EventItem> eventItems, Date currentDate) {
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
    public void onBindViewHolder(EventsRV.ViewHolder holder, int position) {
        final int pos = position;
        try {
            holder.title.setText(eventItems.get(pos).getTitle());
            holder.desc.setText(eventItems.get(pos).getDesc());

            DateFormat originalFormat = new SimpleDateFormat("ddMMyyyyHHmm", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("EEE, dd MMM h:mm a", Locale.UK);
            DateFormat editedFormat = new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH);
            DateFormat editedTargetFormat = new SimpleDateFormat("EEE, dd MMM", Locale.ENGLISH);


            date = null;
            end = null;

            String startDateText = "";
            try {
                //Check if start time exists
                if (!eventItems.get(pos).getStartTime().equalsIgnoreCase("-")) {
                    date = originalFormat.parse(eventItems.get(pos).getStartDate() +
                            eventItems.get(pos).getStartTime());
                    startDateText = targetFormat.format(date);
                    Log.e("TAG", eventItems.get(pos).getEndTime() + " end time");
                    //check if there is an end time
                    if (!eventItems.get(pos).getEndTime().equalsIgnoreCase("-")) {
                        //check if there is an end date otherwise set it to startdate
                        if (!eventItems.get(pos).getEndDate().equalsIgnoreCase("-")) {
                            Log.e("TAG", "end date is not -");
                            end = originalFormat.parse(eventItems.get(pos).getEndDate() +
                                    eventItems.get(pos).getEndTime());
                        } else {
                            Log.e("TAG", "end date is - using start");

                            end = originalFormat.parse(eventItems.get(pos).getStartDate() +
                                    eventItems.get(pos).getEndTime());
                        }

                    } else {
                        //end time is unknown, set end to null
                        end = null;
                        Log.e("TAG", "end set to null");
                    }
                } else {
                    Log.e("TAG", "start time lo1");
                    //start time does not exist . only use day
                    //don't bother for end time or date then
                    date = editedFormat.parse(eventItems.get(pos).getStartDate());
                    startDateText = editedTargetFormat.format(date);
                    end = null;
                }

                //If start time is known, only then we can consider time calculation
                if (!eventItems.get(pos).getStartTime().equalsIgnoreCase("-")) {
                    long datesDiff = date.getTime() - currentDate.getTime();
                    if (end != null) {
                        long endDiff = end.getTime() - currentDate.getTime();

                        long days = datesDiff / (24 * 60 * 60 * 1000);
                        long hours = datesDiff / (60 * 60 * 1000) % 24;
                        long minutes = datesDiff / (60 * 1000) % 60;


                        if (datesDiff >= 0) {
                            if (days == 0) {
                                if (hours == 1) {
                                    holder.status.setText("STARTING IN THE NEXT HOUR");
                                    holder.status.setTextColor(ContextCompat.getColor(context, R.color.teal500));
                                } else if (hours > 1) {
                                    holder.status.setText("IN " + hours + " HOURS");
                                    holder.status.setTextColor(ContextCompat.getColor(context, R.color.green700));
                                } else if (hours == 0) {

                                    if (minutes != 1 || minutes != 0) {
                                        holder.status.setText("IN " + minutes + " MINUTES ");
                                        holder.status.setTextColor(ContextCompat.getColor(context, R.color.green500));

                                    } else {
                                        holder.status.setText("STARTING");
                                        holder.status.setTextColor(ContextCompat.getColor(context, R.color.lightblue500));
                                    }
                                } else {
                                    Log.e("TAG", "used this");
                                    holder.status.setText("EVENT OVER");
                                    holder.status.setTextColor(ContextCompat.getColor(context, R.color.red500));
                                }


                            } else if (days > 0) {
                                if (days == 1) {
                                    holder.status.setText("IN 1 DAY " + hours + " HOUR(s)");
                                    holder.status.setTextColor(ContextCompat.getColor(context, R.color.grey900));
                                } else {
                                    holder.status.setText("AFTER " + days + " DAYS");
                                    holder.status.setTextColor(ContextCompat.getColor(context, R.color.grey500));
                                }
                            }

                        } else if (datesDiff < 0 && endDiff >= 0 && endDiff != 0) {
                            holder.status.setText("ONGOING");
                            holder.status.setTextColor(ContextCompat.getColor(context, R.color.lightblue500));

                        } else {

                            holder.status.setText("EVENT OVER");
                            holder.status.setTextColor(ContextCompat.getColor(context, R.color.red500));
                        }


                    } else {
                        //end is null , but we have start date and time
                        //we can't show end date time or if event is ongoing
                        long days = datesDiff / (24 * 60 * 60 * 1000);
                        long hours = datesDiff / (60 * 60 * 1000) % 24;
                        long minutes = datesDiff / (60 * 1000) % 60;

                        if (datesDiff >= 0) {
                            if (days == 0) {
                                if (hours == 1) {
                                    holder.status.setText("STARTING IN THE NEXT HOUR");
                                    holder.status.setTextColor(ContextCompat.getColor(context, R.color.teal500));
                                } else if (hours > 1) {
                                    holder.status.setText("IN " + hours + " HOURS");
                                    holder.status.setTextColor(ContextCompat.getColor(context, R.color.green700));
                                } else if (hours == 0) {

                                    if (minutes != 1 || minutes != 0) {
                                        holder.status.setText("IN " + minutes + " MINUTES ");
                                        holder.status.setTextColor(ContextCompat.getColor(context, R.color.green500));

                                    } else {
                                        holder.status.setText("STARTING");
                                        holder.status.setTextColor(ContextCompat.getColor(context, R.color.lightblue500));
                                    }
                                } else {
                                    holder.status.setText("");
                                }

                            } else if (days > 0) {
                                if (days == 1) {
                                    holder.status.setText("IN 1 DAY " + hours + " HOUR(s)");
                                    holder.status.setTextColor(ContextCompat.getColor(context, R.color.grey900));
                                } else {
                                    holder.status.setText("AFTER " + days + " DAYS");
                                    holder.status.setTextColor(ContextCompat.getColor(context, R.color.grey500));
                                }
                            }

                        } else {
                            if (days > 1) {
                                holder.status.setText("EVENT MAY BE OVER");
                                holder.status.setTextColor(Color.YELLOW);
                            } else {
                                holder.status.setText("EVENT STATUS");
                            }
                        }
                    }
                } else {
                    //calculate only for date changes due to unavailability of start time
                    //needs work
                    holder.status.setText("EVENT STATUS");

                }

            } catch (ParseException e) {
                startDateText = eventItems.get(pos).getStartDate() + eventItems.get(pos).getStartTime();
                holder.status.setText("EVENT STATUS");
            }
            holder.datetime.setText(startDateText);

            holder.location.setText(eventItems.get(pos).getLocation());
            if (!eventItems.get(pos).getStartTime().equalsIgnoreCase("-")) {
                eventItems.get(pos).setsDate(date);


                holder.add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_EDIT);
                            intent.setType("vnd.android.cursor.item/event");
                            intent.putExtra("beginTime", eventItems.get(pos).getsDate());
                            if (end != null)
                                if (end.getTime() >= date.getTime())
                                    intent.putExtra("endTime", end
                                            .getTime());
                            if (end != null && end.getTime() - date.getTime() >= 0)
                                intent.putExtra("duration", end.getTime() - date.getTime());
                            intent.putExtra("title", eventItems.get(pos).getTitle());
                            intent.putExtra("description", eventItems.get(pos).getDesc());
                            intent.putExtra("eventLocation", eventItems.get(pos).getLocation());
                            context.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Snackbar.make(view, "No calendar found", Snackbar.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Sorry could not open calendar. ", Toast
                                    .LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                holder.add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "Start time is not known yet!", Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        } catch (Exception e) {
            holder.status.setText("check json");
        }
    }

    @Override
    public int getItemCount() {
        return eventItems.size();
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
