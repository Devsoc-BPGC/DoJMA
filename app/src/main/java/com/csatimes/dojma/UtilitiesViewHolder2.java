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

public class UtilitiesViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
    Button a;
    Button c;

    String amessLink = "https://cdn.rawgit.com/MobileApplicationsClub/DoJMA-Assets-Repo/master/Images/Mess%20Menu/amess.jpg";
    String cmessLink = "https://cdn.rawgit.com/MobileApplicationsClub/DoJMA-Assets-Repo/master/Images/Mess%20Menu/cmess.jpg";

    Context context;

    public UtilitiesViewHolder2(View itemView, Context context) {
        super(itemView);
        a = (Button) itemView.findViewById(R.id.viewholder_mess_format_a);
        c = (Button) itemView.findViewById(R.id.viewholder_mess_format_c);

        this.context = context;

        a.setOnClickListener(this);
        c.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == a.getId()) {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(amessLink));
            context.startActivity(intent);

        } else if (id == c.getId()) {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(cmessLink));
            context.startActivity(intent);

        }
    }
}
