package com.csatimes.dojma.herald;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.HeraldItem;
import com.csatimes.dojma.utilities.Browser;
import com.csatimes.dojma.utilities.DHC;
import com.facebook.drawee.view.SimpleDraweeView;
import com.like.LikeButton;
import com.like.OnLikeListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;

import static android.content.Intent.EXTRA_TEXT;

/**
 * @author Rushikesh Jogdand.
 */
public class HeraldViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, OnLikeListener {

    private final Activity parentActivity;
    private final TextView titleTv;
    private final TextView dateTv;
    private final SimpleDraweeView heraldSdv;
    private final TextView descTv;
    private final LikeButton favLb;
    private HeraldItem item;

    HeraldViewHolder(final View itemView, final Activity parentActivity) {
        super(itemView);

        this.parentActivity = parentActivity;
        heraldSdv = itemView.findViewById(R.id.item_format_herald_image);
        dateTv = itemView.findViewById(R.id.item_format_herald_date);
        titleTv = itemView.findViewById(R.id.item_format_herald_title);
        descTv = itemView.findViewById(R.id.item_format_herald_desc);
        favLb = itemView.findViewById(R.id.item_format_herald_heart);
        final ImageButton shareIb = itemView.findViewById(R.id.item_format_herald_share);

        favLb.setOnLikeListener(this);
        itemView.setOnClickListener(this);
        shareIb.setOnClickListener(this);

    }

    @SuppressWarnings("FeatureEnvy")
    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.item_format_herald_share) {
            final Intent shareIntent = new Intent((Intent.ACTION_SEND));
            shareIntent.setType(DHC.MIME_TYPE_PLAINTEXT);
            shareIntent.putExtra(EXTRA_TEXT, item.getTitle_plain() + " at " + item.getUrl());
            parentActivity.startActivity(Intent.createChooser(shareIntent,
                    parentActivity.getString(R.string.share_prompt)));
            return;
        }
        new Browser(parentActivity).launchUrl(item.getUrl());
    }

    @Override
    public void liked(final LikeButton likeButton) {
        item.setFav(true);
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(item);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void unLiked(final LikeButton likeButton) {
        item.setFav(false);
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(item);
        realm.commitTransaction();
        realm.close();
    }

    @SuppressWarnings("FeatureEnvy")
    void populate(@NonNull final HeraldItem item) {
        this.item = item;
        dateTv.setText(item.getFormattedDate());
        heraldSdv.setImageURI(Uri.parse(item.getThumbnailUrl()));
        favLb.setLiked(item.isFav());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            descTv.setText(Html.fromHtml(item.getExcerpt(), Html.FROM_HTML_MODE_LEGACY));
            titleTv.setText(Html.fromHtml(item.getTitle(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            descTv.setText(Html.fromHtml(item.getExcerpt()));
            titleTv.setText(Html.fromHtml(item.getTitle()));
        }
    }
}
