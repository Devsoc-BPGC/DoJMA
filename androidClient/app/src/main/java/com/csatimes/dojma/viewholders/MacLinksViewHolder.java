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
import com.csatimes.dojma.models.Maclinks;
import com.csatimes.dojma.models.Morebymac;
import com.csatimes.dojma.services.CopyLinkBroadcastReceiver;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

public class MacLinksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView linkTitle;
    public SimpleDraweeView iconIv;
    public Activity activity;
    public Maclinks maclinks;
    public Context context ;


    public MacLinksViewHolder(View itemView) {
        super(itemView);
        context=itemView.getContext();
        Fresco.initialize(context);
        linkTitle=itemView.findViewById(R.id.dojma_maclinks_name);
        iconIv=itemView.findViewById(R.id.dojma_maclinks_image);
        itemView.setOnClickListener(this);
    }

    public void populate(Maclinks maclinks) {
        this.maclinks = maclinks;
        linkTitle.setText(maclinks.linkTitle);
        iconIv.setImageURI(Uri.parse(maclinks.macLink));
    }

    @Override
    public void onClick(View view) {
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
        customTabsIntent.launchUrl(context, Uri.parse(maclinks.macLink));
    }

    private int getChromeCustomTabColorFromTheme() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
}
