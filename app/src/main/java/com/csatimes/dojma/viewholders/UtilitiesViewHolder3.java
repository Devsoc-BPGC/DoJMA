package com.csatimes.dojma.viewholders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.csatimes.dojma.R;
import com.csatimes.dojma.activities.UtilitiesLinksActivity;

/**
 * Created by Vikramaditya Kukreja on 21-07-2016.
 */

public class UtilitiesViewHolder3 extends RecyclerView.ViewHolder implements View.OnClickListener {
    Button link1;
    Button link2;
    Button link3;
    Button link4;
    Button link5;

    Context context;

    public UtilitiesViewHolder3(View itemView, Context context) {
        super(itemView);
        link1 = (Button) itemView.findViewById(R.id.viewholder_links_format_link1);
        link2 = (Button) itemView.findViewById(R.id.viewholder_links_format_link2);
        link3 = (Button) itemView.findViewById(R.id.viewholder_links_format_link3);
        link4 = (Button) itemView.findViewById(R.id.viewholder_links_format_link4);
        link5 = (Button) itemView.findViewById(R.id.viewholder_links_format_link5);
        this.context = context;
        link1.setOnClickListener(this);
        link2.setOnClickListener(this);
        link3.setOnClickListener(this);
        link4.setOnClickListener(this);
        link5.setOnClickListener(this);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == link1.getId()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://10.1.1.242/lms/"));
            context.startActivity(intent);
        } else if (view.getId() == link2.getId()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://swd.bits-goa.ac.in/"));
            context.startActivity(intent);

        } else if (view.getId() == link3.getId()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://csatimes.co.in"));
            context.startActivity(intent);

        } else if (view.getId() == link4.getId()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bits-pilani.ac.in/Goa/"));
            context.startActivity(intent);

        } else if (view.getId() == link5.getId()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bits-pilani.ac.in/goa/login"));
            context.startActivity(intent);

        } else if (view.getId() == itemView.getId()) {
            Intent intent = new Intent(context, UtilitiesLinksActivity.class);
            context.startActivity(intent);

        }

    }
}
