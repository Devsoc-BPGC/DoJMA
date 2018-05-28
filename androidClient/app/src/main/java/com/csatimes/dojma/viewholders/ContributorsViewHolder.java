package com.csatimes.dojma.viewholders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Browser;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.Contributor;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by vikramaditya on 24/2/17.
 */

public class ContributorsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView nameTv;
    public TextView emailTv;
    public TextView phoneTv;
    public SimpleDraweeView photoIv;
    public Activity activity;
    public Contributor contributor;
    public Context context ;

    public ContributorsViewHolder(View itemView, final Context context) {
        super(itemView);
        Fresco.initialize(context);
        nameTv = itemView.findViewById(R.id.dojma_contributor_name);
        emailTv = itemView.findViewById(R.id.dojma_contributor_email);
        phoneTv = itemView.findViewById(R.id.dojma_contributor_phone);
        photoIv = itemView.findViewById(R.id.dojma_contributor_image);
        itemView.setOnClickListener(this);
        this.context = context;
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
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(contributor.web));
        context.startActivity(websiteIntent);
    }
}
