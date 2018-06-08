package com.csatimes.dojma.viewholders;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

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
import com.csatimes.dojma.services.CopyLinkBroadcastReceiver;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by vikramaditya on 24/2/17.
 */

public class ContributorsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView nameTv;
    public TextView emailTv;
    public TextView phoneTv;
    public SimpleDraweeView photoIv;
    public Contributor contributor;
    public Context context;

    public ContributorsViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        Fresco.initialize(context);
        nameTv = itemView.findViewById(R.id.dojma_contributor_name);
        emailTv = itemView.findViewById(R.id.dojma_contributor_email);
        phoneTv = itemView.findViewById(R.id.dojma_contributor_phone);
        photoIv = itemView.findViewById(R.id.dojma_contributor_image);
        itemView.setOnClickListener(this);
    }

    public void populate(Contributor contributor) {
        this.contributor = contributor;
        nameTv.setText(contributor.name);
        emailTv.setText(contributor.email);
        phoneTv.setText(contributor.phone);
        photoIv.setImageURI(Uri.parse(contributor.photoUrl));
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
        customTabsIntent.launchUrl(context, Uri.parse(contributor.web));
    }

    private int getChromeCustomTabColorFromTheme() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
}
