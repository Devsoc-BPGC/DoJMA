package com.csatimes.dojma.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.activities.UtilitiesArchivesActivity;
import com.csatimes.dojma.activities.UtilitiesContactsActivity;
import com.csatimes.dojma.activities.UtilitiesLinksActivity;
import com.csatimes.dojma.activities.UtilitiesMapsActivity;
import com.csatimes.dojma.activities.UtilitiesMenuActivity;
import com.csatimes.dojma.models.UtilitiesItem;
import java.util.List;

import static com.csatimes.dojma.utilities.DHC.CONTACTS_SHOW_TAXI_DATA;

/**
 * Created by Vikramaditya Kukreja on 21-07-2016.
 */

public class UtilitiesAdapter extends RecyclerView.Adapter<UtilitiesAdapter.ViewHolder> {

    private List<UtilitiesItem> utilitiesItems;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    public UtilitiesAdapter(Context context, List<UtilitiesItem> utilitiesItems) {
        this.mInflater = LayoutInflater.from(context);
        this.utilitiesItems = utilitiesItems;
        this.context = context;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_format_utilities, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UtilitiesItem utilitiesItem = utilitiesItems.get(position);
        holder.myTextView.setText(utilitiesItem.getUtility());
        holder.imageView.setImageDrawable(context.getDrawable(utilitiesItem.getIcon()));
        holder.relativeLayout.setOnClickListener(view -> {
            if (utilitiesItem.getType().equals("menu")) {
                Intent i = new Intent(context, UtilitiesMenuActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("outlet",utilitiesItem.getUtility());
                context.startActivity(i);
            }
            else if (utilitiesItem.getUtility().equals("Contacts")){
                Intent i = new Intent(context, UtilitiesContactsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
            else if (utilitiesItem.getUtility().equals("Taxi")){
                Intent i = new Intent(context, UtilitiesContactsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(CONTACTS_SHOW_TAXI_DATA,true);
                context.startActivity(i);
            }
            else if (utilitiesItem.getUtility().equals("Scooty Rentals")){
                Intent i = new Intent(context, UtilitiesContactsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
            else if (utilitiesItem.getUtility().equals("Car Rentals")){
                Intent i = new Intent(context, UtilitiesContactsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
            else if (utilitiesItem.getUtility().equals("Links")){
                Intent i = new Intent(context, UtilitiesLinksActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
            else if (utilitiesItem.getUtility().equals("Campus Map")){
                Intent i = new Intent(context, UtilitiesMapsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
            else if (utilitiesItem.getUtility().equals("Archives")){
                Intent i = new Intent(context, UtilitiesArchivesActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return utilitiesItems.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView myTextView;
        public ImageView imageView;
        public RelativeLayout relativeLayout;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.utility_text);
            imageView = itemView.findViewById(R.id.icon_utility);
            relativeLayout = itemView.findViewById(R.id.utility_item_relative);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
//    String getItem(int id) {
//        return mData[id];
//    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}