package com.csatimes.dojma.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.viewholders.UtilitiesCampusMapViewHolder;
import com.csatimes.dojma.viewholders.UtilitiesContactsViewHolder;
import com.csatimes.dojma.viewholders.UtilitiesLinksViewHolder;
import com.csatimes.dojma.viewholders.UtilitiesTitleSubTitleViewHolder;

import static com.csatimes.dojma.utilities.DHC.ARCHIVES;
import static com.csatimes.dojma.utilities.DHC.CONTACTS;
import static com.csatimes.dojma.utilities.DHC.CONTACTS_TAXI;
import static com.csatimes.dojma.utilities.DHC.LINKS;
import static com.csatimes.dojma.utilities.DHC.UTILITIES_ITEM_TYPE_MAP;
import static com.csatimes.dojma.utilities.DHC.MESS;
import static com.csatimes.dojma.utilities.DHC.MISC;

/**
 * Created by Vikramaditya Kukreja on 21-07-2016.
 */

public class UtilitiesAdapter extends RecyclerView.Adapter<UtilitiesAdapter.ViewHolder> {

    private String[] mData = new String[0];
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public UtilitiesAdapter(Context context, String[] data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
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
        holder.myTextView.setText(mData[position]);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.info_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData[id];
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}