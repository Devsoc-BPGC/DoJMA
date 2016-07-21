package com.csatimes.dojma;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

/**
 * Created by Vikramaditya Kukreja on 21-07-2016.
 */

public class UtilitiesViewHolder3 extends RecyclerView.ViewHolder implements View.OnClickListener {
    Button link1;
    Button link2;
    Context context;

    public UtilitiesViewHolder3(View itemView, Context context) {
        super(itemView);
        link1 = (Button) itemView.findViewById(R.id.viewholder_links_format_link1);
        link2 = (Button) itemView.findViewById(R.id.viewholder_links_format_link2);
        this.context = context;
        link1.setOnClickListener(this);
        link2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == link1.getId()) {

        } else if (view.getId() == link2.getId()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://swd.bits-goa.ac.in/"));
            context.startActivity(intent);

        }

    }
}
