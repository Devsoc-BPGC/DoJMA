package com.csatimes.dojma.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.viewholders.UtilitiesViewHolder1;
import com.csatimes.dojma.viewholders.UtilitiesViewHolder2;
import com.csatimes.dojma.viewholders.UtilitiesViewHolder3;
import com.csatimes.dojma.viewholders.UtilitiesViewHolder4;
import com.csatimes.dojma.viewholders.UtilitiesViewHolder5;

/**
 * Created by Vikramaditya Kukreja on 21-07-2016.
 */

public class UtilitiesRV extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private String message;

    public UtilitiesRV(String message) {
    this.message = message;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case 0:
                View v1 = inflater.inflate(R.layout.viewholder_utilities_contact, parent, false);
                viewHolder = new UtilitiesViewHolder1(v1, parent.getContext());
                break;
            case 1:
                View v2 = inflater.inflate(R.layout.viewholder_utilities_mess_menu, parent, false);
                viewHolder = new UtilitiesViewHolder2(v2, parent.getContext());
                break;
            case 2:
                View v3 = inflater.inflate(R.layout.viewholder_utilities_links, parent, false);
                viewHolder = new UtilitiesViewHolder3(v3, parent.getContext());
                break;
            case 3:
                View v4 = inflater.inflate(R.layout.viewholder_utilities_misc, parent, false);
                viewHolder = new UtilitiesViewHolder4(v4);
                break;
            case 4:
                View v5 = inflater.inflate(R.layout.viewholder_utilities_map, parent, false);
                viewHolder = new UtilitiesViewHolder5(v5, parent.getContext());
                break;


        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 3) {
            UtilitiesViewHolder4 vh = (UtilitiesViewHolder4) holder;
            vh.text.setText(message);
        }
    }


    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;
        else if (position == 1)
            return 1;
        else if (position == 2)
            return 2;
        else if (position == 3)
            return 3;
        else return 4;
    }
}
