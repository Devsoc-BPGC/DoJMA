package com.csatimes.dojma.viewholders;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.activities.UtilitiesArchivesActivity;
import com.csatimes.dojma.activities.UtilitiesContactsActivity;
import com.csatimes.dojma.activities.UtilitiesMenuActivity;

import androidx.recyclerview.widget.RecyclerView;

import static com.csatimes.dojma.utilities.DHC.ARCHIVES;
import static com.csatimes.dojma.utilities.DHC.CONTACTS_SHOW_TAXI_DATA;
import static com.csatimes.dojma.utilities.DHC.CONTACTS_TAXI;
import static com.csatimes.dojma.utilities.DHC.MESS;
import static com.csatimes.dojma.utilities.DHC.MISC;

/**
 * Created by Vikramaditya Kukreja on 21-07-2016.
 */

public class UtilitiesTitleSubTitleViewHolder extends RecyclerView.ViewHolder {

    public androidx.cardview.widget.CardView cardView;
    public TextView title;
    public TextView subTitle;

    public UtilitiesTitleSubTitleViewHolder(final View itemView, final Context context, final int classCode) {
        super(itemView);

        cardView = itemView.findViewById(R.id.viewholder_title_subtitle_cv);
        title = itemView.findViewById(R.id.viewholder_title_subtitle_title_tv);
        subTitle = itemView.findViewById(R.id.viewholder_title_subtitle_subtitle_tv);

        itemView.setOnClickListener(view -> {
            Intent intent = null;
            if (classCode == CONTACTS_TAXI) {
                intent = new Intent(context, UtilitiesContactsActivity.class);
                intent.putExtra(CONTACTS_SHOW_TAXI_DATA, true);
            } else if (classCode == MESS) {
                intent = new Intent(context, UtilitiesMenuActivity.class);
            } else if (classCode == MISC) {
                return;
            } else if (classCode == ARCHIVES) {
                intent = new Intent(context, UtilitiesArchivesActivity.class);
            }
            context.startActivity(intent);
        });
    }

}
