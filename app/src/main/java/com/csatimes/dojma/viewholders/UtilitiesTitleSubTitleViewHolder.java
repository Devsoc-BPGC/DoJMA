package com.csatimes.dojma.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.activities.UtilitiesContactsActivity;
import com.csatimes.dojma.activities.UtilitiesMenuActivity;

import static com.csatimes.dojma.utilities.DHC.CONTACTS_SHOW_TAXI_DATA;
import static com.csatimes.dojma.utilities.DHC.UTILITIES_ITEM_TYPE_CONTACTS_TAXI;
import static com.csatimes.dojma.utilities.DHC.UTILITIES_ITEM_TYPE_MESS;
import static com.csatimes.dojma.utilities.DHC.UTILITIES_ITEM_TYPE_MISC;

/**
 * Created by Vikramaditya Kukreja on 21-07-2016.
 */

public class UtilitiesTitleSubTitleViewHolder extends RecyclerView.ViewHolder {

    public CardView cardView;
    public TextView title;
    public TextView subTitle;

    public UtilitiesTitleSubTitleViewHolder(final View itemView, final Context context, final int classCode) {
        super(itemView);

        cardView = (CardView) itemView.findViewById(R.id.viewholder_title_subtitle_cv);
        title = (TextView) itemView.findViewById(R.id.viewholder_title_subtitle_title_tv);
        subTitle = (TextView) itemView.findViewById(R.id.viewholder_title_subtitle_subtitle_tv);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                if (classCode == UTILITIES_ITEM_TYPE_CONTACTS_TAXI) {
                    intent = new Intent(context, UtilitiesContactsActivity.class);
                    intent.putExtra(CONTACTS_SHOW_TAXI_DATA, true);
                } else if (classCode == UTILITIES_ITEM_TYPE_MESS)
                    intent = new Intent(context, UtilitiesMenuActivity.class);
                else if (classCode == UTILITIES_ITEM_TYPE_MISC)
                    intent = new Intent(context, UtilitiesMenuActivity.class);
                context.startActivity(intent);
            }
        });
    }

}
