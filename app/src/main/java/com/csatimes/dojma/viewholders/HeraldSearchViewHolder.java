package com.csatimes.dojma.viewholders;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.activities.OfflineSimpleViewerActivity;
import com.csatimes.dojma.models.HeraldItem;
import com.csatimes.dojma.services.CopyLinkBroadcastReceiver;
import com.csatimes.dojma.utilities.CustomTabActivityHelper;
import com.csatimes.dojma.utilities.DHC;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Herald item in Searchable activity
 */

public class HeraldSearchViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView date;
    public SimpleDraweeView simpleDraweeView;
    public HeraldItem item;
    public Context context;

    public HeraldSearchViewHolder(View itemView, final Context context, final Activity activity) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.search_herald_title);
        date = (TextView) itemView.findViewById(R.id.search_herald_date);
        simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.search_herald_image);
        this.context = context;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (DHC.isOnline(context)) {
                    try {
                        Intent intent = new Intent((Intent.ACTION_SEND));
                        intent.putExtra(android.content.Intent.EXTRA_TEXT, item.getUrl());

                        Intent copy_intent = new Intent(context, CopyLinkBroadcastReceiver.class);
                        PendingIntent copy_pendingIntent = PendingIntent.getBroadcast(context, 0, copy_intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        String copy_label = "Copy Link";

                        int colorResource = getChromeCustomTabColorFromTheme();

                        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                                .setShowTitle(true)
                                .setToolbarColor(colorResource)
                                .setCloseButtonIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_arrow_back_white_24dp))
                                .addMenuItem(copy_label, copy_pendingIntent)
                                .addDefaultShareMenuItem()
                                .enableUrlBarHiding()
                                .build();

                        CustomTabActivityHelper.openCustomTab(activity, customTabsIntent,
                                Uri.parse(item.getUrl()),
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
                        DHC.e("TAG", "Error while opening link " + e.getMessage());
                        e.printStackTrace();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(item.getUrl()));
                        context.startActivity(intent);
                    }

                } else {
                    Intent intent = new Intent(context, OfflineSimpleViewerActivity.class);
                    intent.putExtra("POSTID", item.getPostID());
                    context.startActivity(intent);
                }
            }
        });

    }
    private int getChromeCustomTabColorFromTheme() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
}
