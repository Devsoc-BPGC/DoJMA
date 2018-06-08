package com.csatimes.dojma.viewholders;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.csatimes.dojma.activities.UtilitiesMapsActivity;

/**
 * Created by Vikramaditya Kukreja on 22-07-2016.
 */

public class UtilitiesCampusMapViewHolder extends RecyclerView.ViewHolder {

    public UtilitiesCampusMapViewHolder(final View itemView, final Context context) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UtilitiesMapsActivity.class);
                context.startActivity(intent);

            }
        });
    }

}
