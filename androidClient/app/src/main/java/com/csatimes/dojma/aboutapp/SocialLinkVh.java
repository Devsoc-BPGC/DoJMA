package com.csatimes.dojma.aboutapp;

import android.view.View;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.SocialLink;
import com.csatimes.dojma.utilities.Browser;
import com.facebook.drawee.view.SimpleDraweeView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Rushikesh Jogdand.
 */
public class SocialLinkVh extends RecyclerView.ViewHolder {
    private final SimpleDraweeView thumbSdv;
    private final Browser browser;

    public SocialLinkVh(@NonNull final View itemView,
                        @NonNull final Browser browser) {
        super(itemView);
        this.browser = browser;
        thumbSdv = itemView.findViewById(R.id.sdv_social);
    }

    public void populate(@NonNull final SocialLink socialLink) {
        thumbSdv.setImageURI(socialLink.thumbImageUrl);
        thumbSdv.setOnClickListener(view -> browser.launchUrl(socialLink.url));
    }
}
