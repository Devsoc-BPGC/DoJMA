package com.csatimes.dojma.viewholders;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.LinkItem;
import com.csatimes.dojma.services.CopyLinkBroadcastReceiver;
import com.csatimes.dojma.utilities.CustomTabActivityHelper;
import com.csatimes.dojma.utilities.DHC;

/**
 * Created by vikramaditya on 14/12/16.
 */

public class LinkItemViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView url;
    public LinkItem linkItem;

    public LinkItemViewHolder(final View itemView, final Activity context) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.item_format_links_title);
        url = (TextView) itemView.findViewById(R.id.item_format_links_url);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    int colorResource;
                    try {
                        TypedValue typedValue = new TypedValue();
                        Resources.Theme theme = context.getTheme();
                        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
                        colorResource = typedValue.data;
                    } catch (Exception e) {
                        colorResource = Color.BLACK;
                    }

                    Intent intent = new Intent((Intent.ACTION_SEND));
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, linkItem.getUrl());

                    Intent copy_intent = new Intent(context, CopyLinkBroadcastReceiver.class);
                    PendingIntent copy_pendingIntent = PendingIntent.getBroadcast(context, 0, copy_intent, PendingIntent
                            .FLAG_UPDATE_CURRENT);
                    String copy_label = "Copy Link";


                    CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                            .setShowTitle(true)
                            .setToolbarColor(colorResource)
                            .setCloseButtonIcon(BitmapFactory.decodeResource(context.getResources(),
                                    R.drawable.ic_arrow_back_white_24dp))
                            .addMenuItem(copy_label, copy_pendingIntent)
                            .addDefaultShareMenuItem()
                            .enableUrlBarHiding()
                            .build();

                    CustomTabActivityHelper.openCustomTab(context, customTabsIntent,
                            Uri.parse(linkItem.getUrl()),
                            new CustomTabActivityHelper.CustomTabFallback() {
                                @Override
                                public void openUri(Activity activity, Uri uri) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                                        intent.putExtra(Intent.EXTRA_REFERRER,
                                                Uri.parse(Intent.URI_ANDROID_APP_SCHEME + "//" + context.getPackageName()));
                                    }

                                    context.startActivity(intent);
                                }
                            });
                } catch (Exception e) {
                    DHC.e("LinkItem", "Error while opening link " + e.getMessage());
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(linkItem.getUrl()));
                    try {
                        context.startActivity(intent);
                    } catch (ActivityNotFoundException e1) {
                        Snackbar snackbar = DHC.makeCustomSnackbar(itemView, "No app to view link!", ContextCompat.getColor(context, R.color.colorAccent), Color.WHITE);
                        snackbar.show();
                    }
                }
            }
        });
    }
}