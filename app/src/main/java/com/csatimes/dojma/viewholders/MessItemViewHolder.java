package com.csatimes.dojma.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.utilities.CircleImageDrawable;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by vikramaditya on 22/12/16.
 */

public class MessItemViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public SimpleDraweeView image;

    public MessItemViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.item_format_mess_menu_title);
        image = (SimpleDraweeView) itemView.findViewById(R.id.item_format_mess_menu_image);
        image.getHierarchy().setProgressBarImage(new CircleImageDrawable());
    }
}
