package com.csatimes.dojma.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.csatimes.dojma.UtilitiesMessMenu;

/**
 * Created by Vikramaditya Kukreja on 21-07-2016.
 */

public class UtilitiesViewHolder2 extends RecyclerView.ViewHolder {
    public UtilitiesViewHolder2(View itemView, final Context context) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UtilitiesMessMenu.class);
                context.startActivity(intent);
            }
        });
    }

}
