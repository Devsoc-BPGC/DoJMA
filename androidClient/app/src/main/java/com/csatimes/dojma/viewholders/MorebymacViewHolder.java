package com.csatimes.dojma.viewholders;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.Contributor;
import com.csatimes.dojma.models.Morebymac;
import com.csatimes.dojma.services.CopyLinkBroadcastReceiver;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

public class MorebymacViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView text;
    public Activity activity;
    public Morebymac morebymac;
    public Context context ;

    Intent copy_intent = new Intent(context, CopyLinkBroadcastReceiver.class);
    PendingIntent copy_pendingIntent = PendingIntent.getBroadcast(context, 0, copy_intent, PendingIntent.FLAG_UPDATE_CURRENT);
    String copy_label = "Copy Link";

    int colorResource = getChromeCustomTabColorFromTheme();
    final CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setToolbarColor(colorResource)
            .setCloseButtonIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_arrow_back_white_24dp))
            .addMenuItem(copy_label, copy_pendingIntent)
            .addDefaultShareMenuItem()
            .enableUrlBarHiding()
            .build();

    public MorebymacViewHolder(View itemView, final Context context) {
        super(itemView);
        text = itemView.findViewById(R.id.viewholder_simple_text_textview);
        this.context = context;
        itemView.setOnClickListener(this);

    }

    public void populate(Morebymac morebymac) {
        this.morebymac = morebymac;
        text.setText(morebymac.name);
    }

    @Override
    public void onClick(View view) {
        customTabsIntent.launchUrl(context, Uri.parse(morebymac.web));
    }

    private int getChromeCustomTabColorFromTheme() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
}
