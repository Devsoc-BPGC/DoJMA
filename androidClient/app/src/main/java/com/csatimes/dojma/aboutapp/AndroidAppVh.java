package com.csatimes.dojma.aboutapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.AndroidApp;
import com.csatimes.dojma.utilities.Browser;
import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Rushikesh Jogdand.
 */
public class AndroidAppVh extends RecyclerView.ViewHolder {
    private final MaterialButton btn;
    private final Browser browser;

    public AndroidAppVh(@NonNull final View itemView, @NonNull final Browser browser) {
        super(itemView);
        this.browser = browser;
        btn = itemView.findViewById(R.id.btn_android_app);
    }

    public void populate(@NonNull final AndroidApp app) {
        btn.setText(app.name);
        btn.setOnClickListener(view -> {
            try {
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse("market://details?id=" + app.id)));
            } catch (final ActivityNotFoundException e) {
                browser.launchUrl("http://play.google.com/store/apps/details?id=" + app.id);
            }
        });
    }
}
