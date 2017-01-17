package com.csatimes.dojma.viewholders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.csatimes.dojma.R;
import com.csatimes.dojma.activities.UtilitiesContactsActivity;

/**
 * Created by Vikramaditya Kukreja on 21-07-2016.
 */

public class UtilitiesViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageButton call1, call2, call3, call4;
    private Context context;
    private String[] contactNumbers = {"9552040123", "8326482016", "0832-2580720", "0832-2580324"};

    public UtilitiesViewHolder1(View itemView, Context context) {
        super(itemView);
        call1 = (ImageButton) itemView.findViewById(R.id.viewholder_contact_format_call1);
        call2 = (ImageButton) itemView.findViewById(R.id.viewholder_contact_format_call2);
        call3 = (ImageButton) itemView.findViewById(R.id.viewholder_contact_format_call3);
        call4 = (ImageButton) itemView.findViewById(R.id.viewholder_contact_format_call4);

        this.context = context;
        call1.setOnClickListener(this);
        call2.setOnClickListener(this);
        call3.setOnClickListener(this);
        call4.setOnClickListener(this);
        itemView.setOnClickListener(this);
    }

    public ImageButton getCall1() {
        return call1;
    }

    public void setCall1(ImageButton call1) {
        this.call1 = call1;
    }

    public ImageButton getCall2() {
        return call2;
    }

    public void setCall2(ImageButton call2) {
        this.call2 = call2;
    }

    public ImageButton getCall3() {
        return call3;
    }

    public void setCall3(ImageButton call3) {
        this.call3 = call3;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == call1.getId()) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + contactNumbers[0]));
            context.startActivity(intent);

        } else if (id == call2.getId()) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + contactNumbers[1]));
            context.startActivity(intent);

        } else if (id == call3.getId()) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + contactNumbers[2]));
            context.startActivity(intent);

        } else if (id == call4.getId()) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + contactNumbers[3]));
            context.startActivity(intent);

        } else if (id == itemView.getId()) {
            try {
                Intent intent = new Intent(context, UtilitiesContactsActivity.class);
                context.startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
